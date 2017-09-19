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
 * considerando como acerto qualquer % de acerto do alvo;
 * 
 * @author re_hs
 */
public class PercentagemPorAlvo 
{
    private Boolean bAcertou;
    private final Alvo alvo;
    
    public PercentagemPorAlvo(Alvo pAlvos)  
    {
         alvo = pAlvos;
    }

    public void MontaNumAcertos() 
    {
        bAcertou = false;
        if (alvo.getPercentagem() > 0D) 
            bAcertou = true;
    }
    
    private static Double MontaPercentagemAcertos(ArrayList<PercentagemPorAlvo> listaPercentagemPorAlvo)
    {
        Double percentagemPorAlvo;
        
        try 
        {
            Double numAcertoPorAlvo = ((Long)listaPercentagemPorAlvo.stream().
                    filter(s -> s.getbAcertou()).count()).doubleValue();
            
            percentagemPorAlvo = (numAcertoPorAlvo  * 100) / 
                    listaPercentagemPorAlvo.size();
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        return percentagemPorAlvo;
    }
    
    public static String MontaPercentagemAcertosString(ArrayList<PercentagemPorAlvo> listaPercentagemPorAlvo)
    {
        Double percentagem = MontaPercentagemAcertos(listaPercentagemPorAlvo);
        String percentagemString = "";
        
        try 
        {
            DecimalFormat df = new DecimalFormat("0.00");  
            
            percentagemString = "A Percentagem por alvo foi de " + df.format(percentagem);
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
