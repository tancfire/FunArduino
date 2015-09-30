/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Autre;

/**
 * Comparateur d'Integer.
 * @author tancfire
 */
public class CompInt {
    
        
     /**
     * Permet de savoir si un texte est integer
     * @param s le texte sur lequel on souhaite savoir si c'est un integer.
     * @return si la valeur est un integer
     */
    public static boolean isInteger(String s) {
    return isInteger(s,10);
    }

    private static boolean isInteger(String s, int radix) {
    if(s.isEmpty()) return false;
    for(int i = 0; i < s.length(); i++) {
        if(i == 0 && s.charAt(i) == '-') {
            if(s.length() == 1) return false;
            else continue;
        }
        if(Character.digit(s.charAt(i),radix) < 0) return false;
    }
        return true;
    }
}
