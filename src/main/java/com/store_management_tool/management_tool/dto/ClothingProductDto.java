package com.store_management_tool.management_tool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClothingProductDto extends  ProductDto{
    private String season;
    private String brand;
    private String name;
    private Double price;
    private Integer stock;
}
