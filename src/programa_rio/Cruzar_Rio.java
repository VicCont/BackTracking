/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa_rio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Intelimatica04
 */
public class Cruzar_Rio {
    
    
    private boolean se_ha_realizado(List<String[][]> estados_previos,String [][] estado_actual){
        boolean retorno =false;
        int i=0;
        while (i<estados_previos.size() && !retorno){
            if (estado_actual.hashCode()==estados_previos.get(i).hashCode())
            {
                retorno=true;
            }
        }
        return retorno;
    }
    
    private boolean es_valido (String [][] restricciones,String[] lado,int indA,int indB,int pos,int excluir){
        if (indA+1<lado.length && !lado[indA].isEmpty()){
            if (indB<lado.length && !lado[indB].isEmpty() && indA!=excluir){
                if (pos<restricciones.length && indB!=excluir){
                    if (restricciones[pos][0].equals(lado[indA]) && restricciones[pos][1].equals(lado[indB])
                        || restricciones[pos][0].equals(lado[indB]) && restricciones[pos][1].equals(lado[indA])){
                        return false;
                    }
                    return es_valido(restricciones, lado, indA, indB, pos+1,excluir);
                }
                return es_valido(restricciones, lado, indA, indB+1, 0, excluir);
            }
            return es_valido(restricciones, lado, indA+1, indA+2, 0,excluir);
        }
        return true;
    }
    
    
    private boolean solucionado (String[][] estado_actual, int pos){
        if (pos<estado_actual[0].length){
            if (!estado_actual[1][pos].isEmpty()){
                return solucionado(estado_actual, pos+1);
            }
            return false;
        }
        return true;
    }
    
    private void copia_bidi (String [][] original,String [][] copia, int i){
        if (i<original.length){
            copia[i]=Arrays.copyOf(original[i], original[i].length);
        }
    }
    
    private List<String> simulacion (boolean lado, String [][] restricciones,List<String [][]> estados_previos,String [][] estado_actual,List<String> solucion_actual){
        
    }
    
    private List<List> aux_buscar_soluciones (String[] elementos,int lado, String [][] restricciones ,List<String[][]> estados_previos,String [][] estado_actual,List<List> resultados,List<String> solucion_actual,int indice_actual){
        if (!solucionado(estado_actual, 0)){
            if (indice_actual<=elementos.length){
                if (es_valido(restricciones, estado_actual[lado], 0, 1, 0,indice_actual)){
                    String [][] copia_temporal=new String[estado_actual.length][estado_actual[0].length];
                    copia_bidi(estado_actual, copia_temporal, 0);
                    return aux_buscar_soluciones(elementos, lado, restricciones, estados_previos, estado_actual, resultados, solucion_actual, 0);
                }
            }
        }
        resultados.add(solucion_actual);
    }
    
    public List<List> buscar_soluciones (String[] elementos, String [][] restricciones  ){
        List<List> resultados =new ArrayList<List>();
        String [][] estado_inicial=new String[2][elementos.length];
        
        resultados=aux_buscar_soluciones(elementos,false, restricciones,new ArrayList<String[][]>(),estado_inicial,new ArrayList<List>(),new ArrayList<String>(),0);
        return resultados;
    }
    
}
