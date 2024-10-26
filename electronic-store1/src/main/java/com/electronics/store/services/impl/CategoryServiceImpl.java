package com.electronics.store.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electronics.store.dtos.CategoryDto;
import com.electronics.store.dtos.PageableResponse;
import com.electronics.store.enties.Category;
import com.electronics.store.exceptions.RessourceNotFoundException;
import com.electronics.store.repositories.CategoryRepo;
import com.electronics.store.services.CategoryService;
import com.electronics.store.utilities.PagableResponseHelper;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto create(CategoryDto categoryDto) {

		Category category = modelMapper.map(categoryDto, Category.class);

		category = categoryRepo.save(category);

		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public String update(CategoryDto categoryDto, String categoryId) {

		Category category = categoryRepo.findById(categoryId).orElseThrow(
				() -> new RessourceNotFoundException("given Category not found with given Id :" + categoryId));

		category = modelMapper.map(categoryDto, Category.class);

		category = categoryRepo.save(category);

		categoryDto = modelMapper.map(category, CategoryDto.class);

		return "category updated successfully with given Id : " + categoryId + "\n" + "updated category: "
				+ categoryDto;

	}

	@Override
	public String delete(String categoryId) {

		Category category = categoryRepo.findById(categoryId).orElseThrow(
				() -> new RessourceNotFoundException("given Category not found with given Id :" + categoryId));

		categoryRepo.delete(category);

		return "category deleted successfully with given Id : " + categoryId;
	}

	@Override
	public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortby, String sortdir) {

		Sort sort = (sortdir.equalsIgnoreCase("ASE")) ? (Sort.by(sortby).ascending()) : (Sort.by(sortby).descending());

		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

		Page<Category> page = categoryRepo.findAll(pageable);

		List<Category> categories = page.getContent();

		List<CategoryDto> categoryDtos = categories.stream()
				.map(t -> CategoryDto.builder().categoryId(t.getCategoryId()).discription(t.getDiscription())
						.title(t.getTitle()).coverImageName(t.getCoverImageName()).build())
				.collect(Collectors.toList());

		PagableResponseHelper.getPagableResonse(page, categoryDtos);
		return PagableResponseHelper.getPagableResonse(page, categoryDtos);

	}

	@Override
	public CategoryDto getCategory(String categoryId) {

		Category category = categoryRepo.findById(categoryId).orElseThrow(
				() -> new RessourceNotFoundException("given Category not found with given Id :" + categoryId));

		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> searchCategory(String keyword) {

		List<CategoryDto> listOfDto = categoryRepo.findByTitleContaining(keyword)
				.orElseThrow(() -> new RessourceNotFoundException("given Category not found with given Id :" + keyword))
				.stream()
				.map(e -> CategoryDto.builder().categoryId(e.getCategoryId()).title(e.getTitle())
						.discription(e.getDiscription()).coverImageName(e.getCoverImageName()).build())
				.collect(Collectors.toList());

		return listOfDto;
	}

}
