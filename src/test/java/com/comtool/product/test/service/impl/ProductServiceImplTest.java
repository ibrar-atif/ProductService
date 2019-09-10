package com.comtool.product.test.service.impl;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.comtool.product.dto.ProductDto;
import com.comtool.product.entity.Product;
import com.comtool.product.repository.ProductRepositoy;
import com.comtool.product.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

	@InjectMocks
	private ProductServiceImpl productService;
	
	@Mock
	private ProductRepositoy pRepo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test() {
		ProductDto productDto = getProductDto();
		when(pRepo.findById(productDto.getId())).thenReturn(Optional.of(new Product()));
		productService.addProduct(getProductDto());
		
		verify(pRepo,atLeastOnce()).findById(productDto.getId());
	}
	
	private ProductDto getProductDto() {
		ProductDto p = new ProductDto();
		p.setId("122");
		p.setName("Apple");
		return p;
	}
}
