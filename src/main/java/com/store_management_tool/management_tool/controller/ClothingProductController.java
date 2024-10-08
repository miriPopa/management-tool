package com.store_management_tool.management_tool.controller;

import com.store_management_tool.management_tool.common.BrandList;
import com.store_management_tool.management_tool.dto.ClothingProductDto;
import com.store_management_tool.management_tool.dto.ProductWithChangedPrice;
import com.store_management_tool.management_tool.service.ClothingProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Clothing", description = "Clothing Product endpoints")
@RequestMapping("v1")
public class ClothingProductController {
    @Value("${role.rolename}")
    private String principleAttribute;
    @Autowired
    private ClothingProductService clothingProductService;

    @PostMapping("/create/clothing_product")
    @PreAuthorize("@jwtInfo.isAuthorized(authentication, 'ADMIN', 'group_admin')")
    public ResponseEntity<ClothingProductDto> createClothingProduct(@RequestBody ClothingProductDto clothingProductDto) {
        return new ResponseEntity<>(clothingProductService.addClothingProduct(clothingProductDto), HttpStatus.CREATED);
    }

    @PatchMapping("/update/clothing_product")
    @PreAuthorize("@jwtInfo.isAuthorized(authentication, T(java.util.Arrays).asList(new com.store_management_tool.management_tool.common.AuthorizationInfo('ADMIN', 'group_admin'), new com.store_management_tool.management_tool.common.AuthorizationInfo('EDIT', 'group_user')))")
    public ResponseEntity<ClothingProductDto> updateClothingProduct(
            @RequestBody ProductWithChangedPrice productWithChangedPrice) {
        return new ResponseEntity<>(clothingProductService.changeThePrice(productWithChangedPrice), HttpStatus.OK);
    }

    @GetMapping("/search/clothing_product")
    @PreAuthorize("@jwtInfo.isAuthorized(authentication, T(java.util.Arrays).asList(new com.store_management_tool.management_tool.common.AuthorizationInfo('ADMIN', 'group_admin'), new com.store_management_tool.management_tool.common.AuthorizationInfo('EDIT', 'group_user'), new com.store_management_tool.management_tool.common.AuthorizationInfo('VIEW', 'group_user')))")
    public ResponseEntity<List<ClothingProductDto>> searchClothingProductByBrand(@RequestParam BrandList brand) {
        return new ResponseEntity<>(clothingProductService.findByBrand(brand), HttpStatus.OK);
    }
}
