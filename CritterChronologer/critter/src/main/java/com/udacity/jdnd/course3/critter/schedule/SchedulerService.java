package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Long save(Schedule schedule) {
        Schedule newSchedule = scheduleRepository.save(schedule);
        return newSchedule.getScheduleId();
    }

    public List<Schedule> getSchedulesByEmployee(Employee employee) {
        List<Schedule> schedules = scheduleRepository.findSchedulesByEmployees(employee);
        return schedules;
    }

    public List<Schedule> getSchedulesByPets(List<Pet> pets) {
        return scheduleRepository.findByPetsIn(pets);
    }

    public List<Schedule> getSchedulesByPet(Pet pet) {
        return scheduleRepository.findSchedulesByPets(pet);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}
