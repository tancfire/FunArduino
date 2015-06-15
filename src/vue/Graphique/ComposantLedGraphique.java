/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.Graphique;

import Modèle.ComposantLed;
import Modèle.SimulateurArduino;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class ComposantLedGraphique extends ComposantGraphique{

    public ComposantLedGraphique(ComposantLed comp, SimulateurGraphique simulateur) {
        super(comp, new ImageIcon("src/images/ComposantLed.png"), simulateur);
    }
    
}
