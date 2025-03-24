package org.example.externalmicroservice.controller.cat;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.externalmicroservice.security.OwnerDetails;
import org.example.externalmicroservice.service.CatService;
import org.example.models.cat.Cat;
import org.example.models.request.CatRequest;
import org.example.models.request.RequestMapper;
import org.example.models.response.CatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cats")
public class UserCatController {
    private final CatService catService;
    private final RequestMapper requestMapper;

    @Autowired
    public UserCatController(CatService catService, RequestMapper requestMapper) {
        this.catService = catService;
        this.requestMapper = requestMapper;
    }

    @GetMapping
    public ResponseEntity<List<CatResponse>> read(@AuthenticationPrincipal OwnerDetails ownerDetails) throws JsonProcessingException, InterruptedException {
        final List<CatResponse> ownerCats = catService.getAll();

        return ownerCats != null && !ownerCats.isEmpty()
                ? new ResponseEntity<>(ownerCats, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CatResponse> read(@PathVariable(name = "id") int id, @AuthenticationPrincipal OwnerDetails ownerDetails) throws JsonProcessingException, InterruptedException {
        final CatResponse catResponse = catService.getById(id);
        if (Objects.equals(catResponse.getOwnerId(), ownerDetails.getOwnerId())) {
            return new ResponseEntity<>(catResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CatRequest catRequest, @AuthenticationPrincipal OwnerDetails ownerDetails) throws JsonProcessingException {
        catService.create(catRequest);
        Cat cat = requestMapper.mapToCat(catRequest);
        ownerDetails.getOwner().addCat(cat);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody CatRequest catRequest, @AuthenticationPrincipal OwnerDetails ownerDetails) throws JsonProcessingException {
        if (Objects.equals(catRequest.getOwnerId(), ownerDetails.getOwnerId())) {
            catService.update(catRequest);
            return new ResponseEntity<>(catRequest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id, @AuthenticationPrincipal OwnerDetails ownerDetails) throws JsonProcessingException, InterruptedException {
        if (Objects.equals(catService.getById(id).getOwnerId(), ownerDetails.getOwnerId())) {
            catService.delete(id);
        }
        final CatResponse catResponse = catService.getById(id);

        return catResponse == null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
