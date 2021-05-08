package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        Long customerId = petDTO.getOwnerId();
        Customer customer = customerService.getCustomer(customerId);
        pet.setCustomer(customer);
        Long id = petService.save(pet);
        petDTO.setId(id);
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO pdto = new PetDTO();
        Pet pet = petService.getPetById(petId);
        BeanUtils.copyProperties(pet, pdto);
        pdto.setId(pet.getPed_id());
        pdto.setOwnerId(pet.getCustomer().getUserId());
        return pdto;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getAllPets().stream().map(p -> convertPetToPetDTO(p)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer petOwner = customerService.getCustomer(ownerId);
        List<Pet> pets = petService.getPetsByCustomer(petOwner);
        return pets.stream().map(p -> {
            PetDTO dto = convertPetToPetDTO(p);
            dto.setOwnerId(ownerId);
            return dto;
        }).collect(Collectors.toList());
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        Long ownerId = petDTO.getOwnerId();
        Customer customer = customerService.getCustomer(ownerId);
        pet.setCustomer(customer);
        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setId(pet.getPed_id());
        return petDTO;
    }
}
