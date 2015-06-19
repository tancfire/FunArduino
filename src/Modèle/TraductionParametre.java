/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

/**
 * Permet de traduire les paramètres d'une condition.
 * @author tancfire
 */
public class TraductionParametre {
    private Object objet;
    private String type;
    
    public TraductionParametre(Object objet)
    {
        this.objet = objet;
        
    }
    
    public String traduire()
    {
        String trad = "";
        
        if(objet instanceof Composant)
        {
            trad = ((Composant)objet).getPin().getNom();
            type = "composant";
        } else if (objet instanceof Integer){
            trad = ((Integer)objet).toString();
            type = "int";
        } else if(objet instanceof Variable){
            trad = ((Variable)objet).getNom();
            type = "variable";
        }else if(objet instanceof String){
            if(isInteger((String) objet)){
                trad = objet.toString();
                type = "int";
            }else{
                trad = '"'+objet.toString()+'"';
                type = "string";
            }
        }else {
            System.err.println("L'objet passé en paramètre n'est pas reconnu");
        }
        return trad;
    }

    /**
     * Permet de savoir si le texte est un integer
     * @param s le texte
     * @return true si c'est un integer, false si ça ne l'est pas.
     */
    private static boolean isInteger(String s) {
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
    
    
    public String getType() {
        return type;
    }

    
}
