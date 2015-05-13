/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import saveSystem.AccesXML;

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
    protected AccesXML acces;
    
    
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

        this.acces = ctrl.getAcces();
    }
    
    
    /**
     * Fait la même chose qu'ajotuerBlocALaFin, mais ne sauvegarde pas la parenté
     * dans le fichier de sauvegarde (lorsque l'on charge, par exemple).
     * @param unBloc 
     */
    public void ajouterBlocALaFinSansSauvegarde(Bloc unBloc)
    {
        sesBlocs.put(sesBlocs.size(), unBloc);
        unBloc.setNiveau(niveau+1);
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
        acces.setPositionToBloc(id, position);
        unBloc.setNiveau(niveau+1);
    }
    
    
    /**
     * Permet de supprimer un bloc par rapport à sa position. Sera complété/remplacé
     * prochainement.
     * @param position est la position du bloc que l'on souhaite supprimer.
     */
    public void supprimerBloc(int position)
    {
        sesBlocs.remove(position);
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
        
        code+=(sonCodeDebut);
        
            for(Map.Entry<Integer,Bloc> blocs : sesBlocs.entrySet()) 
            {
                code+=blocs.getValue().getCode();
            }
        
        code+=(sonCodeFin);
        
        return code;
    }
    
    
    /**
     * Le niveau représente à quelle profondeur de parenté se trouve le bloc.
     * @param niveau 
     */
    public void setNiveau(int niveau)
    {
        this.niveau = niveau;
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

    public int getNiveau() {
        return niveau;
    }

    public HashMap<Integer,Bloc> getSesFils()
    {
        return this.sesBlocs;
    }
    
}
