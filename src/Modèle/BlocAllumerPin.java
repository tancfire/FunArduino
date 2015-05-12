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
public class BlocAllumerPin extends BlocComposant {
    private EtatPin etatPin;
    
    public BlocAllumerPin(Composant composant, EtatPin etatPin, AccesXML acces)
    {
        super(Color.BLUE, composant, acces);
        this.etatPin = etatPin;
        
        mettreAjourCode();
    }
    
    
    public BlocAllumerPin(int id, Composant composant, EtatPin etatPin, AccesXML acces)
    {
        super(id, Color.BLUE, composant, acces);
        this.etatPin = etatPin;
        
        mettreAjourCode();
    }
    
    @Override
    public void mettreAjourCode()
    {
        if(etatPin == EtatPin.BAS)
        {
            sonCodeDebut = tab()+"digitalWrite("+composant.getPin().getNom()+",LOW);\n";
        }else{
            sonCodeDebut = tab()+"digitalWrite("+composant.getPin().getNom()+",HIGH);\n";
        }
        acces.setParametre(id, "etatPin", "etatPin", etatPin.toString());
    }

    
    
    
}
