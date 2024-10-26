package com.electronics.store.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponse<T> {

	private List<T> content;
	private int PageNumber;
	private int pageSize;
	private int noOfElement;
	private long totalElement;
	private int totalPages;
	private boolean lastPage;
}
