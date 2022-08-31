package tfm.alzi.models;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Recordatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "recordatorio_id")
    private long id;

    @Column(name = "usuario_id")
    private long usuarioId;
    
    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fecha;

    @Column(name = "etiqueta")
    private String etiqueta;

    @Column(name = "descripcion")
    private String descripcion;

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId(){
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId){
        this.usuarioId = usuarioId;
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

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

}
