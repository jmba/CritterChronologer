package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
    @Nationalized
    private String phoneNumber;
    @Nationalized
    private String notes;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Pet> pets= new ArrayList<>();
}
