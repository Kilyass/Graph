package vizualisation;

import java.util.Map;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import model.*;

public class Graph3DPanel {
    private Map<Node,Vector3D> positions;
    private Set<Edge> edges;
    private Group root;
    private PerspectiveCamera camera;

    public Graph3DPanel(Map<Node,Vector3D> positions,Set<Edge> edges){
        this.positions = positions;
        this.edges = edges;
         root = new Group();

        // Matériaux
        PhongMaterial nodeMaterial = new PhongMaterial(Color.RED);
        PhongMaterial edgeMaterial = new PhongMaterial(Color.BLACK);
        
        // Ajout des nœuds
        for (Map.Entry<Node, Vector3D> entry : positions.entrySet()) {
            Vector3D pos = entry.getValue();
            Sphere sphere = new Sphere(10); // Taille du nœud
            sphere.setMaterial(nodeMaterial);
            sphere.setTranslateX(pos.getX());
            sphere.setTranslateY(pos.getY());
            sphere.setTranslateZ(pos.getZ());
            root.getChildren().add(sphere);
        }

        // Ajout des arêtes
        for (Edge edge : edges) {
            Node source = edge.getSourceNode();
            Node dest = edge.getDestinationNode();
            Vector3D posSource = positions.get(source);
            Vector3D posDest = positions.get(dest);

            if (posSource != null && posDest != null) {
                Cylinder cylinder = createEdge(posSource, posDest);
                cylinder.setMaterial(edgeMaterial);
                root.getChildren().add(cylinder);
            }
        }

        // Ajout d'une caméra 3D
        camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
            new Rotate(-20, Rotate.X_AXIS),
            new Rotate(-20, Rotate.Y_AXIS),
            new Translate(0, 0, -500) // Reculer la caméra
        );
    }
     // Fonction pour créer un cylindre représentant une arête
     private Cylinder createEdge(Vector3D start, Vector3D end) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double dz = end.getZ() - start.getZ();
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        Cylinder line = new Cylinder(3, distance);
        line.setTranslateX((start.getX() + end.getX()) / 2);
        line.setTranslateY((start.getY() + end.getY()) / 2);
        line.setTranslateZ((start.getZ() + end.getZ()) / 2);

        double angleY = Math.toDegrees(Math.atan2(dz, dx));
        double angleZ = Math.toDegrees(Math.acos(dy / distance));

        line.getTransforms().addAll(
            new Rotate(angleY, Rotate.Y_AXIS),
            new Rotate(angleZ, Rotate.Z_AXIS)
        );

        return line;
    }

    public SubScene getSubScene() {
        SubScene subScene = new SubScene(root, 1100, 700);
        subScene.setCamera(camera);
        return subScene;
    }
    
}
