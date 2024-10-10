package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.entity.FoodProduct;
import com.store_management_tool.management_tool.entity.FoodProductIngredient;
import com.store_management_tool.management_tool.entity.Ingredient;
import com.store_management_tool.management_tool.entity.Product;
import com.store_management_tool.management_tool.handler.exception.NoResourceFoundException;
import com.store_management_tool.management_tool.repository.FoodProductIngredientRepository;
import com.store_management_tool.management_tool.repository.FoodProductRepository;
import com.store_management_tool.management_tool.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FoodProductIngredientServiceTest {

    @InjectMocks
    private FoodProductIngredientService foodProductIngredientService;

    @Mock
    private FoodProductIngredientRepository foodProductIngredientRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private FoodProductRepository foodProductRepository;

    @ParameterizedTest
    @MethodSource("provideData")
    public void findByIngredientHappyScenarioTest(Ingredient ingredient, FoodProduct foodProduct, String ingredientName){
        // when
        when(ingredientRepository.findByName(ingredientName)).thenReturn(Optional.of(ingredient));
        when(foodProductIngredientRepository.findByIngredientId(ingredient.getId()))
                .thenReturn(Optional.of(List.of(new FoodProductIngredient(UUID.randomUUID(), foodProduct, ingredient))));
        when(foodProductRepository.findById(foodProduct.getId())).thenReturn(Optional.of(foodProduct));

        List<String> result = foodProductIngredientService.findByIngredient(ingredientName);

        // then
        assertEquals(1, result.size());
        assertEquals("pizza", result.get(0));
    }

    @Test
    public void findByIngredientNoResourceFoundExceptionNoIngredientTest(){
        // when
        when(ingredientRepository.findByName(any())).thenReturn(Optional.empty());

        // then
        assertThrows(NoResourceFoundException.class, () -> {
            foodProductIngredientService.findByIngredient(any());
        });
    }

    @Test
    public void findByIngredientNoResourceFoundExceptionNoFoodProductIngredientTest(){
        // when
        when(ingredientRepository.findByName(any())).thenReturn(Optional.of(new Ingredient()));

        // then
        assertThrows(NoResourceFoundException.class, () -> {
            foodProductIngredientService.findByIngredient(any());
        });
    }

    @ParameterizedTest
    @MethodSource("provideDataTest")
    public void findByIngredientNoResourceFoundExceptionNoFoodProductTest(Ingredient ingredient,
                                                                          FoodProduct foodProduct){
        // when
        when(ingredientRepository.findByName(any())).thenReturn(Optional.of(ingredient));
        when(foodProductIngredientRepository.findByIngredientId(any())).thenReturn(
                Optional.of(List.of(new FoodProductIngredient(UUID.randomUUID(), foodProduct,ingredient))));
        when(foodProductRepository.findById(foodProduct.getId())).thenReturn(Optional.empty());

        // then
        assertThrows(NoResourceFoundException.class, () -> {
            foodProductIngredientService.findByIngredient(any());
        });
    }

    private static Stream<Arguments> provideData(){
        return Stream.of(
                Arguments.of(
                        new Ingredient(UUID.randomUUID(), "cheese", Collections.emptyList()),
                        new FoodProduct(UUID.randomUUID(), Collections.emptyList(),
                                new Product("pizza", 20.89, 9)),
                        "cheese")
        );
    }

    private static Stream<Arguments> provideDataTest(){
        return Stream.of(
                Arguments.of(
                        new Ingredient(UUID.randomUUID(), "cheese", Collections.emptyList()),
                        new FoodProduct(UUID.randomUUID(), Collections.emptyList(),
                                new Product("pizza", 20.89, 9)))
        );
    }
}
