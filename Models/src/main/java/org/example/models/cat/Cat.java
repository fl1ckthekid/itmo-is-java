package org.example.models.cat;

import jakarta.persistence.*;
import lombok.Data;
import org.example.models.owner.Owner;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer catId;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "breed")
    private String breed;

    @Column(name = "color")
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToMany
    @JoinTable(name = "cat_friends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Cat> friends = new ArrayList<>();

    public void addFriend(Cat cat) {
        friends.add(cat);
    }

    public void removeFriend(Cat cat) {
        friends.remove(cat);
    }
}
