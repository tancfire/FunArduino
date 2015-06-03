/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BlocGraphique.BlocSortiSerieGraphique;

/**
 * Permet d'afficher du texte sur le port série.
 * @author tancfire
 */
public class BlocSortiSerie extends Bloc{
    String message;
    
    public BlocSortiSerie(String message, Controleur ctrl)
    {
        super(TypeBloc.programmation, Color.RED, new BlocSortiSerieGraphique(), ctrl);
        initialisation(message);
        init();
    }
    
    public BlocSortiSerie(int id, String message, Controleur ctrl)
    {
        super(id, TypeBloc.programmation, Color.RED, new BlocSortiSerieGraphique(), ctrl);
        initialisation(message);
        init();
    }

    
    private void initialisation(String message)
    {
        this.message = message;
    }
        
        
    @Override
    public void mettreAjourCode() {
       sonCodeDebut = tab()+"Serial.println(\""+message+"\");\n";
       acces.setParametre(id, "String", "message", message);
    }
    
    
}
