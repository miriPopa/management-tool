package com.store_management_tool.management_tool.entity;

import com.store_management_tool.management_tool.common.BrandList;
import com.store_management_tool.management_tool.common.SeasonList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clothing_product")
public class ClothingProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SeasonList season;

    @Enumerated(EnumType.STRING)
    private BrandList brand;

    @Embedded
    private Product product;
}
