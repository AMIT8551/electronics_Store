package com.electronics.store.enties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity
public class User {

	@Id
	private String userId;

	@Column(name = "user_Name")
	private String name;

	@Column(name = "user_Email",unique = true)
	private String email;

	@Column(name = "user_Password",nullable = false,length = 10)
	private String password;


	private String gender;

	@Column(length = 1000)
	private String about;

	@Column(name = "user_City")
	private String city;

	@Column(name = "user_Image_Name")
	private String imageName;
}
