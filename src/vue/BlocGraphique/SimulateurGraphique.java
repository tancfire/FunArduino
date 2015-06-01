/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tancfire
 */
public class SimulateurGraphique extends JLabel{
    private int x;
    private int y;
    
    public SimulateurGraphique(ImageIcon image, int x, int y)
    {
        super(image);
        this.x = x;
        this.y = y;
        setSize(294,197);
        
        mettreAJour();
    }
    
    public void mettreAJour()
    {
        setLocation(x, y);
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
