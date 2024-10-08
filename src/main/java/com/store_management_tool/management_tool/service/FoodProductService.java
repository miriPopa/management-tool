package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.dto.FoodProductDto;
import com.store_management_tool.management_tool.dto.FoodProductInfoDto;
import com.store_management_tool.management_tool.dto.ProductWithChangedPrice;
import com.store_management_tool.management_tool.dto.mapper.MapperUtil;
import com.store_management_tool.management_tool.entity.FoodProduct;
import com.store_management_tool.management_tool.entity.FoodProductIngredient;
import com.store_management_tool.management_tool.entity.Ingredient;
import com.store_management_tool.management_tool.handler.exception.NoResourceFoundException;
import com.store_management_tool.management_tool.handler.exception.NoValidProductException;
import com.store_management_tool.management_tool.repository.FoodProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.store_management_tool.management_tool.common.ValidationManager.isAValidProduct;

@Service
public class FoodProductService {
    @Autowired
    private FoodProductRepository foodProductRepository;

    @Autowired
    private IngredientService ingredientService;

    @Transactional
    public String addFoodProduct(FoodProductInfoDto foodProductInfoDto){
        if (isAValidProduct(foodProductInfoDto)) {

            FoodProductDto foodProductDto = MapperUtil.MAPPER.foodProductRequestToFoodProductDto(foodProductInfoDto);
            FoodProduct foodProduct = MapperUtil.MAPPER.foodProductDtoToFoodProduct(foodProductDto);

            foodProductInfoDto.getIngredientDtoList().forEach(e ->{
                    FoodProductIngredient foodProductIngredient = new FoodProductIngredient();

                    foodProductIngredient.setFoodProduct(foodProduct);

                    Ingredient ingredient = ingredientService.findIngredientByName(e.getName());
                    foodProductIngredient.setIngredient(ingredient);

                    foodProduct.getFoodProductIngredients().add(foodProductIngredient);
            });
            foodProductRepository.save(foodProduct);

            return "Product added successfully";

        } else {
            throw new NoValidProductException();
        }
    }


    public FoodProductDto changeThePrice(ProductWithChangedPrice productWithChangedPrice){
        FoodProduct foodProduct = foodProductRepository
                .findByProductName(productWithChangedPrice.getName())
                .orElseThrow(NoResourceFoundException::new);

        foodProduct.getProduct().setPrice(productWithChangedPrice.getPrice());
        foodProductRepository.save(foodProduct);

        return MapperUtil.MAPPER.foodProductToFoodProductDto(foodProduct);
    }
}
