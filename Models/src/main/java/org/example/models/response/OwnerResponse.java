package org.example.models.response;

import lombok.Data;

@Data
public class OwnerResponse {
    private Integer ownerId;
    private String name;
    private String birthDate;
    private String role;
    private String password;
}
