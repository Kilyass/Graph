package parser;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import model.*;

/**
 * la classe TxtParser, est une classe permettant d'implémenter la logique d'un parseur d'un fichier de type Texte. 
 */

public class TxtParser {
    private String filePath;

    /**
     * public TxtParseur est le constructeur de la classe TxtParseur
     * @param filePath une variable de type chaine de caractère (String) qui represente le chemin du fichier à parser. De préference, privilégier les chemins relatifs aux chemins absolus
     */

    public TxtParser(String filePath){
        this.filePath = filePath; 
    }
        /**
         * Paragraph est une classe intermediaire permettant de stocker temporairement,
         * l'id et le contenu d'un paragraphe avant de les transformer en un Node. 
         * 
         * @param id, l'identifiant du paragraphe.
         * @param content, tout le contenu du paragraphe. 
         * @requires id > 0 
         * @requires content != null
         * @ensures this.id = id && this.content.equals(content) == true
         */

    String regexpParagraph = "^\\d+$";
    String regexpRdvAu = "Rendez-vous au\\s+(\\d+)";



    public List<Paragraph> findParagraph (){

        List<Paragraph> listOfParagraphs = new ArrayList<>();
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            String theLine;

            // buffer.readLine lit chaque ligne du fichier jusqu'à ce qu'il n'y ait plus rien à lire.
            while((theLine = buffer.readLine())!= null ){
                theLine = theLine.trim();
                if(theLine.isEmpty()){
                    continue;
                }
                if(theLine.matches(regexpParagraph) == false){
                    continue;
                }
                int numberMatch = Integer.parseInt(theLine);
                StringBuilder sb = new StringBuilder();
                buffer.mark(500); // marquage de la position pour pouvoir revenir juste après.

                while ((theLine = buffer.readLine()) != null){
                    if (theLine.matches(regexpParagraph) == true){
                        buffer.reset();
                        break;
                    }
                    sb.append(theLine).append("\n");
                    buffer.mark(500);
                }

                String text = sb.toString().trim();
                listOfParagraphs.add(new Paragraph(numberMatch, text));
            }

            buffer.close();
            
        } catch (Exception e) {
            System.out.println("Erreur : " +e.getMessage());
        }
         return listOfParagraphs;
    }

        /**
         * findEdge est la methode qui permet de detecter les arêtes du livre et les ajoutes au graphe.
         * @ensures, toutes les arêtes seront créées.
         */

    private void findEdge(Node nodeBook, Map<Integer, Node> mapNode, Graph graphBook){

        Pattern patternRdvAu = Pattern.compile(regexpRdvAu, Pattern.CASE_INSENSITIVE);
        Matcher matcherRdvAu = patternRdvAu.matcher(nodeBook.getText());

        while (matcherRdvAu.find() == true){ // tant qu'une correspondance avec le matcher est trouvée.
            int targetNodeId = Integer.parseInt(matcherRdvAu.group(1));
            if(mapNode.containsKey(targetNodeId) == false){
                Node newNode = new Node(targetNodeId, " ");
                mapNode.put(targetNodeId, newNode);
                graphBook.addNode(newNode);
            }
            Node targetNode = mapNode.get(targetNodeId);

            // on créé une nouvelle arête et l'ajoute au graphe
            Edge newEdge = new Edge(nodeBook, targetNode);
            nodeBook.addEdge(newEdge);

        }
    }

        /**
         * parsing est la méthode chargé de parser complètement le LDVEH.
         * 
         * @requires filepath, soit valide.
         * @ensures thegraph contiendra, tous les noeuds et les arêtes.
         * @return theGraph.
         */

    public Graph parsing (){

        Graph theGraph = new Graph();
        Map<Integer, Node> nodeMap = new HashMap<>();

        // lecture des paragraphes
        List<Paragraph> paragraphs = findParagraph();

        // on crée les noeuds à partir du pargraphe lu et on les ajoute au graphe.
        for (Paragraph pgr : paragraphs) {
            Node node1 = new Node(pgr.id , pgr.content);
            nodeMap.put(node1.getId(), node1);
            theGraph.addNode(node1);
        }

        // on crée les arêtes.
        for (Node nd : nodeMap.values()) {
            findEdge(nd, nodeMap, theGraph);
        }

        return theGraph;

    }
   
}
