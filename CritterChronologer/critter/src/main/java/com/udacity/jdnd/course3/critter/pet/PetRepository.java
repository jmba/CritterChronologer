package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findPetsByCustomer(Customer customer);

    @Query("select p.id from Pet p where p.customer = :customer")
    List<Long> findIdsByCustomer(Customer customer);
}
