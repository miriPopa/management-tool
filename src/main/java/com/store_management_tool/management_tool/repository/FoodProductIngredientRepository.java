package com.store_management_tool.management_tool.repository;

import com.store_management_tool.management_tool.entity.FoodProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodProductIngredientRepository extends JpaRepository<FoodProductIngredient, UUID> {
    Optional<List<FoodProductIngredient>> findByIngredientId(UUID id);
}
