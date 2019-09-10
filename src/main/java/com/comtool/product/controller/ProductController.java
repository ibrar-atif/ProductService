package com.comtool.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtool.product.dto.ProductDto;
import com.comtool.product.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/allproduct")
	public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(value = "index", required = false,defaultValue="0") Integer index,
			@RequestParam(value = "size", required = false,defaultValue="20") Integer size) {
		List<ProductDto> response = productService.getAllProducts(index, size);		
		
		return new ResponseEntity<List<ProductDto>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/count")
	public ResponseEntity<Object> getCount(@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) throws Exception {
		 Map<String, Integer> response = productService.getStatus(startDate, endDate);		
		
		return new ResponseEntity<Object>(response,HttpStatus.OK);
	}
	
	

}

