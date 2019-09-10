package com.comtool.product.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.comtool.product.dto.ProductDto;
import com.comtool.product.entity.Product;
import com.comtool.product.repository.ProductRepositoy;
import com.comtool.product.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepositoy pRepo;
	
	@Override
	public void addProduct(ProductDto productDto) {
	
		Optional<Product> p = pRepo.findById(productDto.getId());
		if(p.isPresent()) {
			Product product = p.get();			
			BeanUtils.copyProperties(productDto, product);
			pRepo.save(product);
		}else {
			Product product = new Product();
			BeanUtils.copyProperties(productDto, product);
			pRepo.save(product);
		}
	}


	@Override
	public List<ProductDto> getAllProducts(Integer index, Integer pageSize) {
		Pageable pageable = PageRequest.of(index, pageSize);
		 List<Product> products = pRepo.findAllProducts(pageable);
		return toProductDto(products);
	}

	
	public List<ProductDto> toProductDto(List<Product> products){
		List<ProductDto> productDtos = new ArrayList<>();
		for(Product product:products) {
			ProductDto dto = new ProductDto();
			BeanUtils.copyProperties(product, dto);
			productDtos.add(dto);
		}
		return productDtos;
	}


	@Override
	@Transactional
	public Map<String, Integer> getStatus(String startDate, String endDate) throws Exception {
		Date stDate = getDate(startDate, "yyyy-MM-dd",0);
		Date enDate = getDate(endDate, "yyyy-MM-dd",1);
		Integer createdCount = pRepo.getCreatedCount(stDate,enDate);
		Integer updatedCount = pRepo.getUpdateCount(stDate,enDate);
		Map<String, Integer> productCount = new HashMap<>();
		productCount.put("created", createdCount);
		productCount.put("updated", updatedCount);
		return productCount;
	}
	
	public  Date getDate(String srtDate, String pattern,int increament) throws Exception {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			Calendar c = Calendar.getInstance();
			if(StringUtils.isEmpty(srtDate)) {			
				c.add(Calendar.DATE, increament);
				Date d = c.getTime();
				d.setHours(0);
				d.setMinutes(0);
				d.setSeconds(0);
				return d;
			}else {
				Date d = dateFormat.parse(srtDate);
				c.setTime(d);
				c.add(Calendar.DATE, increament);
				return c.getTime();
			}
		} catch (ParseException e) {
			throw new Exception("Invalid Date");
		}
	}


	@Override
	public void updateProduct(String name) {
		// TODO Auto-generated method stub
		
	}

}
