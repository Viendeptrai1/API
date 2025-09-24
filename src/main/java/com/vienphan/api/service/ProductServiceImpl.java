package com.vienphan.api.service;

import com.vienphan.api.entity.Product;
import com.vienphan.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product save(Product entity) {
		return productRepository.save(entity);
	}

	@Override
	public Optional<Product> findByProductName(String name) {
		return productRepository.findByProductName(name);
	}

	@Override
	public Optional<Product> findByCreateDate(Date createAt) {
		return productRepository.findByCreateDate(createAt);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public List<Product> findAllById(Iterable<Long> ids) {
		return productRepository.findAllById(ids);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public List<Product> findByProductNameContaining(String name) {
		return productRepository.findByProductNameContaining(name);
	}

	@Override
	public Page<Product> findByProductNameContaining(String name, Pageable pageable) {
		return productRepository.findByProductNameContaining(name, pageable);
	}
}
