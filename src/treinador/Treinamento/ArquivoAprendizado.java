/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinador.Treinamento;

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
import treinador.Modelo.Frase;

/**
 *
 * @author renato
 */
public class ArquivoAprendizado 
{
    private ArrayList<String> frases;
    private String caminhoArquivo;
    private String caminhoModelo;
    private String caminhoLog;
    private final Integer iteracao;
    private File arquivo;
    private ArrayList<Frase> listaObjFrases;
    
    public ArquivoAprendizado(String pCaminhoPastaArquivos, Integer pIteracao, 
            ArrayList<Frase> frasesTreinamento)
    {
        frases = new ArrayList<>();
        caminhoArquivo = pCaminhoPastaArquivos;
        iteracao = pIteracao;   
        listaObjFrases = frasesTreinamento;
    }
    
    /**
     * Grava o arquivo de saída da rotina de aprendizado
     */
    public void GravarArquivoAprendizado() 
    {
        try 
        {
            listaObjFrases.stream().forEach((frase) ->
            {
                this.getFrases().add(frase.getTexto());
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
                    getIteracao() + "-AprendizadoP" + getIteracao() + ".txt");
            
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
     * Executa a rotina de aprendizado
     * @param caminhoPasta
     * @param sistemaOperacional
     * @param nomeModelo
     * @param nomeArquivo
     * @throws InterruptedException
     */
    public void Aprender(String caminhoPasta, 
            TipoSistema sistemaOperacional, String nomeModelo, 
            String nomeArquivo) throws InterruptedException 
    {
        ArrayList<String> arrayTrainingLog = new ArrayList<>();
        
        Path caminhoArquivoLogMontado = Paths.get(caminhoPasta, "LogsAprendizado",
                    getIteracao() + "-LogAprendizadoP" + getIteracao() + ".txt");
        
        Path caminhoArquivoModelo = Paths.get(caminhoPasta,
                    nomeModelo);
        try 
        {
            
            ProcessBuilder pbLearn;
            Process pLearn;
            File f = new File(caminhoPasta);  
            
            switch(sistemaOperacional)
            {
                case Linux:                   
                    pbLearn = new ProcessBuilder("./crfsuite", "learn", "-m", 
                            nomeModelo, nomeArquivo); 
                    break;
                default:
                    pbLearn = new ProcessBuilder(caminhoPasta + 
                            "\\crfsuite.exe", "learn", "-m", nomeModelo, 
                            nomeArquivo);
                    break;
            }
            
            pbLearn.directory(f);
            pLearn = pbLearn.start();
            
            InputStream stdin = pLearn.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            
            String line;
            
            while ((line = br.readLine()) != null)
                arrayTrainingLog.add(line);
            
            pLearn.waitFor();  
            
            this.setCaminhoModelo(caminhoArquivoModelo.toString());
            
            this.setCaminhoLog(caminhoArquivoLogMontado.toString());
            
            File arquivoLog = new File(this.getCaminhoLog());
            if(!arquivoLog.exists())
                CriarPastas(caminhoArquivoLogMontado);
            
            ArquivoUtil.GravarArquivo(caminhoArquivoLogMontado.toString(),
                    arrayTrainingLog);
        } 
        catch (IOException e) 
        {
            throw new Error(e.getMessage());
        }
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
     * @return the caminhoLog
     */
    public String getCaminhoLog() {
        return caminhoLog;
    }

    /**
     * @param caminhoLog the caminhoLog to set
     */
    public void setCaminhoLog(String caminhoLog) {
        this.caminhoLog = caminhoLog;
    }

    /**
     * @return the caminhoModelo
     */
    public String getCaminhoModelo() {
        return caminhoModelo;
    }

    /**
     * @param caminhoModelo the caminhoModelo to set
     */
    public void setCaminhoModelo(String caminhoModelo) {
        this.caminhoModelo = caminhoModelo;
    }

    /**
     * @return the listaObjFrases
     */
    public ArrayList<Frase> getListaObjFrases() {
        return listaObjFrases;
    }

    /**
     * @param listaObjFrases the listaObjFrases to set
     */
    public void setListaObjFrases(ArrayList<Frase> listaObjFrases) {
        this.listaObjFrases = listaObjFrases;
    }
}
