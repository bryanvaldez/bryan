package pe.gob.onpe.rae.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import pe.gob.onpe.rae.helper.TypesUtil;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Entity
@Table(name = "tab_usuario")
public class Usuario implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "n_usuario_pk")
    private Long id;
    
    @Column(name = "c_usuario")
    private String usuario;
    
    @Column(name = "c_apellido_paterno")
    private String apellidoPaterno;
    
    @Column(name = "c_apellido_materno")    
    private String apellidoMaterno;
    
    @Column(name = "c_nombre")      
    private String nombre;
    
    @Column(name = "c_clave")    
    private String clave;
    
    @Column(name = "n_estado")    
    private int estado;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "n_perfil")
    private Perfil perfil;
    
    @Column(name = "c_ubigeo")      
    private String ubigeo;     
            
    @Column(name = "d_fecha_inicial")      
    private Date fechaInicial;
    
    @Column(name = "d_fecha_final")      
    private Date fechaFinal;             

    public Usuario() {}
    
    public Usuario(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }         
    
    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    
    public String getNombreCompleto(){
        return this.nombre + " "+ this.apellidoPaterno + " "+ this.apellidoMaterno;
    }    
    
    public String getApellidosCompletos(){
        return this.apellidoPaterno + " "+ this.apellidoMaterno;
    }      
}
