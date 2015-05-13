/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;

/**
 * Permet d'afficher du texte sur le port série.
 * @author tancfire
 */
public class BlocSortiSerie extends Bloc{
    String message;
    
    public BlocSortiSerie(String message, Controleur ctrl)
    {
        super(Color.RED, ctrl);
        this.message = message;
    }
    
        public BlocSortiSerie(int id, String message, Controleur ctrl)
    {
        super(id, Color.RED, ctrl);
        this.message = message;
    }

    @Override
    public void mettreAjourCode() {
       sonCodeDebut = tab()+"Serial.println(\""+message+"\");\n";
       acces.setParametre(id, "String", "message", message);
    }
    
    
}
