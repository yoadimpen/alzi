package tfm.alzi.models;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Usuario implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    @Getter private long id;

    public long getId() {
        return this.id;
    }

    @Column(name = "nombre")
    @Getter @Setter private String nombre;

    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "apellidos")
    @Getter @Setter private String apellidos;

    public String getApellidos() {
        return this.apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Column(name = "fecha_nacimiento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter private LocalDate fechaNacimiento;

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Column(name = "dni")
    @Getter @Setter private String dni;

    public String getDni() {
        return this.dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    @Column(name = "pass")
    @Getter @Setter private String pass;

    public String getPass() {
        return this.pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    @Column(name = "direccion")
    @Getter @Setter private String direccion;

    public String getDireccion() {
        return this.direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "telefono")
    @Getter @Setter private Integer telefono;

    public Integer getTelefono() {
        return this.telefono;
    }
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    @Column(name = "email")
    @Getter @Setter private String email;

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "bio")
    @Getter @Setter private String bio;

    public String getBio() {
        return this.bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    @Column(name = "roles")
    @Getter @Setter private String roles;

    public String getRoles() {
        return this.roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    
    @Column(name = "p_relacion_cuidador")
    @Getter @Setter private String PRelacionCuidador;

    public String getPRelacionCuidador() {
        return this.PRelacionCuidador;
    }
    public void setPRelacionCuidador(String PRelacionCuidador) {
        this.PRelacionCuidador = PRelacionCuidador;
    }

    @Column(name = "c_tipo")
    @Getter @Setter private String CTipo;

    public String getCTipo() {
        return this.CTipo;
    }
    public void setCTipo(String CTipo) {
        this.CTipo = CTipo;
    }

    @Column(name = "e_especialidad")
    @Getter @Setter private String EEspecialidad;

    public String getEEspecialidad() {
        return this.EEspecialidad;
    }
    public void setEEspecialidad(String EEspecialidad) {
        this.EEspecialidad = EEspecialidad;
    }

    @Column(name = "e_centro")
    @Getter @Setter private String ECentro;

    public String getECentro() {
        return this.ECentro; 
    }
    public void setECentro(String ECentro) {
        this.ECentro = ECentro;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String getPassword() {
        return pass;
    }
    @Override
    public String getUsername() {
        return dni;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }   

}
