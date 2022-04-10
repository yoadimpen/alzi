package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Programa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "programa_id")
    private long id;

    @Column(name = "especialista")
    private Especialista especialista;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "area")
    private String area;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "tipo_duracion")
    private String tipoDuracion;

    @Column(name = "puntuacion")
    private Integer puntuacion;

    public Long getId(){
        return id;
    }

    public Especialista getEspecialista(){
        return especialista;
    }

    public void setEspecialista(Especialista especialista){
        this.especialista = especialista;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getArea(){
        return area;
    }

    public void setArea(String area){
        this.area = area;
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

    public Integer getPuntuacion(){
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion){
        this.puntuacion = puntuacion;
    }

}
