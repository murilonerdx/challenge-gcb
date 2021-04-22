package com.gcb.main.services;


import com.gcb.main.entities.AddressLocation;
import com.gcb.main.entities.Doctor;
import com.gcb.main.entities.Specialtie;
import com.gcb.main.entities.utils.ServiceCep;
import com.gcb.main.repositories.DoctorRepository;
import com.gcb.main.repositories.SpecialtieRepository;
import com.gcb.main.services.exceptions.NotFoundParam;
import com.gcb.main.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repository;


    @Autowired
    private SpecialtieRepository specialtieRepository;

    public List<Doctor> findAll() {
        return repository.findAll();
    }

    public Doctor findById(Long id) {
        Optional<Doctor> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Doctor findByPhone(String phone) {
        Optional<Doctor> obj = Optional.ofNullable(repository.findByPhone(phone));
        return obj.orElseThrow(() -> new ResourceNotFoundException(phone));
    }

    public Doctor findByCrm(String phone) {
        Optional<Doctor> obj = Optional.ofNullable(repository.findByCrm(phone));
        return obj.orElseThrow(() -> new ResourceNotFoundException(phone));
    }

    public Doctor findByCep(String cep) {
        Optional<Doctor> obj = Optional.ofNullable(repository.findByCep(cep));
        return obj.orElseThrow(() -> new ResourceNotFoundException(cep));
    }

    public Doctor findByName(String name) {
        Optional<Doctor> obj = Optional.ofNullable(repository.findByName(name));
        return obj.orElseThrow(() -> new ResourceNotFoundException(name));
    }

    public Doctor insert(Doctor obj) {
        if (obj.getCep() != null || obj.getCrm() != null) {
            AddressLocation address = ServiceCep.ServicoDeCep.buscaEnderecoPelo(obj.getCep());
            obj.setAddress(address);

            Object[] specialtiesIds = obj.getSpecialties().stream().map(Specialtie::getId).toArray();
            obj.clearSpecialties();

            for (int i = 0; i < specialtiesIds.length; i++) {
                obj.getSpecialties().add(specialtieRepository.getOne((Long) specialtiesIds[i]));
            }
            return repository.save(obj);
        }
        return obj;

        //ADICIONAR VALIDAÇÃO

    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public Doctor update(Long id, Doctor obj) {
        try {
            Doctor entity = repository.getOne(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (Exception e) {
            throw new NotFoundParam("CEP invalido");
        }
    }

    private void updateData(Doctor entity, Doctor obj) throws Exception {
        AddressLocation address = ServiceCep.ServicoDeCep.buscaEnderecoPelo(obj.getCep());
        if (obj.getCep() != null) {
            entity.setCep(obj.getCep());
            entity.setPhone(obj.getPhone());
            entity.setName(obj.getName());
            entity.setCrm(obj.getCrm());
            entity.setAddress(address);
        }if(address.getComplemento() == null){
            throw new NotFoundParam("CEP invalido");
        }


    }
}