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
public class BlocUpdate extends Bloc {
    
    public BlocUpdate(AccesXML acces)
    {
        super(Color.orange, acces); 
    }
    
    
    @Override
    public void mettreAjourCode()
    {
        sonCodeDebut = "\nvoid loop(){\n";
        sonCodeFin = "}\n";
    }
}
