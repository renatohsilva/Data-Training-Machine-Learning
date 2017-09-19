/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinador;

import treinador.Modelo.Frase;
import treinador.Treinamento.ArquivoValidacao;
import static Util.OperacoesUteis.BuscarCaminhoArquivo;
import static Util.OperacoesUteis.BuscarPastaPadrao;
import Util.OperacoesUteis.TipoSistema;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import treinador.Modelo.Alvo;
import treinador.Treinamento.ArquivoLogPercentagem;

/**
 *
 * @author renato
 */
public class Treinador 
{
    private static ArquivoValidacao arquivoValidacao;
    
    private static Integer tamanhoTotalLista;
    private static Integer numIteracao = 0;
    
    private static String caminhoPastaArquivos;
    
    private static final TipoSistema sistemaEscolhido = TipoSistema.Linux;
    private static final String nomeArquivoBase = "Atributos.txt";
    private static final Integer tamanhoParteLista = 324;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        ArrayList<Alvo> alvosFrasesFinaisTreinadas = new ArrayList<>();
        ArrayList<ArquivoValidacao> iteracoesTreinamento = new ArrayList<>();
        
        try 
        {
            Frase objFrases = new Frase();

            caminhoPastaArquivos = 
                    BuscarCaminhoArquivo(sistemaEscolhido, BuscarPastaPadrao()); 
            
            ArrayList<Frase> frases = objFrases.RetornaFrases(caminhoPastaArquivos, 
                    nomeArquivoBase);
                      
            tamanhoTotalLista = frases.size();
            
            for (int i = 0; i < tamanhoTotalLista; i += tamanhoParteLista) 
            {                     
                String nomeModeloAprendizado = (numIteracao + 1) + "-CRF" + 
                                (numIteracao + 1) + ".model";
                
                ArrayList<Frase> frasesParaTeste = 
                        frases.subList(i, MaximoParteLista(i, tamanhoTotalLista)).
                        stream().collect(Collectors.toCollection(ArrayList::new));
                
                ArrayList<Frase> frasesParaTreino = frases.stream().
                        filter(r -> RestoLista(frasesParaTeste, r)).
                        collect(Collectors.toCollection(ArrayList::new));
                
                arquivoValidacao = new ArquivoValidacao(frasesParaTeste, 
                        frasesParaTreino, caminhoPastaArquivos, numIteracao + 1);
                
                ArrayList<String> labelsValidacao = arquivoValidacao.
                        Validar(sistemaEscolhido, nomeModeloAprendizado);
                
                alvosFrasesFinaisTreinadas.addAll(arquivoValidacao.
                        MontarAlvos(labelsValidacao, frasesParaTeste));                
                       
                iteracoesTreinamento.add(arquivoValidacao);
                
                numIteracao++;
            }
           
            ApagarArquivosTreinamento(iteracoesTreinamento);
            
            ArquivoLogPercentagem arquivoLog = 
                    new ArquivoLogPercentagem(alvosFrasesFinaisTreinadas,
                    BuscaCaminhoArquivoLogTag());
            
            arquivoLog.GravarArquivo();
        } 
        catch (Exception e) 
        {
            throw new Error(e.getMessage());
        }            
    }   
    
    
    private static String BuscaCaminhoArquivoLogTag() 
    {
        String caminho = "";
        try 
        {
            Path caminhoArquivoMontado = Paths.get(caminhoPastaArquivos, 
                    "Validação", "LogTagging.txt");
            
            caminho = caminhoArquivoMontado.toString();
        } 
        catch (Exception e) 
        { 
            throw e;
        }
        
        return caminho;
    }
    
    private static void ApagarArquivosTreinamento(ArrayList<ArquivoValidacao> iteracoesTreinamento) 
    {
        try 
        {
            iteracoesTreinamento.stream().forEach((iteracaoTreinamento) -> 
            {
                File arquivoModelo = new File(iteracaoTreinamento.
                        getArquivoAprendizado().getCaminhoModelo());
                
                File arquivoTreinamento = iteracaoTreinamento.
                        getArquivoAprendizado().getArquivo();
                
                File arquivoTeste = iteracaoTreinamento.getArquivoTeste().
                        getArquivo();
                
                arquivoTreinamento.delete();
                arquivoTeste.delete();
                arquivoModelo.delete();
            });
        } 
        catch (Exception e) 
        { 
            throw e;
        }
    }

    private static int MaximoParteLista(int i, Integer tamanhoTotalLista) 
    {
        return i + Math.min(tamanhoParteLista, tamanhoTotalLista - i);
    }

    private static boolean RestoLista(List<Frase> frasesParaTeste, Frase r) 
    {
        return !frasesParaTeste.stream().map(s -> s.getIndice()).
                collect(Collectors.toList()).
                contains(r.getIndice());
    }

    /**
     * @return the arquivoTreinamento
     */
    public static ArquivoValidacao getArquivoValidacao() 
    {
        return arquivoValidacao;
    }

    /**
     * @param ArquivoValidacao the arquivoTreinamento to set
     */
    public static void setArquivoValidacao(ArquivoValidacao ArquivoValidacao) 
    {
        arquivoValidacao = ArquivoValidacao;
    }

    /**
     * @return the caminhoPastaArquivos
     */
    public static String getCaminhoPastaArquivos() {
        return caminhoPastaArquivos;
    }

    /**
     * @param aCaminhoPastaArquivos the caminhoPastaArquivos to set
     */
    public static void setCaminhoPastaArquivos(String aCaminhoPastaArquivos) {
        caminhoPastaArquivos = aCaminhoPastaArquivos;
    }

}
