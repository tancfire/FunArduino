/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * En phase de test. Représentera le bloc.
 * @author tancfire
 */
public class BlocGraphique extends JLabel{
    private final int id;
    
    public BlocGraphique(int id, ImageIcon image)
    {
        super(image);
        this.id = id;
        setSize(120,80);
    }
    
    public int getId()
    {
        return id;
    }
}
