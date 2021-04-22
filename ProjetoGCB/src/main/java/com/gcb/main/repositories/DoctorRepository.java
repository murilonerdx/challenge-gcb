package com.gcb.main.repositories;

import com.gcb.main.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {

    @Query("select u from Doctor u where u.phone = ?1")
    Doctor findByPhone(String crm);

    @Query("select u from Doctor u where u.name = ?1")
    Doctor findByName(String name);

    @Query("select u from Doctor u where u.cep = ?1")
    Doctor findByCep(String cep);

    @Query("select u from Doctor u where u.crm = ?1")
    Doctor findByCrm(String crm);



}
