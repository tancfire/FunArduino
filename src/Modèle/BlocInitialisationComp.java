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
public class BlocInitialisationComp extends BlocComposant{

    
    public BlocInitialisationComp(Composant composant, AccesXML acces) {
        super(Color.GREEN, composant, acces);
    }
    
        public BlocInitialisationComp(int id, Composant composant,  AccesXML acces) {
        super(id, Color.GREEN, composant, acces);
    }
    
    @Override
    public void mettreAjourCode()
    {
        sonCodeDebut = tab()+"pinMode("+composant.getPin().getNom()+",OUTPUT);\n";
    }
 
    
}
