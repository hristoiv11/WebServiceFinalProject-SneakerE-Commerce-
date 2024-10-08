package com.laboneproject.sneakers.businesslayer.brand;




import com.laboneproject.sneakers.presentationlayer.brand.BrandRequestDTO;
import com.laboneproject.sneakers.presentationlayer.brand.BrandResponseDTO;

import java.util.List;


public interface BrandService {

    List<BrandResponseDTO> getAllBrands();

    BrandResponseDTO getBrandByBrandId(String brandId);

    BrandResponseDTO addBrand(BrandRequestDTO brandRequestDTO);

    BrandResponseDTO updateBrand(BrandRequestDTO brandRequestDTO, String brandId);

    void deleteBrand(String brandId);
}
