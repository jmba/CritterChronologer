package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        Long id = customerService.save(customer);
        customerDTO.setId(customer.getUserId());
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        ModelMapper modelMapper = new ModelMapper();
        List<Customer> customerList = customerService.getAllCustomer();
        List<CustomerDTO> dtos = customerList
                .stream()
                .map(user ->{
                    CustomerDTO cdto = modelMapper.map(user, CustomerDTO.class);
                    cdto.setPetIds(petService.getPetIdsByCustomer(user));
                    return cdto;
                })
                .collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPetById(petId);
        Customer customer = pet.getCustomer();
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setId(customer.getUserId());
        BeanUtils.copyProperties(customer, customerDto);
        List<Long> petIds = new ArrayList<Long>();
        petIds.add(pet.getPed_id());
        customerDto.setPetIds(petIds);
        return customerDto;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        Long id = employeeService.save(employee);
        employeeDTO.setId(id);
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        EmployeeDTO edto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, edto);
        edto.setId(employee.getUserId());
        return edto;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setDaysAvailable(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.findAvailableEmployees(employeeDTO.getDate(), employeeDTO.getSkills());
        List<EmployeeDTO> dtos = employees
                .stream()
                .map(employee ->{
                    EmployeeDTO edto = new EmployeeDTO();
                    BeanUtils.copyProperties(employee, edto);
                    edto.setId(employee.getUserId());
                    return edto;
                })
                .collect(Collectors.toList());
        return dtos;
    }
}
