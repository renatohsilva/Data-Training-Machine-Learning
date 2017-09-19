/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinador.Modelo;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author re_hs
 */
public class Alvo 
{
    private Integer tamanho;
    private ArrayList<String> labelsReferencia;
    private ArrayList<String> labelsPrevisto;
    private Double percentagem;
    private String frase;
    private Integer indiceFrase;
    
    
    public Alvo()
    {
        labelsReferencia = new ArrayList<>();
        labelsPrevisto = new ArrayList<>();
    }
    
    public static ArrayList<Alvo> MontarListaAlvos(ArrayList<Label> labelsArquivoTexto,
            ArrayList<String> labelsPrev, String fraseAlvo, Integer indiceFraseAlvo) throws Exception
    {
        ArrayList<Alvo> alvos = new ArrayList<>();
        
        Alvo alvo = new Alvo();
        Integer tamanhoAlvo;
        
        try 
        {
            ArrayList<Label> labelsArquivoTextoRef = labelsArquivoTexto.stream().
                    filter(s -> s.getLabel().trim().equals("B") || 
                                s.getLabel().trim().equals("I")).
                    collect(Collectors.toCollection(ArrayList::new));
            
            for (Label label : labelsArquivoTextoRef) 
            {
                if ((label.getLabel().equals("B")) &&
                    !(VerificaAlvoVazio(alvo)))
                {
                    if (alvo.getLabelsReferencia().size() ==
                        alvo.getLabelsPrevisto().size())
                    {
                        tamanhoAlvo = alvo.getLabelsPrevisto().size();
                        alvo.setTamanho(tamanhoAlvo);
                        alvo.setFrase(fraseAlvo);
                        alvo.setIndiceFrase(indiceFraseAlvo);
                        alvos.add(alvo);
                        
                        alvo = new Alvo();
                    }      
                    else
                    {
                        throw new Exception();
                    }
                }
                
                alvo.getLabelsReferencia().add(label.getLabel());
                alvo.getLabelsPrevisto().add(labelsPrev.get(label.getIndice()));
            }
            
            if (!VerificaAlvoVazio(alvo))
            {
                if (alvo.getLabelsReferencia().size() ==
                    alvo.getLabelsPrevisto().size())
                {
                    tamanhoAlvo = alvo.getLabelsPrevisto().size();
                    alvo.setTamanho(tamanhoAlvo);  
                    alvo.setFrase(fraseAlvo);
                    alvo.setIndiceFrase(indiceFraseAlvo);
                    alvos.add(alvo); 
                }
                else
                {
                    throw new Exception();
                }
            }
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        return alvos;
    }
        
    private static boolean VerificaAlvoVazio(Alvo alvo) 
    {
        return ((alvo.getLabelsReferencia().isEmpty()) && 
                (alvo.getLabelsPrevisto().isEmpty()));
    }
    
    /**
     * @return the Tamanho
     */
    public Integer getTamanho() {
        return tamanho;
    }

    /**
     * @param tamanho the Tamanho to set
     */
    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * @return the labelsReferencia
     */
    public ArrayList<String> getLabelsReferencia() {
        return labelsReferencia;
    }

    /**
     * @param labelsReferencia the labelsReferencia to set
     */
    public void setLabelsReferencia(ArrayList<String> labelsReferencia) {
        this.labelsReferencia = labelsReferencia;
    }

    /**
     * @return the labelsPrevisto
     */
    public ArrayList<String> getLabelsPrevisto() {
        return labelsPrevisto;
    }

    /**
     * @param labelsPrevisto the labelsPrevisto to set
     */
    public void setLabelsPrevisto(ArrayList<String> labelsPrevisto) {
        this.labelsPrevisto = labelsPrevisto;
    }

    /**
     * @return the percentagem
     */
    public Double getPercentagem() {
        return percentagem;
    }

    /**
     * @param percentagem the percentagem to set
     */
    public void setPercentagem(Double percentagem) {
        this.percentagem = percentagem;
    }

    /**
     * @return the frase
     */
    public String getFrase() {
        return frase;
    }

    /**
     * @param frase the frase to set
     */
    public void setFrase(String frase) {
        this.frase = frase;
    }

    /**
     * @return the indiceFrase
     */
    public Integer getIndiceFrase() {
        return indiceFrase;
    }

    /**
     * @param indiceFrase the indiceFrase to set
     */
    public void setIndiceFrase(Integer indiceFrase) {
        this.indiceFrase = indiceFrase;
    }
    
}
