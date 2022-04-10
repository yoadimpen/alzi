package tfm.alzi.models;

import jakarta.persistence.Column;

public class Especialista extends Persona {
    
    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "centro")
    private String centro;

    public String getEspecialidad(){
        return especialidad;
    }

    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }

    public String getCentro(){
        return centro;
    }

    public void setCentro(String centro){
        this.centro = centro;
    }

}
