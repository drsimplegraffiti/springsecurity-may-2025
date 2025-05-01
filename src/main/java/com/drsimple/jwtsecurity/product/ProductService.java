package com.drsimple.jwtsecurity.product;


import com.drsimple.jwtsecurity.exception.ConflictException;
import com.drsimple.jwtsecurity.user.User;
import com.drsimple.jwtsecurity.pagination.PaginationResponse;
import com.drsimple.jwtsecurity.pagination.PaginationUtil;
import com.drsimple.jwtsecurity.util.CurrentUserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CurrentUserUtil currentUserUtil;
    private final PaginationUtil paginationUtil;



    public ProductService(ProductRepository productRepository, CurrentUserUtil currentUserUtil, PaginationUtil paginationUtil) {
        this.productRepository = productRepository;
        this.currentUserUtil = currentUserUtil;
        this.paginationUtil = paginationUtil;
    }

//    public PaginationResponse<Product> getAllProducts(int page, int size, String sortBy, String sortDir, String nameFilter) {
//        Pageable pageable = paginationUtil.createPageRequest(page, size, sortBy, sortDir);
//        Page<Product> productPage;
//
//        if (nameFilter != null && !nameFilter.isEmpty()) {
//            productPage = productRepository.findByNameContainingIgnoreCase(nameFilter, pageable);
//        } else {
//            productPage = productRepository.findAll(pageable);
//        }
//
//        return new PaginationResponse<>(productPage);  // Wrap the result in PaginationResponse
//    }

    public PaginationResponse<Product> getAllProductsPaginated(
            int page, int size, String sortBy, String sortDir,
            String nameFilter, String categoryFilter, BigDecimal minPrice, BigDecimal maxPrice) {

        Pageable pageable = paginationUtil.createPageRequest(page, size, sortBy, sortDir);

        Specification<Product> spec = Specification
                .where(ProductSpecification.nameContains(nameFilter))
                .and(ProductSpecification.categoryEquals(categoryFilter))
                .and(ProductSpecification.priceGreaterThanOrEqual(minPrice))
                .and(ProductSpecification.priceLessThanOrEqual(maxPrice));

        Page<Product> productPage = productRepository.findAll(spec, pageable);
        return new PaginationResponse<>(productPage);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return  productRepository.findById(id);
    }

    public Product saveProduct(ProductRequest product){
        User loggedInUser = currentUserUtil.getLoggedInUser();

        Optional<Product> productExist = productRepository.findByName(product.getName());
        if(productExist.isPresent()) throw  new ConflictException("product already exist");

        Product productEntity = new Product();
        productEntity.setPrice(product.getPrice());
        productEntity.setCreatedBy(loggedInUser.getId());
        productEntity.setCategory(product.getCategory());
        productEntity.setName(product.getName());
        return productRepository.save(productEntity);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(Product product, Long id) throws Exception {
        Optional<Product> theProduct = productRepository.findById(id);

        if (theProduct.isEmpty()) {
            throw new Exception("Product not found");
        }

        Product existingProduct = theProduct.get();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());

        return productRepository.save(existingProduct);
    }
}
