package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    @Column(name = "tipo_duracion")
    private String tipoDuracion;

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

    public String getTipoDuracion(){
        return tipoDuracion;
    }

    public void setTipoDuracion(String tipoDuracion){
        this.tipoDuracion = tipoDuracion;
    }

}
