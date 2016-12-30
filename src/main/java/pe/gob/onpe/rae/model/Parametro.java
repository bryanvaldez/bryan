package pe.gob.onpe.rae.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import pe.gob.onpe.rae.helper.TypesUtil;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Entity
@Table(name = "tab_parametro")
public class Parametro implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "n_parametro_pk")
    private Long id;
    
    @Column(name = "c_descripcion")
    private String descripcion;
    
//    @Column(name = "c_valor")
//    private String valor;
    
    @Column(name = "n_codigo")
    private int codigo;
    
    @Column(name = "n_orden")
    private int orden;
    
    @Column(name = "n_flag")
    private int flag;
    
    @Column(name = "n_tipo")
    private int tipo;
    
    @Column(name = "c_titulo")
    private String titulo;
    
    @Column(name = "c_observacion")
    private String observacion;
    
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "n_tipo_parametro_pk")
//    private TipoParametro tipoParametro;
//    

    public Parametro() {
    }
    
    public Parametro(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    public String getValor() {
//        return valor;
//    }
//
//    public void setValor(String valor) {
//        this.valor = valor;
//    }

//    public TipoParametro getTipoParametro() {
//        return tipoParametro;
//    }
//
//    public void setTipoParametro(TipoParametro tipoParametro) {
//        this.tipoParametro = tipoParametro;
//    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }        

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
        
}
