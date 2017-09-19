/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author renato
 */
public class OperacoesUteis 
{      
    public TipoSistema sistemaOperacional;
    
    public enum TipoSistema 
    {
        Linux,
        Windows
    }
    
    /**
     * Busca a pasta padrão do sistema.
     * @return
     */
    public static String BuscarPastaPadrao()
    {
        return System.getProperty("user.dir");  
    }
    
    /**
     * Busca o caminho do arquivo de treinamento de acordo com o 
     * sistema operacional
     * @param sistemaOperacional
     * @param caminhoArquivo
     * @return
     */
    public static String BuscarCaminhoArquivo(TipoSistema sistemaOperacional, 
            String caminhoArquivo) 
    {
        try 
        {
            switch(sistemaOperacional)
            {
                case Linux:
                    caminhoArquivo += "/src/Arquivos/Linux";
                    break;
                default:
                    caminhoArquivo += "\\src\\Arquivos\\Windows";
                    break;
            }
        }
        catch (Exception e) 
        {
            throw e;
        }
        return caminhoArquivo;
    }

    /**
     * Cria algumas pastas de sistema que serão utilizadas na rotina de 
     * treinamento
     * @param caminhoArquivoMontado
     */
    public static void CriarPastas(Path caminhoArquivoMontado) 
    {
        try 
        {
            File file = new File(caminhoArquivoMontado.getParent().toString());
            file.mkdirs();
        } 
        catch (Exception e) 
        {
            throw e;
        }        
    }
}
