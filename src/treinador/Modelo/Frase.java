/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinador.Modelo;

import static Util.ArquivoUtil.AbrirAquivo;
import java.io.BufferedReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 *
 * @author renato
 */
public class Frase 
{
    private Integer indice;
    private String texto;
    private ArrayList<Label> labelsArquivoTexto;
    
    public Frase()
    {
        labelsArquivoTexto = new ArrayList<>();
    }
    
     /**
     *
     * @param caminhoArquivo
     * @param nomeArquivo
     * @return
     */
    public ArrayList<Frase> RetornaFrases(String caminhoArquivo, 
            String nomeArquivo)
    {
        ArrayList<Frase> listaFrase = new ArrayList<>();
        Frase objFrase;
        
        ArrayList<Label> listaLabels = new ArrayList<>();
        
        ArrayList<String> frase = new ArrayList<>();
        Integer contadorFrase = 0;
        Integer contadorLabel = 0;
        
        StringBuilder linhaBuilder  = new StringBuilder();
        
        try 
        {            
            BufferedReader readerCorpus = AbrirAquivo(Paths.
                    get(caminhoArquivo, nomeArquivo).toString());
            
            ArrayList<String> textoArquivo = readerCorpus.lines().
                    collect(Collectors.toCollection(ArrayList::new));
            
            for (String linhaTexto : textoArquivo) 
            {
                if ((linhaTexto.trim().equals("")) &&
                    !(textoArquivo.toString().isEmpty())) 
                {
                    frase.add(linhaBuilder.toString());
                    linhaBuilder = new StringBuilder();
                    
                    objFrase = CriaObjFrase(contadorFrase, frase, listaLabels);
                    listaFrase.add(objFrase);
                    
                    frase = new ArrayList<>();
                    listaLabels = new ArrayList<>();
                    contadorLabel = 0;
                    
                    contadorFrase++;
                }
                else
                {
                    String linha = linhaTexto.trim();
                    linhaBuilder.append(linha);
                    linhaBuilder.append(System.getProperty("line.separator"));
                    
                    String valorLabelRef = String.valueOf(linha.toCharArray()[0]);                   
                    listaLabels.add(new Label(contadorLabel, valorLabelRef));
                    
                    contadorLabel++;
                }
            }
        } 
        catch (Exception e) 
        {
            throw new Error(e.getMessage());
        }
        
        Collections.shuffle(listaFrase);
        
        return listaFrase;      
    }

    private Frase CriaObjFrase(Integer contadorFrase, ArrayList<String> frase,
            ArrayList<Label> listaLabels) 
    {
        Frase objFrase;
        try 
        {
            objFrase = new Frase();
            objFrase.setIndice(contadorFrase);
            objFrase.setTexto(String.join("\n", frase));
            objFrase.setLabelsArquivoTexto(listaLabels);
        }
        catch (Exception e) 
        {
            throw new Error(e.getMessage());
        }
        return objFrase;
    }
    
    /**
     * @return the indice
     */
    public Integer getIndice() 
    {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(Integer indice) 
    {
        this.indice = indice;
    }

    /**
     * @return the texto
     */
    public String getTexto() 
    {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) 
    {
        this.texto = texto;
    }

    /**
     * @return the labelsArquivoTexto
     */
    public ArrayList<Label> getLabelsArquivoTexto() {
        return labelsArquivoTexto;
    }

    /**
     * @param labelsArquivoTexto the labelsArquivoTexto to set
     */
    public void setLabelsArquivoTexto(ArrayList<Label> labelsArquivoTexto) {
        this.labelsArquivoTexto = labelsArquivoTexto;
    }

    
}
