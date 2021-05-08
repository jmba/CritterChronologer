package com.udacity.jdnd.course3.critter.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
/**
 * I used inheritance type joined to able to use polymorphic queries and null constraints.
 * Performance is not the most critical aspect for a pet app.
 */
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue
    private long userId;

    @Nationalized
    @Column(length = 512)
    private String name;
}
