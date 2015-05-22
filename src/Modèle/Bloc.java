/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import saveSystem.AccesXML;
import vue.BlocGraphique.BlocGraphique;

/**
 * Le bloc est un bloc de code.
 * @author tancfire
 */
public abstract class Bloc {
    private final Color couleur;
    private HashMap<Integer,Bloc> sesBlocs;
    private static int nbID=0;
    protected final int id;
    protected int niveau;
    protected String sonCodeDebut;
    protected String sonCodeFin;
    protected Controleur ctrl;
    protected AccesXML acces;
    protected Bloc blocParent;
    
    protected BlocGraphique blocGraph;
    
    /**
     * C'est le constructeur qui permet de créer un Bloc et de le sauvegarder dans
     * l'accès XML.
     * @param couleur La couleur qui apparaitra dans le texte
     * @param ctrl 
     */
    public Bloc(Color couleur, Controleur ctrl)
    {
        sesBlocs = new HashMap<Integer,Bloc>();
        this.couleur = couleur;
        sonCodeDebut = "";
        sonCodeFin = "";
        niveau = 0; // c'est le nombre de tabulation qu'il faudra faire.
        id = nbID;
        nbID++;
        this.ctrl = ctrl;
        this.blocParent = null;
        
        this.acces = ctrl.getAcces();
        acces.creerBloc(id, getClass().getSimpleName()); //permet de sauvegarder le bloc directement
    }
    
    
     /**
     * C'est le constructeur qui permet de créer un Bloc à partir du fichier de sauvegarde.
     * @param couleur
     * @param ctrl 
     */
        public Bloc(int id, Color couleur, Controleur ctrl)
    {
        sesBlocs = new HashMap<Integer,Bloc>();
        this.couleur = couleur;
        sonCodeDebut = "";
        sonCodeFin = "";
        niveau = 0; // c'est le nombre de tabulation qu'il faudra faire.
        this.id = id;
        this.ctrl = ctrl;
        this.blocParent = null;

        this.acces = ctrl.getAcces();
    }
        
        
  
    /**
     * Fait la même chose qu'ajotuerBlocALaFin, mais ne sauvegarde pas la parenté
     * dans le fichier de sauvegarde (lorsque l'on charge, par exemple).
     * @param unBloc 
     */
    public void ajouterBlocALaFinSansSauvegarde(Bloc unBloc)
    {
        int taille = sesBlocs.size();
        sesBlocs.put(taille+1, unBloc);
        unBloc.setNiveau(niveau+1);
        unBloc.setBlocParent(this);
    }
    
    
    /**
     * Permet d'ajouter un bloc fils. On l'ajoute à la fin de la file.
     * @param unBloc c'est le bloc qui deviendra le fils de ce bloc courant.
     */
    public void ajouterBlocALaFin(Bloc unBloc)
    {
        ajouterBlocALaFinSansSauvegarde(unBloc);
        acces.ajouterBlocDansBloc(id, unBloc.getId());
        acces.setPositionToBloc(unBloc.getId(), sesBlocs.size());
    }
    
