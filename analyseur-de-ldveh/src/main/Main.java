package main;

import model.*;
import parser.TxtParser;
import vizualisation.Fruchtman;
import vizualisation.Windows;

import java.io.IOException;
import java.util.*;


/**
 * Main est point d'entrée du logiciel, elle permet de le lancer.
 */
public class Main {
    public static void main(String [] args) throws IOException {
        
        Scanner scanner = new Scanner(System.in);
        String path1 = "books/df-06-le-labyrinthe-de-la-mort.txt";
        String path2 = "books/df-34-le-voleur-d-ames.txt";
        TxtParser parsingLdveh = new TxtParser(path1);
        Graph graph = parsingLdveh.parsing();
        int rep;

        for(Map.Entry<Integer, Node> eachPairKV : graph.getAllNode().entrySet()){
            System.out.println("Node : "+eachPairKV.getKey());
            System.out.println("******");
            System.out.println("Description : "+eachPairKV.getValue().getText());
            System.out.println("****************");
            System.out.println("Edges : "+eachPairKV.getValue().getEdges());
            System.out.println("**************************************************************************");

        }
        try {
            Fruchtman applyFruchterman = new Fruchtman(1100, 650, graph, 50, 0.1);
            applyFruchterman.applicationFruchterman();
            Windows.launchApp(applyFruchterman);
        } catch (Exception e) {
            System.out.println("Une exception s'est levée : " + e.getMessage());
        }
        
        }
       /* 
        while(true){
            System.out.println("vous voulez jouer le livre : 1-'le labyrinthe de la mort ' ou 2-'le voleur d'ames' ");
            String reponse = scanner.next();
            if(reponse.matches("[12]")){
                 rep = Integer.parseInt(reponse) ;
                break;
            }else{
                System.out.println("Entrée invalide ! Veuillez entrer 1 ou 2.");
            }    
        }
        TxtParser parsingLdveh;
        if(rep==1){
             parsingLdveh = new TxtParser(path1);
        }else{
             parsingLdveh = new TxtParser(path2);
        } 
        
        Graph ourGraph = parsingLdveh.parsing();
        
        Map<Integer, Node> jeux = ourGraph.getAllNode();
        System.out.println("****************");
        System.out.println("Description : "+jeux.get(1).getText());
        System.out.println("****************");

        Graph graph = new Graph();
        while(true){
            System.out.println("entrer votre choix : ");
            String obj1 = scanner.next(); 
            int obj = Integer.parseInt(obj1);
            String txt = jeux.get(obj).getText();
            System.out.println("****************");
            System.out.println("Description : "+txt);
            System.out.println("****************");
            Node node = jeux.get(obj);
            graph.addNode(node);
           
            if(txt.contains(" votre aventure.")){
                System.out.println(" vous avez perdu  :( \n");
                break;
            }
            if(obj == 400){
                System.out.println(" vous avez gagné  :) \n");
                break;
            }
            
             
        }
        scanner.close();
        
        try {
            Fruchterman applyFruchterman = new Fruchterman(1100, 650, graph, 1, 0.1); 
            applyFruchterman.applicationFruchterman();
            new Windows(applyFruchterman);
        } catch (Exception e) {
            System.out.println("une exception s'est levée : " +e.getMessage()); 
        }
    }
     
            /* 
            if(txt.matches(regexpParagraph)){
                System.out.println("Partie terminé \n");
                break;
            }
                
  
            if(obj == 366 || obj == 19 || obj == 140 || obj== 34 || obj == 61){
                System.out.println(" vous avez perdu  :( \n");
                break;
            }
                
             */       
}
