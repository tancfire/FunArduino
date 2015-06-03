/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.BlocAttendre;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocAttendreGraphique extends BlocGraphique{

    public BlocAttendreGraphique() {
        super(new ImageIcon("src/images/BlocAttendre.png"));
    }
    
    @Override
    protected String getTexte1() {
        return "Attendre";
    }

    @Override
    protected String getTexte2() {
        return ((BlocAttendre)bloc).getDelai()+" ms";
    }   
}
