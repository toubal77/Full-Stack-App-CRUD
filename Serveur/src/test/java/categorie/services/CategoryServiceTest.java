package categorie.services;

import categorie.DTO.ResponseDTO;
import categorie.DTO.UpdateChildrenRequest;
import categorie.entities.Category;
import categorie.reponses.ApiResponse;
import categorie.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category parentCategory;
    private Category childCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parentCategory = new Category("Parent", null, true);
        parentCategory.setId(1L);
        childCategory = new Category("Child", parentCategory, false);
        childCategory.setId(2L);
        parentCategory.setChildren(childCategory);

        if (parentCategory.getNbrChildrends() == null) {
            parentCategory.setNbrChildrends(0);
        }

        Date now = new Date();
        parentCategory.setCreationDate(now);
        childCategory.setCreationDate(now);
    }

    @Test
    void testGetAllCategories_NoCategories() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<Object> response = categoryService.getAllCategories();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Aucune catégorie trouvée", ((ApiResponse) response.getBody()).getMessage());
    }

    @Test
    void testGetAllCategories_WithCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(parentCategory));

        ResponseEntity<Object> response = categoryService.getAllCategories();
        assertEquals(200, response.getStatusCodeValue());

        List<ResponseDTO> responseDTOs = (List<ResponseDTO>) response.getBody();
        assertEquals(1, responseDTOs.size());
        assertEquals("Parent", responseDTOs.get(0).getName());
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = categoryService.getCategoryById(1L);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Catégorie parent non trouvée", ((ApiResponse) response.getBody()).getMessage());
    }

    @Test
    void testGetCategoryById_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(parentCategory));

        ResponseEntity<Object> response = categoryService.getCategoryById(1L);
        assertEquals(200, response.getStatusCodeValue());

        ResponseDTO responseDTO = (ResponseDTO) response.getBody();
        assertEquals("Parent", responseDTO.getName());
    }

    @Test
    void testCreateCategory_Success() {
        when(categoryRepository.save(any(Category.class))).thenReturn(parentCategory);

        ResponseEntity<ApiResponse> response = categoryService.createCategory(parentCategory);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Catégorie créée avec succès : Parent", response.getBody().getMessage());
    }

    @Test
    void testCreateCategory_InvalidData() {
        Category invalidCategory = new Category();
        ResponseEntity<ApiResponse> response = categoryService.createCategory(invalidCategory);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Données de catégorie invalides", response.getBody().getMessage());
    }

    @Test
    void testUpdateCategory_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = categoryService.updateCategory(1L, new Category());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Catégorie non trouvée", response.getBody().getMessage());
    }

    @Test
    void testDeleteCategory_Success() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<ApiResponse> response = categoryService.deleteCategory(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Catégorie supprimée avec succès", response.getBody().getMessage());
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<ApiResponse> response = categoryService.deleteCategory(1L);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Catégorie non trouvée pour suppression", response.getBody().getMessage());
    }

    @Test
    void testUpdateCategoryChildren_Success() {
        UpdateChildrenRequest request = new UpdateChildrenRequest();
        request.setId(parentCategory.getId());
        request.setChildrens(List.of(childCategory.getId()));
        when(categoryRepository.findById(parentCategory.getId())).thenReturn(Optional.of(parentCategory));
        when(categoryRepository.findById(childCategory.getId())).thenReturn(Optional.of(childCategory));

        ResponseEntity<ApiResponse> response = categoryService.updateCategoryChildren(request);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Catégorie mise à jour avec succès : Parent", response.getBody().getMessage());
    }
}
