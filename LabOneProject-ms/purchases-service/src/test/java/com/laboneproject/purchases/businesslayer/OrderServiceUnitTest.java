package com.laboneproject.purchases.businesslayer;

import com.laboneproject.purchases.dataaccesslayer.*;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerModel;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerStatus;
import com.laboneproject.purchases.domainclientlayer.customers.CustomersServiceClient;
import com.laboneproject.purchases.domainclientlayer.inventories.InventoryModel;
import com.laboneproject.purchases.domainclientlayer.inventories.InventoryServiceClient;
import com.laboneproject.purchases.domainclientlayer.sneakers.SneakerModel;
import com.laboneproject.purchases.domainclientlayer.sneakers.SneakerServiceClient;
import com.laboneproject.purchases.mapperlayer.OrderRequestMapper;
import com.laboneproject.purchases.mapperlayer.OrderResponseMapper;
import com.laboneproject.purchases.presentationlayer.OrderRequestDTO;
import com.laboneproject.purchases.presentationlayer.OrderResponseDTO;
import com.laboneproject.purchases.utils.exceptions.InvalidInputException;
import com.laboneproject.purchases.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration")
class OrderServiceUnitTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private CustomersServiceClient customersServiceClient;

    @MockBean
    private InventoryServiceClient inventoryServiceClient;

    @MockBean
    private SneakerServiceClient sneakerServiceClient;

    @MockBean
    private OrderRequestMapper orderRequestMapper;

    @MockBean
    private OrderResponseMapper orderResponseMapper;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test if needed
        Mockito.reset(orderRepository, customersServiceClient, inventoryServiceClient, sneakerServiceClient, orderRequestMapper, orderResponseMapper);

    }

    @Test
    void whenValidCustomerIdSneakerIdInventoryId_thenAddCustomerOrder() {
        // Arrange
        String customerId = "001";
        CustomerModel customerModel = CustomerModel.builder()
                .customerId(customerId)
                .customerFName("John")
                .customerLName("Doe")
                .number("123-456-7890")
                .email("john.doe@example.com")
                .customerStatus(CustomerStatus.AVAILABLE)
                .build();

        String inventoryId = "inv001";
        InventoryModel inventoryModel = InventoryModel.builder()
                .inventoryId(inventoryId)
                .build();

        String sneakerId = "sneaker001";
        SneakerModel sneakerModel = SneakerModel.builder()
                .sneakerId(sneakerId)
                .model("Air Max")
                .price("120.00")
                .size(10)
                .color("Red")
                .build();

        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .sneakerId(sneakerId)
                .inventoryId(inventoryId)
                .quantityBought(1)
                .orderStatus(OrderStatus.PENDING)
                .build();

        Order order = Order.builder()
                .orderIdentifier(new OrderIdentifier())
                .customerModel(customerModel)
                .sneakerModel(sneakerModel)
                .inventoryModel(inventoryModel)
                .quantityBought(1)
                .orderStatus(OrderStatus.PENDING)
                .build();

        OrderResponseDTO expectedResponse = OrderResponseDTO.builder()
                .orderId(order.getOrderIdentifier().getOrderId())
                .customerId(customerId)
                .build();

        // Mocking
        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(inventoryServiceClient.getInventoryByInventoryId(inventoryId)).thenReturn(inventoryModel);
        when(sneakerServiceClient.getSneakerBySneakerId(sneakerId)).thenReturn(sneakerModel);
        when(orderRequestMapper.requestDTOToEntity(any(OrderRequestDTO.class), any(OrderIdentifier.class), any(InventoryModel.class), any(CustomerModel.class), any(SneakerModel.class)))
                .thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderResponseMapper.entityToResponseDTO(any(Order.class))).thenReturn(expectedResponse);

        // Act
        OrderResponseDTO result = orderService.addCustomerOrder(customerId, orderRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse.getOrderId(), result.getOrderId());
        assertEquals(customerId, result.getCustomerId());

        // Verify interactions
        verify(orderRepository).save(any(Order.class));
        verify(orderResponseMapper).entityToResponseDTO(any(Order.class));
    }


    @Test
    void getAllOrdersForCustomers_withValidCustomer_returnsAllOrders() {
        // Arrange
        String customerId = "001";
        CustomerModel customerModel = CustomerModel.builder()
                .customerId(customerId)
                .build();
        List<Order> orders = List.of(new Order());
        List<OrderResponseDTO> expectedResponse = List.of(new OrderResponseDTO());

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(orderRepository.findOrdersByCustomerModel_CustomerId(customerId)).thenReturn(orders);
        when(orderResponseMapper.entityListToResponseModelList(orders)).thenReturn(expectedResponse);

        // Act
        List<OrderResponseDTO> result = orderService.getAllOrdersForCustomers(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse, result);

        // Verify interactions
        verify(orderRepository).findOrdersByCustomerModel_CustomerId(customerId);
        verify(orderResponseMapper).entityListToResponseModelList(orders);
    }

    @Test
    void addCustomerOrder_withInvalidCustomer_throwsInvalidInputException() {
        // Arrange
        String customerId = "025";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            orderService.addCustomerOrder(customerId, orderRequestDTO);
        });

        // Assert
        assertEquals("CustomerId provided is Invalid" + customerId, exception.getMessage());
    }

    @Test
    void getAllOrdersForCustomers_withInvalidCustomer_throwsException() {
        // Arrange
        String customerId = "999";
        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> orderService.getAllOrdersForCustomers(customerId));
    }

    @Test
    void getCustomerOrderById_withValidCustomerAndOrderId_returnsOrder() {
        // Arrange
        String customerId = "001";
        String orderId = "101";
        Order order = new Order();
        OrderResponseDTO expectedResponse = new OrderResponseDTO();

        // Use CustomerModel.builder() to create a mock CustomerModel
        CustomerModel customerModel = CustomerModel.builder()
                .customerId(customerId)
                .customerFName("John")
                .customerLName("Doe")
                .number("123-456-7890")
                .email("email@example.com")
                .customerPreferredSneaker("Nike Air Max")
                .customerPreferredBrand("Nike")
                .customerPreferredSize("10")
                .customerStatus(CustomerStatus.AVAILABLE)
                .build();

        // Configure the mock service to return this specific customer model
        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId)).thenReturn(order);
        when(orderResponseMapper.entityToResponseDTO(order)).thenReturn(expectedResponse);

        // Act
        OrderResponseDTO result = orderService.getCustomerOrderById(customerId, orderId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse, result);

        // Verify interactions
        verify(orderRepository).findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId);
    }

    @Test
    void getCustomerOrderById_withInvalidOrderId_throwsNotFoundException() {
        // Arrange
        String customerId = "001";
        String orderId = "unknown";
        CustomerModel customerModel = CustomerModel.builder()
                .customerId(customerId)
                .customerFName("John")
                .customerLName("Doe")
                .number("123-456-7890")
                .email("john@example.com")
                .build();

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.getCustomerOrderById(customerId, orderId));
    }

    @Test
    void updateCustomerOrder_withValidData_updatesOrderSuccessfully() {
        // Arrange
        String customerId = "001";
        String orderId = "101";
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .sneakerId("040")
                .inventoryId("011")
                .quantityBought(1)
                .orderStatus(OrderStatus.PENDING)
                .build();

        Order existingOrder = new Order(); // Assume proper initialization
        Order updatedOrder = new Order();  // Assume proper initialization
        OrderResponseDTO expectedResponse = new OrderResponseDTO();

        CustomerModel customerModel = CustomerModel.builder()
                .customerId(customerId)
                .customerFName("John")
                .customerLName("Doe")
                .number("123-456-7890")
                .email("john@example.com")
                .build();

        SneakerModel sneakerModel = SneakerModel.builder()
                .sneakerId("040")
                .model("Air Max 95")
                .price("120.00")
                .size(10)
                .color("Black/White")
                .build();

        InventoryModel inventoryModel = InventoryModel.builder()
                .inventoryId("011")
                .build();

        // Mock setup as previously described
        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(sneakerServiceClient.getSneakerBySneakerId("040")).thenReturn(sneakerModel);
        when(inventoryServiceClient.getInventoryByInventoryId("011")).thenReturn(inventoryModel);
        when(orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId)).thenReturn(existingOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);
        when(orderResponseMapper.entityToResponseDTO(any(Order.class))).thenReturn(expectedResponse);

        // Act
        OrderResponseDTO result = orderService.updateCustomerOrder(orderId, orderRequestDTO, customerId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse, result);

        // Verify interactions
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateCustomerOrder_withInvalidOrderId_throwsNotFoundException() {
        // Arrange
        String customerId = "001";
        String orderId = "unknown";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();  // Populate with necessary data if needed

        CustomerModel customerModel = CustomerModel.builder()
                .customerId(customerId)
                .customerFName("John")
                .customerLName("Doe")
                .number("123-456-7890")
                .email("john@example.com")
                .build();

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.updateCustomerOrder(orderId, orderRequestDTO, customerId));
    }

    @Test
    void deleteCustomerOrder_withValidOrderId_deletesOrderSuccessfully() {
        // Arrange
        String customerId = "001";
        String orderId = "101";
        Order order = new Order();

        CustomerModel customerModel = CustomerModel.builder()
                .customerId(customerId)
                .customerFName("John")
                .customerLName("Doe")
                .number("123-456-7890")
                .email("john@example.com")
                .build();

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId)).thenReturn(order);

        // Act
        orderService.deleteCustomerOrder(orderId, customerId);

        // Assert
        verify(orderRepository).delete(order);
    }

    @Test
    void deleteCustomerOrder_withInvalidOrderId_throwsNotFoundException() {
        // Arrange
        String customerId = "001";
        String orderId = "unknown";

        CustomerModel customerModel = CustomerModel.builder()
                .customerId(customerId)
                .customerFName("John")
                .customerLName("Doe")
                .number("123-456-7890")
                .email("john@example.com")
                .build();

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.deleteCustomerOrder(orderId, customerId));
    }
    /*

    @Test
    public void whenValidCustomerId_SneakerId_InventoryId_thenProcessUpdateOrder() {
        var inventoryModel= InventoryModel.builder().inventoryId("011")
                .inventoryId("011")
                .build();

        assertNotNull(inventoryModel);
        assertNotNull(inventoryModel.getInventoryId());

        var customerModel= CustomerModel.builder().customerId("001")
                .customerId("001")
                .customerFName("John")
                .customerLName("Doe")
                .number("313-321-312")
                .email("email@gmail.com")
                .customerStatus(CustomerStatus.AVAILABLE)
                .build();


        var updateCustomer= CustomerModel.builder().customerId("001")
                .customerId("001")
                .customerFName("John")
                .customerLName("Doe")
                .number("313-321-312")
                .email("email@gmail.com")
                .customerStatus(CustomerStatus.AVAILABLE)
                .build();

        var sneakerModel= SneakerModel.builder().sneakerId("040")
                .sneakerId("040")
                .model("Nike 270")
                .price("100")
                .size(8)
                .color("white and blue")
                .build();


        var orderRequestModel= OrderRequestDTO.builder()
                .inventoryId("011")
                .sneakerId("040")
                .transferFee(new BigDecimal(540000))
                .dateOfTransfer(localDate)
                .teamName("rma")
                .location("espagne")
                .stadium("santiago")
                .leagueName("liga")
                .country("Spain")
                .firstName("zako")
                .lastName("boudboub")
                .position("back")
                .nationality("jog")
                .receivingTeam("Home")
                .build();

        //sale objects
        var transfertIdentifier1 = new TransferIdentifier();
        var transfert1= Transfer.builder()
                .transferIdentifier(transfertIdentifier1)
                .playerModel(playerModel)
                .leagueModel(leagueModel)
                .teamModel(teamModel)
                .teamIdentifier2(new ToTeamIdentifier("v12"))
                .receivingTeam("Home")
                .transferFee(new BigDecimal("5555.343"))
                .dateOfTransfer(LocalDate.of(2024,1,1))
                .build();

        var updatedTransfer= Transfer.builder()
                .transferIdentifier(transfertIdentifier1)
                .playerModel(playerModel)
                .leagueModel(leagueModel)
                .teamModel(teamModel)
                .teamIdentifier2(new ToTeamIdentifier("v12"))
                .receivingTeam("Home")
                .transferFee(new BigDecimal("5555.343"))
                .dateOfTransfer(LocalDate.of(2024,1,1))
                .build();

        //define mock behaviors
        when(teamServiceClient.getTeamByTeamId(teamModel.getTeamId())).thenReturn(teamModel);
        when(leagueServiceClient.getLeagueByLeagueId(leagueModel.getLeagueId())).thenReturn(leagueModel);
        when(playerServiceClient.getPlayerByPlayerId(playerModel.getPlayerId())).thenReturn(playerModel);
        when(transferRepository.findTransferByPlayerModel_PlayerIdAndAndTransferIdentifier_TransferId(playerModel.getPlayerId() ,transfert1.getTransferIdentifier().getTransferId())).thenReturn(transfert1);

// Mock behavior for saving updated transfer
        when(transferRepository.save(any(Transfer.class))).thenAnswer(invocation -> {
            Transfer transfer = invocation.getArgument(0);
            // Simulate update
            transfer.setPlayerModel(updatePlayer);
            return transfer;
        });

        // Mock behavior for saving updated transfer
        when(transferRepository.save(any(Transfer.class))).thenAnswer(invocation -> {
            Transfer transfer = invocation.getArgument(0);
            // Simulate update
            transfer.setPlayerModel(updatePlayer);
            return transfer;
        });

        // Call method to update transfer
        // Call method to update transfer
        TransferResponseModel updatedResponseModel = transferService.UpdateTransfer(transferRequestModel, playerModel.getPlayerId(), transfert1.getTransferIdentifier().getTransferId());


        assertNotNull(updatedResponseModel);
        assertNotNull(updatedResponseModel.getPlayerId());
        assertEquals(leagueModel.getLeagueId(), updatedResponseModel.getLeagueId());
        assertEquals(leagueModel.getName(), updatedResponseModel.getLeagueName());
        assertEquals(teamModel.getTeamId(), updatedResponseModel.getTeamId());
        assertEquals(teamModel.getLocation(), updatedResponseModel.getLocation());
        assertEquals(transferRequestModel.getReceivingTeam(), updatedResponseModel.getReceivingTeam());
        assertEquals(transferRequestModel.getDateOfTransfer(), updatedResponseModel.getDateOfTransfer());

    }



    @Test
    void whenWrongPlayerId_thenReturnNotFoundException() {
        // Mock the behavior of the player service client to throw HttpClientErrorException
        when(playerServiceClient.getPlayerByPlayerId(any(String.class)))
                .thenThrow(HttpClientErrorException.NotFound.class);

        // Define the transfer request model with a wrong player ID
        TransferRequestModel transferRequestModel = TransferRequestModel.builder()
                .teamId("1a2b3c4d-1234-5678-90ab-cdef12345679")
                .toTeamId("v12")
                .playerId("wrong_player_id")  // Wrong player ID
                .leagueId("1a2b3c4d-1244-5678-90ab-cdef12345678")
                .transferFee(new BigDecimal(540000))
                .dateOfTransfer(LocalDate.parse("2024-01-01"))
                .teamName("rma")
                .location("espagne")
                .stadium("santiago")
                .leagueName("liga")
                .country("Spain")
                .firstName("zako")
                .lastName("boudboub")
                .position("back")
                .nationality("jog")
                .receivingTeam("Home")
                .build();

        // Verify that an exception is thrown when processing the transfer
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            transferService.AddTransfer(transferRequestModel, "some_player_id");
        });
    }

    */


    @Test
    public void testGetAllOrdersForCustomers_InvalidCustomer() {
        // Setup
        when(customersServiceClient.getCustomerByCustomerId(anyString())).thenReturn(null);

        // Execute & Assert
        assertThrows(InvalidInputException.class, () -> {
            orderService.getAllOrdersForCustomers("035");
        });
    }



