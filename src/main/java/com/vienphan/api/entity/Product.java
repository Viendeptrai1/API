package com.vienphan.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	@NotBlank(message = "Tên sản phẩm không được để trống")
	@Size(max = 500, message = "Tên sản phẩm không được vượt quá 500 ký tự")
	@Column(name = "product_name", length = 500, nullable = false)
	private String productName;

	@NotNull(message = "Số lượng không được để trống")
	@Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
	@Column(nullable = false)
	private int quantity;

	@NotNull(message = "Giá sản phẩm không được để trống")
	@DecimalMin(value = "0.0", message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
	@Column(name = "unit_price", nullable = false)
	private double unitPrice;

	@Column(length = 200)
	private String images;

	@Column(columnDefinition = "text")
	private String description;

	@DecimalMin(value = "0.0", message = "Giảm giá phải lớn hơn hoặc bằng 0")
	@DecimalMax(value = "100.0", message = "Giảm giá không được vượt quá 100%")
	@Column(nullable = false)
	private double discount;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_date")
	private Date createDate = new Date();

	@Column(nullable = false)
	private short status = 1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Category category;
}
