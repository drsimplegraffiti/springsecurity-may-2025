package com.drsimple.jwtsecurity.product;


import com.drsimple.jwtsecurity.pagination.PaginationResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/paginated/product")
    public ResponseEntity<PaginationResponse<Product>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,  // starts from 1
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) String categoryFilter,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        PaginationResponse<Product> productResponse = productService.getAllProductsPaginated(
                page, size, sortBy, sortDir, nameFilter, categoryFilter, minPrice, maxPrice);

        return ResponseEntity.ok(productResponse);
    }


    @GetMapping()
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductRequest product){
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product,@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.updateProduct(product, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }


}
