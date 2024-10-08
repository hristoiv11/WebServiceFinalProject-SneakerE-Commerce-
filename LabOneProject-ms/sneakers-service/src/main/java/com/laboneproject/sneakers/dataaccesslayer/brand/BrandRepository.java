package com.laboneproject.sneakers.dataaccesslayer.brand;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BrandRepository extends JpaRepository<Brand,Integer> {

    Brand findBrandByBrandIdentifier_BrandId(String brandId);
}


