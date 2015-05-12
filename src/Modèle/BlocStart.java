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
public class BlocStart extends Bloc{
    
    public BlocStart(AccesXML acces)
    {
        super(Color.DARK_GRAY, acces);
    }
    
    
    @Override
    public void mettreAjourCode()
    {
        sonCodeDebut = "\nvoid setup(){\n";
        sonCodeFin = "}\n";  
    }
 
    
}
