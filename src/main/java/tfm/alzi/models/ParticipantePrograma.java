package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ParticipantePrograma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "partpro_id")
    private long id;

    @Column(name = "usuario_id")
    private long usuarioId;

    @Column(name = "programa_id")
    private long programaId;

    public Long getId(){
        return id;
    }

    public Long getUsuarioId(){
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId){
        this.usuarioId = usuarioId;
    }

    public Long getProgramaId(){
        return programaId;
    }

    public void setProgramaId(Long programaId){
        this.programaId = programaId;
    }

}
