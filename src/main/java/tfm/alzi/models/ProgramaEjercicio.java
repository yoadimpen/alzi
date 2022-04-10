package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class ProgramaEjercicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pej_id")
    private long id;

    @OneToOne
    @Column(name = "programa")
    private Programa programa;

    @OneToOne
    @Column(name = "ejercicio")
    private Ejercicio ejercicio;

    public Long getId(){
        return id;
    }

    public Programa getPrograma(){
        return programa;
    }

    public void setPrograma(Programa programa){
        this.programa = programa;
    }

    public Ejercicio getEjercicio(){
        return ejercicio;
    }

    public void setEjercicio (Ejercicio ejercicio){
        this.ejercicio = ejercicio;
    }

}
