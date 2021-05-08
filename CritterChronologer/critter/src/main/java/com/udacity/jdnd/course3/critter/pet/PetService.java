package com.udacity.jdnd.course3.critter.pet;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Long save(Pet pet) {
        Pet newPed = petRepository.save(pet);
        return newPed.getPed_id();
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
    public List<Pet> getPetsById(List<Long> ids) {
        return petRepository.findAllById(ids);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id).get();
    }

    public List<Long> getPetIdsByCustomer(Customer customer){
        return petRepository.findIdsByCustomer(customer);
    }

    public List<Pet> getPetsByCustomer(Customer customer){
        return petRepository.findPetsByCustomer(customer);
    }
}
