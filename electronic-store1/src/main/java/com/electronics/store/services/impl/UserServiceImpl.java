package com.electronics.store.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electronics.store.dtos.PageableResponse;
import com.electronics.store.dtos.UserDto;
import com.electronics.store.enties.User;
import com.electronics.store.exceptions.RessourceNotFoundException;
import com.electronics.store.repositories.UserRepo;
import com.electronics.store.services.UserService;
import com.electronics.store.utilities.PagableResponseHelper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	// create user

	@Override
	public UserDto createUser(UserDto userDto) {

		// genrate userId

		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);

		// convert Dto --> Entity

		User user = DtoToEntity(userDto);

		// save Data in DB

		User saveUser = userRepo.save(user);

		// convert Entity --> Dto

		UserDto newDto = EntityToDto(saveUser);

		return newDto;
	}

	// create multiple users

	@Override
	public List<UserDto> createMultipleUsers(List<UserDto> userDtos) {

		// map userDtos --> User Entities
		List<User> users = userDtos.stream().map(e -> new User(e.getUserId(), e.getName(), e.getEmail(),
				e.getPassword(), e.getGender(), e.getAbout(), e.getCity(), e.getImageName()))
				.collect(Collectors.toList());

		// save multiple user in db
		users = userRepo.saveAll(users);

		// map user Entities --> User Dtos
		userDtos = users.stream().map(e -> new UserDto(e.getUserId(), e.getName(), e.getEmail(), e.getPassword(),
				e.getGender(), e.getAbout(), e.getCity(), e.getImageName())).collect(Collectors.toList());

		return userDtos;
	}

	// update user

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {

		// Check user available in DB

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with given Id: " + userId));

		// map userDto --> User

		user.setName(userDto.getName());
		user.setGender(userDto.getGender());
		user.setPassword(userDto.getPassword());
		user.setImageName(userDto.getImageName());
		user.setCity(userDto.getCity());
		user.setAbout(userDto.getAbout());

		// update user in Db

		User updatedUser = userRepo.save(user);

		// map Entity --> userDto

		UserDto updatedDto = EntityToDto(updatedUser);

		return updatedDto;
	}

	// delete user

	@Override
	public String deleteUser(String userId) {

		// check user available in db

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RessourceNotFoundException("User not found with given Id : " + userId));

		// delete user from db

		userRepo.delete(user);

		// check userId exist in db

		boolean existsById = userRepo.existsById(userId);

		// give msg response to client ,user is deleted or not.

		if (existsById) {
			return "User not deleted successfully !";
		} else {
			return "User deleted successfully !";
		}

	}

	// get all user

	@Override
	public PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		// sort as per direction with conditional operator

		Sort sort = (sortDir.equalsIgnoreCase("ASE")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());

		// pageRequest class implements by Pageable
		// pass to the of method

		Pageable pageble = PageRequest.of(pageNumber-1, pageSize, sort);

		Page<User> page = userRepo.findAll(pageble);
		
		List<User> users = page.getContent();
		

		// get All user from db

//		List<User> users = userRepo.findAll();

		// map list of user Entity --> list of user dto

		List<UserDto> userDtos = users.stream().map(e -> new UserDto(e.getUserId(), e.getName(), e.getEmail(),
				e.getPassword(), e.getGender(), e.getAbout(), e.getCity(), e.getImageName()))
				.collect(Collectors.toList());
		
		// convert normal UserDto response into pageableResponse to user with the help of static method of PageableResponsehelper Class 
		// which is present in utility package
		
//		PageableResponse<UserDto> response = new PageableResponse<>();
//		response.setContent(UserDtos);
//		response.setPageNumber(page.getNumber()+1);
//		response.setPageSize(page.getSize());
//		response.setTotalElement(page.getNumberOfElements());
//		response.setTotalPages(page.getTotalPages());
//		response.setLastPage(page.isLast());
		
		PageableResponse<UserDto> response = PagableResponseHelper.getPagableResonse(page,userDtos);

		return response;
	}

	// get user by userId

	@Override
	public UserDto getUserById(String userId) {

		// check the user in dab with userId and get user with userId
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RessourceNotFoundException("user not found with given Id : " + userId));

		// map user Entity --> user Dto
		UserDto userDto = EntityToDto(user);

		return userDto;
	}

	// get user by user Email

	@Override
	public UserDto getUserByEmail(String userEmail) {

		// get user by custom method of find by user Email

		User user = userRepo.findByEmail(userEmail)
				.orElseThrow(() -> new RessourceNotFoundException("user not found with given Email : " + userEmail));

		// map User Entity --> user Dto
		UserDto userDto = EntityToDto(user);

		return userDto;
	}

	// search user by any keyword

	@Override
	public List<UserDto> searchUser(String keyword) {

		// get user by custom method of find by user Email

		List<User> users = userRepo.findByNameContaining(keyword);

		// map list of user Entity --> list of user dto

		List<UserDto> UserDtos = users.stream().map(e -> new UserDto(e.getUserId(), e.getName(), e.getEmail(),
				e.getPassword(), e.getGender(), e.getAbout(), e.getCity(), e.getImageName()))
				.collect(Collectors.toList());

		return UserDtos;
	}

	// custom method call
	// Get User by userId with Sort by Name

	public List<UserDto> getbyGenderSortByName(String gender) {

		// custom method with sorting call
		List<User> users = userRepo.getUserByGenderSortByName(gender, Sort.by("name"));

		// map user entity --> userDto
		List<UserDto> UserDtos = users.stream().map(e -> new UserDto(e.getUserId(), e.getName(), e.getEmail(),
				e.getPassword(), e.getGender(), e.getAbout(), e.getCity(), e.getImageName()))
				.collect(Collectors.toList());

		return UserDtos;

	}

	// method that convert entity --> Dto
	private UserDto EntityToDto(User saveUser) {

//		UserDto userDto = UserDto.builder().about(saveUser.getAbout()).city(saveUser.getCity())
//				.email(saveUser.getEmail()).gender(saveUser.getGender()).imageName(saveUser.getImageName())
//				.name(saveUser.getName()).password(saveUser.getPassword()).userId(saveUser.getUserId()).build();

		UserDto userDto = modelMapper.map(saveUser, UserDto.class);

		return userDto;
	}

	// method that convert Dto --> Entity
	private User DtoToEntity(UserDto userDto) {
//		User user = User.builder().city(userDto.getCity()).email(userDto.getEmail()).imageName(userDto.getImageName())
//				.name(userDto.getName()).gender(userDto.getGender()).about(userDto.getAbout())
//				.password(userDto.getPassword()).userId(userDto.getUserId()).build();

		User user = modelMapper.map(userDto, User.class);
		return user;
	}

}
