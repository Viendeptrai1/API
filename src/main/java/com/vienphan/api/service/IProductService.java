package com.vienphan.api.service;

import com.vienphan.api.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IProductService {
	void delete(Product entity);
	void deleteById(Long id);
	long count();
	Optional<Product> findById(Long id);
	List<Product> findAllById(Iterable<Long> ids);
	List<Product> findAll(Sort sort);
	Page<Product> findAll(Pageable pageable);
	List<Product> findAll();
	Optional<Product> findByProductName(String name);
	Optional<Product> findByCreateDate(Date createAt);
	Product save(Product entity);
	Page<Product> findByProductNameContaining(String name, Pageable pageable);
	List<Product> findByProductNameContaining(String name);
}
