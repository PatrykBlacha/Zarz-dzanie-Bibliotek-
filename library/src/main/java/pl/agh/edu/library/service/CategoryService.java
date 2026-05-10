package pl.agh.edu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.library.dto.CategoryDto;
import pl.agh.edu.library.mapper.CategoryMapper;
import pl.agh.edu.library.model.Category;
import pl.agh.edu.library.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository=categoryRepository;
	}

	public List<CategoryDto> getCategories() {
		return categoryRepository.findAll().stream()
				.map(CategoryMapper::toDto)
				.collect(Collectors.toList());
	}

	public void addCategory(CategoryDto categoryDto) {
		Category category = CategoryMapper.toEntity(categoryDto);
		categoryRepository.save(category);
	}

	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

	public Optional<Category> getCategory(Long id) {
		return categoryRepository.findById(id);
	}
}