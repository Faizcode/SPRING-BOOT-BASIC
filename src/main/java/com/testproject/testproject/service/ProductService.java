package com.testproject.testproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;

import com.testproject.testproject.model.Product;
import com.testproject.testproject.repository.ProductRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepo pr;

    public List<Product> gettingAllProduct(int page, int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        List<Product> resultProduct = pr.findAll(pageable).getContent();
        if(resultProduct.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The category data is empty:");
        }
        return resultProduct;
    }

    public Product gettingProductById(long id )
    {
        return pr.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No proudct found for this id: "+ id));
    }

    public Product addingNewProduct(Product product)
    {
        return pr.save(product);
    }

    public Product updatingProductById(long id, Product product)
    {
        Product existingProduct = pr.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product id is not found in the database:"+ id));
        existingProduct.setProductName(product.getProductName());
        return existingProduct;
    }

    public String deletingProductById(long id)
    {
        pr.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This id is not found in the category :"+id));
        pr.deleteById(id);
        return "Deleted Product Successfully by ID :";

    }
}
