package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InformePregunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "informe_pregunta_id")
    private long id;

    @Column(name = "programa_id")
    private Long programaId;

    @Column(name = "ejercicio_id")
    private Long ejercicioId;

    @Column(name = "pregunta_id")
    private Long preguntaId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "respuesta")
    private String respuesta;

    @Column(name = "resultado")
    private Boolean resultado;

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

    public Long getPreguntaId(){
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId){
        this.preguntaId = preguntaId;
    }

    public Long getUsuarioId(){
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId){
        this.usuarioId = usuarioId;
    }

    public String getRespuesta(){
        return respuesta;
    }

    public void setRespuesta(String respuesta){
        this.respuesta = respuesta;
    }

    public Boolean getResultado(){
        return resultado;
    }

    public void setResultado(Boolean resultado){
        this.resultado = resultado;
    }

}
