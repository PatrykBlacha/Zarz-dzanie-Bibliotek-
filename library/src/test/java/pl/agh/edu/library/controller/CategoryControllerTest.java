package pl.agh.edu.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.agh.edu.library.dto.CategoryDto;
import pl.agh.edu.library.security.JwtFilter;
import pl.agh.edu.library.security.JwtUtil;
import pl.agh.edu.library.service.CategoryService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private JwtFilter jwtFilter;

    private CategoryDto testCategoryDto;

    @BeforeEach
    void setUp() {
        testCategoryDto = new CategoryDto(1, "Science Fiction");
    }

    @Test
    void getCategories_ReturnsListOfCategories() throws Exception {
        when(categoryService.getCategories()).thenReturn(Arrays.asList(testCategoryDto));

        mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Science Fiction"));
    }

    @Test
    void addCategory_SavesCategory() throws Exception {
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"Science Fiction\"}"))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).addCategory(any(CategoryDto.class));
    }

    @Test
    void deleteCategory_CallsServiceDelete() throws Exception {
        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(1L);
    }
}