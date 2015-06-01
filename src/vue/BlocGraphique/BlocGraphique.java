/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.Bloc;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * En phase de test. Représentera le bloc sur la partie graphique.
 * @author tancfire
 */
public abstract class BlocGraphique extends JLabel{
    protected Bloc bloc;
    private JLabel label;
    private JLabel label2;
    private JLabel labelCroix;
    private JLabel labelCroixAjout;
    private static int nbrPosition = 0;
    private int position;
 //   private JPanel panel;
    
    public BlocGraphique(final Bloc bloc, String texte, String texte2, ImageIcon image)
    {
        super(image);
        this.bloc = bloc;
        setSize(120,45);
        
        label = new JLabel(texte);
        label.setSize(120, 45);
        label.setHorizontalAlignment(CENTER);
        
        label2 = new JLabel(texte2);
        label2.setSize(100, 45);
        label2.setHorizontalAlignment(CENTER);
        
        labelCroix = new JLabel(new ImageIcon("src/images/croix.png"));
        labelCroix.setSize(20, 20);
        labelCroix.setVisible(false);
        
        labelCroixAjout = new JLabel(new ImageIcon("src/images/croixAjout.png"));
        labelCroixAjout.setSize(20, 20);
        if(bloc.autoriseLesFils()){
            labelCroixAjout.setVisible(true);
        }else{
            labelCroixAjout.setVisible(false);
        }

        
        // position "par défaut"
        position = 0;
        
        mettreAjour();
        
        
        
        /*
        ========================================================================
        ------------------------- Actions du bloc ------------------------------
        ========================================================================
        */
        
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                //clic de souris
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //clic maintenu
                 labelCroix.setVisible(false);
                 if(e.getButton()==BUTTON3)
                 {
                     bloc.ouvrirMenuModifier(getX()+e.getX()+120,getY()+e.getY()+45);
                 }
            }

            @Override
            public void mouseReleased(MouseEvent e) { // clic relâché
                if(e.getButton()==BUTTON1){ //si c'est un clic gauche
                if(e.getX()>=0 && e.getX()<=120){
                    
                    if(e.getY()>=45){ //déplacement vers le bas
                        bloc.move((e.getY()/45));
                     }else if(e.getY()<=0) //déplacement vers le haut
                     {
                       bloc.move((e.getY()/45)-1);
                     }
                }else if(e.getX()<0){ //déplacement vers la gauche
                    bloc.descendreNiveau();
                }else{ // déplacement vers la droite
                    bloc.monterNiveau();
                }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
              // si le curseur rentre dans le bloc
               if(bloc.estSupprimable()){
              labelCroix.setVisible(true);
               }
            }

            @Override
            public void mouseExited(MouseEvent e) {
               //si le curseur sort du bloc
                if(e.getX()>=120||e.getX()<=0||e.getY()<=0||e.getY()>=45)
                labelCroix.setVisible(false);
            }
        }); 
        
        
        /*
        ========================================================================
        -------------------- Actions du bouton supprimmer ----------------------
        ========================================================================
        */
        
        labelCroix.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                    bloc.delete();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelCroix.setVisible(false);
            }
        });
        
        
        
        
                /*
        ========================================================================
        ---------------------- Actions du bouton ajouter -----------------------
        ========================================================================
        */
        
        labelCroixAjout.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
              
            }

            @Override
            public void mousePressed(MouseEvent e) {
                 bloc.ouvrirChoixBlocAjout();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                 labelCroix.setVisible(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
    
    
      
    public void setPosition(int position)
    {
        this.position = position;
        mettreAjour();
    }
    
    
    public void mettreAjour()
    {
        mettreAjourTexte();
        setLocation(bloc.getNiveau()*40, position*45);
        label.setLocation((bloc.getNiveau()*40), (position*45)-10);
        label2.setLocation((bloc.getNiveau()*40)+10, (position*45)+10);
        labelCroix.setLocation((bloc.getNiveau()*40)+90, (position*45)+3);
        labelCroixAjout.setLocation((bloc.getNiveau()*40)+115, (position*45)+15);
    }
    
    protected abstract void mettreAjourTexte();
    
    protected void setTexte(String txt1, String txt2)
    {
        this.label.setText(txt1);
        this.label2.setText(txt2);
    }
    
    
    public void attacher(JPanel panel)
    {
        panel.add(label);
        panel.add(label2);
        panel.add(labelCroix);
        panel.add(labelCroixAjout);
        panel.add(this);
    }
    
    
     public void detacher(JPanel panel)
    {
        panel.remove(label);
        panel.remove(label2);
        panel.remove(labelCroix);
        panel.remove(labelCroixAjout);
        panel.remove(this);
    }
            
    public int getId()
    {
        return bloc.getId();
    }

    public int getPosition() {
        return position;
    }
}
