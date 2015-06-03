/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

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
    protected void mettreAjourTexte() {
        this.setTexte("Si "+((BlocConditions)bloc).getParam1(), ((BlocConditions)bloc).getComparateur().getFormule()+" "+((BlocConditions)bloc).getParam2());
    }
    
}
