package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    SchedulerService schedulerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        List<Pet> pets = petService.getPetsById(scheduleDTO.getPetIds());
        List<Employee> employees = employeeService.getEmployees(scheduleDTO.getEmployeeIds());
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        Long id = schedulerService.save(schedule);
        scheduleDTO.setId(id);
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = schedulerService.getAllSchedules();
        return schedules.stream().map(schedule -> getScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        List<Schedule> schedules = schedulerService.getSchedulesByPet(pet);
        return schedules.stream().map(schedule -> getScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        List<Schedule> schedules = schedulerService.getSchedulesByEmployee(employee);
        return schedules.stream().map(schedule -> getScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        List<Pet> pets = petService.getPetsByCustomer(customer);
        List<Schedule> schedules = schedulerService.getSchedulesByPets(pets);
        return schedules.stream().map(schedule -> getScheduleDTO(schedule)).collect(Collectors.toList());
    }

    private ScheduleDTO getScheduleDTO(Schedule schedule){
        List<Employee> employees = schedule.getEmployees();
        List<Pet> pets = schedule.getPets();
        List<Long> employeeIds = employees.stream().map(e -> e.getUserId()).collect(Collectors.toList());
        List<Long> petIds = pets.stream().map(p -> p.getPed_id()).collect(Collectors.toList());
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);
        return scheduleDTO;
    }
}
