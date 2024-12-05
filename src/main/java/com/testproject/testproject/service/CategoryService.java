package com.testproject.testproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.testproject.testproject.model.Category;
import com.testproject.testproject.repository.CategoryRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepo cr;

    public List<Category> gettingAllcategory(int page, int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        List<Category> resultCategory = cr.findAll(pageable).getContent();
        if(resultCategory.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The category data is empty:");
        }
        return resultCategory;

    }
    
    public Category gettingCategoryById(long id)
    {
        return cr.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found for this id :"+ id));
    }

    public Category addingNewCategory(Category category)
    {
        return cr.save(category);
    }

    public Category updatingCategoryById(long id, Category category)
    {
        Category existingCategory = cr.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This id is not found in the category :"+ id));
        existingCategory.setCategoryName(category.getCategoryName());
        return existingCategory;
    }

    public String deletingCategoryById(long id)
    {
        cr.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This id is not found in the category :"+id));
        cr.deleteById(id);
        return "Deleted Category Successfully by ID :";

    }
}
