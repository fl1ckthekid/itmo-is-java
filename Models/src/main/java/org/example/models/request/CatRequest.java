package org.example.models.request;

import lombok.Data;

@Data
public class CatRequest {
    private String name;
    private String birthDate;
    private String breed;
    private String color;
    private Integer ownerId;
}
