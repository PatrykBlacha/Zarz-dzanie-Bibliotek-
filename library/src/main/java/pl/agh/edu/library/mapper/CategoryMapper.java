package pl.agh.edu.library.mapper;

import pl.agh.edu.library.dto.CategoryDto;
import pl.agh.edu.library.model.Category;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }
}