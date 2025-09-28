# Product Management API

**Developer:** Phan Quốc Viễn  
**MSSV:** 23110362

## Mô tả dự án

Đây là một API RESTful được xây dựng bằng Spring Boot để quản lý sản phẩm và danh mục. Dự án bao gồm:

- **Backend API:** Spring Boot với JPA/Hibernate
- **Database:** PostgreSQL
- **Frontend:** HTML/JavaScript đơn giản với AJAX
- **File Storage:** Hệ thống lưu trữ file local
- **Documentation:** Swagger UI

## Công nghệ sử dụng

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **PostgreSQL 16**
- **Lombok**
- **SpringDoc OpenAPI (Swagger)**
- **Docker & Docker Compose** (chỉ cho database)

## Cấu trúc dự án

```
src/main/java/com/vienphan/api/
├── ApiApplication.java          # Main application class
├── config/
│   └── StorageProperties.java   # File storage configuration
├── controller/
│   ├── CategoryApiController.java
│   └── ProductApiController.java
├── entity/
│   ├── Category.java
│   └── Product.java
├── exception/
│   ├── StorageException.java
│   └── StorageFileNotFoundException.java
├── model/
│   └── ApiResponse.java
├── repository/
│   ├── CategoryRepository.java
│   └── ProductRepository.java
└── service/
    ├── CategoryServiceImpl.java
    ├── FileSystemStorageServiceImpl.java
    ├── ICategoryService.java
    ├── IProductService.java
    ├── IStorageService.java
    └── ProductServiceImpl.java
```

## API Endpoints

### Category API
- `GET /api/category` - Lấy danh sách tất cả categories
- `POST /api/category/getCategory` - Lấy category theo ID
- `POST /api/category/addCategory` - Thêm category mới
- `PUT /api/category/updateCategory` - Cập nhật category
- `DELETE /api/category/deleteCategory` - Xóa category

### Product API
- `GET /api/product` - Lấy danh sách tất cả products
- `POST /api/product/getProduct` - Lấy product theo ID
- `POST /api/product/addProduct` - Thêm product mới
- `PUT /api/product/updateProduct` - Cập nhật product
- `DELETE /api/product/deleteProduct` - Xóa product

## Cài đặt và chạy

### Yêu cầu hệ thống
- Java 17+
- Maven 3.6+
- IntelliJ IDEA (khuyến nghị)
- Docker & Docker Compose (chỉ cho database)

### Chạy dự án

1. Clone repository:
```bash
git clone <repository-url>
cd API
```

2. Chạy database với Docker:
```bash
docker-compose up -d
```

3. Chạy ứng dụng trong IntelliJ IDEA:
   - Mở project trong IntelliJ IDEA
   - Chạy class `ApiApplication.java` hoặc
   - Sử dụng Maven: `./mvnw spring-boot:run`

### Truy cập ứng dụng

- **API Documentation (Swagger):** http://localhost:8080/swagger-ui.html
- **Web Interface:** http://localhost:8080
- **Database Admin (pgAdmin):** http://localhost:8081

## Cấu hình Database

Database được cấu hình trong `application.properties`:
- **Host:** localhost:5432
- **Database:** api_db
- **Username:** api_user
- **Password:** api_pass

## Tính năng chính

1. **Quản lý Category:**
   - CRUD operations
   - Upload icon
   - Validation

2. **Quản lý Product:**
   - CRUD operations
   - Upload images
   - Liên kết với Category
   - Quản lý giá, số lượng, giảm giá
   - Trạng thái sản phẩm

3. **File Storage:**
   - Upload và lưu trữ file
   - Tự động tạo tên file unique
   - Quản lý thư mục uploads

4. **API Response:**
   - Chuẩn hóa response format
   - Error handling
   - Status codes

## Development

### Chạy tests
```bash
./mvnw test
```

### Build project
```bash
./mvnw clean package
```

### Chạy trong IntelliJ IDEA
1. Mở project trong IntelliJ IDEA
2. Đợi Maven sync hoàn tất
3. Chạy class `ApiApplication.java` hoặc sử dụng Spring Boot run configuration
4. Ứng dụng sẽ chạy trên port 8080

## License

MIT License - Xem file LICENSE để biết thêm chi tiết.

## Liên hệ

**Phan Quốc Viễn**  
MSSV: 23110362  
Email: 23110362@student.hcmute.edu.vn
