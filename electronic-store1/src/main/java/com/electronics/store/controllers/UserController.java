package com.electronics.store.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.electronics.store.dtos.ImageUploadResponse;
import com.electronics.store.dtos.PageableResponse;
import com.electronics.store.dtos.UserDto;
import com.electronics.store.exceptions.ImageNotFoundException;
import com.electronics.store.services.FileService;
import com.electronics.store.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Value("${user.profile.image.path}")
	private String imageClassPath;

	// create User

	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto dto = userService.createUser(userDto);
		return new ResponseEntity<UserDto>(dto, HttpStatus.CREATED);
	}

	// create multipke users

	@PostMapping("/multipleUser")
	public ResponseEntity<List<UserDto>> createMultipleUsers(@Valid @RequestBody List<UserDto> userDtos) {
		List<UserDto> multipleUsers = userService.createMultipleUsers(userDtos);
		return new ResponseEntity<List<UserDto>>(multipleUsers, HttpStatus.CREATED);
	}

	// update User

	@PutMapping("/updateUser")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @Valid @RequestParam String userId) {
		UserDto updateUser = userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);

	}

	// delete User
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<String> deleteUser(@Valid @PathVariable String userId) {

		UserDto user = userService.getUserById(userId);
		String imageName = user.getImageName();
		String fullPath = imageClassPath + imageName;
		File file = new File(fullPath);

		if (file.exists()) {
			fileService.deleteresource(fullPath);
		}

		String deleteUser = userService.deleteUser(userId);

		return new ResponseEntity<String>(deleteUser, HttpStatus.ACCEPTED);

	}

	// get All User

	@GetMapping("/getAllUser")
	public ResponseEntity<PageableResponse<UserDto>> getAllUser(
			@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir) {
		/* List<UserDto> allUser = userService.getAllUser(); */
//		List<UserDto> allUser = userService.getAllUser(pageNumber, pageSize,sortBy,sortDir);

		return new ResponseEntity<PageableResponse<UserDto>>(
				userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

	}

	// get user by userId

	@GetMapping("/getUserById/{id}")
	public ResponseEntity<UserDto> getUserById(@Valid @PathVariable("id") String userId) {
		UserDto userById = userService.getUserById(userId);
		return new ResponseEntity<UserDto>(userById, HttpStatus.OK);
	}

	// get User by email

	@GetMapping("/getUserByEmail/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@Valid @PathVariable String email) {
		UserDto userByEmail = userService.getUserByEmail(email);
		return new ResponseEntity<UserDto>(userByEmail, HttpStatus.OK);

	}

	// search user by name

	@GetMapping("/serchUser/{name}")
	public ResponseEntity<List<UserDto>> searchUser(@Valid @PathVariable String name) {
		List<UserDto> users = userService.searchUser(name);
		return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
	}

	// get user by gender and sort by name
 
	@GetMapping("/getbyGenderSortByName/{gender}")
	public ResponseEntity<List<UserDto>> getbyUserIdSortByName(@Valid @PathVariable String gender) {
		List<UserDto> getbyUserIdSortByName = userService.getbyGenderSortByName(gender);
		return new ResponseEntity<List<UserDto>>(getbyUserIdSortByName, HttpStatus.OK);
	}

	// upload image
	@PostMapping("/uploadImage")
	public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("userImage") MultipartFile file,
			@RequestParam String userId) throws IOException {

		UserDto user = userService.getUserById(userId);

		String image = fileService.uploadImage(file, imageClassPath, user.getName(), userId);

		user.setImageName(image);

		userService.updateUser(user, userId);

		ImageUploadResponse imageUploadResponse = ImageUploadResponse.builder().imageName(image)
				.message("Image created successfully").status(HttpStatus.CREATED).success(true).build();

		return new ResponseEntity<ImageUploadResponse>(imageUploadResponse, HttpStatus.CREATED);

	}

	// get image
	@GetMapping(value = "/getImage/{userId}")
	public ResponseEntity<byte[]> getImage(@PathVariable String userId) {

		UserDto user = userService.getUserById(userId);
		String imageName = user.getImageName();
		String fullPath = imageClassPath + imageName;

		File abc = new File(fullPath);

		if (!abc.exists()) {

			throw new ImageNotFoundException("Image not Found in class path");
		}

		try {

			InputStream resource = fileService.getResource(imageClassPath, imageName);

			byte[] imagebyte = resource.readAllBytes();

			HttpHeaders headers = new HttpHeaders();

			MediaType contenttype = null;

			if (imageName.endsWith(".png")) {

				contenttype = MediaType.IMAGE_PNG;

			} else if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {

				contenttype = MediaType.IMAGE_JPEG;
			}

			headers.setContentType(contenttype);

			return new ResponseEntity<byte[]>(imagebyte, headers, HttpStatus.FOUND);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/getImageResponce/{userId}")
	public void getImageWithHttpResponse(@PathVariable String userId, HttpServletResponse httpResource) {

		UserDto user = userService.getUserById(userId);
		String imageName = user.getImageName();

//		String fullPath = imageClassPath + imageName;
//		File file = new File(fullPath);

//		if (!file.exists()) {
//
//			throw new ImageNotFoundException("Image not Found in class path");
//		}

		InputStream resource1;

		try {
			resource1 = fileService.getResource(imageClassPath, imageName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ImageNotFoundException("Image Not Found in class path");
		}

		String contenttype = null;

		if (imageName.endsWith(".png")) {

			contenttype = MediaType.IMAGE_PNG_VALUE;

		} else if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {

			contenttype = MediaType.IMAGE_JPEG_VALUE;
		}

		httpResource.setContentType(contenttype);

		try {
			StreamUtils.copy(resource1, httpResource.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@DeleteMapping("/deleteImageFromClassPath/{userId}")
	public ResponseEntity<String> deleteImageFromClassPath(@PathVariable String userId) {

		UserDto user = userService.getUserById(userId);
		String imageName = user.getImageName();

		String fullPath = imageClassPath + imageName;

		fileService.deleteresource(fullPath);

		File abc = new File(fullPath);

		if (!abc.exists()) {

			return new ResponseEntity<String>("Image Deleted Successfully:" + imageName + ".", HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<String>("Image Not Deleted Successfully:" + imageName + ".", HttpStatus.BAD_REQUEST);

	}
}
