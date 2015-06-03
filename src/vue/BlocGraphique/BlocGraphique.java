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
    
    public BlocGraphique(ImageIcon image)
    {
        super(image);
       // this.bloc = bloc;
        setSize(120,45);
        
        label = new JLabel();
        label.setSize(120, 45);
        label.setHorizontalAlignment(CENTER);
        
        label2 = new JLabel();
        label2.setSize(100, 45);
        label2.setHorizontalAlignment(CENTER);
        
        labelCroix = new JLabel(new ImageIcon("src/images/croix.png"));
        labelCroix.setSize(20, 20);
        labelCroix.setVisible(false);
        
        labelCroixAjout = new JLabel(new ImageIcon("src/images/croixAjout.png"));
        labelCroixAjout.setSize(20, 20);

        
        // position "par défaut"
        position = 0;
        
        
        
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
    
    /**
     * Permet d'ajouter le bloc au bloc graphique
     * @param bloc le bloc
     */
    public void setBloc(Bloc bloc)
    {
        this.bloc = bloc;
        
        if(bloc.autoriseLesFils()){
            labelCroixAjout.setVisible(true);
        }else{
            labelCroixAjout.setVisible(false);
        }
        mettreAjour();
    }
      
    /**
     * Mettre à jour la position vertical
     * @param position la nouvelle position
     */
    public void setPosition(int position)
    {
        this.position = position;
        mettreAjour();
    }
    
    /**
     * Mettre à jour la position en x et y, ainsi que mettre à jour le texte
     */
    public void mettreAjour()
    {
        setLocation(bloc.getNiveau()*40, position*45);
        label.setLocation((bloc.getNiveau()*40), (position*45)-10);
        label2.setLocation((bloc.getNiveau()*40)+10, (position*45)+10);
        labelCroix.setLocation((bloc.getNiveau()*40)+90, (position*45)+3);
        labelCroixAjout.setLocation((bloc.getNiveau()*40)+115, (position*45)+15);
        
        this.label.setText(getTexte1());
        this.label2.setText(getTexte2());
    }
    
   // protected abstract void mettreAjourTexte();
    
    protected abstract String getTexte1();
    protected abstract String getTexte2();
    
    
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
