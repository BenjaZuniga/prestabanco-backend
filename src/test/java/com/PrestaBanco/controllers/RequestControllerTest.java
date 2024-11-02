package com.PrestaBanco.controllers;

import com.PrestaBanco.entities.RequestEntity;
import com.PrestaBanco.services.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RequestControllerTest {

    @InjectMocks
    private RequestController requestController;

    @Mock
    private RequestService requestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRequests() {
        // Arrange
        ArrayList<RequestEntity> requests = new ArrayList<>();
        requests.add(new RequestEntity());
        when(requestService.getAllRequests()).thenReturn(requests);

        // Act
        ResponseEntity<List<RequestEntity>> response = requestController.getAllRequests();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(requests, response.getBody());
        verify(requestService, times(1)).getAllRequests();
    }

    @Test
    public void testGetRequestByOwnerId() {
        // Arrange
        Long ownerId = 1L;
        ArrayList<RequestEntity> requests = new ArrayList<>();
        requests.add(new RequestEntity());
        when(requestService.getRequestByOwnerId(ownerId)).thenReturn(requests);

        // Act
        ResponseEntity<List<RequestEntity>> response = requestController.getRequestByOwnerId(ownerId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(requests, response.getBody());
        verify(requestService, times(1)).getRequestByOwnerId(ownerId);
    }

    @Test
    public void testGetRequestById() {
        // Arrange
        Long requestId = 1L;
        RequestEntity request = new RequestEntity();
        when(requestService.getRequestById(requestId)).thenReturn(request);

        // Act
        ResponseEntity<RequestEntity> response = requestController.getRequestById(requestId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request, response.getBody());
        verify(requestService, times(1)).getRequestById(requestId);
    }

    @Test
    public void testGetRequestByCredit() {
        // Arrange
        Long amount = 10000L;
        float interestRate = 5.0f;
        Long months = 12L;
        Double expectedFee = 856.0636418749033;
        when(requestService.calculateMonthlyFee(amount, interestRate, months)).thenReturn(expectedFee);

        // Act
        ResponseEntity<Double> response = requestController.getRequestByCredit(amount, interestRate, months);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedFee, response.getBody());
        verify(requestService, times(1)).calculateMonthlyFee(amount, interestRate, months);
    }

    @Test
    public void testAddRequest() {
        // Arrange
        RequestEntity request = new RequestEntity();
        request.setAmount(10000L);
        request.setInterestRate(5.0f);
        request.setMonths(12L);
        when(requestService.saveRequest(request)).thenReturn(request);

        // Act
        ResponseEntity<RequestEntity> response = requestController.addRequest(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request, response.getBody());
        verify(requestService, times(1)).saveRequest(request);
    }

    @Test
    public void testUpdateRequest() {
        // Arrange
        RequestEntity request = new RequestEntity();
        when(requestService.updateRequest(request)).thenReturn(request);

        // Act
        ResponseEntity<RequestEntity> response = requestController.updateRequest(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request, response.getBody());
        verify(requestService, times(1)).updateRequest(request);
    }

    @Test
    public void testDeleteRequest() throws Exception {
        // Arrange
        Long requestId = 1L;
        when(requestService.deleteRequest(requestId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = requestController.deleteRequest(requestId);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(requestService, times(1)).deleteRequest(requestId);
    }
}

