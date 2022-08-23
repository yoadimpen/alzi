package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InformePrograma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "informe_programa_id")
    private long id;

    @Column(name = "programa_id")
    private Long programaId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "progreso")
    private Integer progreso;

    @Column(name = "aciertos")
    private Integer aciertos;

    @Column(name = "fallos")
    private Integer fallos;

    @Column(name = "observaciones")
    private String observaciones;

    public Long getId(){
        return id;
    }

    public Long getProgramaId(){
        return programaId;
    }

    public void setProgramaId(Long programaId){
        this.programaId = programaId;
    }

    public Long getUsuarioId(){
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId){
        this.usuarioId = usuarioId;
    }

    public Integer getProgreso(){
        return progreso;
    }

    public void setProgreso(Integer progreso){
        this.progreso = progreso;
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

}
