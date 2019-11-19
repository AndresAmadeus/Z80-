package Macro;

import static Macro.Z80;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alacr
 */
public class Main {
    public static void main(String[] args) {
        String linea = "eti1:LDA,B";
        int FinEti = linea.indexOf(":");
        //linea = linea.substring(6, linea.length()-1);
        
        int prueba = Integer.parseInt("FF", 16);
        String salida = Integer.toBinaryString(prueba);
        String PC = "A";
        String COM = "1111110101"+Z80.VAR(PC)+"110"+Z80.VAR(linea.substring(6, linea.length()-1));
        int a = 0;
        if (2 >= 4)
            a = 0;
        else
            a = 1;
        
        PC = linea.substring(0, linea.indexOf(",")); // PL = Primera Caracter
        String SC = linea.substring(linea.indexOf(",")+1);     // SC = Segundo Caracter
        
        System.out.println(linea.substring(FinEti+1));
    }
}
