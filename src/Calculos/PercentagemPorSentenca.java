/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import treinador.Modelo.Alvo;

/**
 * Percentagem de acerto *por sentença*, considerando que, 
 * se o algoritmo acertou alguma percentagem de algum alvo da frase, 
 * então ele acertou aquela sentença; caso contrário, errou.
 * 
 * @author re_hs
 */
public class PercentagemPorSentenca 
{
    private Boolean bAcertou;
    private final ArrayList<Alvo> alvosFrasesFinaisTreinadas;
    
    public PercentagemPorSentenca(ArrayList<Alvo> pAlvosFrasesFinaisTreinadas) 
    {
        alvosFrasesFinaisTreinadas = pAlvosFrasesFinaisTreinadas;
    }

    public void MontaNumAcertos() 
    {
        bAcertou = false;
        alvosFrasesFinaisTreinadas.stream().
                filter((alvo) -> (alvo.getPercentagem() > 0D)).
                forEach((_item) -> {
            bAcertou = true;
        });
    }
    
    public static Double MontaPercentagemAcertos(ArrayList<PercentagemPorSentenca> listaPercentagemPorSentenca)
    {
        Double percentagemPorSentenca;
        
        try 
        {
            Double numAcertoPorSentenca = ((Long)listaPercentagemPorSentenca.stream().
                    filter(s -> s.getbAcertou()).count()).doubleValue();
            percentagemPorSentenca = (numAcertoPorSentenca * 100) / 
                    listaPercentagemPorSentenca.size();
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        return percentagemPorSentenca;
    }
    
    public static String MontaPercentagemAcertosString(ArrayList<PercentagemPorSentenca> listaPercentagemPorSentenca)
    {
        Double percentagem = MontaPercentagemAcertos(listaPercentagemPorSentenca);
        String percentagemString = "";
        
        try 
        {
            DecimalFormat df = new DecimalFormat("0.00");  
             
            percentagemString = "A Percentagem por sentença foi de " +
                    df.format(percentagem);
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        return percentagemString;
    }
    
    /**
     * @return the bAcertou
     */
    public Boolean getbAcertou() {
        return bAcertou;
    }

    /**
     * @return the alvosFrasesFinaisTreinadas
     */
    public ArrayList<Alvo> getAlvosFrasesFinaisTreinadas() {
        return alvosFrasesFinaisTreinadas;
    }
}
