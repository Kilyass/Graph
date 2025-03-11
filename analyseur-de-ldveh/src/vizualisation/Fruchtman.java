package vizualisation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import model.* ;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D ;
public class Fruchtman {
    
    public double width;
    public double length;
    public Set<Node> nodeSet;
    public Set<Edge> edgeSet;
    public int iterations;
    public double temperature;
    public double optimalDistance;
    public double area;
    private Map<Node, Vector3D> pos3d;
    private Map<Node, Vector3D> disp3d;

    public Fruchtman(double width, double length, Graph ourGraph, int iterations, double temperature) {
         this.width = width;
        this.length = length;
        this.nodeSet = new HashSet<>(); // nodeSet est un ensemble contenant tous les noeuds.
        this.edgeSet = new HashSet<>(); // edgeSet est un ensemble contenant toutes les arêtes.
        this.iterations = iterations;
        this.temperature = temperature;
        this.area  = this.width * this.length;
        this.pos3d = new HashMap<>(); // pos3d est un tableau associatif qui à chaque noeud correspond sa pos3dition x (sur l'abscisse ) et sa pos3dition y (sur l'ordonnée).
        this.disp3d = new HashMap<>(); // disp3d est egalement un tableau qui à chaque noeud correspond un vecteur de déplacement.   
        
        if (ourGraph.getAllNode() == null || ourGraph.getAllNode().isEmpty()) {
            throw new IllegalStateException("Le graphe ne contient aucun noeud.");
        }

        for (Node node : ourGraph.getAllNode().values()) {
            this.nodeSet.add(node);
        }

        for (Node eachNode : nodeSet) {
            if (eachNode.getEdges() != null) {
                edgeSet.addAll(eachNode.getEdges());
            }
        }
        this.optimalDistance = Math.sqrt(area / Math.abs(this.nodeSet.size()));
        initialisationpos3dition();
    }
    
    public void initialisationpos3dition() {
        System.out.println("***initialisation aléatoire des noeuds.***");
        Random rd = new Random();
        for (Node node : nodeSet) {
            double x = rd.nextDouble() * width ;
            double y = rd.nextDouble() * length;
            pos3d.put(node, new Vector3D(x, y));
            System.out.println("init noeud : (" +node+ ") à la pos3dition ("+pos3d.get(node)+")");
        }
    }

    /**
     * getWidth est une methode permettant de recupérer la largeur de la fenếtre.
     * @return la largeur de la fenetre.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * setWidth, est une méthode mutatrice qui modifie la largeur de la fenêtre.
     * @param width une variable de type double qui est la nouvelle valeur de la largeur de fenètre.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * getLength est une methode permettant de recupérer la hauteur de la fenếtre.
     * @return la hauteur de la fenêtre.
     */
    public double getLength() {
        return this.length;
    }

    /**
     * setLength, est une méthode mutatrice qui modifie la hauteur de la fenêtre.
     * @param length une variable de type double qui est la nouvelle hauteur voulue de la fenetre. 
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * getIterations permet d'obtenir le nombre d'itérations effectués par l'algorithme.
     * @return le nombre d'itérations de l'algorithme
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * est une méthode mutatrice qui modifie le nombre d'iterations de l'algorithme.
     * @param iterations une valeur entière
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public Map<Node, Vector3D> getpos3d() {
        return this.pos3d;
    }

    public Set<Edge> getEdgeSet() {
        return edgeSet;
    }

    

    /**
     * attractiveForce() calcule la force attractive entre deux noeuds liés par une arête, ce qui leur permet de se rapprocher.
     * @param distance une variable de type double représentant la distance entre deux noeuds.
     * @requires distance > 0
     * @ensures resultat > 0
     * @return la force attractive entre deux noeuds.
     */
    public double attractiveForce(double distance) {
        return Math.pow(distance, 2) / optimalDistance;
    }

    /**
     * repulsiveForce calcule la force répulsive entre deux noeuds.
     * @param distance de type double, est la distance entre deux noeuds.
     * @requires distance > 0
     * @ensures resultat > 0
     * @return la force repulsive entre deux noeuds.
     */

    public double repulsiveForce(double distance) {
        return Math.pow(optimalDistance, 2) / distance;
    }

    private Vector3D divNorm(Vector3D vector, double normVector) {
        return vector.scalarMultiply(1 / normVector);
    }
    
    public void applicationFruchterman() {
        for (int i = 1; i <= iterations; i++) {
            for (Node v : nodeSet) {
                disp3d.put(v, new Vector3D(0, 0, 0));
                for (Node u : nodeSet) {
                    if (u.equals(v) == false) {
                        Vector3D pos3dV = pos3d.get(v);
                        Vector3D pos3dU = pos3d.get(u);
                        Vector3D delta = pos3dV.subtract(pos3dU);
                        double normDelta = delta.getNorm();
                        Vector3D deltaDivNormDelta = divNorm(delta, normDelta);
                        disp3d.put(v, disp3d.get(v).add(deltaDivNormDelta.scalarMultiply(repulsiveForce(normDelta))));
                        
                    }
                }
            }
            for (Edge e : edgeSet) {
                Node v = e.getSourceNode();
                Node u = e.getDestinationNode();
                Vector3D delta;
                delta = pos3d.get(v).subtract(pos3d.get(u));
                double normDelta = delta.getNorm();
                Vector3D deltaDivNormDelta = divNorm(delta, normDelta);
                deltaDivNormDelta = divNorm(delta, normDelta);
                Vector3D updatedisp3dV = disp3d.get(v).subtract(deltaDivNormDelta.scalarMultiply(attractiveForce(normDelta)));
                disp3d.put(v, updatedisp3dV);
                Vector3D updatedisp3dU = disp3d.get(u).add(deltaDivNormDelta.scalarMultiply(attractiveForce(normDelta)));
                disp3d.put(u, updatedisp3dU);
            }
            for (Node v : nodeSet) {
                Vector3D disp3dV = disp3d.get(v);
                double normVdisp3d = disp3dV.getNorm();
                Vector3D divNormdisp3d = divNorm(disp3dV, normVdisp3d);
                double minimum = Math.min(normVdisp3d, temperature); 
                pos3d.put(v, pos3d.get(v).add(divNormdisp3d.scalarMultiply(minimum)));
                
                double wl = (width+length)/2;
                double newX = Math.min(width, Math.max(15, pos3d.get(v).getX()));  
                double newY = Math.min(length, Math.max(15, pos3d.get(v).getY()));
                double newZ = Math.min(wl, Math.max(15, pos3d.get(v).getY()));  
                pos3d.put(v, new Vector3D(newX, newY));
                
            }
            temperature *= 0.65;
        }
    }

}