package com.gcb.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gcb.main.entities.utils.exceptions.UtilValidation;
import com.gcb.main.services.exceptions.ObjectNotFoundException;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "tb_doctor")
@Entity
@Where(clause = "DELETED = 0")
public class Doctor implements Serializable {

    public static String maskCrm = "##.###.##";
    public static String maskCep = "#####-###";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DELETED")
    private Integer deleted = 0;

    public void setDeleted() {
        this.deleted = 1;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Column(length = 120, nullable = false)
    private String name;
    @Column(nullable = false)
    private String crm;
    private String phone;
    private String phoneFix;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "TB_DOCTORS_SPECIALTIES",
            joinColumns = {@JoinColumn(name = "DOCTOR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SPECIALTIE_ID")}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Specialtie> specialties = new HashSet<>();

    @Column(nullable=false)
    private String cep;

    @OneToOne(cascade = CascadeType.PERSIST)
    private AddressLocation address;

    public Doctor(Long id, String name, String crm, String phone, String phoneFix, String cep, Specialtie[] specialties) throws Exception {
        this.id = id;
        this.name = name;
        this.crm = crm;
        this.phone = phone;

        this.cep = cep;
        this.phoneFix = phoneFix;
    }

    public Doctor() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) throws UtilValidation, ParseException {
        this.crm = formatCrm(crm, maskCrm);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) throws UtilValidation, ParseException {
        this.cep = formatCep(cep, maskCep);

    }

    public Set<Specialtie> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialtie> specialties) {
        this.specialties = specialties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor user = (Doctor) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public AddressLocation getAddress() throws Exception {
        return address;
    }

    public String getPhoneFix() {
        return phoneFix;
    }

    public void setPhoneFix(String phoneFix) {
        this.phoneFix = phoneFix;
    }

    public void setAddress(AddressLocation address)  {
        this.address = address;
    }

    public static String formatCep(String cep, String maskCep) {
        try{
            if (cep != null) {
                cep = cep.replaceAll("\\D", "");
                if (cep.length() == 8) {
                    return formatString(cep, maskCep);
                }
            }
            return cep;
        }catch(Exception e){
            throw new ObjectNotFoundException("CEP invalido");
        }

    }

    public static String formatCrm(String crm, String maskCep) throws UtilValidation, ParseException {
        if (crm != null) {
            crm = crm.replaceAll("\\D", "");
            if (crm.length() == 7) {
                return formatString(crm, maskCep);
            }
        }
        return crm;
    }

    public static String formatString(String value, String mask)
            throws java.text.ParseException {
        javax.swing.text.MaskFormatter mf =
                new javax.swing.text.MaskFormatter(mask);
        mf.setValueContainsLiteralCharacters(false);
        return mf.valueToString(value);
    }

    public void clearSpecialties() {
        this.specialties = new HashSet<>();
    }
}