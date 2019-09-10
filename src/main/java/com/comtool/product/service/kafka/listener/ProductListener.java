package com.comtool.product.service.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.comtool.product.dto.ProductDto;
import com.comtool.product.service.ProductService;

@Component
public class ProductListener {
	
	@Autowired
	private ProductService productService;

	@KafkaListener(topics= "${product.topic}")
	public void readProduct(ProductDto product) {
		
		productService.addProduct(product);
		
	}
}
