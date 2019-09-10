package com.comtool.product.service;

import java.util.List;
import java.util.Map;

import com.comtool.product.dto.ProductDto;

public interface ProductService {

	void addProduct(ProductDto productDto);
		
	List<ProductDto> getAllProducts(Integer index, Integer pageSize);
	
	Map<String, Integer> getStatus(String startDate, String endDate) throws Exception;

	void updateProduct(String name);
}
