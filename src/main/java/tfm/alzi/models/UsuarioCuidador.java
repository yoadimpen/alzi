package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UsuarioCuidador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "usuario_cuidador_id")
    private long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "cuidador_id")
    private Long cuidadorId;

    public Long getId(){
        return id;
    }

    public Long getUsuarioId(){
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId){
        this.usuarioId = usuarioId;
    }

    public Long getCuidadorId(){
        return cuidadorId;
    }

    public void setCuidadorId(Long cuidadorId){
        this.cuidadorId = cuidadorId;
    }

}
