package com.ey.codesara.ProductService.service;

import com.ey.codesara.ProductService.model.ProductRequest;
import com.ey.codesara.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(long productId, long quantity);
}
