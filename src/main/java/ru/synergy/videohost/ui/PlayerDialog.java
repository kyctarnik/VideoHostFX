package ru.synergy.videohost.ui;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;
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

        VBox root = new VBox(14, title, playerStub, info, closeButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(18));
        root.getStyleClass().add("player-root");
        root.setOpacity(0);
        root.setTranslateY(22);

        Scene scene = new Scene(root, 620, 460);
        scene.getStylesheets().add(PlayerDialog.class.getResource("/styles/styles.css").toExternalForm());

        closeButton.setOnAction(event -> playCloseTransition(root, stage));
        stage.setOnShown(event -> playOpenTransition(root));
        stage.setScene(scene);
        stage.show();
    }

    private static void playOpenTransition(VBox root) {
        FadeTransition fade = new FadeTransition(Duration.millis(260), root);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(260), root);
        slide.setFromY(22);
        slide.setToY(0);

        new ParallelTransition(fade, slide).play();
    }

    private static void playCloseTransition(VBox root, Stage stage) {
        FadeTransition fade = new FadeTransition(Duration.millis(180), root);
        fade.setFromValue(root.getOpacity());
        fade.setToValue(0);

        TranslateTransition slide = new TranslateTransition(Duration.millis(180), root);
        slide.setFromY(root.getTranslateY());
        slide.setToY(14);

        ParallelTransition close = new ParallelTransition(fade, slide);
        close.setOnFinished(event -> stage.close());
        close.play();
    }
}
