package com.gcb.main.resources;


import com.gcb.main.entities.Doctor;
import com.gcb.main.entities.utils.ServiceCep;
import com.gcb.main.services.DoctorService;
import com.gcb.main.services.exceptions.NotFoundParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RestController
@RequestMapping(value = "/doctors")
public class DoctorController {

    @Autowired
    private DoctorService service;


    @GetMapping
    public ResponseEntity<List<Doctor>> findAll() throws Exception {
        List<Doctor> lista = service.findAll();
        return ResponseEntity.ok().body(lista);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {
        Doctor obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public ResponseEntity<Doctor> findByPhone(@RequestParam(required = false) String phone, @RequestParam(required = false) String name, @RequestParam(required = false) String crm, @RequestParam(required = false) String cep) {
        if (phone != null) {
            Doctor obj = service.findByPhone(phone);
            return ResponseEntity.ok().body(obj);
        }
        if (name != null) {
            Doctor obj = service.findByName(name);
            return ResponseEntity.ok().body(obj);
        }
        if (crm != null) {
            Doctor obj = service.findByCrm(crm);
            return ResponseEntity.ok().body(obj);
        }
        if (cep != null) {
            Doctor obj = service.findByCep(cep);
            return ResponseEntity.ok().body(obj);
        }
        return null;
    }


    @PostMapping
    public ResponseEntity<Doctor> insert(@RequestBody Doctor obj) throws Exception {
            obj.setAddress(ServiceCep.ServicoDeCep.buscaEnderecoPelo(obj.getCep()));
            if(obj.getAddress().getComplemento() == null){
                throw new NotFoundParam("CEP vazio");
            }
            else if(obj.getCrm() == null){
                throw new NotFoundParam("CRM vazio");
            }
            obj = service.insert(obj);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
            return ResponseEntity.created(uri).body(obj);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Doctor obj = service.findById(id);
        obj.setDeleted();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Doctor> update(@PathVariable Long id, @RequestBody Doctor obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}