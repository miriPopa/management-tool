package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.dto.IngredientDto;
import com.store_management_tool.management_tool.dto.mapper.MapperUtil;
import com.store_management_tool.management_tool.entity.Ingredient;
import com.store_management_tool.management_tool.handler.exception.NoResourceFoundException;
import com.store_management_tool.management_tool.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public String addIngredient(IngredientDto ingredientDto){
        Ingredient ingredient = MapperUtil.MAPPER.ingredientDtoToIngredient(ingredientDto);
        ingredientRepository.save(ingredient);

        return "Product added successfully";
    }

    public Ingredient findIngredientByName(String name){
        return ingredientRepository.findByName(name).orElseThrow(NoResourceFoundException::new);
    }
}
