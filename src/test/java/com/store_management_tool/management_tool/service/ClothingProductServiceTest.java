package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.common.BrandList;
import com.store_management_tool.management_tool.common.SeasonList;
import com.store_management_tool.management_tool.common.ValidationManager;
import com.store_management_tool.management_tool.dto.ClothingProductDto;
import com.store_management_tool.management_tool.dto.ProductWithChangedPrice;
import com.store_management_tool.management_tool.entity.ClothingProduct;
import com.store_management_tool.management_tool.entity.Product;
import com.store_management_tool.management_tool.handler.exception.NoValidProductException;
import com.store_management_tool.management_tool.repository.ClothingProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClothingProductServiceTest {

    @InjectMocks
    private ClothingProductService clothingProductService;

    @Mock
    private ClothingProductRepository clothingProductRepository;

    @ParameterizedTest
    @CsvSource({
            "true, false",
            "false, true",
            "false, false"
    })
    public void createClothingProductNoValidProductExceptionTest(Boolean isValidProduct, Boolean hasValidAttributes) {

        try (MockedStatic<ValidationManager> mockedStatic = mockStatic(ValidationManager.class)) {
            // when
            when(ValidationManager.isAValidProduct(any())).thenReturn(isValidProduct);
            when(ValidationManager.hasAllValidClothingAttributes(any())).thenReturn(hasValidAttributes);

            // then
            assertThrows(NoValidProductException.class, () -> {
                clothingProductService.addClothingProduct(any());
            });
        }
    }

    @ParameterizedTest
    @MethodSource("provideClothingData")
    public void createClothingProductHappyScenarioTest(ClothingProduct clothingProduct) {
        // given
        ClothingProductDto clothingProductDto = new ClothingProductDto(
                clothingProduct.getProduct().getName(),
                clothingProduct.getProduct().getPrice(),
                clothingProduct.getProduct().getStock(),
                clothingProduct.getSeason().toString(),
                clothingProduct.getBrand().toString()
                );

        try (MockedStatic<ValidationManager> mockedStatic = mockStatic(ValidationManager.class)) {
            // when
            when(ValidationManager.isAValidProduct(clothingProductDto)).thenReturn(true);
            when(ValidationManager.hasAllValidClothingAttributes(clothingProductDto)).thenReturn(true);
            when(clothingProductRepository.save(any(ClothingProduct.class))).thenReturn(clothingProduct);

            ClothingProductDto result = clothingProductService.addClothingProduct(clothingProductDto);

            // then
            assertEquals(clothingProductDto.getPrice(), result.getPrice());
            assertEquals(clothingProductDto.getName(), result.getName());
            assertEquals(clothingProductDto.getStock(), result.getStock());
            assertEquals(clothingProductDto.getBrand(), result.getBrand());
            assertEquals(clothingProductDto.getSeason(), result.getSeason());
        }
    }

    @ParameterizedTest
    @MethodSource("provideClothingData")
    public void changeThePriceHappyScenarioTest(ClothingProduct clothingProduct) {
        // given
        Double updatedPrice = 40.88;
        ProductWithChangedPrice product = new ProductWithChangedPrice(clothingProduct.getProduct().getName(), updatedPrice);

        try (MockedStatic<ValidationManager> mockedStatic = mockStatic(ValidationManager.class)) {
            // when
            when(ValidationManager.isAValidProduct(any())).thenReturn(true);
            when(clothingProductRepository.findByProductName(anyString())).thenReturn(
                    Optional.of(clothingProduct));
            clothingProduct.getProduct().setPrice(updatedPrice);
            when(clothingProductRepository.save(any(ClothingProduct.class))).thenReturn(clothingProduct);

            // then
            assertEquals(updatedPrice, clothingProductService.changeThePrice(product).getPrice());
        }
    }

    @Test
    public void changeThePriceNoValidProductExceptionTest() {
        // given
        ProductWithChangedPrice product = new ProductWithChangedPrice("t-shirt", 0.0);

        try (MockedStatic<ValidationManager> mockedStatic = mockStatic(ValidationManager.class)) {
            // when
            when(ValidationManager.isAValidProduct(any())).thenReturn(false);

            // then
            assertThrows(NoValidProductException.class, () -> {
                clothingProductService.changeThePrice(product);
            });
        }
    }

    @ParameterizedTest
    @MethodSource("provideClothingDataByBrand")
    public void findByBrandHappyScenarioTest(List<ClothingProduct> clothingProducts, int sizeResult, BrandList brand) {
        // when
        when(clothingProductRepository.findByBrand(any())).thenReturn(Optional.of(clothingProducts));

        List<ClothingProductDto> result = clothingProductService.findByBrand(brand);

        // then
        assertEquals(sizeResult, result.size());
        if (clothingProducts.size() > 0) {
            assertEquals(clothingProducts.get(0).getProduct().getName(), result.get(0).getName());
            assertEquals(clothingProducts.get(0).getBrand().toString(), result.get(0).getBrand());
        }
    }

    private static Stream<Arguments> provideClothingDataByBrand(){
        return Stream.of(
                Arguments.of((List.of(new ClothingProduct(UUID.randomUUID(),SeasonList.SPRING,
                BrandList.ZARA, new Product("t-shirt", 30.88, 7)))), 1, BrandList.ZARA),
                Arguments.of(Collections.emptyList(), 0, BrandList.ZARA)
        );
    }

    private static Stream<Arguments> provideClothingData(){
        return Stream.of(
                Arguments.of(new ClothingProduct(UUID.randomUUID(),SeasonList.SPRING,
                        BrandList.ZARA, new Product("t-shirt", 30.88, 7)))
        );
    }

}
