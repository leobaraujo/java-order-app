package com.example.demo.api;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.api.dto.NewPartialPaymentDTO;
import com.example.demo.api.dto.UpdatePartialPaymentDTO;
import com.example.demo.domain.entity.PartialPayment;
import com.example.demo.service.PartialPaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/partialpayment")
@Tag(name = "Partial payment")
@SecurityRequirement(name = "Bearer Token")
public class PartialPaymentApi {

    private PartialPaymentService partialPaymentService;

    public PartialPaymentApi(PartialPaymentService partialPaymentService) {
        this.partialPaymentService = partialPaymentService;
    }

    @GetMapping
    @Operation(summary = "Get all partial payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all partial payment as array")
    })
    public ResponseEntity<List<PartialPayment>> getAll() {
        List<PartialPayment> partialPaymentsList = partialPaymentService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(partialPaymentsList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get partial payment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the partial payment"),
            @ApiResponse(responseCode = "404", description = "If partial payment is not found")
    })
    public ResponseEntity<PartialPayment> getById(@PathVariable UUID id) {
        try {
            PartialPayment partialPayment = partialPaymentService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(partialPayment);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Get all partial payments from customer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all partial payment as array")
    })
    public ResponseEntity<List<PartialPayment>> getAllByCustomerId(@PathVariable UUID id) {
        List<PartialPayment> partialPaymentsList = partialPaymentService.getAllByCustomerId(id);

        return ResponseEntity.status(HttpStatus.OK).body(partialPaymentsList);
    }

    @PostMapping
    @Operation(summary = "Create a new partial payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If amount is less than zero, customer status is not BUSY, invalid id or customer is not found")
    })
    public ResponseEntity<Void> createPartialPayment(@RequestBody @Valid NewPartialPaymentDTO newPartialPaymentDTO) {
        PartialPayment partialPayment = partialPaymentService.createPartialPayment(newPartialPaymentDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(partialPayment.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update partial payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If amount is less than zero, customer status is not BUSY, or partial payment is not found")
    })
    public ResponseEntity<Void> updatePartialPayment(@PathVariable UUID id,
            @RequestBody @Valid UpdatePartialPaymentDTO updatePartialPaymentDTO) {
        partialPaymentService.updatePartialPayment(id, updatePartialPaymentDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete partial payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "If id is null")
    })
    public ResponseEntity<Void> deletePartialPaymentById(@PathVariable UUID id) {
        try {
            partialPaymentService.deletePartialPaymentById(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