/*
    @Test
    void whenInvalidCustomerId_thenThrowException() {
        String invalidCustomerId = "054";
        when(customersServiceClient.getCustomerByCustomerId(invalidCustomerId))
                .thenThrow(new NotFoundException("Customer not found"));

        // Creating OrderRequestDTO using the builder pattern
        OrderRequestDTO requestDTO = OrderRequestDTO.builder()
                .sneakerId("sneaker001")
                .inventoryId("inventory001")
                .quantityBought(1)
                .orderStatus(OrderStatus.PENDING)
                .shippingAddress(new ShippingAddress("1234 Street", "City", "State", "Country", "12345"))
                .build();

        // Attempt to add an order with an invalid customer ID and expect a NotFoundException
        assertThrows(NotFoundException.class, () -> orderService.addCustomerOrder(invalidCustomerId, requestDTO));
    }

    @Test
    void whenEmptyOrderDetails_thenThrowInvalidInputException() {
        String customerId = "001";

        // Assuming the service should throw an InvalidInputException when order details are missing
        assertThrows(InvalidInputException.class, () -> {
            orderService.addCustomerOrder(customerId, new OrderRequestDTO());
        });

        // Ensure no order is saved
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void getAllOrdersForCustomers_CustomerExists_WithOrders() {
        String customerId = "001";
        CustomerModel customerModel = mock(CustomerModel.class);
        List<Order> orders = List.of(new Order()); // Assuming Order is appropriately mocked or instantiated

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(orderRepository.findOrdersByCustomerModel_CustomerId(customerId)).thenReturn(orders);
        when(orderResponseMapper.entityListToResponseModelList(orders)).thenReturn(Collections.singletonList(new OrderResponseDTO()));

        List<OrderResponseDTO> results = orderService.getAllOrdersForCustomers(customerId);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        verify(orderRepository).findOrdersByCustomerModel_CustomerId(customerId);
    }

    @Test
    void getAllOrdersForCustomers_CustomerDoesNotExist() {
        String customerId = "035";

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(null);

        assertThrows(InvalidInputException.class, () -> orderService.getAllOrdersForCustomers(customerId));

        verify(orderRepository, never()).findOrdersByCustomerModel_CustomerId(anyString());
    }

    @Test
    void getCustomerOrderById_CustomerAndOrderExist() {
        String customerId = "001";
        String orderId = "validOrderID";
        CustomerModel customerModel = mock(CustomerModel.class);
        Order order = mock(Order.class);  // Ensure Order is appropriately mocked

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId)).thenReturn(order);
        when(orderResponseMapper.entityToResponseDTO(order)).thenReturn(new OrderResponseDTO());

        OrderResponseDTO result = orderService.getCustomerOrderById(customerId, orderId);

        assertNotNull(result);
        verify(orderRepository).findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId);
    }

    @Test
    void getCustomerOrderById_CustomerDoesNotExist() {
        String customerId = "335";
        String orderId = "validOrderID";

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(null);

        assertThrows(InvalidInputException.class, () -> orderService.getCustomerOrderById(customerId, orderId));

        verify(orderRepository, never()).findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(anyString(), anyString());
    }

    @Test
    void getCustomerOrderById_OrderDoesNotExist() {
        String customerId = "001";
        String orderId = "invalidOrderID";
        CustomerModel customerModel = mock(CustomerModel.class);

        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(customerModel);
        when(orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> orderService.getCustomerOrderById(customerId, orderId));
    }

    @Test
    void addCustomerOrder_InvalidCustomerId_ThrowsException() {
        String customerId = "155";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        when(customersServiceClient.getCustomerByCustomerId(customerId)).thenReturn(null);

        assertThrows(InvalidInputException.class, () -> orderService.addCustomerOrder(customerId, orderRequestDTO));

        verify(sneakerServiceClient, never()).getSneakerBySneakerId(any());
        verify(inventoryServiceClient, never()).getInventoryByInventoryId(any());
        verify(orderRepository, never()).save(any());
    }


 */



}


