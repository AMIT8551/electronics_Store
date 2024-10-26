package com.electronics.store.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

	private String categoryId;
	
	@NotBlank(message = "category title must be required")
	@Size(min = 4,message = "lenght of title must be minimum more than 3")
	private String title;
	
	@NotBlank(message = "Discription must be required for category")
	private String discription;
	
//	@NotBlank(message = "Image should be required")
	private String coverImageName;
}
