package pl.agh.edu.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.library.dto.CategoryDto;
import pl.agh.edu.library.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
	private final CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	public void addCategory(@RequestBody CategoryDto categoryDto) {
		categoryService.addCategory(categoryDto);
	}

	@DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
	}

	@GetMapping
	public List<CategoryDto> getCategories() {
		return categoryService.getCategories();
	}
}