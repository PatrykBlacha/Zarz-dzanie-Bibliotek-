package pl.agh.edu.library.mapper;

import pl.agh.edu.library.dto.BookDto;
import pl.agh.edu.library.model.Book;
import pl.agh.edu.library.model.Category;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .quantity(book.getQuantity())
                .categories(book.getCategories() != null ? 
                        book.getCategories().stream().map(Category::getName).collect(Collectors.toSet()) : null)
                .build();
    }

    public static Book toEntity(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setQuantity(bookDto.getQuantity());
        return book;
    }
}