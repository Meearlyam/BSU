package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * view.Main class which run JavaFX application
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    /**
     * start method
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("CompanyAppFX");

        initRootLayout();

        showStaffOverview();
    }

    /**
     * initialize root layout
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * show staff info in root layout center
     */
    public void showStaffOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Overview.fxml"));
            AnchorPane staffOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(staffOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * return main stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * main method
     *
     * @param args arguments of application
     */
    public static void main(String[] args) {
        launch(args);
    }
}