    /**
     * Permet d'ajouter un bloc fils. 
     * @param position la position à lauquelle on souhaite ajouter le fils
     * @param unBloc le bloc fils que l'on souhaite ajouter
     */
    public void ajouterBlocSansSauvegarder(int position, Bloc unBloc)
    {
        if(sesBlocs.containsKey(position)){ //si un bloc existe déjà sur cette position
             // on décale tout les blocs d'une position
             int taille = sesBlocs.size()-1;
              for(int i=taille; i>=position;i--)
               {
                   sesBlocs.put(i+1, sesBlocs.get(i));
             }
        }
        // On ré-écrit par dessus la position courante, après décalage éventuel.
        sesBlocs.put(position, unBloc);
        
        unBloc.setNiveau(niveau+1);
        unBloc.setBlocParent(this);
    }
    
    
        public void ajouterBloc(int position, Bloc unBloc)
    {
        if(sesBlocs.containsKey(position)){ //si un bloc existe déjà sur cette position
             // on décale tout les blocs d'une position
             int taille = sesBlocs.size()-1;
              for(int i=taille; i>=position;i--)
               {
                   sesBlocs.put(i+1, sesBlocs.get(i));
                   acces.setPositionToBloc(sesBlocs.get(i).getId(), i+1);
             }
        }
        // On ré-écrit par dessus la position courante, après décalage éventuel.
        sesBlocs.put(position, unBloc);
        
        //méthode temporaire
        acces.setPositionToBloc(id, position);
        unBloc.setNiveau(niveau+1);
        unBloc.setBlocParent(this);
    }

    
    public void supprimerBloc(Bloc unBloc)
    {
        int cle = -1;
        for(Map.Entry<Integer,Bloc> blocs : sesBlocs.entrySet()) 
        {
          if(blocs.getValue()==unBloc)
              cle = blocs.getKey();
        }
            if(cle!=-1){
           sesBlocs.remove(cle);
           unBloc.setBlocParent(null);
           unBloc.setNiveau(0);
           
           // On repositionne les blocs qui sont derrières
           int taille = sesBlocs.size()+1;
            for(int i=(cle+1); i<=taille; i++) 
            {
                    acces.setPositionToBloc(sesBlocs.get(i).getId(), i-1);//On enregistre la nouvelle position
                    sesBlocs.put(i-1, sesBlocs.get(i));
                    if(i==taille){
                        sesBlocs.remove(taille); 
                    }
            }
         }
    }
    
    
    /**
     * Permet d'affecter les variables sonCodeDebut et sonCodeFin. Cette fonction
     * est appelé à chaque fois que nous avons besoin de mettre à jour le code du bloc.
     */
    public abstract void mettreAjourCode();

    
    /**
     * Permet de récupérer le code arduino généré par le bloc.
     * @return Le code arduino généré par le bloc.
     */
    public String getCode()
    {
        mettreAjourCode();
        String code="";
        
        code+="[color r"+this.couleur.getRed()+" b"+this.couleur.getBlue()+" g"+this.couleur.getGreen() +"]";
        code+=(sonCodeDebut);
        code+="[/color]";
        
            for(Map.Entry<Integer,Bloc> blocs : sesBlocs.entrySet()) 
            {
                code+=blocs.getValue().getCode();
            }
        
        code+="[color r"+this.couleur.getRed()+" b"+this.couleur.getBlue()+" g"+this.couleur.getGreen() +"]";
        code+=(sonCodeFin);
        code+="[/color]";
        
        return code;
    }
    
    
        public ArrayList<Bloc> getToutSesFils()
    {
        ArrayList<Bloc> blocsFils = new ArrayList<Bloc>();
        
        for(Map.Entry<Integer,Bloc> blocs : sesBlocs.entrySet()) 
            {
                blocsFils.add(blocs.getValue());
            }
        for(Map.Entry<Integer,Bloc> blocs : sesBlocs.entrySet()) 
            {
                blocsFils.addAll(blocs.getValue().getToutSesFils());
            }
        return blocsFils;
    }
        
        
        
    public ArrayList<BlocGraphique> getToutLesBlocsGraphiques()
    {
        ArrayList<BlocGraphique> blocsGraphs = new ArrayList<BlocGraphique>();
        
        blocsGraphs.add(blocGraph);
        for(Map.Entry<Integer,Bloc> blocs : sesBlocs.entrySet()) 
            {
                blocsGraphs.addAll(blocs.getValue().getToutLesBlocsGraphiques());
            }
        
        return blocsGraphs;
    }
    
    
    public void descendreNiveau()
    {
        if(getParent().getParent()!=null){
            Bloc blocGrandPere = getParent().getParent();
            getParent().supprimerBloc(this);
            blocGrandPere.ajouterBlocALaFin(this);
            ctrl.mettreAjourCode();
        }else{
            System.out.println("grand-pere n'existe pas D:");
        }
    }
    
        public void monterNiveau()
    {
        if(getParent()!=null){
            if(getParent().getSesFils().containsKey(getPosition()-1)){
               Bloc blocFrere =getParent().getSesFils().get(getPosition()-1);
               getParent().supprimerBloc(this);
               blocFrere.ajouterBlocALaFin(this);
               ctrl.mettreAjourCode();
            }
        }
    }
    
        
        
    public void delete()
    {
        getParent().supprimerBloc(this);
        ctrl.mettreAjourCode();
    }
    
    
    // A OPTIMISER + CORRECTION
    public void move(int distance)
    {
        if(distance >0){ 
            distance-=this.getSesFils().size();//on soustrait la distance à parcourir du nombre de fils que possède le bloc
            if(distance<(getParent().getSesFils().size()-getPosition())+2){

                 for(int i=0; i<distance;i++)
                 {
                      moveDown();
                 }
            }else{
                int positionP = getParent().getPosition();
                if(ctrl.getAssemblage().getSesBlocs().containsKey(positionP+1))
                {
                    getParent().supprimerBloc(this);
                    ctrl.getAssemblage().getSesBlocs().get(positionP+1).ajouterBlocALaFin(this);
                    ctrl.mettreAjourCode();
                }  
            }
        }else{
            distance*=(-1); //On passe la distance en positif
            if(distance<getPosition()+1){
            for(int i=0; i<(distance);i++)
            {
                if(getParent().getSesFils().get(getPosition()-i-1)!=null){
                    distance-=getParent().getSesFils().get(getPosition()-i-1).getToutSesFils().size();
                }
                if(i<distance)
                     moveUp();
             }
             }else{  
                int positionP = getParent().getPosition();
                if(ctrl.getAssemblage().getSesBlocs().containsKey(positionP-1))
                {
                    getParent().supprimerBloc(this);
                    ctrl.getAssemblage().getSesBlocs().get(positionP-1).ajouterBlocALaFin(this);
                    ctrl.mettreAjourCode();
                }
            }
        }
        
    }
    
    
    
