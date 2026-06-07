package ru.synergy.videohost.ui;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import ru.synergy.videohost.model.VideoItem;

public class MainView {

    private final List<VideoItem> popularVideos = List.of(
            new VideoItem("JavaFX за 15 минут", "CodeLab", "12K просмотров"),
            new VideoItem("GitHub для новичков", "DevStart", "8K просмотров"),
            new VideoItem("UI анимации в Java", "MotionHub", "5K просмотров"),
            new VideoItem("Основы ООП на Java", "EasyTech", "18K просмотров")
    );

    public Parent build() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-pane");

        root.setLeft(buildSidebar());
        root.setTop(buildHeader());
        root.setCenter(buildPopularSection());

        return root;
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(24));
        sidebar.setPrefWidth(220);
        sidebar.getStyleClass().add("sidebar");

        Label navTitle = new Label("Навигация");
        navTitle.getStyleClass().add("section-title");

        Button homeBtn = createNavButton("Главная");
        Button myVideosBtn = createNavButton("Мои видео");
        Button trendsBtn = createNavButton("Тренды");
        Button subscriptionsBtn = createNavButton("Подписки");

        sidebar.getChildren().addAll(navTitle, new Separator(), homeBtn, myVideosBtn, trendsBtn, subscriptionsBtn);
        return sidebar;
    }

    private HBox buildHeader() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(18, 24, 18, 24));
        header.getStyleClass().add("header");

        StackPane logo = createLogo();

        Label title = new Label("VideoHostFX");
        title.getStyleClass().add("title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        TextField searchField = new TextField();
        searchField.setPromptText("Поиск видео...");
        searchField.getStyleClass().add("search-field");

        header.getChildren().addAll(logo, title, spacer, searchField);
        return header;
    }

    private VBox buildPopularSection() {
        VBox content = new VBox(14);
        content.setPadding(new Insets(24));

        Label section = new Label("Популярные видео");
        section.getStyleClass().add("section-title");

        FlowPane cards = new FlowPane(14, 14);
        cards.getStyleClass().add("cards-pane");
        popularVideos.stream()
            .map(this::createCard)
            .forEach(cards.getChildren()::add);

        content.getChildren().addAll(section, cards);
        return content;
    }

    private VBox createCard(VideoItem videoItem) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setPrefWidth(230);
        card.getStyleClass().add("video-card");
        card.setOnMouseClicked(event -> PlayerDialog.open(card.getScene().getWindow(), videoItem));

        StackPane preview = new StackPane(new Label("Превью"));
        preview.setMinHeight(120);
        preview.getStyleClass().add("video-preview");

        Label titleLabel = new Label(videoItem.title());
        titleLabel.getStyleClass().add("video-title");

        Label authorLabel = new Label(videoItem.author());
        authorLabel.getStyleClass().add("video-author");

        Label statsLabel = new Label(videoItem.views());
        statsLabel.getStyleClass().add("video-stats");

        card.getChildren().addAll(preview, titleLabel, authorLabel, statsLabel);
        return card;
    }

    private StackPane createLogo() {
        Circle circle = new Circle(16);
        circle.getStyleClass().add("logo-circle");

        Polygon play = new Polygon();
        play.getPoints().addAll(
                -4.0, -7.0,
                8.0, 0.0,
                -4.0, 7.0
        );
        play.getStyleClass().add("logo-play");

        return new StackPane(circle, play);
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("nav-btn");
        return button;
    }
}
