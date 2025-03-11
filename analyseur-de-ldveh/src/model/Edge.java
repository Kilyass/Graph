package model;

public class Edge {
    /**
     * une Edge, est composé d'un noeud de depart, et d'un noeud de destination
     * @param sourceNode, le noeud de depart
     * @param destinationNode, le noeud d'arrivee
     */

    private Node sourceNode;
    private Node destinationNode;

    public Edge(Node sourceNode, Node destinationNode){
        /**
         * l'initialiation d'une arete part, du principe que l'on a un noeud source et un noeud destination
         */
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
    }

    /**
     * une methode getSourceNode()
     * @return this.sourceNode, le noeud source
     */
    public Node getSourceNode() {

        return this.sourceNode;
    }
    /**
     * une methode getDestinationNode()
     * @return this.destinationNode, le noeud d'arrivee
     */
    public Node getDestinationNode(){

        return this.destinationNode;
    }
    /**
     * une methode setSourceNode()
     * elle met a jour le noeud source,
     */

    public void setSourceNode(Node source){

        this.sourceNode = source;
    }

    /**
     * une methode setdestinationNode()
     * elle met a jour le noeud d'arrivee,
     */
    public void setDestinationNode(Node destination){
        this.destinationNode = destination;
    }

    @Override
    public String toString(){

        return "" + this.destinationNode;
    }
}
