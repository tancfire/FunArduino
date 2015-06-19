/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import saveSystem.AccesXML;
import vue.Graphique.BlocGraphique;

/**
 * L'assemblage des blocs contient tout les blocs à la racine.
 * @author tancfire
 */
public class AssemblageBlocs {
    private HashMap<Integer,Bloc> sesBlocs;
    private AccesXML acces;
    
    public AssemblageBlocs(AccesXML acces)
    {
        sesBlocs = new HashMap<Integer, Bloc>();
        this.acces = acces;
    }
    
    public void ajouterBloc(int position, Bloc unBloc)
    {
        sesBlocs.put(position, unBloc);
        acces.setPositionToBloc(unBloc.getId(), position);
    }
    
    
    public void supprimerBloc(int position)
    {
        sesBlocs.remove(position);
    }
    
    /**
     * Récupère le code Arduino, avec les balises de couleurs
     * @return le code généré avec les balises de couleurs
     */
    public String getCode()
    {
        String code = "/* fichier généré par FunArduino v.bétâ 0.1 */\n\n";
        
        for(Map.Entry<Integer,Bloc> blocs : sesBlocs.entrySet()) //Trié dans l'ordre des clés
            {
                code+=blocs.getValue().getCode();
            }
        //System.out.println(code); //A dé-commenter si vous voulez voir à quoi ressemble le code avec les balises
        return code;
    }

    public HashMap<Integer, Bloc> getSesBlocs() {
        return sesBlocs;
    }
    
    public ArrayList<BlocGraphique> getBlocsGraphiques()
    {
        ArrayList<BlocGraphique> blocsGraphs = new ArrayList<BlocGraphique>();
        for(Map.Entry<Integer,Bloc> blocs : sesBlocs.entrySet()) //Trié dans l'ordre des clés
            {
               blocsGraphs.addAll(blocs.getValue().getToutLesBlocsGraphiques());
            }
        return blocsGraphs;
    }
    
    
}
