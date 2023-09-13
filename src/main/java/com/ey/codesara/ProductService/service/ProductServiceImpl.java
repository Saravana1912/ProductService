package com.ey.codesara.ProductService.service;

import com.ey.codesara.ProductService.entity.Product;
import com.ey.codesara.ProductService.exception.ProductServiceCustomException;
import com.ey.codesara.ProductService.model.ProductRequest;
import com.ey.codesara.ProductService.model.ProductResponse;
import com.ey.codesara.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl  implements ProductService{

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product ");
        Product product = Product.builder()
            .productName(productRequest.getName())
            .quantity(productRequest.getQuantity())
            .price(productRequest.getPrice())
            .build();

        productRepository.save(product);
        log.info("Product created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("Get the Product for productId :{}",productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product with given id  not found","PRODUCT NOT FOUND"));

        ProductResponse productResponse =new ProductResponse();
        copyProperties(product, productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity {} for Id : {}", quantity,productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException(
                        "Product with given Id not Found",
                        "PRODUCT_NOT_FOUND"
                ));

        if(product.getQuantity()<quantity){
            throw new ProductServiceCustomException(
                    "Product does not have sufficient Quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }
        
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);

        log.info("Product Quantity : {}", product.getQuantity(),"updated successfully");
    }


}
