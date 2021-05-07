package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue
    private long scheduleId;

    @ManyToMany
    private List<Pet> pets;
    @ManyToMany
    private List<Employee> employees;

    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "ScheduleActivitiesTable",
            joinColumns = @JoinColumn(name = "scheduleId")
    )
    @Column(name = "activity")
    private Set<EmployeeSkill> activities= new HashSet<>();
}

