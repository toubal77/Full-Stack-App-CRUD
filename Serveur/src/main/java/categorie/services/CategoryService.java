package categorie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import categorie.entities.Category;
import categorie.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
    public Optional<Category> updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(updatedCategory.getName());
            existingCategory.setParent(updatedCategory.getParent());
            existingCategory.setIfRacine(updatedCategory.isIfRacine());
            existingCategory.setChildrens(updatedCategory.getChildren());
            return categoryRepository.save(existingCategory);
        });
    }


    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}