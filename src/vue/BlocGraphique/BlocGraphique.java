/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.Bloc;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * En phase de test. Représentera le bloc sur la partie graphique.
 * @author tancfire
 */
public class BlocGraphique extends JLabel{
    private Bloc bloc;
    private JLabel label;
    private static int position = 0;
    
    public BlocGraphique(Bloc bloc, String texte, ImageIcon image)
    {
        super(image);
        setSize(120,80);
        
        label = new JLabel(texte);
        label.setSize(120, 80);
        label.setHorizontalAlignment(CENTER);
        
        setLocation(bloc.getNiveau()*20, position*45);
        label.setLocation(bloc.getNiveau()*20, position*45);
        
        position++;
    }
    
    
    public void attacher(JPanel panel)
    {
        panel.add(label);
        panel.add(this);
    }
            
    public int getId()
    {
        return bloc.getId();
    }
}
