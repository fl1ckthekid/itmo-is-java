package org.example.models.response;

import lombok.Data;

@Data
public class CatResponse {
    private Integer catId;
    private String name;
    private String birthDate;
    private String breed;
    private String color;
    private Integer ownerId;
}
