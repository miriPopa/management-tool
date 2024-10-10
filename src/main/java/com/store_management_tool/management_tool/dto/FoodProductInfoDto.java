package com.store_management_tool.management_tool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodProductInfoDto extends ProductDto{
    private List<IngredientDto > ingredientDtoList;

    public FoodProductInfoDto(String name, Double price, Integer stock, List<IngredientDto> ingredientDtoList) {
        super(name, price, stock);
        this.ingredientDtoList = ingredientDtoList;
    }
}
