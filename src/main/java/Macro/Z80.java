/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Macro;

import java.util.Hashtable;
/**
 *
 * @author alacr
 */
public class Z80 {
    private int CL;
    private String HEX;
    private String LST;
    private Hashtable<String, Integer> ETIQUETAS;
    
    public Z80(String org){
        this.HEX = "";
        this.LST = "";
        this.ETIQUETAS = new Hashtable<>();
        if (org.equals("")){
            this.CL = 0;
        }else {
            this.CL = Integer.parseInt(org);
        }
    }
    
    public String etiqueta (String linea){
        int FinEti = linea.indexOf(":");
        if ( FinEti != -1 ){
            this.ETIQUETAS.put(linea.substring(0, FinEti), this.CL);
            linea = linea.substring(FinEti);
        }
        return linea;
    }
    
    private void agregar(String COM){
        this.HEX.concat(COM + "\n");   //AQui transformamos y agregamos a cada archivo.
        int aux = Integer.parseInt(COM, 10);
        COM = Integer.toHexString(aux);
        this.LST.concat(COM + "\n");
    }
    
    public static String VAR(String c){
        switch(c){
            case "A": return "111";
            case "L": return "101";
            case "H": return "100";
            case "E": return "011";
            case "D": return "010";
            case "C": return "001";
            case "B": return "000";
            default: {
                int aux = Integer.parseInt(c);
                String bin = Integer.toBinaryString(aux);
                if ( c.length() == 1 ){
                    if (bin.length() >= 9){
                        return "Z";
                    }
                    while (bin.length() <= 7){
                        bin = "0" + bin;
                    }
                } else {
                    if (bin.length() >= 17){
                        System.out.println("Entro");
                        return "Z";
                    }
                    while (bin.length() <= 15){
                        bin = "0" + bin;
                    }
                }
                return bin;
            }
        }
    }
    
    public void NeuLD (String linea){
        String COM = "";
        String PC = linea.substring(0, linea.indexOf(",")+1); // PL = Primera Caracter
        String SC = linea.substring(linea.indexOf(",")+1);     // SC = Segundo Caracter
        
        switch(PC){
            //8 bits
            case "B", "C", "D", "E", "H", "L" -> { }
            case "A" -> { 
                switch(SC){
                    case "(BC)" -> { COM = "00001010"; }  
                    case "(DE)" -> { COM = "00011010"; }
                    case "I" -> { COM = "1110110101010111"; }
                    case "R" -> { COM = "1110110101011111"; }
                    default -> { COM = SC.charAt(0)=='('?"00111010"+VAR(SC.substring(1,SC.length()-1)):"ERROR"; }
                }
            }
            case "I" -> { COM = "A".equals(SC)?"1110110101000111":"ERROR"; }
            case "R" -> { COM = "A".equals(SC)?"1110110101001111":"ERROR"; }
            //16 bits
            case "SP", "HL", "DE", "BC" -> { }
            case "IX" -> { }
            case "IY" -> { }
            case "(" -> { }
        }
        
        agregar(COM);
    }
}


/*
switch(SC){ 
                    case "A", "B", "C", "D", "E", "H", "L"  -> {  COM = "01"+VAR(PC)+VAR(SC); }
                    //Agredar 16 bits
                    case "(" -> { //Caso si termine en parentesis
                        String aux = linea.substring(3, 5);
                        switch(aux){
                            case "IX" -> { COM = "1101110101"+VAR(PC)+"110"+VAR(linea.substring(6, linea.length()-1)); }
                            case "IY" -> { COM = "1111110101"+VAR(PC)+"110"+VAR(linea.substring(6, linea.length()-1));}
                            case "HL" -> { COM = "01"+VAR(PC)+"110"; }
                        }
                    }
                    default -> { COM = "00"+VAR(PC)+"110"+VAR(SC); }
                }
*/