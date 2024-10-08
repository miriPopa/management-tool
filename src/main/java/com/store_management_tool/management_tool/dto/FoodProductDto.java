package com.store_management_tool.management_tool.dto;

import com.store_management_tool.management_tool.entity.Ingredient;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FoodProductDto extends ProductDto {
    private List<Ingredient> ingredients;

    public FoodProductDto(){
        this.ingredients = new ArrayList<>();
    }
}
