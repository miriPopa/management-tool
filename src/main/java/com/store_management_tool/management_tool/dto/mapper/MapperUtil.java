package com.store_management_tool.management_tool.dto.mapper;

import com.store_management_tool.management_tool.common.BrandList;
import com.store_management_tool.management_tool.dto.*;
import com.store_management_tool.management_tool.entity.ClothingProduct;
import com.store_management_tool.management_tool.entity.FoodProduct;
import com.store_management_tool.management_tool.entity.Ingredient;
import com.store_management_tool.management_tool.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface MapperUtil {

    MapperUtil MAPPER = Mappers.getMapper(MapperUtil.class);

    @Mapping(target = "product.name", source = "name")
    @Mapping(target = "product.price", source = "price")
    @Mapping(target = "product.stock", source = "stock")
    @Mapping(target = "brand", expression = "java(convertFromStringToEnum(clothingProductDto))")
    ClothingProduct clothingProductDtoToClothingProduct(ClothingProductDto clothingProductDto);

    @Mapping(target = "name", expression = "java(getProductName(clothingProduct.getProduct()))")
    @Mapping(target = "price", expression = "java(getProductPrice(clothingProduct.getProduct()))")
    @Mapping(target = "stock", expression = "java(getProductStock(clothingProduct.getProduct()))")
    ClothingProductDto clothingProductToClothingProductDto(ClothingProduct clothingProduct);

    @Mapping(target = "product.name", source = "name")
    @Mapping(target = "product.price", source = "price")
    @Mapping(target = "product.stock", source = "stock")
    FoodProduct foodProductDtoToFoodProduct(FoodProductDto foodProductDto);

    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "stock", source = "product.stock")
    FoodProductDto foodProductToFoodProductDto(FoodProduct foodProduct);

    @Mapping(target = "ingredients", expression =
            "java(ingredientDtoToIngredientList(foodProductInfoDto.getIngredientDtoList()))")
    FoodProductDto foodProductRequestToFoodProductDto(FoodProductInfoDto foodProductInfoDto);

    Ingredient ingredientDtoToIngredient(IngredientDto ingredient);

    ProductDto productWithChangedPriceToProductDto(ProductWithChangedPrice product);

    default List<Ingredient> ingredientDtoToIngredientList(List<IngredientDto> songList) {
        return songList.stream()
                .map(e -> ingredientDtoToIngredient(e))
                .collect(Collectors.toList());
    }

    default BrandList convertFromStringToEnum(ClothingProductDto clothingProductDto){
        return BrandList.valueOf(clothingProductDto.getBrand());
    }
    default String getProductName(Product product){
        return product.getName();
    }
    default Double getProductPrice(Product product){
        return product.getPrice();
    }
    default Integer getProductStock(Product product ){
        return product.getStock();
    }
}
