package model;
import java.util.*;


public class Graph {
    /**
     * la classe Graphe, représente l'ensemble des noeuds et des aretes  du livre
     * @param allnodes, represente tous les noeuds du graphe
     */

    private Map<Integer, Node> allNodes;

    /**
     * lorsqu'on appelle le constructeur, il nous construit un graphe, i.e chaque noeud est rassemblé dans un tableau
     * associatif
     */
    public Graph(){
        allNodes = new HashMap<>();
    }

    /**
     * dans le livre, on peut ajouter des noeuds, obtenir un noeud specifique, ou tous les noeuds
     */

    public void addNode(Node newNode){
        /**
         * addNode permet d'ajouter a la collection de noeud existant, un nouveau noeud
         */
        allNodes.put(newNode.getId(), newNode);
    }

    public Map<Integer, Node> getAllNode(){
        /**
         * getAllNode nous permets d'obtenir tous les noeuds
         *@ensures  
         *@return renvoie l'ensemble des noeuds
         */
        return allNodes;
    }
    

    public Node getNode(int idOfNode){
        /**
         * getNode permet de recuperer un noeud specifique a partir de son id
         */
        return allNodes.get(idOfNode);
    }
}
