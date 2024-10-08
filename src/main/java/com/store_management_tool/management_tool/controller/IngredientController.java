package com.store_management_tool.management_tool.controller;

import com.store_management_tool.management_tool.dto.IngredientDto;
import com.store_management_tool.management_tool.service.IngredientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Ingredient", description = "Ingredient endpoints")
@RequestMapping("v1")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/create/ingredient")
    @PreAuthorize("@jwtInfo.isAuthorized(authentication, 'ADMIN', 'group_admin')")
    public ResponseEntity<String> createIngredient(@RequestBody IngredientDto ingredientDto) {
        return new ResponseEntity<>(ingredientService.addIngredient(ingredientDto), HttpStatus.CREATED);
    }
}
