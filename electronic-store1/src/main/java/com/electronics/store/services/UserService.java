package com.electronics.store.services;

import java.util.List;

import com.electronics.store.dtos.PageableResponse;
import com.electronics.store.dtos.UserDto;

public interface UserService {

	// create

	UserDto createUser(UserDto userDto);

	// create multiple users

	List<UserDto> createMultipleUsers(List<UserDto> userDtos);

	// update

	UserDto updateUser(UserDto userDto, String userId);

	// delete

	String deleteUser(String userId);

	// get all users

	PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get single user by id

	UserDto getUserById(String userId);

	// get single user by email

	UserDto getUserByEmail(String userEmail);

	// search user

	List<UserDto> searchUser(String keyword);

	// custom method call
	// Get User by userId with Sort by Name

	List<UserDto> getbyGenderSortByName(String userid);
}
