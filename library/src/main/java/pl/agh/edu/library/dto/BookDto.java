package pl.agh.edu.library.dto;

import java.util.Set;

public class BookDto {
    private Integer id;
    private String name;
    private String author;
    private Integer quantity;
    private Set<String> categories;

    public BookDto() {}

    public BookDto(Integer id, String name, String author, Integer quantity, Set<String> categories) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
        this.categories = categories;
    }

    public static class Builder {
        private Integer id;
        private String name;
        private String author;
        private Integer quantity;
        private Set<String> categories;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder categories(Set<String> categories) {
            this.categories = categories;
            return this;
        }

        public BookDto build() {
            return new BookDto(id, name, author, quantity, categories);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }
}