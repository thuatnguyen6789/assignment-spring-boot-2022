package com.example.springbootthuatdev.api;

import com.example.springbootthuatdev.entity.Product;
import com.example.springbootthuatdev.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/products")
@CrossOrigin(value = "*")

public class ProductApi {
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> findAll(){
        return productRepository.findAll();
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public Product findById(@PathVariable String id) {
        Optional<Product> optionalProduct = productRepository.findById(String.valueOf(id));
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Product create(@RequestBody Product product){
        productRepository.save(product);
        return product;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public boolean deleteById(@PathVariable String id){
        Optional<Product> optionalProduct = productRepository.findById(String.valueOf(id));
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            productRepository.delete(product);
        }
        return false;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public Product updateById(@RequestBody Product updateProduct, @PathVariable String id){
        Optional<Product> optionalProduct = productRepository.findById(String.valueOf(id));
        if(optionalProduct.isPresent()){
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(updateProduct.getName());
            existingProduct.setPrice(updateProduct.getPrice());
            existingProduct.setDescription(updateProduct.getDescription());
            existingProduct.setDetail(updateProduct.getDetail());
            existingProduct.setThumbnails(updateProduct.getThumbnails());
            existingProduct.setCreatedAt(updateProduct.getCreatedAt());
            existingProduct.setUpdatedAt(updateProduct.getUpdatedAt());
            existingProduct.setDeletedAt(updateProduct.getDeletedAt());
            existingProduct.setCreatedBy(updateProduct.getCreatedBy());
            existingProduct.setUpdatedBy(updateProduct.getUpdatedBy());
            existingProduct.setDeletedBy(updateProduct.getDeletedBy());
            existingProduct.setStatus(updateProduct.getStatus());
            productRepository.save(existingProduct);
        }
        return null;
    }
}
