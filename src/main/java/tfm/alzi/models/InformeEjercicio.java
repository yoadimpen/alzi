package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InformeEjercicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "informe_ejercicio_id")
    private long id;

    @Column(name = "programa_id")
    private Long programaId;

    @Column(name = "ejercicio_id")
    private Long ejercicioId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "aciertos")
    private Integer aciertos;

    @Column(name = "fallos")
    private Integer fallos;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "finalizado")
    private Boolean finalizado;

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

    public void setEjercicioId(Long ejercicioId){
        this.ejercicioId = ejercicioId;
    }

    public Long getUsuarioId(){
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId){
        this.usuarioId = usuarioId;
    }

    public Integer getAciertos(){
        return aciertos;
    }

    public void setAciertos(Integer aciertos){
        this.aciertos = aciertos;
    }

    public Integer getFallos(){
        return fallos;
    }

    public void setFallos(Integer fallos){
        this.fallos = fallos;
    }

    public String getObservaciones(){
        return observaciones;
    }

    public void setObservaciones(String observaciones){
        this.observaciones = observaciones;
    }

    public Boolean esFinalizado(){
        return finalizado;
    }

    public void esFinalizado(Boolean finalizado){
        this.finalizado = finalizado;
    }

}
