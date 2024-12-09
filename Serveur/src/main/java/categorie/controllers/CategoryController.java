package categorie.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import categorie.DTO.ChildDTO;
import categorie.DTO.ParentDTO;
import categorie.DTO.ResponseDTO;
import categorie.DTO.UpdateChildrenRequest;
import categorie.entities.Category;
import categorie.reponses.ApiResponse;
import categorie.repositories.CategoryRepository;
import categorie.services.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryService.updateCategory(id, updatedCategory);
    }

    @PutMapping("/update-children")
    public ResponseEntity<ApiResponse> updateCategoryChildren(@RequestBody UpdateChildrenRequest request) {
        return categoryService.updateCategoryChildren(request);    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> filterCategories(
            @RequestParam(required = false) Boolean isRacine,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(required = false) Integer childCount) {

        try {
            List<Category> categories;

            if (startDate == null && endDate == null) {
            	if (isRacine == null) {
                    // Si isRacine est indéfini, recherchez uniquement par nbrChildrens
                    categories = categoryRepository.findByNbrChildrens(childCount);
                } else {
                    // Sinon, recherchez par isRacine et childCount
                    categories = categoryRepository.findByIsRacine(isRacine, childCount);
                }
            } else if (startDate != null && endDate == null) {
                categories = categoryRepository.findByCreationDateAfter(startDate, childCount);
            } else if (endDate != null && startDate == null) {
                categories = categoryRepository.findByCreationDateBefore(endDate, childCount);
            } else if (startDate != null && endDate != null) {
                categories = categoryRepository.findByCreationDateBetween(startDate, endDate, childCount);
            } else {
                categories = categoryRepository.findByFilters(isRacine, startDate, endDate, childCount);
            }
            
            List<ResponseDTO> responseDTOs = new ArrayList<>();

            for (Category category : categories) {
                ParentDTO parentDTO = null;
                if (category.getParent() != null) {
                    Category parent = category.getParent();
                    parentDTO = new ParentDTO(parent.getId(), parent.getName(), parent.getCreationDate(), parent.isIfRacine(), parent.getNbrChildrends());
                }
                
                List<Category> childrens = category.getChildren();
                List<ChildDTO> childDTOs = new ArrayList<>();

                for (Category child : childrens) {
                    ChildDTO childDTO = new ChildDTO(
                        child.getId(),
                        child.getName(),
                        child.getCreationDate(),
                        child.isIfRacine(),
                        child.getNbrChildrends()
                    );
                    childDTOs.add(childDTO);
                }
                
                ResponseDTO responseDTO = new ResponseDTO(
                	    category.getId(),
                	    category.getName(),
                	    category.getCreationDate(),
                	    category.isIfRacine(),
                	    parentDTO,
                	    childDTOs,
                	    category.getNbrChildrends()
                	);

                responseDTOs.add(responseDTO);
            }
            
            return ResponseEntity.ok(responseDTOs);
        } catch (Exception e) {
            logger.error("Erreur lors du filtrage des catégories", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors du filtrage des catégories"+ e.toString(), false));
        }
    }

}
