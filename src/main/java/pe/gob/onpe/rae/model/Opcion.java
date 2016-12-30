package pe.gob.onpe.rae.model;

import java.io.Serializable;
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
@Table(name = "tab_opcion")
public class Opcion implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "n_opcion_pk")        
    private Long id;
    
    @Column(name = "c_nombre_opcion")
    private String nombre;
    
    @Column(name = "c_enlace")
    private String enlace;
    
    @Column(name = "n_orden")
    private int orden;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "n_modulo")
    private Modulo modulo;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "n_perfil")    
    private Perfil perfil;

    public Opcion() {
    }
    
    public Opcion(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }       

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
}
