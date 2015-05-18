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
    egal("==", "égal à"), inegal("!=", "non-égal à"), superieur(">", "supérieur à"), inferieur("<", "inférieur à"), superieurOuEgal(">=", "supérieur ou égal à"), inferieurOuEgal("<=", "inférieur ou égal à");
    
    private final String signe;
    private final String formule;
    
    Comparateur(String signe, String formule)
    {
        this.signe = signe;
        this.formule = formule;
    }
    
    @Override
    public String toString()
    {
        return signe;
    }

    public String getFormule() {
        return formule;
    }
    
    
}
