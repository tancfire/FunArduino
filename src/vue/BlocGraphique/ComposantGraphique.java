/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.Composant;
import Modèle.SimulateurArduino;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tancfire
 */
public class ComposantGraphique extends JLabel {
    protected Composant comp;
    protected SimulateurGraphique simu;
    private JLabel labelLigne;
    private JLabel labelLigneVert;
   
    public ComposantGraphique(final Composant comp, ImageIcon image, SimulateurGraphique simulateur)
    {
        super(image);
        this.comp = comp;
        this.simu = simulateur;
        setSize(20,50);
        
        this.labelLigne = new JLabel(new ImageIcon("src/images/ligneJaune.png"));
        this.labelLigneVert = new JLabel(new ImageIcon("src/images/ligneJauneVertical.png"));
        
        mettreAJour();
    }
    
    public void mettreAJour()
    {
        setLocation(400,10);
        int longueur = comp.getPin().getX()+simu.getX()-this.getX()-20;
        int hauteur = comp.getPin().getY()+simu.getY()-this.getY();
        labelLigne.setLocation(this.getX()+15, this.getY()+45);
        labelLigne.setSize(longueur, 5);
        labelLigneVert.setLocation(this.getX()+longueur+15, this.getY()+45);
        labelLigneVert.setSize(5, hauteur);
    }
    
     public void attacher(JPanel panel)
    {
        panel.add(this);
        panel.add(labelLigne);
        panel.add(labelLigneVert);
    }
     
    public void detacher(JPanel panel)
    {
        panel.remove(this);
        panel.remove(labelLigne);
        panel.remove(labelLigneVert);
    }
    
    
}
