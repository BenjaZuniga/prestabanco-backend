package com.PrestaBanco.services;

import com.PrestaBanco.entities.RequestEntity;
import com.PrestaBanco.repositories.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RequestServiceTest {

    @InjectMocks
    private RequestService requestService;

    @Mock
    private RequestRepository requestRepository;

    private RequestEntity request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new RequestEntity();
        request.setId(1L);
        request.setOwnerId(1L);
        request.setAmount(10000L);
        request.setInterestRate(5.0f);
        request.setMonths(12L);
    }

    @Test
    public void testGetAllRequests() {
        ArrayList<RequestEntity> requests = new ArrayList<>();
        requests.add(request);
        when(requestRepository.findAll()).thenReturn(requests);

        ArrayList<RequestEntity> result = requestService.getAllRequests();

        assertEquals(1, result.size());
        assertEquals(request.getOwnerId(), result.get(0).getOwnerId());
        verify(requestRepository, times(1)).findAll();
    }

    @Test
    public void testGetRequestByOwnerId() {
        ArrayList<RequestEntity> requests = new ArrayList<>();
        requests.add(request);
        when(requestRepository.findByOwnerId(1L)).thenReturn(requests);

        ArrayList<RequestEntity> result = requestService.getRequestByOwnerId(1L);

        assertEquals(1, result.size());
        assertEquals(request.getOwnerId(), result.get(0).getOwnerId());
        verify(requestRepository, times(1)).findByOwnerId(1L);
    }

    @Test
    public void testGetRequestById() {
        when(requestRepository.findById(1L)).thenReturn(Optional.of(request));

        RequestEntity result = requestService.getRequestById(1L);

        assertEquals(request.getOwnerId(), result.getOwnerId());
        verify(requestRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveRequest() {
        when(requestRepository.save(request)).thenReturn(request);

        RequestEntity result = requestService.saveRequest(request);

        assertEquals(request.getOwnerId(), result.getOwnerId());
        verify(requestRepository, times(1)).save(request);
    }

    @Test
    public void testUpdateRequest() {
        when(requestRepository.save(request)).thenReturn(request);

        RequestEntity result = requestService.updateRequest(request);

        assertEquals(request.getOwnerId(), result.getOwnerId());
        verify(requestRepository, times(1)).save(request);
    }

    @Test
    public void testDeleteRequest() throws Exception {
        doNothing().when(requestRepository).deleteById(1L);

        boolean result = requestService.deleteRequest(1L);

        assertTrue(result);
        verify(requestRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteRequestThrowsException() {
        doThrow(new RuntimeException("Error al eliminar")).when(requestRepository).deleteById(1L);

        Exception exception = assertThrows(Exception.class, () -> {
            requestService.deleteRequest(1L);
        });

        assertEquals("Error al eliminar", exception.getMessage());
        verify(requestRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCalculateMonthlyFee() {
        Double expectedFee = 856.0636418749033; // Este valor debe ser calculado correctamente.
        Double result = requestService.calculateMonthlyFee(request.getAmount(), request.getInterestRate(), request.getMonths());

        assertEquals(expectedFee, result, 0.01);
    }
}


