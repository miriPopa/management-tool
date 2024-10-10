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
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.store_management_tool.management_tool.common.ValidationManager.isAValidProduct;

@Service
public class FoodProductService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FoodProductService.class);

    @Autowired
    private FoodProductRepository foodProductRepository;

    @Autowired
    private IngredientService ingredientService;

    @Transactional
    public String addFoodProduct(FoodProductInfoDto foodProductInfoDto){
        LOGGER.info("Food product adding is in progress");

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
            LOGGER.info("Food product adding with success");

            return "Product added successfully";

        } else {
            LOGGER.error("Food product adding failed");
            throw new NoValidProductException();
        }
    }


    public FoodProductDto changeThePrice(ProductWithChangedPrice productWithChangedPrice){
        LOGGER.info("Price changing is in progress");

        if (isAValidProduct(MapperUtil.MAPPER.productWithChangedPriceToProductDto(productWithChangedPrice))) {

            FoodProduct foodProduct = foodProductRepository
                    .findByProductName(productWithChangedPrice.getName())
                    .orElseThrow(NoResourceFoundException::new);

            foodProduct.getProduct().setPrice(productWithChangedPrice.getPrice());
            FoodProduct savedProduct = foodProductRepository.save(foodProduct);

            FoodProductDto foodProductDto = MapperUtil.MAPPER.foodProductToFoodProductDto(savedProduct);
            LOGGER.info("Price changing with success");

            return foodProductDto;
        } else {
            LOGGER.error("Price changing failed");
            throw new NoValidProductException();
        }
    }
}
