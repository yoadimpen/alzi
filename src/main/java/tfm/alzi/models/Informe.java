package tfm.alzi.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Informe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "informe_id")
    private long id;

    @ManyToOne
    @Column(name = "participante")
    private Participante participante;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "resultados_areas")
    private String resultadosAreas;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "observaciones")
    private String observaciones;

    public Long getId(){
        return id;
    }

    public Participante getParticipante(){
        return participante;
    }

    public void setParticipante(Participante participante){
        this.participante = participante;
    }

    public LocalDate getFecha(){
        return fecha;
    }

    public String getResultadosAreas(){
        return resultadosAreas;
    }

    public void setResultadosAreas(String resultadosAreas){
        this.resultadosAreas = resultadosAreas;
    }

    public String getDiagnostico(){
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico){
        this.diagnostico = diagnostico;
    }

    public String getObservaciones(){
        return observaciones;
    }

    public void setObservaciones(String observaciones){
        this.observaciones = observaciones;
    }

}
