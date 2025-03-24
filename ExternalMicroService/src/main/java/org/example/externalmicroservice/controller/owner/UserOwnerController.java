package org.example.externalmicroservice.controller.owner;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.externalmicroservice.security.OwnerDetails;
import org.example.externalmicroservice.service.OwnerService;
import org.example.models.response.OwnerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
public class UserOwnerController {
    private final OwnerService ownerService;

    @Autowired
    public UserOwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/me")
    public ResponseEntity<OwnerResponse> get(@AuthenticationPrincipal OwnerDetails ownerDetails) throws JsonProcessingException, InterruptedException {
        OwnerResponse ownerResponse = ownerService.getById(ownerDetails.getOwnerId());

        return ownerResponse != null
                ? new ResponseEntity<>(ownerResponse, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping("/me")
//    public ResponseEntity<?> update(@RequestBody OwnerRequest ownerRequest, @AuthenticationPrincipal OwnerDetails ownerDetails) throws JsonProcessingException {
//        OwnerResponse currentOwner = ownerService.getById(ownerDetails.getOwnerId());
//
//        Owner updatedOwner = null;
//        if (Objects.equals(currentOwner.getOwnerId(), ownerRequest.getOwnerId())) {
//            updatedOwner = ownerService.update(ownerRequest);
//        }
//
//        return updatedOwner != null
//                ? new ResponseEntity<>(HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
}
