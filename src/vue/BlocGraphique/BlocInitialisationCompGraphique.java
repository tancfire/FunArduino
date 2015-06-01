/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.BlocInitialisationComp;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocInitialisationCompGraphique extends BlocGraphique {

    public BlocInitialisationCompGraphique(BlocInitialisationComp bloc) {
        super(bloc, "Composant", bloc.getEntreeSortie(), new ImageIcon("src/images/BlocInitComp.png"));
    }
    
    @Override
    protected void mettreAjourTexte() {
        this.setTexte("Composant", ((BlocInitialisationComp)bloc).getEntreeSortie());
    }
    
}
