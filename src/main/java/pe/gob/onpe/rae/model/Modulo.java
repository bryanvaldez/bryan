package pe.gob.onpe.rae.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import pe.gob.onpe.rae.helper.TypesUtil;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Entity
@Table(name = "tab_modulo")
public class Modulo implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "n_modulo_pk")        
    private Long id;
    
    @Column(name = "c_nombre_modulo")   
    private String nombre;
    
    @Column(name = "c_enlace")   
    private String enlace;
    
    @OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY)
    private List<Opcion> opciones = new ArrayList<Opcion>();    
    
    public Modulo() {
    }
    
    public Modulo(Object id) {
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

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }


}
