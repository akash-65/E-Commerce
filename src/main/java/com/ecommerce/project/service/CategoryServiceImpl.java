package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        // Validation
        if (categories.isEmpty()) {
            throw new APIException("No Category created till now !!!");
        }

        // Map every object from Category with the Category DTO class
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        // Save the newly created categoryDTOS into the categoryResponse
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        // We are getting CategoryDTO from RequestBody but to save it in DB we need to convert it into
        // Category Entity
        Category category = modelMapper.map(categoryDTO, Category.class);

        // Validation
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDb != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");
        }

        // Save
        Category savedCategory = categoryRepository.save(category);

        // Now we got saved Category Entity but send to user Response we need to convert it into DTO
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        // We are getting CategoryDTO from RequestBody but to save it in DB we need to convert it into
        // Category Entity
        Category category = modelMapper.map(categoryDTO, Category.class);

        // Validation
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        category.setCategoryID(categoryId);

        // Save
        category.setCategoryID(categoryId);
        savedCategory = categoryRepository.save(category);

        // Now we got saved Category Entity but send to user Response we need to convert it into DTO
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        // Validation
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        // Delete
        categoryRepository.delete(category);

        // Now we got saved Category Entity but send to user Response we need to convert it into DTO
        return modelMapper.map(category, CategoryDTO.class);
    }


}
