package org.example.externalmicroservice.controller.owner;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.externalmicroservice.service.OwnerService;
import org.example.models.request.OwnerRequest;
import org.example.models.response.OwnerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/owners")
public class AdminOwnerController {
    private final OwnerService ownerService;

    @Autowired
    public AdminOwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponse>> read() throws JsonProcessingException, InterruptedException {
        final List<OwnerResponse> ownersResponse = ownerService.getAll();

        return ownersResponse != null && !ownersResponse.isEmpty()
                ? new ResponseEntity<>(ownersResponse, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OwnerResponse> read(@PathVariable(name = "id") int id) throws JsonProcessingException, InterruptedException {
        final OwnerResponse ownerResponse = ownerService.getById(id);

        return ownerResponse != null
                ? new ResponseEntity<>(ownerResponse, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OwnerRequest ownerRequest) throws JsonProcessingException {
        ownerService.create(ownerRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody OwnerRequest ownerRequest) throws JsonProcessingException {
        ownerService.update(ownerRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) throws JsonProcessingException, InterruptedException {
        ownerService.delete(id);
        final OwnerResponse ownerResponse = ownerService.getById(id);

        return ownerResponse == null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
