package com.pos.laborator.data;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Document(collection = "students")


public class Student {
    @Id
    private ObjectId _id;
    private String prenume;
    private String nume;
    private stare stare;

    private ObjectId id_catalog_promovat;
    private Map<String, Integer> note = new HashMap<>();
    private Double medie;


    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

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

    public stare getStare() {
        return stare;
    }

    public void setStare(stare stare) {
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

    public Student() {
    }

    public Student(String prenume, String nume, stare stare, ObjectId id_catalog_promovat, Map<String, Integer> note, Double medie) {
        this.prenume = prenume;
        this.nume = nume;
        this.stare = stare;
        this.id_catalog_promovat = id_catalog_promovat;
        this.note = note;
        this.medie = medie;
    }
}



