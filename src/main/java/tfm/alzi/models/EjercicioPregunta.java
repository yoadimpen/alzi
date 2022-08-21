package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class EjercicioPregunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ejpre_id")
    private long id;

    @OneToOne
    @Column(name = "ejercicio")
    private Ejercicio ejercicio;

    @OneToOne
    @Column(name = "pregunta")
    private Pregunta pregunta;

    public Long getId(){
        return id;
    }

    public Ejercicio getEjercicio(){
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio){
        this.ejercicio = ejercicio;
    }

    public Pregunta getPregunta(){
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta){
        this.pregunta = pregunta;
    }

}
