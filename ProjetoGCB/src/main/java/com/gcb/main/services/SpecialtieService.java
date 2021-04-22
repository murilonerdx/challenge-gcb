package com.gcb.main.services;


import com.gcb.main.entities.Doctor;
import com.gcb.main.entities.Specialtie;
import com.gcb.main.repositories.SpecialtieRepository;
import com.gcb.main.services.exceptions.DataIntegretyException;
import com.gcb.main.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialtieService {

    @Autowired
    private SpecialtieRepository repository;

    public List<Specialtie> findAll() {
        return repository.findAll();
    }

    public Specialtie findById(Long id) {
        Optional<Specialtie> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Specialtie insert(Specialtie obj) throws Exception {
        return repository.save(obj);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public Specialtie update(Long id, Specialtie obj){
        try {
            Specialtie entity = repository.getOne(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (RuntimeException e) {
            throw new DataIntegretyException("Parametro invalido");
        }
    }

    private void updateData(Specialtie entity, Specialtie obj) {
        entity.setName(obj.getName());
        entity.setDeleted(obj.getDeleted());
    }
}