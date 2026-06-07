package ru.synergy.videohost.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.synergy.videohost.model.VideoItem;

public final class PlayerDialog {

    private PlayerDialog() {
    }

    public static void open(Window owner, VideoItem item) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        if (owner != null) {
            stage.initOwner(owner);
        }
        stage.setTitle("Плеер");

        Label title = new Label(item.title());
        title.getStyleClass().add("player-title");

        Region playerStub = new Region();
        playerStub.getStyleClass().add("player-stub");
        playerStub.setMinSize(560, 300);

        Label info = new Label("Канал: " + item.author() + " | " + item.views());
        info.getStyleClass().add("player-info");

        Button closeButton = new Button("Закрыть");
        closeButton.getStyleClass().add("close-btn");
        closeButton.setOnAction(event -> stage.close());

        VBox root = new VBox(14, title, playerStub, info, closeButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(18));
        root.getStyleClass().add("player-root");

        Scene scene = new Scene(root, 620, 460);
        scene.getStylesheets().add(PlayerDialog.class.getResource("/styles/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.showAndWait();
    }
}
