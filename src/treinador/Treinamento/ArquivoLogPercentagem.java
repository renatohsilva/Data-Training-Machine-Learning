/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinador.Treinamento;

import Calculos.PercentagemPorAlvo;
import Calculos.PercentagemPorAlvoIgual100;
import Calculos.PercentagemPorAlvoMaior50;
import Calculos.PercentagemPorSentenca;
import Calculos.PercentagemPorSentencaIgual100;
import Util.ArquivoUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import treinador.Modelo.Alvo;

/**
 *
 * @author re_hs
 */
public class ArquivoLogPercentagem 
{
    private ArrayList<Alvo> alvosFrasesFinaisTreinadas;
    private String caminhoArquivoLogTagging;
    
    Integer menorIndiceFrase;
    Integer maiorIndiceFrase;
    
    ArrayList<PercentagemPorSentenca> listaPercentagemPorSentenca;
    ArrayList<PercentagemPorSentencaIgual100> listaPercentagemPorSentencaIgual100;
    ArrayList<PercentagemPorAlvo> listaPercentagemPorAlvo;
    ArrayList<PercentagemPorAlvoMaior50> listaPercentagemPorAlvoMaior50;
    ArrayList<PercentagemPorAlvoIgual100> listaPercentagemPorAlvoIgual100;
    
    PercentagemPorSentenca objPercentagemPorSentenca;
    PercentagemPorSentencaIgual100 objPPercentagemPorSentencaIgual100;
    PercentagemPorAlvo objPercentagemPorAlvo;
    PercentagemPorAlvoMaior50 objPPercentagemPorAlvoMaior50;
    PercentagemPorAlvoIgual100 objPercentagemPorAlvoIgual100;
    
    String objPercentagemPorSentencaString;
    String objPPercentagemPorSentencaIgual100String;
    String objPercentagemPorAlvoString;
    String objPPercentagemPorAlvoMaior50String;
    String objPercentagemPorAlvoIgual100String;
        
    public ArquivoLogPercentagem(ArrayList<Alvo> pAlvosFrasesFinaisTreinadas,
            String pCaminhoArquivoLogTagging)
    {
        caminhoArquivoLogTagging = pCaminhoArquivoLogTagging;
        alvosFrasesFinaisTreinadas = pAlvosFrasesFinaisTreinadas;
    }

    /**
     * Monta e Grava o arquivo de log de percentagens extraídas da 
     * rotina de teste
     */
    public void GravarArquivo() 
    {
        listaPercentagemPorSentenca = new ArrayList<>();
        listaPercentagemPorSentencaIgual100 = new ArrayList<>();
        listaPercentagemPorAlvo = new ArrayList<>();
        listaPercentagemPorAlvoMaior50 = new ArrayList<>();
        listaPercentagemPorAlvoIgual100 = new ArrayList<>();
        
        try 
        {
            CalcularPercentagens();
            OrdenarListaPorIndiceFrase();
            
            EncontraIndicesFrase(alvosFrasesFinaisTreinadas);
            
            for (int i = menorIndiceFrase; i <= maiorIndiceFrase; i++) 
            {
                Integer contador = i;
                
                ArrayList<Alvo> alvosDaFrase = alvosFrasesFinaisTreinadas.stream().
                        filter(s -> Objects.equals(s.getIndiceFrase(), contador)).
                        collect(Collectors.toCollection(ArrayList::new));
                
                objPercentagemPorSentenca = 
                        new PercentagemPorSentenca(alvosDaFrase);
                
                objPPercentagemPorSentencaIgual100 = 
                        new PercentagemPorSentencaIgual100(alvosDaFrase);
                
                objPercentagemPorSentenca.MontaNumAcertos();
                objPPercentagemPorSentencaIgual100.MontaNumAcertos();
                
                listaPercentagemPorSentenca.add(objPercentagemPorSentenca);
                listaPercentagemPorSentencaIgual100.add(objPPercentagemPorSentencaIgual100);
                
                alvosDaFrase.stream().forEach((alvo) -> 
                {
                    objPercentagemPorAlvo = 
                            new PercentagemPorAlvo(alvo);
                    
                    objPPercentagemPorAlvoMaior50 = 
                            new PercentagemPorAlvoMaior50(alvo);
                    
                    objPercentagemPorAlvoIgual100 = 
                            new PercentagemPorAlvoIgual100(alvo);
                    
                    objPercentagemPorAlvo.MontaNumAcertos();
                    objPPercentagemPorAlvoMaior50.MontaNumAcertos();
                    objPercentagemPorAlvoIgual100.MontaNumAcertos();
                    
                    listaPercentagemPorAlvo.add(objPercentagemPorAlvo);
                    listaPercentagemPorAlvoMaior50.add(objPPercentagemPorAlvoMaior50);
                    listaPercentagemPorAlvoIgual100.add(objPercentagemPorAlvoIgual100);
                });
            }
           
            objPercentagemPorSentencaString = PercentagemPorSentenca.
                    MontaPercentagemAcertosString(listaPercentagemPorSentenca);
            
            objPPercentagemPorSentencaIgual100String = PercentagemPorSentencaIgual100.
                    MontaPercentagemAcertosString(listaPercentagemPorSentencaIgual100);
            
            objPercentagemPorAlvoString = PercentagemPorAlvo.
                    MontaPercentagemAcertosString(listaPercentagemPorAlvo);
            
            objPPercentagemPorAlvoMaior50String = PercentagemPorAlvoMaior50.
                    MontaPercentagemAcertosString(listaPercentagemPorAlvoMaior50);
            
            objPercentagemPorAlvoIgual100String = PercentagemPorAlvoIgual100.
                    MontaPercentagemAcertosString(listaPercentagemPorAlvoIgual100);
            
            ArrayList<String> arquivoLogTagging = new ArrayList<>();
            
            arquivoLogTagging.add(objPercentagemPorSentencaString);
            arquivoLogTagging.add(objPPercentagemPorSentencaIgual100String);
            arquivoLogTagging.add(objPercentagemPorAlvoString);
            arquivoLogTagging.add(objPPercentagemPorAlvoMaior50String);
            arquivoLogTagging.add(objPercentagemPorAlvoIgual100String);
            
            ArquivoUtil.GravarArquivo(caminhoArquivoLogTagging, arquivoLogTagging);
        } 
        catch (Exception e) 
        { 
            throw e;
        }
    }
     
