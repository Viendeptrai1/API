package com.vienphan.api.controller;

import com.vienphan.api.entity.Category;
import com.vienphan.api.model.ApiResponse;
import com.vienphan.api.service.ICategoryService;
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
@RequestMapping(path = "/api/category")
@Validated
public class CategoryApiController {
	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IStorageService storageService;

	@GetMapping
	public ResponseEntity<?> getAllCategory() {
		return new ResponseEntity<>(new ApiResponse(true, "Thành công", categoryService.findAll()), HttpStatus.OK);
	}

	@PostMapping(path = "/getCategory")
	public ResponseEntity<?> getCategory(@RequestParam("id") @NotNull Long id) {
		Optional<Category> category = categoryService.findById(id);
		if (category.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(true, "Thành công", category.get()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse(false, "Không tìm thấy", null), HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "/addCategory")
	public ResponseEntity<?> addCategory(@RequestParam("categoryName") @NotBlank(message = "Tên danh mục không được để trống") @Size(max = 255, message = "Tên danh mục không được vượt quá 255 ký tự") String categoryName,
			@RequestParam(value = "icon", required = false) MultipartFile icon) {
		Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
		if (optCategory.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "Category đã tồn tại trong hệ thống", null), HttpStatus.BAD_REQUEST);
		}
		Category category = new Category();
		if (icon != null && !icon.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			category.setIcon(storageService.getStorageFilename(icon, uuid));
			storageService.store(icon, category.getIcon());
		}
		category.setCategoryName(categoryName);
		categoryService.save(category);
		return new ResponseEntity<>(new ApiResponse(true, "Thêm Thành công", category), HttpStatus.OK);
	}

	@PutMapping(path = "/updateCategory")
	public ResponseEntity<?> updateCategory(@RequestParam("categoryId") @NotNull Long categoryId,
			@RequestParam("categoryName") @NotBlank String categoryName,
			@RequestParam(value = "icon", required = false) MultipartFile icon) {
		Optional<Category> optCategory = categoryService.findById(categoryId);
		if (optCategory.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
		}
		Category category = optCategory.get();
		if (icon != null && !icon.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			category.setIcon(storageService.getStorageFilename(icon, uuid));
			storageService.store(icon, category.getIcon());
		}
		category.setCategoryName(categoryName);
		categoryService.save(category);
		return new ResponseEntity<>(new ApiResponse(true, "Cập nhật Thành công", category), HttpStatus.OK);
	}

	@DeleteMapping(path = "/deleteCategory")
	public ResponseEntity<?> deleteCategory(@RequestParam("categoryId") @NotNull Long categoryId) {
		Optional<Category> optCategory = categoryService.findById(categoryId);
		if (optCategory.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
		}
		categoryService.delete(optCategory.get());
		return new ResponseEntity<>(new ApiResponse(true, "Xóa Thành công", optCategory.get()), HttpStatus.OK);
	}
}
