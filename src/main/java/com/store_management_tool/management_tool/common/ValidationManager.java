package com.store_management_tool.management_tool.common;

import com.store_management_tool.management_tool.dto.ClothingProductDto;
import com.store_management_tool.management_tool.dto.ProductDto;

public class ValidationManager {
    private static boolean isAValidPrice(Double price){
        return price > 0;
    }

    public static boolean isAValidProduct(ProductDto clothingProductDto){
        return isAValidPrice(clothingProductDto.getPrice());
    }
}
