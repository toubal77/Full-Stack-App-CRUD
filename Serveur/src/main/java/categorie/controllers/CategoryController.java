package categorie.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import categorie.DTO.UpdateChildrenRequest;
import categorie.entities.Category;
import categorie.reponses.ApiResponse; 
import categorie.services.CategoryService;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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

    @PutMapping("/update-children")
    public ResponseEntity<ApiResponse> updateCategoryChildren(@RequestBody UpdateChildrenRequest request) {
        return categoryService.updateCategoryChildren(request);    
    }
}