package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.common.ValidationManager;
import com.store_management_tool.management_tool.dto.*;
import com.store_management_tool.management_tool.entity.FoodProduct;
import com.store_management_tool.management_tool.entity.FoodProductIngredient;
import com.store_management_tool.management_tool.entity.Ingredient;
import com.store_management_tool.management_tool.entity.Product;
import com.store_management_tool.management_tool.handler.exception.NoValidProductException;
import com.store_management_tool.management_tool.repository.FoodProductRepository;
import com.store_management_tool.management_tool.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodProductServiceTest {
    @InjectMocks
    private FoodProductService foodProductService;

    @Mock
    private FoodProductRepository foodProductRepository;

    @Mock
    private IngredientService ingredientService;

    @Test
    public void addFoodProductHappyScenarioTest() {
        // given
        boolean isValidProduct = true;
        FoodProductInfoDto foodProductInfoDto = new FoodProductInfoDto("pizza", 20.50, 10,
                List.of(new IngredientDto("salami"),
                        new IngredientDto("cheese"),
                        new IngredientDto("mushrooms")));

        try (MockedStatic<ValidationManager> mockedStatic = mockStatic(ValidationManager.class)) {
            // when
            when(ValidationManager.isAValidProduct(any())).thenReturn(isValidProduct);
            when(ingredientService.findIngredientByName(any())).thenReturn(any());

            String result = foodProductService.addFoodProduct(foodProductInfoDto);

            // then
            assertEquals("Product added successfully", result);
        }
    }

    @Test
    public void addFoodProductNoValidProductExceptionTest() {
        boolean isValidProduct = false;

        try (MockedStatic<ValidationManager> mockedStatic = mockStatic(ValidationManager.class)) {
            // when
            when(ValidationManager.isAValidProduct(any())).thenReturn(isValidProduct);

            // then
            assertThrows(NoValidProductException.class, () -> {
                foodProductService.addFoodProduct(any());
            });
        }
    }

    @Test
    public void changeThePriceHappyScenarioTest() {
        // given
        boolean isValidProduct = true;
        Double updatedPrice = 30.90;
        FoodProduct foodProduct = new FoodProduct(UUID.randomUUID(),
                List.of(new FoodProductIngredient()),
                new Product("pizza", 20.90, 7));

        ProductWithChangedPrice product = new ProductWithChangedPrice("pizza", updatedPrice);

        try (MockedStatic<ValidationManager> mockedStatic = mockStatic(ValidationManager.class)) {
            // when
            when(ValidationManager.isAValidProduct(any())).thenReturn(isValidProduct);
            when(foodProductRepository.findByProductName(any())).thenReturn(Optional.of(foodProduct));
            foodProduct.getProduct().setPrice(30.90);
            when(foodProductRepository.save(foodProduct)).thenReturn(foodProduct);

            FoodProductDto result = foodProductService.changeThePrice(product);
            assertEquals(updatedPrice, result.getPrice());
        }
    }

    @Test
    public void changeThePriceNoValidProductExceptionTest() {
        // given
        boolean isValidProduct = false;

        try (MockedStatic<ValidationManager> mockedStatic = mockStatic(ValidationManager.class)) {
            // when
            when(ValidationManager.isAValidProduct(any())).thenReturn(isValidProduct);

            // then
            assertThrows(NoValidProductException.class, () -> {
                foodProductService.changeThePrice(any());
            });
        }
    }
}
