package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.common.BrandList;
import com.store_management_tool.management_tool.dto.ClothingProductDto;
import com.store_management_tool.management_tool.dto.ProductWithChangedPrice;
import com.store_management_tool.management_tool.dto.mapper.MapperUtil;
import com.store_management_tool.management_tool.entity.ClothingProduct;
import com.store_management_tool.management_tool.handler.exception.NoResourceFoundException;
import com.store_management_tool.management_tool.handler.exception.NoValidProductException;
import com.store_management_tool.management_tool.repository.ClothingProductRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.store_management_tool.management_tool.common.ValidationManager.hasAllValidClothingAttributes;
import static com.store_management_tool.management_tool.common.ValidationManager.isAValidProduct;

@Service
public class ClothingProductService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ClothingProductService.class);

    @Autowired
    private ClothingProductRepository clothingProductRepository;

    public ClothingProductDto addClothingProduct(ClothingProductDto clothingProductDto){
        LOGGER.info("Clothing product adding is in progress");
        ClothingProduct clothingProduct;

        if (isAValidProduct(clothingProductDto) && hasAllValidClothingAttributes(clothingProductDto))
            clothingProduct =clothingProductRepository.save(
                    MapperUtil.MAPPER.clothingProductDtoToClothingProduct(clothingProductDto));
        else {
            LOGGER.error("Clothing product adding failed");
            throw new NoValidProductException();
        }
        LOGGER.info("Clothing product adding with success");

        return MapperUtil.MAPPER.clothingProductToClothingProductDto(clothingProduct);
    }

    public ClothingProductDto changeThePrice(ProductWithChangedPrice productWithChangedPrice){
        LOGGER.info("Price changing is in progress");

        if (isAValidProduct(MapperUtil.MAPPER.productWithChangedPriceToProductDto(productWithChangedPrice))) {

            ClothingProduct clothingProduct = clothingProductRepository
                    .findByProductName(productWithChangedPrice.getName())
                    .orElseThrow(NoResourceFoundException::new);

            clothingProduct.getProduct().setPrice(productWithChangedPrice.getPrice());
            ClothingProduct savedProduct = clothingProductRepository.save(clothingProduct);

            ClothingProductDto clothingProductDto = MapperUtil.MAPPER.clothingProductToClothingProductDto(savedProduct);
            LOGGER.info("Price changing with success");

            return clothingProductDto;
        } else {
            LOGGER.error("Price changing failed");
            throw new NoValidProductException();
        }
    }

    public List<ClothingProductDto> findByBrand(BrandList brand){
        LOGGER.info("Searching is in progress");

        List<ClothingProduct> clothingProducts = clothingProductRepository.findByBrand(brand).orElseThrow(
                NoResourceFoundException::new);

        List<ClothingProductDto>  listResult = clothingProducts.stream()
                .map(MapperUtil.MAPPER::clothingProductToClothingProductDto)
                .collect(Collectors.toList());
        LOGGER.info("Searching with success");

        return listResult;
    }
}
