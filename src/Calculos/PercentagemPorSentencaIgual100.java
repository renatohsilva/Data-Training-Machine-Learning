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
 * Percentaem de acerto por sentença, mas considerando que o algoritmo acertou 
 * a sentença só qdo ele acertou 100% do alvo de pelo menos um alvo.
 * 
 * @author re_hs
 */
public class PercentagemPorSentencaIgual100 
{
    private Boolean bAcertou;
    private final ArrayList<Alvo> alvosFrasesFinaisTreinadas;
    
    public PercentagemPorSentencaIgual100(ArrayList<Alvo> pAlvosFrasesFinaisTreinadas)  
    {
         alvosFrasesFinaisTreinadas = pAlvosFrasesFinaisTreinadas;
    }

    public void MontaNumAcertos() 
    {
        bAcertou = false;
        long numElementos = alvosFrasesFinaisTreinadas.stream().
                filter(s -> s.getPercentagem() == 100D).count();
        
        if (numElementos == alvosFrasesFinaisTreinadas.size()) 
            bAcertou = true;
    }
    
  
    private static Double MontaPercentagemAcertos(ArrayList<PercentagemPorSentencaIgual100> listaPercentagemPorSentencaIgual100)
    {
        Double percentagemPorSentencaIgual100;
        try 
        {
            Double numAcertoPorSentencaIgual100 = ((Long)listaPercentagemPorSentencaIgual100.stream().
                    filter(s -> s.getbAcertou()).count()).doubleValue();
            percentagemPorSentencaIgual100 = (numAcertoPorSentencaIgual100 * 100)/ 
                    listaPercentagemPorSentencaIgual100.size();
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        return percentagemPorSentencaIgual100;
    }
    
    public static String MontaPercentagemAcertosString(ArrayList<PercentagemPorSentencaIgual100> listaPercentagemPorSentencaIgual100)
    {
        Double percentagem = MontaPercentagemAcertos(listaPercentagemPorSentencaIgual100);
        String percentagemString = "";
        
        try 
        {
            DecimalFormat df = new DecimalFormat("0.00");  
   
            percentagemString = "A Percentagem por sentença "
                    + "igual a 100% foi de " + df.format(percentagem);
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
