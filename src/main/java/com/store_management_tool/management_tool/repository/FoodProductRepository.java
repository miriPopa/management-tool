package com.store_management_tool.management_tool.repository;

import com.store_management_tool.management_tool.entity.FoodProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodProductRepository extends JpaRepository<FoodProduct, UUID> {
    Optional<FoodProduct> findByProductName(String name);


}
