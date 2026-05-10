package pl.agh.edu.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.library.dto.BookDto;
import pl.agh.edu.library.service.BookService;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public List<BookDto> getBooks() {
		return bookService.getBooks();
	}

	@PostMapping
	public void addBook(@RequestBody BookDto bookDto) {
		bookService.addBook(bookDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
		return bookService.getBook(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDetails) {
		return bookService.updateBook(id, bookDetails)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/{bookId}/categories/{categoryId}")
	public void addCategory(@PathVariable Long bookId, @PathVariable Long categoryId) {
		bookService.addCategoryToBook(bookId, categoryId);
	}

}