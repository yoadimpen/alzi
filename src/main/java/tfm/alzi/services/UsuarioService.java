package tfm.alzi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.Usuario;
import tfm.alzi.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
	private BCryptPasswordEncoder passw;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepository.findByDNI(username);
        
		if (usuario == null) {
			throw new UsernameNotFoundException("No se puede encontrar el usuario");
		}

		return usuario;
    }

    @Transactional
    public void creaUsuario(final Usuario usuario) {
        usuario.setPass(this.passw.encode(usuario.getPass()));
        this.usuarioRepository.save(usuario);
    }

    public long getNumUsuariosByDNI(final String dni) {
        return this.usuarioRepository.numUsuariosByDNI(dni);
    }

    public long getNumUsuariosByEmail(final String email){
        return this.usuarioRepository.numUsuariosByEmail(email);
    }
    
}
