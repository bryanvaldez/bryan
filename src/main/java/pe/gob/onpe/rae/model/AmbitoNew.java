/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model;

/**
 *
 * @author MArrisueno
 */
public class AmbitoNew {
    private Long ID;
    private String NOMBREAMBITO;
    private Long AMBITOPADRE;
    private String DESCRIPCION;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNOMBREAMBITO() {
        return NOMBREAMBITO;
    }

    public void setNOMBREAMBITO(String NOMBREAMBITO) {
        this.NOMBREAMBITO = NOMBREAMBITO;
    }

    public Long getAMBITOPADRE() {
        return AMBITOPADRE;
    }

    public void setAMBITOPADRE(Long AMBITOPADRE) {
        this.AMBITOPADRE = AMBITOPADRE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }
    
    
}
