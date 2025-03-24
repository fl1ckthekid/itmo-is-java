package org.example.models.owner;

import lombok.Data;
import org.example.models.cat.Cat;

import java.util.List;

@Data
public class OwnerDto {
    private Integer ownerId;
    private String name;
    private String birthDate;
    private String role;
    private List<Cat> cats;
}
