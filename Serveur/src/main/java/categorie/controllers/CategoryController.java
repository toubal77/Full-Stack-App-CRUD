package categorie.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> filterCategories(
            @RequestParam(required = false) boolean isRacine,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        try {
            List<Category> categories;

            if (startDate == null && endDate == null) {
                categories = categoryRepository.findByIsRacine(isRacine);
            } else if (startDate != null && endDate == null) {
                categories = categoryRepository.findByCreationDateAfter(startDate);
            } else if (endDate != null && startDate == null) {
                categories = categoryRepository.findByCreationDateBefore(endDate);
            } else if (startDate != null && endDate != null) {
                categories = categoryRepository.findByCreationDateBetween(startDate, endDate);
            } else {
                categories = categoryRepository.findAll();
            }
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Erreur lors du filtrage des catégories", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erreur lors du filtrage des catégories"+ e.toString(), false));
        }
    }

}