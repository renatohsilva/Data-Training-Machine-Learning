/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinador.Treinamento;

import treinador.Modelo.Frase;
import Util.OperacoesUteis.TipoSistema;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import treinador.Modelo.Alvo;
import treinador.Modelo.Label;

/**
 *
 * @author renato
 */
public class ArquivoValidacao 
{
    private ArquivoAprendizado objAprendizado;
    private ArquivoTestador objTeste;
    
    public ArquivoValidacao(ArrayList<Frase> frasesTeste, 
            ArrayList<Frase> frasesTreinamento, String caminhoArquivo, 
            Integer iteracao)
    {
        try 
        {
            objAprendizado = new ArquivoAprendizado(caminhoArquivo, iteracao, 
                    frasesTreinamento);
            objAprendizado.GravarArquivoAprendizado();
            
            objTeste = new ArquivoTestador(caminhoArquivo, iteracao, 
                    frasesTeste);
            objTeste.GravarArquivoTeste();       
        } 
        catch (Exception e) 
        {
            throw new Error(e.getMessage());
        }
    }

    /**
     * Executa o aprendizado e o teste do treinamento.
     * @param sistemaOperacional
     * @param nomeModelo
     * @return
     */
    public ArrayList<String> Validar(TipoSistema sistemaOperacional, 
            String nomeModelo)
    {
        
        ArrayList<String> conjLabels;
                
        File arquivoAprendizado = this.getArquivoAprendizado().getArquivo();
        File arquivoTeste = this.getArquivoTeste().getArquivo();
        try 
        {              
            this.getArquivoAprendizado().Aprender(arquivoAprendizado.getParent(), 
                    sistemaOperacional, nomeModelo, arquivoAprendizado.getName());
            
            conjLabels = this.getArquivoTeste().Testar(arquivoTeste.getParent(), 
                    sistemaOperacional, nomeModelo, arquivoTeste.getName(), 
                    this.getArquivoTeste());
        }
        catch(Exception e)
        {
             throw new Error(e.getMessage());
        }
        
        return conjLabels;
    } 

    /**
     * Monta lista de arquivos de alvos
     * @param conjLabels
     * @param frasesParaTeste
     * @return
     * @throws Exception
     */
    public ArrayList<Alvo> MontarAlvos(ArrayList<String> conjLabels, 
           ArrayList<Frase> frasesParaTeste) throws Exception 
    {
        ArrayList<Alvo> listaAlvosRetorno = new ArrayList<>();
        try 
        {
            ArrayList<Label> labelsArquivoTexto;
            ArrayList<String> labelsPrev = new ArrayList<>();
            
            ArrayList<String> listaLabels = new ArrayList<>();
            StringBuilder label = new StringBuilder();
            
            for (String labels : conjLabels) 
            {
                if ((labels.trim().equals("")) &&
                    !(label.toString().isEmpty())) 
                {
                    listaLabels.add(label.toString());
                    label = new StringBuilder();
                }
                else
                {
                    label.append(labels.trim());
                    label.append(System.getProperty("line.separator"));
                }
            }
            
            Integer contador = 0;
            for (String labels : listaLabels) 
            {         
                ArrayList<Alvo> listaAlvos = new ArrayList<>();
                List<String> labelsFrase = 
                        Arrays.asList(labels.split(System.getProperty("line.separator"))).
                        stream().filter(s -> !s.equals("")).map(a -> a.trim()).
                        collect(Collectors.toCollection(ArrayList::new));
                
                labelsPrev.addAll(labelsFrase);
                                   
                labelsArquivoTexto = this.objTeste.getListaObjFrases().
                        get(contador).getLabelsArquivoTexto();
                
                Integer indiceFraseAlvo = frasesParaTeste.get(contador).getIndice();
                String fraseAlvo = frasesParaTeste.get(contador).getTexto();
                listaAlvos.addAll(Alvo.
                        MontarListaAlvos(labelsArquivoTexto, labelsPrev, 
                                fraseAlvo, indiceFraseAlvo));                
                listaAlvosRetorno.addAll(listaAlvos);
                
                labelsPrev = new ArrayList<>();
                contador++;
            }  
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        return listaAlvosRetorno;
    }
    
    /**
     * @return the arquivoAprendizado
     */
    public ArquivoAprendizado getArquivoAprendizado() 
    {
        return objAprendizado;
    }

    /**
     * @return the arquivoTeste
     */
    public ArquivoTestador getArquivoTeste() 
    {
        return objTeste;
    }

}