    private void EncontraIndicesFrase(ArrayList<Alvo> pAlvosFrasesFinaisTreinadas)
    {  
        try 
        {
            Integer tamanhoLista = pAlvosFrasesFinaisTreinadas.size();
            
            menorIndiceFrase = pAlvosFrasesFinaisTreinadas.get(0).getIndiceFrase();
            maiorIndiceFrase = pAlvosFrasesFinaisTreinadas.
                    get(tamanhoLista - 1).getIndiceFrase();    
        } 
        catch (Exception e) 
        { 
            throw e;
        }
    }
    
    private void CalcularPercentagens() 
    {
        try 
        {
            alvosFrasesFinaisTreinadas.stream().forEach((Alvo alvos) -> 
            {
                Double numAcertos = 0D;
                Integer tamanhoAlvo = alvos.getTamanho();
                for (int i = 0; i < tamanhoAlvo; i++)
                {
                    String labelRef = alvos.getLabelsReferencia().get(i);
                    String labelPrev = alvos.getLabelsPrevisto().get(i);
                    
                    if (labelRef.trim().equals(labelPrev.trim()))
                        numAcertos++;
                }
                alvos.setPercentagem((numAcertos * 100) / tamanhoAlvo);
            });
        } 
        catch (Exception e) 
        { 
            throw e;
        }
    }

    private void OrdenarListaPorIndiceFrase() 
    {
        Collections.sort(alvosFrasesFinaisTreinadas, (Alvo o1, Alvo o2) ->
        {
            if(Objects.equals(o1.getIndiceFrase(), o2.getIndiceFrase()))
                return 0;
            return o1.getIndiceFrase() < o2.getIndiceFrase() ? -1 : 1;
        });
    }

    /**
     * @return the alvosFrasesFinaisTreinadas
     */
    public ArrayList<Alvo> getAlvosFrasesFinaisTreinadas() {
        return alvosFrasesFinaisTreinadas;
    }

    /**
     * @param alvosFrasesFinaisTreinadas the alvosFrasesFinaisTreinadas to set
     */
    public void setAlvosFrasesFinaisTreinadas(ArrayList<Alvo> alvosFrasesFinaisTreinadas) {
        this.alvosFrasesFinaisTreinadas = alvosFrasesFinaisTreinadas;
    }

    /**
     * @return the caminhoArquivoLogTagging
     */
    public String getCaminhoArquivoLogTagging() {
        return caminhoArquivoLogTagging;
    }

    /**
     * @param caminhoArquivoLogTagging the caminhoArquivoLogTagging to set
     */
    public void setCaminhoArquivoLogTagging(String caminhoArquivoLogTagging) {
        this.caminhoArquivoLogTagging = caminhoArquivoLogTagging;
    }
}
