package com.comtool.product.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comtool.product.entity.Product;

@Repository
public interface ProductRepositoy extends JpaRepository<Product, String>{
	
	@Query("from Product")
	List<Product> findAllProducts(Pageable pageable);
	
	@Query("Select count(*) from Product p where p.createdOn BETWEEN :startDate AND :endDate")
	Integer getCreatedCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate );

	@Query("Select count(*) from Product p where p.updatedOn BETWEEN :startDate AND :endDate and p.version!=0")
	Integer getUpdateCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate );

}
