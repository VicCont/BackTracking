/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa_rio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Intelimatica04
 */
public class Cruzar_Rio {
    
    private static boolean aux_se_ha_realizado (String [][] estadoA,String [][] estadoB){
        boolean bandera=false;
        int i=0;
        while (i<estadoA.length && !bandera){
            bandera=Arrays.deepEquals(estadoB[i], estadoA[i]);
            i++;
        }
        return bandera;
    }
    
    private static boolean se_ha_realizado(List<String[][]> estados_previos,String [][] estado_actual,
    List<Integer> lados_previos,int lado
    ){
        boolean retorno =false;
        int i=0;
        while (i<estados_previos.size() && !retorno){
            if (aux_se_ha_realizado(estado_actual, estados_previos.get(i)) && lado==lados_previos.get(i))
            {
                retorno=true;
            }
            i+=1;
        }
        return retorno;
    }
    
    private static boolean es_valido (String [][] restricciones,String[] lado,int indA,int indB,int pos,int excluir){
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
    
    
    private static boolean solucionado (String[][] estado_actual, int pos){
        if (pos<estado_actual[0].length){
            if (!estado_actual[1][pos].isEmpty()){
                return solucionado(estado_actual, pos+1);
            }
            return false;
        }
        return true;
    }
    
    private static void copia_bidi (String [][] original,String [][] copia, int i){
        if (i<original.length){
            copia[i]=Arrays.copyOf(original[i], original[i].length);
            copia_bidi(original, copia, i+1);

        }
    }
    
    private static void ordenar(String [] arr ){
        int tama=0;
        while (tama<arr.length && !arr[tama].isEmpty()){
            tama+=1;
        }
        String [] aux =Arrays.copyOfRange(arr, 0, tama);
        Arrays.sort(aux);
        for (int i = 0; i < aux.length; i++) {
            arr[i]=aux[i];
        }
    }
    
    private static String realizar_paso (String [][] estado_actual, int movido1, int lado){
        String paso="";
        String aux=null;
        if (movido1<estado_actual[lado].length)
        {
            paso="Llevo a: "+estado_actual[lado][movido1]+" al lado: "+(lado+1)%2;
            aux=estado_actual[lado][movido1];            
        }
        else {
            paso="Vuelvo al lado: "+(lado+1)%2 +" con nadie";
        }
        int i=movido1;
        boolean bandera=true;
        while (i<estado_actual[lado].length && bandera && movido1<estado_actual[lado].length){
            if (i+1<estado_actual[lado].length && !estado_actual[lado][i+1].isEmpty())
            {
                estado_actual[lado][i]=estado_actual[lado][i+1];
            }
            else
            {
                bandera=false;
                estado_actual[lado][i]="";
            }
            i+=1;
        }
        i=0;
        bandera=true;
        while (i<estado_actual[lado].length &&  bandera && movido1<estado_actual[lado].length) {            
            if (estado_actual[(lado+1)%2][i].isEmpty()){
                bandera=false;
                estado_actual[(lado+1)%2][i]=aux;
            }
            i+=1;
        }
        for (int j = 0; j < estado_actual.length; j++) {
            ordenar(estado_actual[j]);
        }
        return paso;
    }
    
    private static void aux_buscar_soluciones (String[] elementos,int lado, String [][] restricciones 
            ,List<String[][]> estados_previos,List<Integer> lados_previos,String [][] estado_actual,
            ArrayList<ArrayList> resultados,ArrayList<String> solucion_actual,int indice_actual){
        if (!solucionado(estado_actual, 0)){
            if (indice_actual<=elementos.length){
                if (indice_actual<estado_actual[lado].length && estado_actual[lado][indice_actual].isEmpty()){
                    indice_actual=estado_actual[lado].length;
                }
                if (es_valido(restricciones, estado_actual[lado], 0, 1, 0,indice_actual)){
                    String [][] copia_temporal=new String[estado_actual.length][estado_actual[0].length];
                    copia_bidi(estado_actual, copia_temporal, 0);
                    String paso_actual=realizar_paso(copia_temporal, indice_actual, lado);
                    if (!se_ha_realizado(estados_previos, copia_temporal,lados_previos,(lado+1)%2)){
                        estados_previos.add(copia_temporal);
                        lados_previos.add((lado+1)%2);
                        ArrayList<ArrayList>pasos_proximos=new ArrayList();
                        aux_buscar_soluciones(elementos, (lado+1)%2, restricciones, estados_previos,lados_previos, copia_temporal, pasos_proximos, solucion_actual, 0);
                        if (!pasos_proximos.isEmpty()){
                            for (int i = 0; i < pasos_proximos.size(); i++) {
                                ArrayList aux = pasos_proximos.get(i);
                                aux.add(0, paso_actual);
                                resultados.add(aux);
                            }
                        }
                    }
                }
                aux_buscar_soluciones(elementos, lado, restricciones, estados_previos, lados_previos, estado_actual, resultados, solucion_actual, indice_actual+1);
            }
        }
        else {
            ArrayList<String> ARL_aux=new ArrayList();
            ARL_aux.add("Terminado");
            resultados.add(ARL_aux);
        }
    }

    
    public static ArrayList<ArrayList> buscar_soluciones (String[] elementos, String [][] restricciones  ){
        Arrays.sort(elementos);
        ArrayList<ArrayList> resultados =new ArrayList<ArrayList>();
        String [][] estado_inicial=new String[2][elementos.length];
        String [][] aux=new String[2][elementos.length];
        estado_inicial[0]=elementos;
        for (int i = 0; i < estado_inicial[1].length; i++) {
            estado_inicial[1][i]="";
        }
        List<Integer> lados_previos=new ArrayList<Integer>();
        lados_previos.add(0);
        List <String [][]> previos=new ArrayList<String[][]>();
        copia_bidi(estado_inicial, aux, 0);
        previos.add(aux);
        aux_buscar_soluciones(elementos,0, restricciones,previos,lados_previos,estado_inicial,resultados,new ArrayList<String>(),0);
        return resultados;
    }
    
}
