package com.laboneproject.sneakers.businesslayer.brand;



import com.laboneproject.sneakers.dataaccesslayer.brand.Brand;
import com.laboneproject.sneakers.dataaccesslayer.brand.BrandIdentifier;
import com.laboneproject.sneakers.dataaccesslayer.brand.BrandRepository;
import com.laboneproject.sneakers.datamapperlayer.brand.BrandRequestMapper;
import com.laboneproject.sneakers.datamapperlayer.brand.BrandResponseMapper;
import com.laboneproject.sneakers.presentationlayer.brand.BrandRequestDTO;
import com.laboneproject.sneakers.presentationlayer.brand.BrandResponseDTO;
import com.laboneproject.sneakers.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandServiceImpl implements BrandService{

    private final BrandRepository brandRepository;
    private final BrandResponseMapper brandResponseMapper;
    private final BrandRequestMapper brandRequestMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandResponseMapper brandResponseMapper, BrandRequestMapper brandRequestMapper) {
        this.brandRepository = brandRepository;
        this.brandResponseMapper = brandResponseMapper;
        this.brandRequestMapper = brandRequestMapper;
    }

    @Override
    public List<BrandResponseDTO> getAllBrands() {

        List<Brand> brand = brandRepository.findAll();
        return brandResponseMapper.entityListToResponseDTOList(brand);
    }

    @Override
    public BrandResponseDTO getBrandByBrandId(String brandId) {

        Brand brand = brandRepository.findBrandByBrandIdentifier_BrandId(brandId);
        if(brand==null){
            throw new NotFoundException(("Unknown brandId " + brandId));
        }
        return brandResponseMapper.entityToResponseDTO(brand);
    }

    @Override
    public BrandResponseDTO addBrand(BrandRequestDTO brandRequestDTO) {

        Brand brand = brandRequestMapper.requestDTOToEntity(brandRequestDTO, new BrandIdentifier());
        return brandResponseMapper.entityToResponseDTO(brandRepository.save(brand));
    }

    @Override
    public BrandResponseDTO updateBrand(BrandRequestDTO brandRequestDTO, String brandId) {

        Brand foundBrand = brandRepository.findBrandByBrandIdentifier_BrandId(brandId);

        Brand updatedBrand = brandRequestMapper.requestDTOToEntity(brandRequestDTO, foundBrand.getBrandIdentifier());
        updatedBrand.setId(foundBrand.getId());
        return brandResponseMapper.entityToResponseDTO(brandRepository.save(updatedBrand));
    }

    @Override
    public void deleteBrand(String brandId) {

        Brand brand = brandRepository.findBrandByBrandIdentifier_BrandId(brandId);
        brandRepository.delete(brand);
    }
}

