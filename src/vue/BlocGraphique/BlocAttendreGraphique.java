/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.Bloc;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocAttendreGraphique extends BlocGraphique{

    public BlocAttendreGraphique(Bloc bloc) {
        super(bloc, "Attendre", new ImageIcon("src/images/BlocStart.png"));
    }
    
}
