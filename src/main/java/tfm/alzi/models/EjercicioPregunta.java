package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class EjercicioPregunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ejpre_id")
    private long id;

    @Column(name = "ejercicio_id")
    private Long ejercicioId;

    @Column(name = "pregunta_id")
    private Long preguntaId;

    public Long getId(){
        return id;
    }

    public Long getEjercicioId(){
        return ejercicioId;
    }

    public void setEjercicioId(Long ejercicioId){
        this.ejercicioId = ejercicioId;
    }

    public Long getPreguntaId(){
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId){
        this.preguntaId = preguntaId;
    }

}
