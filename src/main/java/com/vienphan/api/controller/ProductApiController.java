package com.vienphan.api.controller;

import com.vienphan.api.entity.Category;
import com.vienphan.api.entity.Product;
import com.vienphan.api.model.ApiResponse;
import com.vienphan.api.service.ICategoryService;
import com.vienphan.api.service.IProductService;
import com.vienphan.api.service.IStorageService;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/product")
@Validated
public class ProductApiController {
	@Autowired
	private IProductService productService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IStorageService storageService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(new ApiResponse(true, "Thành công", productService.findAll()), HttpStatus.OK);
	}

	@PostMapping(path = "/getProduct")
	public ResponseEntity<?> getProduct(@RequestParam("id") @NotNull Long id) {
		Optional<Product> product = productService.findById(id);
		if (product.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(true, "Thành công", product.get()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse(false, "Không tìm thấy", null), HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "/addProduct")
	public ResponseEntity<?> addProduct(
			@RequestParam("productName") @NotBlank(message = "Tên sản phẩm không được để trống") String productName,
			@RequestParam("quantity") @NotNull(message = "Số lượng không được để trống") @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0") Integer quantity,
			@RequestParam("unitPrice") @NotNull(message = "Giá sản phẩm không được để trống") @DecimalMin(value = "0.0", message = "Giá sản phẩm phải lớn hơn hoặc bằng 0") Double unitPrice,
			@RequestParam(value = "images", required = false) MultipartFile images,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "discount", required = false, defaultValue = "0") @DecimalMin(value = "0.0", message = "Giảm giá phải lớn hơn hoặc bằng 0") @DecimalMax(value = "100.0", message = "Giảm giá không được vượt quá 100%") Double discount,
			@RequestParam("status") @NotNull(message = "Trạng thái không được để trống") Short status,
			@RequestParam("categoryId") @NotNull(message = "Danh mục không được để trống") Long categoryId
	) {
		Optional<Category> category = categoryService.findById(categoryId);
		if (category.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Category không tồn tại", null), HttpStatus.BAD_REQUEST);
		}
		Product product = new Product();
		product.setProductName(productName);
		product.setQuantity(quantity);
		product.setUnitPrice(unitPrice);
		product.setDescription(description);
		product.setDiscount(discount);
		product.setStatus(status);
		product.setCategory(category.get());
		if (images != null && !images.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			product.setImages(storageService.getStorageFilename(images, uuid));
			storageService.store(images, product.getImages());
		}
		productService.save(product);
		return new ResponseEntity<>(new ApiResponse(true, "Thêm Thành công", product), HttpStatus.OK);
	}

	@PutMapping(path = "/updateProduct")
	public ResponseEntity<?> updateProduct(
			@RequestParam("productId") @NotNull Long productId,
			@RequestParam("productName") @NotBlank String productName,
			@RequestParam("quantity") @NotNull Integer quantity,
			@RequestParam("unitPrice") @NotNull Double unitPrice,
			@RequestParam(value = "images", required = false) MultipartFile images,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "discount", required = false, defaultValue = "0") Double discount,
			@RequestParam("status") @NotNull Short status,
			@RequestParam("categoryId") @NotNull Long categoryId
	) {
		Optional<Product> optProduct = productService.findById(productId);
		if (optProduct.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Không tìm thấy Product", null), HttpStatus.BAD_REQUEST);
		}
		Optional<Category> category = categoryService.findById(categoryId);
		if (category.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Category không tồn tại", null), HttpStatus.BAD_REQUEST);
		}
		Product product = optProduct.get();
		product.setProductName(productName);
		product.setQuantity(quantity);
		product.setUnitPrice(unitPrice);
		product.setDescription(description);
		product.setDiscount(discount);
		product.setStatus(status);
		product.setCategory(category.get());
		if (images != null && !images.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			product.setImages(storageService.getStorageFilename(images, uuid));
			storageService.store(images, product.getImages());
		}
		productService.save(product);
		return new ResponseEntity<>(new ApiResponse(true, "Cập nhật Thành công", product), HttpStatus.OK);
	}

	@DeleteMapping(path = "/deleteProduct")
	public ResponseEntity<?> deleteProduct(@RequestParam("productId") @NotNull Long productId) {
		Optional<Product> optProduct = productService.findById(productId);
		if (optProduct.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Không tìm thấy Product", null), HttpStatus.BAD_REQUEST);
		}
		productService.delete(optProduct.get());
		return new ResponseEntity<>(new ApiResponse(true, "Xóa Thành công", optProduct.get()), HttpStatus.OK);
	}
}
