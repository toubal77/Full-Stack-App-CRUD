package categorie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import categorie.entities.Category;
import categorie.repositories.CategoryRepository;
import categorie.reponses.ApiResponse;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<Object> getCategoryById(Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(new ApiResponse("ID invalide", false));
        }

        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Catégorie non trouvée", false));
        }
    }
    
    public ResponseEntity<ApiResponse> updateCategory(Long id, Category updatedCategory) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(new ApiResponse("ID invalide", false));
        }

        return categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(updatedCategory.getName());
            existingCategory.setParent(updatedCategory.getParent());
            existingCategory.setIfRacine(updatedCategory.isIfRacine());
            existingCategory.setChildrens(updatedCategory.getChildren());
            categoryRepository.save(existingCategory);
            return ResponseEntity.ok(new ApiResponse("Catégorie mise à jour avec succès", true));
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Catégorie non trouvée pour la mise à jour", false)));
    }

    public ResponseEntity<ApiResponse> createCategory(Category category) {
        if (category == null || category.getName() == null || category.getName().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Données de catégorie invalides", false));
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