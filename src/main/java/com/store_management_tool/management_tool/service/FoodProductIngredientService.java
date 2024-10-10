package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.entity.FoodProduct;
import com.store_management_tool.management_tool.entity.FoodProductIngredient;
import com.store_management_tool.management_tool.entity.Ingredient;
import com.store_management_tool.management_tool.handler.exception.NoResourceFoundException;
import com.store_management_tool.management_tool.repository.FoodProductIngredientRepository;
import com.store_management_tool.management_tool.repository.FoodProductRepository;
import com.store_management_tool.management_tool.repository.IngredientRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodProductIngredientService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FoodProductIngredientService.class);

    @Autowired
    private FoodProductIngredientRepository foodProductIngredientRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private FoodProductRepository foodProductRepository;

    public List<String> findByIngredient(String ingredientName){
        LOGGER.info("Searching is in progress");

        Ingredient ingredient = ingredientRepository.findByName(ingredientName).orElseThrow(NoResourceFoundException::new);
        List<FoodProductIngredient> foodProductIngredients =
                foodProductIngredientRepository.findByIngredientId(ingredient.getId()).orElseThrow(NoResourceFoundException::new);

        List<String> listResult = foodProductIngredients.stream().map(e-> {
            FoodProduct foodProduct = foodProductRepository.findById(e.getFoodProduct().getId()).orElseThrow(NoResourceFoundException::new);
            return foodProduct.getProduct().getName();
        }).toList();
        LOGGER.info("Searching with success");

        return listResult;
    }
}
