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
 * Percentagem de acerto *por alvo*, 
 * considerando como acerto somente os casos em acertou 100% do alvo;
 * 
 * @author re_hs
 */
public class PercentagemPorAlvoIgual100 
{
    private Boolean bAcertou;
    private final Alvo alvo;
    
    public PercentagemPorAlvoIgual100(Alvo pAlvos)  
    {
         alvo = pAlvos;
    }

    public void MontaNumAcertos() 
    {
        bAcertou = false;
        if (alvo.getPercentagem() == 100D) 
            bAcertou = true;
    }
    
    public static Double MontaPercentagemAcertos(ArrayList<PercentagemPorAlvoIgual100> listaPercentagemPorAlvoIgual100)
    {
        Double percentagemPorAlvoIgual100;
        try 
        {
            Double numAcertoPorAlvoIgual100 = ((Long)listaPercentagemPorAlvoIgual100.stream().
                    filter(s -> s.getbAcertou()).count()).doubleValue();
            percentagemPorAlvoIgual100 = (numAcertoPorAlvoIgual100 * 100) / 
                    listaPercentagemPorAlvoIgual100.size();             
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        return percentagemPorAlvoIgual100;
    }
    
    public static String MontaPercentagemAcertosString(ArrayList<PercentagemPorAlvoIgual100> listaPercentagemPorAlvoIgual100)
    {
        Double percentagem = MontaPercentagemAcertos(listaPercentagemPorAlvoIgual100);
        String percentagemString = "";
        
        try 
        {
            DecimalFormat df = new DecimalFormat("0.00");  
             
            percentagemString = "A Percentagem por alvo igual "
                    + "a 100% foi de " + df.format(percentagem);
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
     * @return the alvo
     */
    public Alvo getAlvo() {
        return alvo;
    }
}
