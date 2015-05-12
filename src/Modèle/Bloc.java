/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

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
    
    
    
    public Bloc(Color couleur, AccesXML acces)
    {
        sesBlocs = new HashMap<Integer,Bloc>();
        this.couleur = couleur;
        sonCodeDebut = "";
        sonCodeFin = "";
        niveau = 0; // c'est le nombre de tabulation qu'il faudra faire.
        id = nbID;
        nbID++;
        
        this.acces = acces;
        acces.creerBloc(id, getClass().getSimpleName()); //permet de sauvegarder le bloc directement
    }
    
    
        public Bloc(int id, Color couleur, AccesXML acces)
    {
        sesBlocs = new HashMap<Integer,Bloc>();
        this.couleur = couleur;
        sonCodeDebut = "";
        sonCodeFin = "";
        niveau = 0; // c'est le nombre de tabulation qu'il faudra faire.
        this.id = id;

         this.acces = acces;
    }
    
        
    public void ajouterBlocALaFinSansSauvegarde(Bloc unBloc)
    {
        sesBlocs.put(sesBlocs.size(), unBloc);
        unBloc.setNiveau(niveau+1);
    }
    
    
    
    public void ajouterBlocALaFin(Bloc unBloc)
    {
        ajouterBlocALaFinSansSauvegarde(unBloc);
        acces.ajouterBlocDansBloc(id, unBloc.getId());
        acces.setPositionToBloc(unBloc.getId(), sesBlocs.size());
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
        acces.setPositionToBloc(id, position);
        unBloc.setNiveau(niveau+1);
    }
    
    
    
    public void supprimerBloc(int position)
    {
        sesBlocs.remove(position);
    }
    
    public int getNombreBlocs()
    {
        return sesBlocs.size();
    }
    
    
    public abstract void mettreAjourCode();

    
    
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
    
    
    public void setNiveau(int niveau)
    {
        this.niveau = niveau;
    }
    
    
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

    public HashMap<Integer,Bloc> getSesFils()
    {
        return this.sesBlocs;
    }
    
}
