/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import java.awt.Color;
import saveSystem.AccesXML;

/**
 *
 * @author Utilisateur
 */
public class BlocSortiSerie extends Bloc{
    String message;
    
    public BlocSortiSerie(String message, AccesXML acces)
    {
        super(Color.RED, acces);
        this.message = message;
    }
    
        public BlocSortiSerie(int id, String message, AccesXML acces)
    {
        super(id, Color.RED, acces);
        this.message = message;
    }

    @Override
    public void mettreAjourCode() {
       sonCodeDebut = tab()+"Serial.println(\""+message+"\");\n";
       acces.setParametre(id, "String", "message", message);
    }
    
    
}
