/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.SimulateurArduino;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tancfire
 */
public class SimulateurGraphique extends JLabel{
    private SimulateurArduino simu;

    
    public SimulateurGraphique(ImageIcon image, int x, int y, SimulateurArduino simu)
    {
        super(image);
        this.simu = simu;
        setLocation(x,y);
        setSize(294,197);
        
        /*=======================================
        ---------GESTION SIMULATEUR--------
        ========================================*/
         this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) { //clic relâché
                 if(e.getButton()==BUTTON1){ //si c'est un clic gauche
                     setLocation(e.getX()+getX()-(getWidth()/2),e.getY()+getY()-(getHeight()/2));
                     getSimu().mettreAJourBranchements();
                 }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    
    private SimulateurArduino getSimu() {
        return simu;
    }
    
 
    
    public void attacher(JPanel panel)
    {
        panel.add(this);
    }
     
    public void detacher(JPanel panel)
    {
        panel.remove(this);
    }
}
