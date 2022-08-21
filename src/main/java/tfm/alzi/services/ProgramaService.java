package tfm.alzi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.alzi.models.Programa;
import tfm.alzi.repositories.ProgramaRepository;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;
    
    public Programa getProgramaById(long programaId) {
        return this.programaRepository.getById(programaId);
    }

}
