package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UsuarioEspecialista {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "usuario_especialista_id")
    private long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "especialista_id")
    private Long especialistaId;

    public Long getId(){
        return id;
    }

    public Long getUsuarioId(){
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId){
        this.usuarioId = usuarioId;
    }

    public Long getEspecialidadId(){
        return especialistaId;
    }

    public void setEspecialidadId(Long especialidadId){
        this.especialistaId = especialidadId;
    }

}
