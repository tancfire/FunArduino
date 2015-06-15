/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.Graphique;

import Modèle.BlocConditions;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocConditionsGraphique extends BlocGraphique {

    public BlocConditionsGraphique() {
        super(new ImageIcon("src/images/BlocConditions.png"));
    }

    @Override
    protected String getTexte1() {
        return "Si "+((BlocConditions)bloc).getParam1();
    }

    @Override
    protected String getTexte2() {
        return ((BlocConditions)bloc).getComparateur().getFormule()+" "+((BlocConditions)bloc).getParam2();
    }
}
