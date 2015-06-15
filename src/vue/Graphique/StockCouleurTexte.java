/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.Graphique;

import java.awt.Color;

/**
 *
 * @author tancfire
 */
public class StockCouleurTexte {
    private final int debut;
    private final int longueur;
    private final Color couleur;
    
    public StockCouleurTexte(Color couleur, int debut, int longueur)
    {
        this.couleur = couleur;
        this.debut = debut;
        this.longueur = longueur;
    }

    public int getDebut() {
        return debut;
    }

    public int getLongueur() {
        return longueur;
    }

    public Color getCouleur() {
        return couleur;
    }
    
    
    
}
