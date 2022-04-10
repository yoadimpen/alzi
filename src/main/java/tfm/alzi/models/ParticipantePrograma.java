package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class ParticipantePrograma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partpro_id")
    private long id;

    @OneToOne
    @Column(name = "participante")
    private Participante participante;

    @OneToOne
    @Column(name = "programa")
    private Programa programa;

    public Long getId(){
        return id;
    }

    public Participante getParticipante(){
        return participante;
    }

    public void setParticipante(Participante participante){
        this.participante = participante;
    }

    public Programa getPrograma(){
        return programa;
    }

    public void setPrograma(Programa programa){
        this.programa = programa;
    }

}
