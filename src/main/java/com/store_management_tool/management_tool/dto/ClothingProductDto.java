package com.store_management_tool.management_tool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClothingProductDto extends ProductDto{
    private String season;
    private String brand;

    public ClothingProductDto(String name, Double price, Integer stock, String season, String brand) {
        super(name, price, stock);
        this.season = season;
        this.brand = brand;
    }
}
