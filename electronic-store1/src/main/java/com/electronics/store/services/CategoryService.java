package com.electronics.store.services;

import java.util.List;

import com.electronics.store.dtos.CategoryDto;
import com.electronics.store.dtos.PageableResponse;

public interface CategoryService {

	// create

	public CategoryDto create(CategoryDto categoryDto);

	// update

	public String update(CategoryDto categoryDto, String categoryId);

	// delete

	public String delete(String categoryId);

	// get all

	public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortby, String sortdir);

	// get by id

	public CategoryDto getCategory(String categoryId);

	// search
	
	public List<CategoryDto> searchCategory(String keyword);
	

	//

}
