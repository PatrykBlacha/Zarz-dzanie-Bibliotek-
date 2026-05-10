package pl.agh.edu.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.agh.edu.library.dto.BookDto;
import pl.agh.edu.library.model.Book;
import pl.agh.edu.library.model.Category;
import pl.agh.edu.library.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private BookDto testBookDto;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1);
        testBook.setName("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setQuantity(5);

        testBookDto = BookDto.builder()
                .id(1)
                .name("Test Book")
                .author("Test Author")
                .quantity(5)
                .build();
    }

    @Test
    void getBooks_ReturnsListOfBookDtos() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(testBook));

        List<BookDto> result = bookService.getBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getName());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void addBook_SavesMappedBookEntity() {
        bookService.addBook(testBookDto);

        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository, times(1)).save(bookCaptor.capture());
        
        Book savedBook = bookCaptor.getValue();
        assertEquals("Test Book", savedBook.getName());
        assertEquals("Test Author", savedBook.getAuthor());
        assertEquals(5, savedBook.getQuantity());
    }

    @Test
    void getBook_WhenExists_ReturnsBookDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        Optional<BookDto> result = bookService.getBook(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getName());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBook_WhenDoesNotExist_ReturnsEmpty() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<BookDto> result = bookService.getBook(1L);

        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBook_CallsRepositoryDelete() {
        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void addCategoryToBook_WhenBothExist_AddsCategoryAndSaves() {
        Category category = new Category();
        category.setId(1);
        category.setName("Fantasy");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(categoryService.getCategory(1L)).thenReturn(Optional.of(category));

        bookService.addCategoryToBook(1L, 1L);

        assertTrue(testBook.getCategories().contains(category));
        verify(bookRepository, times(1)).save(testBook);
    }
}