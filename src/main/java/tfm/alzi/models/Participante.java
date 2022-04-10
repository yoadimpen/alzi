package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;

public class Participante extends Persona {
    
    @Column(name = "relacion_cuidador")
    private String relacionCuidador;

    @ManyToOne(optional = true)
    private Cuidador cuidador;

    public String getRelacionCuidador(){
        return relacionCuidador;
    }

    public void setRelacionCuidador(String relacionString){
        this.relacionCuidador = relacionString;
    }

    public Cuidador getCuidador(){
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador){
        this.cuidador = cuidador;
    }

}
