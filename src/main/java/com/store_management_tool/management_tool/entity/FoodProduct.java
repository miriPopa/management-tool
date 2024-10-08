package com.store_management_tool.management_tool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "food_product")
public class FoodProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "foodProduct", cascade = CascadeType.ALL)
    List<FoodProductIngredient> foodProductIngredients = new ArrayList<>();

    @Embedded
    private Product product;
}
