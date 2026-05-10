package pl.agh.edu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.library.dto.BookDto;
import pl.agh.edu.library.mapper.BookMapper;
import pl.agh.edu.library.model.Book;
import pl.agh.edu.library.model.Category;
import pl.agh.edu.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
	private final BookRepository bookRepository;
	private final CategoryService categoryService;

	@Autowired
	public BookService(BookRepository bookRepository, CategoryService categoryService) {
		this.bookRepository = bookRepository;
		this.categoryService = categoryService;
	}

	public List<BookDto> getBooks() {
		return bookRepository.findAll().stream()
				.map(BookMapper::toDto)
				.collect(Collectors.toList());
	}

	public void addBook(BookDto bookDto) {
		Book book = BookMapper.toEntity(bookDto);
		bookRepository.save(book);
	}

	public Optional<BookDto> getBook(Long id) {
		return bookRepository.findById(id).map(BookMapper::toDto);
	}

	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}

	public Optional<BookDto> updateBook(Long id, BookDto bookDetails) {
		return bookRepository.findById(id).map(book -> {
			book.setName(bookDetails.getName());
			book.setAuthor(bookDetails.getAuthor());
			book.setQuantity(bookDetails.getQuantity());
			bookRepository.save(book);
			return BookMapper.toDto(book);
		});
	}


	public void addCategoryToBook(Long bookId, Long categoryId) {
		Optional<Book> optionalBook = bookRepository.findById(bookId);
		if(optionalBook.isEmpty())
			System.out.println("Book not found!");
		Optional<Category> optionalCategory = categoryService.getCategory(categoryId);
		if(optionalCategory.isEmpty())
			System.out.println("Category not found!");
		if (optionalBook.isPresent() && optionalCategory.isPresent()) {
			optionalBook.get().getCategories().add(optionalCategory.get());
			bookRepository.save(optionalBook.get());
		}
	}
}