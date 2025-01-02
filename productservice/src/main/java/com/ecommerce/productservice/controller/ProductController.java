package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.exception.ProductException;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public  ResponseEntity<Product> createProduct(@RequestBody ProductRequest request){
        Instant startTime = Instant.now();
        log.info("Entered Create Product with Product Request ={}",request.toString());
        try {
           Product savedProduct = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        }catch(ProductException e){
            log.error("Exception While Creating a Product at Controller Level ={}",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }finally {
           log.info("TIME_TAKEN for Creating a Product ={}",Duration.between(Instant.now(),startTime).toMillis());
            log.info("Exited from Create Product");
        }

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Product>> findAllProducts(){
        Instant startTime = Instant.now();
        log.info("Entered Find All Products");
        try{
            List<Product> productList = productService.findAllProdcuts();
           return ResponseEntity.status(HttpStatus.OK).body(productList);
        }catch (ProductException e){
            log.error("Exception while fetching all products ={}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }finally {
            log.info("TIME_TAKEN for Fetching All Products ={}",Duration.between(Instant.now(),startTime).toMillis());
            log.info("Exited from Find All Products");
        }

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") final String id){
        Instant startTime = Instant.now();
        log.info("Entered Find Product By Id ={}",id);
        try{
            Product product = productService.findProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }catch (ProductException e){
            log.error("Exception while fetching a product ={}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }finally {
            log.info("TIME_TAKEN for Fetching a Product with Id ={}",Duration.between(Instant.now(),startTime).toMillis());
            log.info("Exited from Find All Products");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") final String id){
        Instant startTime = Instant.now();
        log.info("Entered Delete Product with Id ={}",id);
        try{
            productService.deletProduct(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (ProductException e){
            log.error("Exception while deleting  a product ={}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }finally {
            log.info("TIME_TAKEN for deleting a Product ={}",Duration.between(Instant.now(),startTime).toMillis());
            log.info("Exited from deleting product");
        }
    }





}
