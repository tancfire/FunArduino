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

    public BlocAttendreGraphique(BlocAttendre bloc) {
        super(bloc, "Attendre",bloc.getDelai()+" ms", new ImageIcon("src/images/BlocAttendre.png"));
    }
    
        @Override
    protected void mettreAjourTexte() {
        this.setTexte("Attendre",((BlocAttendre)bloc).getDelai()+" ms");
    }
    
}
