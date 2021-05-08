package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Long save(Employee employee) {
        employeeRepository.save(employee);
        return employee.getUserId();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployees(List<Long> ids) {
        return employeeRepository.findAllById(ids);
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }

    public List<Employee> findAvailableEmployees(LocalDate date, Set<EmployeeSkill> employeeSkills){
        List<Employee> employees = new ArrayList<>();
        employees = employeeRepository.getEmployeesByDaysAvailable(date.getDayOfWeek());

        List<Employee> matchedEmployees = employees
                .stream()
                .filter(e -> e.getSkills().containsAll(employeeSkills))
                .collect(Collectors.toList());

        return matchedEmployees;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable, Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }
}
