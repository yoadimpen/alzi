package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProgramaEjercicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pej_id")
    private long id;

    @Column(name = "programa_id")
    private long programaId;

    @Column(name = "ejercicio_id")
    private long ejercicioId;

    public Long getId(){
        return id;
    }

    public Long getProgramaId(){
        return programaId;
    }

    public void setProgramaId(Long programaId){
        this.programaId = programaId;
    }

    public Long getEjercicioId(){
        return ejercicioId;
    }

    public void setEjercicioId (Long ejercicioId){
        this.ejercicioId = ejercicioId;
    }

}
