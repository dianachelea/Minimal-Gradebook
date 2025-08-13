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
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@Document(collection = "disciplina")


public class Disciplina  {
    @Id
    private ObjectId _id;
    @Field("denumire_program_studii")
    private String denumireProgramStudii;
    private Integer an_de_studiu;
    private String nume_titular;
    private String grad_didactic;
    private List<Student> studenti = new ArrayList<>();

    public Disciplina() {
        this.studenti = new ArrayList<>();
    }

    public Disciplina(ObjectId _id, String denumire_program_studii, Integer an_de_studiu, String nume_titular, String grad_didactic, List<Student> studenti) {
        this._id = _id;
        this.denumireProgramStudii = denumire_program_studii;
        this.an_de_studiu = an_de_studiu;
        this.nume_titular = nume_titular;
        this.grad_didactic = grad_didactic;
        this.studenti = (studenti != null) ? studenti : new ArrayList<>();
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getDenumire_program_studii() {
        return denumireProgramStudii;
    }

    public void setDenumire_program_studii(String denumire_program_studii) {
        this.denumireProgramStudii = denumire_program_studii;
    }

    public Integer getAn_de_studiu() {
        return an_de_studiu;
    }

    public void setAn_de_studiu(Integer an_de_studiu) {
        this.an_de_studiu = an_de_studiu;
    }

    public String getNume_titular() {
        return nume_titular;
    }

    public void setNume_titular(String nume_titular) {
        this.nume_titular = nume_titular;
    }

    public String getGrad_didactic() {
        return grad_didactic;
    }

    public void setGrad_didactic(String grad_didactic) {
        this.grad_didactic = grad_didactic;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }
}
