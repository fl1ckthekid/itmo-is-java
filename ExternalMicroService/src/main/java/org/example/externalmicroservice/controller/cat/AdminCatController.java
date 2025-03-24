package org.example.externalmicroservice.controller.cat;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.externalmicroservice.service.CatService;
import org.example.models.request.CatRequest;
import org.example.models.response.CatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cats")
public class AdminCatController {
    private final CatService catService;

    @Autowired
    public AdminCatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping
    public ResponseEntity<List<CatResponse>> read() throws JsonProcessingException, InterruptedException {
        final List<CatResponse> cats = catService.getAll();

        return cats != null && !cats.isEmpty()
                ? new ResponseEntity<>(cats, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CatResponse> read(@PathVariable(name = "id") int id) throws JsonProcessingException, InterruptedException {
        final CatResponse catResponse = catService.getById(id);

        return catResponse != null
                ? new ResponseEntity<>(catResponse, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CatRequest catRequest) throws JsonProcessingException {
        catService.create(catRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody CatRequest catRequest) throws JsonProcessingException {
        catService.update(catRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) throws JsonProcessingException, InterruptedException {
        catService.delete(id);
        final CatResponse catResponse = catService.getById(id);

        return catResponse == null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
