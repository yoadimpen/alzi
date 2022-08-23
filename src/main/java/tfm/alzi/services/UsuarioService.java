package tfm.alzi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.Usuario;
import tfm.alzi.models.UsuarioCuidador;
import tfm.alzi.models.UsuarioEspecialista;
import tfm.alzi.repositories.UsuarioCuidadorRepository;
import tfm.alzi.repositories.UsuarioEspecialistaRepository;
import tfm.alzi.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
	private BCryptPasswordEncoder passw;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioCuidadorRepository usuarioCuidadorRepository;

    @Autowired
    private UsuarioEspecialistaRepository usuarioEspecialistaRepository;

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
    
    @Transactional
    public void editarUsuario(final Usuario usuario) {
        usuario.setPass(this.passw.encode(usuario.getPass()));
        this.usuarioRepository.save(usuario);
    }

    public long getNumUsuariosByDNI(final String dni) {
        return this.usuarioRepository.numUsuariosByDNI(dni);
    }

    public Usuario getUsuarioById(final long id) {
        return this.usuarioRepository.getById(id);
    }

    public long getNumUsuariosByEmail(final String email){
        return this.usuarioRepository.numUsuariosByEmail(email);
    }

    public Usuario getUsuarioByDNI(final String dni){
        return this.usuarioRepository.findByDNI(dni);
    }

    public void deleteUsuario(final String dni) {
        Usuario usuario = this.usuarioRepository.findByDNI(dni);
        this.usuarioRepository.deleteById(usuario.getId());
    }

    public String getAuth(){
        return this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName()).getRoles();
    }

    public String getNameSurname(){
        Usuario usuario = this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName());
        return usuario.getNombre() + " " + usuario.getApellidos();
    }

    public String getNameSurnameAbrv(){
        Usuario usuario = this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName());
        return usuario.getNombre().substring(0, 1) + ". " + usuario.getApellidos();
    }

    public List<Usuario> getUsuariosCuidador(){
        Usuario usuario = this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName());
        List<UsuarioCuidador> ls = this.usuarioCuidadorRepository.findByCuidadorId(usuario.getId());

        List<Usuario> usuarios = new ArrayList<Usuario>();
        for(UsuarioCuidador u:ls){
            usuarios.add(this.usuarioRepository.getById(u.getUsuarioId()));
        }

        return usuarios;
    }

    public List<Usuario> getUsuariosEspecialista(){
        Usuario usuario = this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName());
        List<UsuarioEspecialista> ls = this.usuarioEspecialistaRepository.findByEspecialistaId(usuario.getId());

        List<Usuario> usuarios = new ArrayList<Usuario>();
        for(UsuarioEspecialista u:ls){
            usuarios.add(this.usuarioRepository.getById(u.getUsuarioId()));
        }

        return usuarios;
    }
    
}
