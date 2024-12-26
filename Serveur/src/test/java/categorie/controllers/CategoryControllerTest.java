package categorie.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import categorie.DTO.UpdateChildrenRequest;
import categorie.entities.Category;
import categorie.reponses.ApiResponse;
import categorie.services.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testGetAllCategories() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testGetCategoryById() throws Exception {
        Long categoryId = 1L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(get("/api/categories/{id}", categoryId))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    public void testCreateCategory() throws Exception {
        when(categoryService.createCategory(any(Category.class)))
                .thenReturn(new ResponseEntity<>(new ApiResponse("Créé avec succès", true), HttpStatus.CREATED));

        mockMvc.perform(post("/api/categories")
                .contentType("application/json")
                .content("{\"name\":\"Test Category\"}"))
                .andExpect(status().isCreated());

        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Long categoryId = 1L;
        when(categoryService.updateCategory(eq(categoryId), any(Category.class)))
                .thenReturn(new ResponseEntity<>(new ApiResponse("Mis à jour avec succès", true), HttpStatus.OK));

        mockMvc.perform(put("/api/categories/{id}", categoryId)
                .contentType("application/json")
                .content("{\"name\":\"Updated Category\"}"))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).updateCategory(eq(categoryId), any(Category.class));
    }

    @Test
    public void testUpdateCategoryChildren() throws Exception {
        when(categoryService.updateCategoryChildren(any(UpdateChildrenRequest.class)))
                .thenReturn(
                        new ResponseEntity<>(new ApiResponse("Enfants mis à jour avec succès", true), HttpStatus.OK));

        mockMvc.perform(put("/api/categories/update-children")
                .contentType("application/json")
                .content("{\"childrenIds\":[1,2,3]}"))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).updateCategoryChildren(any(UpdateChildrenRequest.class));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        Long categoryId = 1L;
        when(categoryService.deleteCategory(categoryId))
                .thenReturn(new ResponseEntity<>(new ApiResponse("Supprimé avec succès", true), HttpStatus.OK));

        mockMvc.perform(delete("/api/categories/{id}", categoryId))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(categoryId);
    }
}
