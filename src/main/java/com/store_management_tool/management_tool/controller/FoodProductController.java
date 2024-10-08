package com.store_management_tool.management_tool.controller;

import com.store_management_tool.management_tool.common.BrandList;
import com.store_management_tool.management_tool.dto.ClothingProductDto;
import com.store_management_tool.management_tool.dto.FoodProductDto;
import com.store_management_tool.management_tool.dto.FoodProductInfoDto;
import com.store_management_tool.management_tool.dto.ProductWithChangedPrice;
import com.store_management_tool.management_tool.service.FoodProductIngredientService;
import com.store_management_tool.management_tool.service.FoodProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Food", description = "Food Product endpoints")
@RequestMapping("v1")
public class FoodProductController {

    @Autowired
    private FoodProductService foodProductService;

    @Autowired
    private FoodProductIngredientService foodProductIngredientService;

    @PostMapping("/create/food_product")
    @PreAuthorize("@jwtInfo.isAuthorized(authentication, 'ADMIN', 'group_admin')")
    public ResponseEntity<String> createClothingProduct(@RequestBody FoodProductInfoDto foodProductRequestDto) {
        return new ResponseEntity<>(foodProductService.addFoodProduct(foodProductRequestDto), HttpStatus.CREATED);
    }

    @PatchMapping("/update/food_product")
    @PreAuthorize("@jwtInfo.isAuthorized(authentication, T(java.util.Arrays).asList(new com.store_management_tool.management_tool.common.AuthorizationInfo('ADMIN', 'group_admin'), new com.store_management_tool.management_tool.common.AuthorizationInfo('EDIT', 'group_user')))")
    public ResponseEntity<FoodProductDto> updateFoodProduct(
            @RequestBody ProductWithChangedPrice productWithChangedPrice) {
        return new ResponseEntity<>(foodProductService.changeThePrice(productWithChangedPrice), HttpStatus.OK);
    }

    @GetMapping("/search/food_product")
    @PreAuthorize("@jwtInfo.isAuthorized(authentication, T(java.util.Arrays).asList(new com.store_management_tool.management_tool.common.AuthorizationInfo('ADMIN', 'group_admin'), new com.store_management_tool.management_tool.common.AuthorizationInfo('EDIT', 'group_user'), new com.store_management_tool.management_tool.common.AuthorizationInfo('VIEW', 'group_user')))")
    public ResponseEntity<List<String>> searchFoodProductByIngredient(@RequestParam String ingredientName) {
        return new ResponseEntity<>(foodProductIngredientService.findByIngredient(ingredientName), HttpStatus.OK);
    }
}
