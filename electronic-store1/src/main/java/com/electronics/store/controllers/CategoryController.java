package com.electronics.store.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electronics.store.dtos.ApiResponceMassage;
import com.electronics.store.dtos.CategoryDto;
import com.electronics.store.dtos.PageableResponse;
import com.electronics.store.services.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	public CategoryServiceImpl categoryService;

	// post category

	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto) {

		categoryDto = categoryService.create(categoryDto);

		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.CREATED);

	}

	// update category

	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<ApiResponceMassage> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@Valid @PathVariable String categoryId) {

		String msg = categoryService.update(categoryDto, categoryId);

		ApiResponceMassage apiResponceMassage = ApiResponceMassage.builder().message(msg).status(HttpStatus.OK)
				.success(true).build();

		return new ResponseEntity<ApiResponceMassage>(apiResponceMassage, HttpStatus.ACCEPTED);
	}

	// delete category
	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<ApiResponceMassage> deleteCategory(@PathVariable String categoryId) {
		String msg = categoryService.delete(categoryId);
		ApiResponceMassage apiResponceMassage = ApiResponceMassage.builder().message(msg).status(HttpStatus.OK)
				.success(true).build();

		return new ResponseEntity<ApiResponceMassage>(apiResponceMassage, HttpStatus.ACCEPTED);
	}

	// get all category
	@GetMapping("/getAllCategory")
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
			@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir) {

		PageableResponse<CategoryDto> pageableResponse = categoryService.getAllCategory(pageNumber, pageSize, sortBy,
				sortDir);

		return new ResponseEntity<PageableResponse<CategoryDto>>(pageableResponse, HttpStatus.FOUND);

	}

	// get category by id
	@GetMapping("/getCategory")
	public ResponseEntity<CategoryDto> getCategory(@Valid @RequestParam String categoryId) {

		CategoryDto category = categoryService.getCategory(categoryId);

		return new ResponseEntity<CategoryDto>(category, HttpStatus.FOUND);
	}

	/// get category by

	// search
	@GetMapping("/searchCategory")
	public ResponseEntity<List<CategoryDto>> seachCategory(@RequestParam String keyword) {

		List<CategoryDto> searchCategory = categoryService.searchCategory(keyword);

		return new ResponseEntity<List<CategoryDto>>(searchCategory, HttpStatus.FOUND);
	}

	// requred controller endpoints..
}

