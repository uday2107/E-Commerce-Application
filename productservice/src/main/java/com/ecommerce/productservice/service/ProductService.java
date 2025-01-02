package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.exception.ProductException;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository reporsioty;

    ProductService(ProductRepository reporsioty){
        this.reporsioty = reporsioty;
    }

    public Product createProduct(ProductRequest request) throws ProductException {
        Product product = Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .build();

       Product respose = reporsioty.save(product);
       log.info("Product Saved Succesfully");
       return respose;

    }

    public List<Product> findAllProdcuts() throws ProductException{
        List<Product> productList = reporsioty.findAll();
        return productList;
    }


    public Product findProductById(String id) throws ProductException{
        return reporsioty.findById(id).orElseThrow(()-> new ProductException("Product Not Found"));
    }

    public void deletProduct(String id) throws ProductException{
        reporsioty.deleteById(id);
    }
}
