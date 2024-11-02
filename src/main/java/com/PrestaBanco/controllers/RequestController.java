package com.PrestaBanco.controllers;

import com.PrestaBanco.entities.RequestEntity;
import com.PrestaBanco.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin("*")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @GetMapping("/")
    public ResponseEntity<List<RequestEntity>> getAllRequests() {
        List<RequestEntity> requests = requestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/findByOwnerId/{id}")
    public ResponseEntity<List<RequestEntity>> getRequestByOwnerId(@PathVariable Long id) {
        List<RequestEntity> requests = requestService.getRequestByOwnerId(id);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestEntity> getRequestById(@PathVariable Long id) {
        RequestEntity request = requestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/simulateCredit/")
    public ResponseEntity<Double> getRequestByCredit(
            @RequestParam("amount") Long p,
            @RequestParam("interestRate") float r,
            @RequestParam("months") Long n) {
        Double m = requestService.calculateMonthlyFee(p, r, n);
        return ResponseEntity.ok(m);
    }

    @PostMapping("/")
    public ResponseEntity<RequestEntity> addRequest(@RequestBody RequestEntity request) {
        RequestEntity newRequest = requestService.saveRequest(request);
        return ResponseEntity.ok(newRequest);
    }

    @PutMapping("/")
    public ResponseEntity<RequestEntity> updateRequest(@RequestBody RequestEntity request) {
        RequestEntity updatedRequest = requestService.updateRequest(request);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) throws Exception {
        var isDeleted = requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}
