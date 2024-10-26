package com.electronics.store.enties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "Categories")
public class Category {

	@Id
	private String categoryId;
	
	@Column(name="category_Title",nullable = false)
	private String title;
	
	@Column(name = "category_Discription")
	private String discription;
	
	private String coverImageName;
	
}
