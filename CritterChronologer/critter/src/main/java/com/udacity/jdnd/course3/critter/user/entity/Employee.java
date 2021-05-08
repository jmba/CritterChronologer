package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User{
    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "EmployeeSkillTable",
            joinColumns = @JoinColumn(name = "userId")
    )
    @Column(name = "skillId")
    private Set<EmployeeSkill> skills= new HashSet<>();

    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "EmployeeDaysAvailableTable",
            joinColumns = @JoinColumn(name = "userId")
    )
    @Column(name = "dayId")
    private Set<DayOfWeek> daysAvailable = new HashSet<>();
}
