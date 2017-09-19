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
 * Percentagem de acerto *por alvo*, considerando como acerto 
 * qualquer % de acerto do alvo > 50%;
 * 
 * @author re_hs
 */
public class PercentagemPorAlvoMaior50 
{
    private Boolean bAcertou;
    private final Alvo alvo;
    
    public PercentagemPorAlvoMaior50(Alvo pAlvos)  
    {
         alvo = pAlvos;
    }

    public void MontaNumAcertos() 
    {
        bAcertou = false;
        if (alvo.getPercentagem() > 50D) 
            bAcertou = true;
    }
    
    public static Double MontaPercentagemAcertos(ArrayList<PercentagemPorAlvoMaior50> listaPercentagemPorAlvoMaior50)
    {
        Double percentagemPorAlvoMaior50;
        try 
        {
            Double numAcertoPorAlvoMaior50 = ((Long)listaPercentagemPorAlvoMaior50.stream().
                    filter(s -> s.getbAcertou()).count()).doubleValue();
            percentagemPorAlvoMaior50 = (numAcertoPorAlvoMaior50 * 100)/ 
                    listaPercentagemPorAlvoMaior50.size();
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        return percentagemPorAlvoMaior50;
    }
    
    public static String MontaPercentagemAcertosString(ArrayList<PercentagemPorAlvoMaior50> listaPercentagemPorAlvoMaior50)
    {
        Double percentagem = MontaPercentagemAcertos(listaPercentagemPorAlvoMaior50);
        String percentagemString = "";
        
        try 
        {
            DecimalFormat df = new DecimalFormat("0.00");  
                        
            percentagemString = "A Percentagem por alvo "
                    + "maior que 50% foi de " + df.format(percentagem);
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
