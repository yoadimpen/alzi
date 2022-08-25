package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ejercicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ejercicio_id")
    private long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "publico")
    private Boolean publico;

    public Long getId(){
        return id;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public Integer getDuracion(){
        return duracion;
    }

    public void setDuracion(Integer duracion){
        this.duracion = duracion;
    }

    public Boolean getPublico(){
        return publico;
    }

    public void setPublico(Boolean publico){
        this.publico = publico;
    }

}
