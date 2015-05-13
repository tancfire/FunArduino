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
            trad = ((Composant)objet).getNom();
            type = "composant";
        } else if (objet instanceof Integer){
            trad = ((Integer)objet).toString();
            type = "int";
        } else if(objet instanceof Variable){
            trad = ((Variable)objet).getNom();
            type = "variable";
        }else {
            System.err.println("L'objet passé en paramètre n'est pas reconnu");
        }
        
        return trad;
    }

    
    public String getType() {
        return type;
    }

    
}
