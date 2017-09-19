/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinador.Modelo;

/**
 *
 * @author re_hs
 */
public class Label 
{
    private Integer indice;
    private String label;

    Label(Integer contadorLabel, String valorLabelRef) 
    {
        indice = contadorLabel;
        label = valorLabelRef;
    }

    /**
     * @return the indice
     */
    public Integer getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
}
