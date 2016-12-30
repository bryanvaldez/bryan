/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
 * @author Bryan
 */
@Entity
@Table(name = "tab_perfil")
public class Perfil implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "n_perfil_pk")        
    private Long id;
    
    @Column(name = "c_nombre_perfil")
    private String nombre;
    
    @Column(name = "n_estado")
    private int estado;
    
    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    private Set<Usuario> usuarios;
        
    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    private List<Opcion> opciones = new ArrayList<Opcion>(); 
    
    public Perfil() {
    }
    
    public Perfil(Object id) {
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }
    
}
