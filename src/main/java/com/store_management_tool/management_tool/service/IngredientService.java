package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.dto.IngredientDto;
import com.store_management_tool.management_tool.dto.mapper.MapperUtil;
import com.store_management_tool.management_tool.entity.Ingredient;
import com.store_management_tool.management_tool.handler.exception.NoResourceFoundException;
import com.store_management_tool.management_tool.repository.IngredientRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(IngredientService.class);

    @Autowired
    private IngredientRepository ingredientRepository;

    public String addIngredient(IngredientDto ingredientDto){
        LOGGER.info("Ingredient adding is in progress");

        Ingredient ingredient = MapperUtil.MAPPER.ingredientDtoToIngredient(ingredientDto);
        ingredientRepository.save(ingredient);

        LOGGER.info("Ingredient adding with success");

        return "Product added successfully";
    }

    public Ingredient findIngredientByName(String name){
        LOGGER.info("Searching by name");

        return ingredientRepository.findByName(name).orElseThrow(NoResourceFoundException::new);
    }
}
