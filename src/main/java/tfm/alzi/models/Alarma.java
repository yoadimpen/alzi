package tfm.alzi.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class Alarma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarma_id")
    private long id;

    @OneToOne
    @Column(name = "participante")
    private Participante participante;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "etiqueta")
    private String etiqueta;

    public Long getId(){
        return id;
    }

    public Participante getParticipante(){
        return participante;
    }

    public void setParticipante(Participante participante){
        this.participante = participante;
    }

    public LocalDateTime getFecha(){
        return fecha;
    }

    public void setFecha(LocalDateTime fecha){
        this.fecha = fecha;
    }

    public String getEtiqueta(){
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta){
        this.etiqueta = etiqueta;
    }

}
