package model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    /**
     * cette classe represente un paragraph de notre livre
     * @param id , un entier representant  l'identifiant du paragraphe
     * @param text, un string representant, le texte contenu dans le paragraphe
     * @param List<Edge> edges, qui est une liste de lien vers d'autre paragraphe
     */

    private int id;
    private String text;
    private List<Edge> edges;

    public Node(int id, String text){
        /**
         * le constructeur initialise un noeud
         * @param id, l'id du paragraphe
         * @param text, son contenu
         */
        this.id = id;
        this.text = text;
        this.edges = new ArrayList<>();
    }

    /**
     * la class a besoin de certaines methodes pour fonctionner,
     * getId, qui retourne l'identifiant du noeud
     * getText, qui retourne le text contenu dans le noeud
     * setId, qui est un setter, au travers duquel on peut modifier l'id du noeud
     * setText, egalement un setter, au travers duquel on peut modifier le contenu du noeud
     * addEdge, qui permet d'ajouter un nouvel arete, a notre noeud.
     * getEdge qui retourne la liste des aretes du noeud.
     */

    public int getId(){
        return this.id;
    }

    public String getText() {
        return text;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setText (String text){
        this.text = text;
    }

    public void addEdge(Edge newEdge){
        this.edges.add(newEdge);
    }

    public List<Edge> getEdges(){
        return this.edges;
    }

    @Override
    public String toString(){
        return  ""+this.id ;
    }


}
