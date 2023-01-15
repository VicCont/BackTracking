/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa_rio;

import java.util.ArrayList;

/**
 *
 * @author Intelimatica04
 */
public class Programa_Rio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String [] elementos= {"lechuga","cordero","lobo"};
        String [][] restricciones={
            {"lobo","cordero"},
            {"lechuga","cordero"}
        };
        // TODO code application logic here
        ArrayList<ArrayList> soluciones= Cruzar_Rio.buscar_soluciones(elementos, restricciones);
        for (int i = 0; i < soluciones.size(); i++) {
            System.out.println("Inicio de solucion: "+(i+1));
            String[] restriccione = restricciones[i];
            for (int j = 0; j < soluciones.get(i).size(); j++) {
                for (int k = 0; k < j; k++) {
                    System.out.print("    ");
                }
                System.out.println(soluciones.get(i).get(j));
            }
            System.out.println("Fin de solucion: "+(i+1));
            System.out.println("\n\n");
        }
    }
    
}
