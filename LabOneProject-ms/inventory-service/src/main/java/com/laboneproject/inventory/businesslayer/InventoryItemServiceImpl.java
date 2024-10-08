package com.laboneproject.inventory.businesslayer;



import com.laboneproject.inventory.dataaccesslayer.InventoryItem;
import com.laboneproject.inventory.dataaccesslayer.InventoryItemIdentifier;
import com.laboneproject.inventory.dataaccesslayer.InventoryItemRepository;
import com.laboneproject.inventory.dataaccesslayer.StockLevel;
import com.laboneproject.inventory.datamapperlayer.InventoryItemRequestMapper;
import com.laboneproject.inventory.datamapperlayer.InventoryItemResponseMapper;
import com.laboneproject.inventory.presentationlayer.InventoryItemRequestDTO;
import com.laboneproject.inventory.presentationlayer.InventoryItemResponseDTO;

import com.laboneproject.inventory.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InventoryItemServiceImpl implements InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryItemResponseMapper inventoryItemResponseMapper;
    private final InventoryItemRequestMapper inventoryItemRequestMapper;

    public InventoryItemServiceImpl(InventoryItemRepository inventoryItemRepository, InventoryItemResponseMapper inventoryItemResponseMapper, InventoryItemRequestMapper inventoryItemRequestMapper) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryItemResponseMapper = inventoryItemResponseMapper;
        this.inventoryItemRequestMapper = inventoryItemRequestMapper;
    }

    @Override
    public List<InventoryItemResponseDTO> getAllInventoryItems() {

        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        return inventoryItemResponseMapper.entityListToResponseDTOList(inventoryItems);
    }

    @Override
    public InventoryItemResponseDTO getInventoryItemById(String inventoryId) {

        InventoryItem inventoryItem = inventoryItemRepository.findInventoryItemByInventoryItemIdentifier_InventoryId(inventoryId);

        if (inventoryItem == null) {
            throw new NotFoundException("Unknown inventoryId: " + inventoryId);
        }
        return inventoryItemResponseMapper.entityToResponseDTO(inventoryItem);
    }

    @Override
    public InventoryItemResponseDTO addInventoryItem(InventoryItemRequestDTO inventoryItemRequestDTO) {

        StockLevel stockLevel = new StockLevel(inventoryItemRequestDTO.getAvailableLevel(),inventoryItemRequestDTO.getRestockLevel());

        InventoryItem inventoryItem = inventoryItemRequestMapper.requestDTOToEntity(inventoryItemRequestDTO, new InventoryItemIdentifier(),stockLevel);
        return inventoryItemResponseMapper.entityToResponseDTO(inventoryItemRepository.save(inventoryItem));
    }

    @Override
    public InventoryItemResponseDTO updateInventoryItem(InventoryItemRequestDTO inventoryItemRequestDTO, String inventoryId) {

        InventoryItem foundInventoryItem = inventoryItemRepository.findInventoryItemByInventoryItemIdentifier_InventoryId(inventoryId);

        /*
        if (foundInventoryItem == null) {
            throw new NotFoundException("Unknown inventoryId: " + foundInventoryItem);
        }

         */

        StockLevel stockLevel = new StockLevel(inventoryItemRequestDTO.getAvailableLevel(),inventoryItemRequestDTO.getRestockLevel());

        InventoryItem updatedInventoryItem = inventoryItemRequestMapper.requestDTOToEntity(inventoryItemRequestDTO, foundInventoryItem.getInventoryItemIdentifier(),stockLevel);
        updatedInventoryItem.setId(foundInventoryItem.getId());
        return inventoryItemResponseMapper.entityToResponseDTO(inventoryItemRepository.save(updatedInventoryItem));
    }

    @Override
    public void deleteInventoryItem(String inventoryId) {

        InventoryItem inventoryItem = inventoryItemRepository.findInventoryItemByInventoryItemIdentifier_InventoryId(inventoryId);
        inventoryItemRepository.delete(inventoryItem);
    }
}


