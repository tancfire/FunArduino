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
    private JLabel label2;
    private static int nbrPosition = 0;
    private int position;
    
    public BlocGraphique(Bloc bloc, String texte, String texte2, ImageIcon image)
    {
        super(image);
        this.bloc = bloc;
        setSize(120,80);
        
        label = new JLabel(texte);
        label.setSize(120, 80);
        label.setHorizontalAlignment(CENTER);
        
        label2 = new JLabel(texte2);
        label2.setSize(120, 80);
        label2.setHorizontalAlignment(CENTER);
        
        // position "par défaut"
        position = nbrPosition;
        nbrPosition++;
        
        mettreAjour();
    }
    
    
    public void setPosition(int position)
    {
        this.position = position;
        mettreAjour();
    }
    
    
    public void mettreAjour()
    {
        setLocation(bloc.getNiveau()*40, position*45);
        label.setLocation(bloc.getNiveau()*40, (position*45)-10);
        label2.setLocation(bloc.getNiveau()*40, (position*45)+10);
    }
    
    
    public void attacher(JPanel panel)
    {
        panel.add(label);
        panel.add(label2);
        panel.add(this);
    }
            
    public int getId()
    {
        return bloc.getId();
    }

    public int getPosition() {
        return position;
    }
}
