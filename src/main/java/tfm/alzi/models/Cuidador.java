package tfm.alzi.models;

import jakarta.persistence.Column;

public class Cuidador extends Persona {
    
    @Column(name = "tipo")
    private String tipo;

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }
}
