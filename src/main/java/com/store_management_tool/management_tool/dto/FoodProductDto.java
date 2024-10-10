package com.store_management_tool.management_tool.dto;

import com.store_management_tool.management_tool.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodProductDto extends ProductDto {
    private List<Ingredient> ingredients;

    public FoodProductDto(String name, Double price, Integer stock, List<Ingredient> ingredients) {
        super(name, price, stock);
        this.ingredients = ingredients;
    }
}
