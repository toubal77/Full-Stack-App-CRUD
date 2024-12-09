package categorie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import categorie.entities.Category;
import categorie.DTO.ParentDTO;
import categorie.DTO.ResponseDTO;
import categorie.DTO.UpdateChildrenRequest;
import categorie.repositories.CategoryRepository;
import categorie.reponses.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<Object> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        
        if (categories.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("Aucune catégorie trouvée", false));
        }
        
        List<ResponseDTO> responseDTOs = new ArrayList<>();

        for (Category category : categories) {
            ParentDTO parentDTO = null;
            if (category.getParent() != null) {
                Category parent = category.getParent();
                parentDTO = new ParentDTO(parent.getId(), parent.getName(), parent.getCreationDate(), parent.isIfRacine());
            }
            
            List<Category> childrens = category.getChildren();

            ResponseDTO responseDTO = new ResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getCreationDate(),
                    category.isIfRacine(),
                    parentDTO, 
                    childrens
                );

            responseDTOs.add(responseDTO);
        }

        return ResponseEntity.ok(responseDTOs);
    }

    public ResponseEntity<Object> getCategoryById(Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(new ApiResponse("ID invalide", false));
        }

        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Catégorie parent non trouvée", false));
        }
        Category category = categoryOpt.get();

        ParentDTO parentDTO = null;
        if (category.getParent() != null) {
            Category parent = category.getParent();
            parentDTO = new ParentDTO(parent.getId(), parent.getName(), parent.getCreationDate(), parent.isIfRacine());
        }
        
        List<Category> childrens = category.getChildren();

        ResponseDTO responseDTO = new ResponseDTO(
            category.getId(),
            category.getName(),
            category.getCreationDate(),
            category.isIfRacine(),
            parentDTO, 
            childrens
        );

        return ResponseEntity.ok(responseDTO);
    }
    
    public ResponseEntity<ApiResponse> updateCategory(Long id, Category updatedCategory) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(new ApiResponse("ID invalide", false));
        }

        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Catégorie non trouvée", false));
        }

        Category existingCategory = category.get();
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setIfRacine(updatedCategory.isIfRacine());

        if (updatedCategory.getParent() != null && updatedCategory.getParent().getId() != null) {
            Optional<Category> parent = categoryRepository.findById(updatedCategory.getParent().getId());
            if (parent.isPresent()) {
                existingCategory.setParent(parent.get());
                existingCategory.setIfRacine(false);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse("Parent non trouvé", false));
            }
        } else {
            existingCategory.setParent(null);
        }

        categoryRepository.save(existingCategory);
        return ResponseEntity.ok(new ApiResponse("Catégorie mise à jour avec succès : " + existingCategory.getName(), true));
    }

    public ResponseEntity<ApiResponse> updateCategoryChildren(UpdateChildrenRequest request) {
        Category parentCategory = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Catégorie parent non trouvée"));

        if (request.getChildrens() == null || request.getChildrens().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse("La liste des enfants est vide ou non définie.", false));
        }

        try {
            for (Long childId : request.getChildrens()) {
                Category childCategory = categoryRepository.findById(childId)
                        .orElseThrow(() -> new RuntimeException("Catégorie enfant non trouvée"));

                childCategory.setParent(parentCategory);
                childCategory.setIfRacine(false);
                categoryRepository.save(childCategory);
            }
            
            for (Long childId : request.getChildrensRemoved()) {
                Category childCategory = categoryRepository.findById(childId)
                        .orElseThrow(() -> new RuntimeException("Catégorie enfant non trouvée"));

                childCategory.setParent(null);
                childCategory.setIfRacine(true);
                categoryRepository.save(childCategory);
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Catégorie mise à jour avec succès : " + parentCategory.getName(), true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Echec de la mise à jour", false));
        }
    }

    public ResponseEntity<ApiResponse> createCategory(Category category) {
        if (category == null || category.getName() == null || category.getName().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Données de catégorie invalides", false));
        }

        if (category.getParent() != null) {
            Optional<Category> parentCategoryOpt = categoryRepository.findById(category.getParent().getId());
            if (parentCategoryOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("Catégorie parent non trouvée", false));
            }
            category.setParent(parentCategoryOpt.get());
        }

        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Catégorie créée avec succès : " + savedCategory.getName(), true));
    }
    
    public ResponseEntity<ApiResponse> deleteCategory(Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(new ApiResponse("ID invalide", false));
        }

        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Catégorie supprimée avec succès", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Catégorie non trouvée pour suppression", false));
        }
    }
}