package com.pos.laborator.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;


public class StudentDto {
    private ObjectId _id;

    @NotNull
    private String prenume;
    @NotNull
    private String nume;
    @NotNull
    @Pattern(regexp = "promovat|respins|echivalat|absent", message = "Starea trebuie sa fie una dintre: promovat, respins, echivalat, absent.")
    private String stare;
    private ObjectId id_catalog_promovat;

    private Map<String, Integer> note;
    private Double medie;


    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getStare() {
        return stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public ObjectId getId_catalog_promovat() {
        return id_catalog_promovat;
    }

    public void setId_catalog_promovat(ObjectId id_catalog_promovat) {
        this.id_catalog_promovat = id_catalog_promovat;
    }

    public Map<String, Integer> getNote() {
        return note;
    }

    public void setNote(Map<String, Integer> note) {
        this.note = note;
    }

    public Double getMedie() {
        return medie;
    }

    public void setMedie(Double medie) {
        this.medie = medie;
    }
    public StudentDto() {
    }
    public ObjectId get_id()
    {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}
