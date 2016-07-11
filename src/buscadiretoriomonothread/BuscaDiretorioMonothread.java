/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscadiretoriomonothread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author murilo
 */
public class BuscaDiretorioMonothread {

    /**
     * @param arquivo
     * @param palavra
     * @return 
     * @throws java.io.FileNotFoundException
     */
    public static boolean pesquisa(File arquivo, String palavra) throws FileNotFoundException{        
        Scanner scanner = new Scanner(arquivo);
        while(scanner.hasNext()){
            String leitura = scanner.next();
            if(palavra.equalsIgnoreCase(leitura)){
                return true;
            }
        }
        return false;
    }
    public static List<File> percorreDiretorio(File diretorio, String palavra) throws FileNotFoundException{
        List<File> encontrados = new ArrayList<>();
        
        if(diretorio == null){
            return encontrados;
        }
        
        File[] arquivos = diretorio.listFiles();
        
        for (File arquivo : arquivos) {
            if(arquivo.isDirectory()){
                List<File> encontradosSubPasta;
                encontradosSubPasta = percorreDiretorio(arquivo, palavra);
                encontrados.addAll(encontradosSubPasta);
            }else{
                if(pesquisa(arquivo, palavra)){
                    encontrados.add(arquivo);
                } else {
                }
            }
        }
        return encontrados;
    }
    public static void main(String[] args) {
        List<File> encontrados = new ArrayList<>();
        long tempoInicial = 0;
        long tempoExecucao = 0;
        String palavra = "produto";
        File dir = null;
        
        if(args.length>0){
            palavra = args[0];
            dir = new File(args[1]);
        } else{
            //abre a busca do diretorio
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                dir = fc.getSelectedFile();
            }
        }
        
        if(dir.exists()){
            tempoInicial = System.currentTimeMillis();
            try{
                encontrados = percorreDiretorio(dir, palavra);
            } catch(FileNotFoundException e){
                
            }
        }
        tempoExecucao = System.currentTimeMillis() - tempoInicial;
        /*System.out.println("Encontrados: ");
        for (File arquivo : encontrados) {
            System.out.println("\t"+arquivo.getPath());
        }*/
        System.out.println("Tempo de execucao: "+ tempoExecucao + " ms");
    }
    
}
