/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.Graphique;

import Modèle.BlocUpdate;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocUpdateGraphique extends BlocGraphique {

    public BlocUpdateGraphique() {
        super(new ImageIcon("src/images/BlocUpdate.png"));
    }

    @Override
    protected String getTexte1() {
        return "Tout le temps";
    }

    @Override
    protected String getTexte2() {
        return "";
    }
    
}