        public void moveUp()
    {
        if(getParent()!=null){
         int taille = getParent().getSesFils().size()+1;
         for(int i=1; i<taille;i++)
         {
             if(getParent().getSesFils().get(i)==this && getParent().getSesFils().containsKey(i-1))
             {
                 Bloc unBloc = getParent().getSesFils().get(i-1);
                getParent().getSesFils().put(i-1, this);
                getParent().getSesFils().put(i, unBloc);
                //On enregistre le déplacement
                acces.setPositionToBloc(id, i-1);
                acces.setPositionToBloc(unBloc.getId(), i);
                ctrl.mettreAjourCode();
                break;
             }
         }
        }
    }
    
    
    public void moveDown()
    {
        if(getParent()!=null){
         int taille = getParent().getSesFils().size()+1;
         for(int i=1; i<taille;i++)
         {
             if(getParent().getSesFils().get(i)==this && getParent().getSesFils().containsKey(i+1))
             {
                 Bloc unBloc = getParent().getSesFils().get(i+1);
                getParent().getSesFils().put(i+1, this);
                getParent().getSesFils().put(i, unBloc);
                //On enregistre le déplacement
                acces.setPositionToBloc(id, i+1);
                acces.setPositionToBloc(unBloc.getId(), i);
                ctrl.mettreAjourCode();
                break;
             }
         }
        }
    }
    
    
    public Bloc getParent()
    {
      //Fonction de debug:
      /*  Bloc blocParent = null;
        
        for(Map.Entry<Integer,Bloc> blocs : ctrl.getAssemblage().getSesBlocs().entrySet()) 
        {
            if(blocs.getValue()==this)
            {
                break;
            }else{
                blocParent = filsParent(blocs.getValue());
            }
        }*/
        return blocParent;
    }
    
    //Pour la fonction de debug de getParent()
    private Bloc filsParent(Bloc blocParent)
    {
       Bloc blocP = null;
       for(Map.Entry<Integer,Bloc> blocs : blocParent.getSesFils().entrySet()) 
        {
            if(blocs.getValue()==this)
            {
                blocP = blocParent;
                break;
            }else{
                blocP = blocs.getValue().filsParent(blocParent);
            }
        }
       return blocP;
    }
    
    
    
    /**
     * Le niveau représente à quelle profondeur de parenté se trouve le bloc.
     * @param niveau 
     */
    public void setNiveau(int niveau)
    {
        this.niveau = niveau;
        if(blocGraph!=null)
        blocGraph.mettreAjour();
    }

    
    public void setBlocParent(Bloc blocParent) {
        this.blocParent = blocParent;
    }

    
    
    /**
     * Permet de générer le nombre bon nombre de tabulations.
     * @return Les tabulations.
     */
    protected String tab()
    {
        String tab = "";
        
            for(int i=0; i<niveau;i++)
            {
                tab+="\t";
            }
        
        return tab;
    }
    
    
    public Color getCouleur()
    {
        return couleur;
    }

    public int getId() {
        return id;
    }
    
    
    public int getPosition()
    {
        int position = -1; //Erreur si -1
        HashMap<Integer,Bloc> liste = new HashMap<Integer,Bloc>();
       if(blocParent!=null){
           liste = blocParent.getSesFils();
       }else{
           liste = ctrl.getAssemblage().getSesBlocs();
       }
           
       for(Map.Entry<Integer,Bloc> blocs : liste.entrySet()) 
        {
             if(blocs.getValue()==this)
             {
                position= blocs.getKey();
             }
         }
       return position;
    }
    

    public int getNiveau() {
        return niveau;
    }

    public HashMap<Integer,Bloc> getSesFils()
    {
        return this.sesBlocs;
    }
    
        
    public BlocGraphique getBlocGraphique()
    {
        return this.blocGraph;
    }
    
}
