package com.gcb.main.resources;


import com.gcb.main.entities.Doctor;
import com.gcb.main.entities.Specialtie;
import com.gcb.main.services.DoctorService;
import com.gcb.main.services.SpecialtieService;
import com.gcb.main.services.exceptions.DataIntegretyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RestController
@RequestMapping(value = "/specialties")
public class SpecialtieController {

    @Autowired
    private SpecialtieService service;


    @GetMapping
    public ResponseEntity<List<Specialtie>> findAll() throws Exception {
        List<Specialtie> lista = service.findAll();
        return ResponseEntity.ok().body(lista);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Specialtie> findById(@PathVariable Long id) {
        Specialtie obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Specialtie> insert(@RequestBody Specialtie obj) throws Exception {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try{
            if(id != null){
                service.delete(id);
                return ResponseEntity.noContent().build();
            }

        }catch(RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Specialtie> update(@PathVariable Long id, @RequestBody Specialtie obj) {
        try{
            obj = service.update(id, obj);
            return ResponseEntity.ok().body(obj);
        }catch(DataIntegretyException e){
            throw new DataIntegretyException("Parametro invalido");
        }

    }
}