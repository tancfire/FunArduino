/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.Graphique;

import Modèle.BlocInitialisationComp;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocInitialisationCompGraphique extends BlocGraphique {

    public BlocInitialisationCompGraphique() {
        super(new ImageIcon("src/images/BlocInitComp.png"));
    }

    @Override
    protected String getTexte1() {
        return "Composant";
    }

    @Override
    protected String getTexte2() {
        return ((BlocInitialisationComp)bloc).getEntreeSortie();
    }
    
}
