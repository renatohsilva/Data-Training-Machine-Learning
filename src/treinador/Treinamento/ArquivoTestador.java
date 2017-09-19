/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinador.Treinamento;

import treinador.Modelo.Frase;
import Util.ArquivoUtil;
import static Util.OperacoesUteis.CriarPastas;
import Util.OperacoesUteis.TipoSistema;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author renato
 */
public class ArquivoTestador 
{
    private final Integer iteracao;
    private ArrayList<String> frases;
    private ArrayList<Integer> indicesFrases;
    private String caminhoArquivo;
    private File arquivo;
    private ArrayList<Frase> listaObjFrases;
    
    public ArquivoTestador(String pCaminhoPastaArquivos, Integer pIteracao,
            ArrayList<Frase> frasesPTeste)
    {
        frases = new ArrayList<>();
        indicesFrases = new ArrayList<>();
        caminhoArquivo = pCaminhoPastaArquivos;
        iteracao = pIteracao;
        listaObjFrases = frasesPTeste;
    }
    
    /**
     * Grava o arquivo de saída da rotina de teste
     */
    public void GravarArquivoTeste() 
    {
        try 
        {
            listaObjFrases.stream().forEach((frase) ->
            {
                this.getFrases().add(frase.getTexto());
                this.getIndicesFrases().add(frase.getIndice());
            });
            this.GravarArquivo();   
        }
        catch (Exception e) 
        {
            throw e;
        }
    }
    
    private void GravarArquivo()
    {
        try 
        {
            Path caminhoArquivoMontado = Paths.get(caminhoArquivo, "Validação", 
                    getIteracao() + "-TesteP" + getIteracao() + ".txt");
            
            arquivo = caminhoArquivoMontado.toFile();
            if(!arquivo.exists())
            {
                CriarPastas(caminhoArquivoMontado);
            }
            
            caminhoArquivo = caminhoArquivoMontado.toString();
            
            ArquivoUtil.GravarArquivo(caminhoArquivo, frases);
        } 
        catch (Exception e) 
        {
            throw new Error(e.getMessage());
        }
    }
    
    /**
     * Executa a rotina de teste
     * @param caminhoArquivo
     * @param sistemaOperacional
     * @param nomeModelo
     * @param nomeArquivo
     * @param objTestador
     * @return
     * @throws InterruptedException
     */
    public ArrayList<String> Testar(String caminhoArquivo, TipoSistema sistemaOperacional, 
            String nomeModelo, String nomeArquivo, ArquivoTestador objTestador)
            throws InterruptedException
    {
        ArrayList<String> arrayTagging = new ArrayList<>();
       
        try 
        {
            ProcessBuilder pbLearn;
            Process pLearn;

            File f = new File(caminhoArquivo);  
            
            switch(sistemaOperacional)
            {
                case Linux:
                    pbLearn = new ProcessBuilder("./crfsuite", "tag", "-m", 
                            nomeModelo, nomeArquivo);
                    break;
                default:
                    pbLearn = new ProcessBuilder(caminhoArquivo + "\\crfsuite.exe", 
                            "tag", "-m", nomeModelo, nomeArquivo);
                    break;
            }
            
            pbLearn.directory(f);
            pLearn = pbLearn.start();
            
            InputStream stdin = pLearn.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            
            String line;
            
            while ((line = br.readLine()) != null)
                arrayTagging.add(line);
            
            pLearn.waitFor();  
        } 
        catch (IOException e) 
        {
            throw new Error(e.getMessage());
        }
        
        return arrayTagging;
    }
      
    /**
     * @return the frases
     */
    public ArrayList<String> getFrases() 
    {
        return frases;
    }

    /**
     * @param frases the frases to set
     */
    public void setFrases(ArrayList<String> frases) 
    {
        this.frases = frases;
    }

    /**
     * @return the caminhoArquivo
     */
    public String getCaminhoArquivo() 
    {
        return caminhoArquivo;
    }

    /**
     * @param caminhoArquivo the caminhoArquivo to set
     */
    public void setCaminhoArquivo(String caminhoArquivo) 
    {
        this.caminhoArquivo = caminhoArquivo;
    }
    
    /**
     * @return the iteracao
     */
    public Integer getIteracao() 
    {
        return iteracao;
    }

    /**
     * @return the arquivo
     */
    public File getArquivo() 
    {
        return arquivo;
    }

    /**
     * @return the indicesFrases
     */
    public ArrayList<Integer> getIndicesFrases() 
    {
        return indicesFrases;
    }

    /**
     * @param indicesFrases the indicesFrases to set
     */
    public void setIndicesFrases(ArrayList<Integer> indicesFrases) 
    {
        this.indicesFrases = indicesFrases;
    }

    /**
     * @return the frasesParaTeste
     */
    public List<Frase> getListaObjFrases() 
    {
        return listaObjFrases;
    }

    /**
     * @param listaObjFrases the frasesParaTeste to set
     */
    public void setListaObjFrases(ArrayList<Frase> listaObjFrases) 
    {
        this.listaObjFrases = listaObjFrases;
    }
    
}
