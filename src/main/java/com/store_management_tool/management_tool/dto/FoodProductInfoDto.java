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
    List<IngredientDto > ingredientDtoList;
}
