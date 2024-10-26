package com.electronics.store.utilities;

import java.util.List;

import org.springframework.data.domain.Page;

import com.electronics.store.dtos.PageableResponse;

public class PagableResponseHelper {

	public static <U,V> PageableResponse<V> getPagableResonse(Page<U> page,List<V> dto ){
		
		PageableResponse<V> response = new PageableResponse<>();
		response.setContent(dto);
		response.setPageNumber(page.getNumber()+1);
		response.setNoOfElement(page.getNumberOfElements());
		response.setPageSize(page.getSize());
		response.setTotalElement(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLastPage(page.isLast());

		return response;
	}
}
