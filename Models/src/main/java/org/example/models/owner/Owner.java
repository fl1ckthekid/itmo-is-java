package org.example.models.owner;

import jakarta.persistence.*;
import lombok.Data;
import org.example.models.cat.Cat;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ownerId;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Cat> cats = new ArrayList<>();

    public void addCat(Cat cat) {
        cat.setOwner(this);
        cats.add(cat);
    }

    public void removeCat(Cat cat) {
        cats.remove(cat);
    }
}
