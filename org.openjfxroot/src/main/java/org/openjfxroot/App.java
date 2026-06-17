package org.openjfxroot;

import org.openjfxroot.ui.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
    	
        scene = new Scene(loadFXML("ui/primary"), 640, 480);
        stage.setScene(scene);
/*
        OrderCellController orderController = OrderCellController.newInstance();
    	System.out.println("order controller new instanciated");
    	App.setRootInstance( orderController.getRoot());
  */  	
        PrimaryController primaryController = PrimaryController.newInstance();
    	System.out.println("primary controller new instanciated");
    	App.setRootInstance(primaryController.getRoot());
        stage.show();        
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    public static void setRootInstance(Node root) throws IOException {
    	scene.setRoot((Parent)root);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}