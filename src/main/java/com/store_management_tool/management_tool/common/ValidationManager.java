package com.store_management_tool.management_tool.common;

import com.store_management_tool.management_tool.dto.ClothingProductDto;
import com.store_management_tool.management_tool.dto.ProductDto;

import java.util.Arrays;

public class ValidationManager {
    private static boolean isAValidPrice(Double price){
        return price > 0;
    }

    public static boolean isAValidProduct(ProductDto clothingProductDto){
        return isAValidPrice(clothingProductDto.getPrice());
    }

    public static boolean hasAllValidClothingAttributes(ClothingProductDto clothingProductDto){
        return Arrays.stream(BrandList.values()).toList().stream()
                .map(e -> e.toString())
                .toList()
                .contains(clothingProductDto.getBrand()) &&
                Arrays.stream(SeasonList.values()).toList().stream()
                        .map(e -> e.toString())
                        .toList()
                        .contains(clothingProductDto.getSeason());
    }
}
