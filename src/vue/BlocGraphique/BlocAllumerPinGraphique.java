/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.BlocAllumerPin;
import javax.swing.ImageIcon;

/**
 * C'est le bloc graphique qui représentera le blocAllumerPin.
 * @author tancfire
 */
public class BlocAllumerPinGraphique extends BlocGraphique {

    public BlocAllumerPinGraphique() {
        super(new ImageIcon("src/images/BlocAllumerPin.png"));
    }

    @Override
    protected String getTexte1() {
        return ((BlocAllumerPin)bloc).getEtatPin();
    }

    @Override
    protected String getTexte2() {
        return "";
    }
    
    
    
}
