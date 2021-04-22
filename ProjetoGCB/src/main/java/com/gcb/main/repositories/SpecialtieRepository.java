package com.gcb.main.repositories;

import com.gcb.main.entities.Specialtie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtieRepository extends JpaRepository<Specialtie, Long> {

}
