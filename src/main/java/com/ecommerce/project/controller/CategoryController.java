package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Categories Added Successfully !!!", HttpStatus.CREATED);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategories(@Valid @RequestBody Category category, @PathVariable Long categoryId) {
        Category savedCategory = categoryService.updateCategory(category, categoryId);
        return new ResponseEntity<>("Category with ID: " + categoryId + " Update Successfully !!!", HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{CategoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long CategoryId) {
        String status = categoryService.deleteCategory(CategoryId);
        return new ResponseEntity<>(status, HttpStatus.OK);
        // return ResponseEntity.ok(status);
        // return ResponseEntity.status(HttpStatus.OK).body(status);
    }
}
