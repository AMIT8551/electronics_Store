package com.electronics.store.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.electronics.store.utilities.ImageNameValidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto {
	
	
	private String userId;
	
	@NotNull(message = "name can not be nulll") 
	private String name;

//	@Email(regexp = "^[a-zA-Z0-9_.]+$+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",message = "Invalid Email format")
	//@Email(message = "Invalid Email format")
	@NotBlank(message = "Email field is mendatory")
	@Size(min =  3,message = "email minimum size requred more than 3")
	private String email;

	@NotBlank(message = "password Field must be filled")
	@Size(min = 3,message = "minimum size of password is 3")
	@Size(max =  8,message = "maximum size of password is 8")
	private String password;

	@Length(min = 4,max = 6,message = "Gender length must be between 4-6 charactor")
	private String gender;

	@NotBlank(message = "write something here about you")
	private String about;

	@NotBlank(message = "Add your city")
	private String city;

	@ImageNameValidate(message = "Image format must be filled")
	private String imageName;

}
