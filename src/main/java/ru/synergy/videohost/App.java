package ru.synergy.videohost;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.synergy.videohost.ui.MainView;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView.build(), 1100, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        stage.setTitle("VideoHostFX");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
