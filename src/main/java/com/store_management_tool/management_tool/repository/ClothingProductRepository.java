package com.store_management_tool.management_tool.repository;

import com.store_management_tool.management_tool.common.BrandList;
import com.store_management_tool.management_tool.entity.ClothingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ClothingProductRepository extends JpaRepository<ClothingProduct, UUID> {
    Optional<ClothingProduct> findByProductName(String name);
    Optional<List<ClothingProduct>> findByBrand(BrandList brand);
}
