package org.example.models.cat;

import lombok.Data;

import java.util.List;

@Data
public class CatDto {
    private Integer catId;
    private String name;
    private String birthDate;
    private String breed;
    private String color;
    private Integer ownerId;
    private List<Cat> friends;
}
