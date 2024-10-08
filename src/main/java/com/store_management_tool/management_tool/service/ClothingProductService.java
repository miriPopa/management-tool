package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.common.BrandList;
import com.store_management_tool.management_tool.dto.ClothingProductDto;
import com.store_management_tool.management_tool.dto.ProductWithChangedPrice;
import com.store_management_tool.management_tool.dto.mapper.MapperUtil;
import com.store_management_tool.management_tool.entity.ClothingProduct;
import com.store_management_tool.management_tool.handler.exception.NoResourceFoundException;
import com.store_management_tool.management_tool.handler.exception.NoValidProductException;
import com.store_management_tool.management_tool.repository.ClothingProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.store_management_tool.management_tool.common.ValidationManager.isAValidProduct;

@Service
public class ClothingProductService {
    @Autowired
    private ClothingProductRepository clothingProductRepository;
    public ClothingProductDto addClothingProduct(ClothingProductDto clothingProductDto){
        if (isAValidProduct(clothingProductDto))
            clothingProductRepository.save(MapperUtil.MAPPER.clothingProductDtoToClothingProduct(clothingProductDto));
        else
            throw new NoValidProductException();

        return clothingProductDto;
    }

    public ClothingProductDto changeThePrice(ProductWithChangedPrice productWithChangedPrice){
        ClothingProduct clothingProduct = clothingProductRepository
                .findByProductName(productWithChangedPrice.getName())
                .orElseThrow(NoResourceFoundException::new);

        clothingProduct.getProduct().setPrice(productWithChangedPrice.getPrice());
        clothingProductRepository.save(clothingProduct);

        return MapperUtil.MAPPER.clothingProductToClothingProductDto(clothingProduct);
    }

    public List<ClothingProductDto> findByBrand(BrandList brand){
        List<ClothingProduct> clothingProducts = clothingProductRepository.findByBrand(brand).orElseThrow(
                NoResourceFoundException::new);

        return clothingProducts.stream()
                .map(MapperUtil.MAPPER::clothingProductToClothingProductDto)
                .collect(Collectors.toList());
    }
}
