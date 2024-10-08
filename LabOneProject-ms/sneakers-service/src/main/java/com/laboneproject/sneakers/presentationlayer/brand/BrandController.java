package com.laboneproject.sneakers.presentationlayer.brand;


import com.laboneproject.sneakers.businesslayer.brand.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        return ResponseEntity.ok().body(brandService.getAllBrands());
    }

    @GetMapping(value = "/{brandId}",produces = "application/json")
    public ResponseEntity<BrandResponseDTO> getBrandById(@PathVariable String brandId) {
        BrandResponseDTO brandResponseDTO = brandService.getBrandByBrandId(brandId);
        return ResponseEntity.status(HttpStatus.OK).body(brandResponseDTO);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<BrandResponseDTO> addBrand(@RequestBody BrandRequestDTO brandRequestDTO) {
        BrandResponseDTO addedBrand = brandService.addBrand(brandRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedBrand);
    }

    @PutMapping(value = "/{brandId}",produces = "application/json")
    public ResponseEntity<BrandResponseDTO> updateBrand(
            @RequestBody BrandRequestDTO brandRequestDTO,
            @PathVariable String brandId) {
        BrandResponseDTO updatedBrand = brandService.updateBrand(brandRequestDTO, brandId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBrand);
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<Void> deleteBrand(@PathVariable String brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


