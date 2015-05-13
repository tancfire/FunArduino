/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

/**
 * Le comparateur est utilisé dans le bloc de condition. Il permet de comparer
 * deux valeurs.
 * @author tancfire
 */
public enum Comparateur {
    egal("=="), inegal("!="), superieur(">"), inferieur("<"), superieurOuEgal(">="), inferieurOuEgal("<=");
    
    private final String signe;
    
    Comparateur(String signe)
    {
        this.signe = signe;
    }
    
    @Override
    public String toString()
    {
        return signe;
    }
}
