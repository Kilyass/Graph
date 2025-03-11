package vizualisation;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.util.Map;
import java.util.Set;
import vizualisation.Graph3DPanel;
import model.*;
public class Windows extends  Application {
    private Map<Node, Vector3D> positions;
    private Set<Edge> edges;
    public Windows(Fruchtman frucht){
        this.positions = frucht.getpos3d();
        this.edges = frucht.getEdgeSet();
    }
    
    public void start(Stage primaryStage) {
        Graph3DPanel graphPanel = new Graph3DPanel(positions, edges);

        StackPane root = new StackPane();
        root.getChildren().add(graphPanel.getSubScene());

        Scene scene = new Scene(root, 1100, 700);
        primaryStage.setTitle("Visualisation du graphe 3D");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void launchApp(Fruchtman frucht) {
        Windows instance = new Windows(frucht);
        Application.launch(instance.getClass());
    }
}
