package pl.agh.edu.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.agh.edu.library.dto.BookDto;
import pl.agh.edu.library.security.JwtFilter;
import pl.agh.edu.library.security.JwtUtil;
import pl.agh.edu.library.service.BookService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private JwtFilter jwtFilter;

    private BookDto testBookDto;

    @BeforeEach
    void setUp() {
        testBookDto = BookDto.builder()
                .id(1)
                .name("Test Book")
                .author("Test Author")
                .quantity(5)
                .build();
    }

    @Test
    void getBooks_ReturnsListOfBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(Arrays.asList(testBookDto));

        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Book"))
                .andExpect(jsonPath("$[0].author").value("Test Author"));
    }

    @Test
    void getBook_WhenExists_ReturnsBook() throws Exception {
        when(bookService.getBook(1L)).thenReturn(Optional.of(testBookDto));

        mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Book"));
    }

    @Test
    void getBook_WhenDoesNotExist_ReturnsNotFound() throws Exception {
        when(bookService.getBook(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBook_SavesBook() throws Exception {
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"Test Book\",\"author\":\"Test Author\",\"quantity\":5}"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).addBook(any(BookDto.class));
    }

    @Test
    void deleteBook_CallsServiceDelete() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(1L);
    }
}