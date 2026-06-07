package ru.synergy.videohost.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainView {

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

        Label logo = new Label("VH");
        logo.getStyleClass().add("logo-mark");

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

        HBox cards = new HBox(14,
                createCard("JavaFX за 15 минут", "CodeLab", "12K просмотров"),
                createCard("GitHub для новичков", "DevStart", "8K просмотров"),
                createCard("UI анимации в Java", "MotionHub", "5K просмотров")
        );

        content.getChildren().addAll(section, cards);
        return content;
    }

    private VBox createCard(String title, String author, String stats) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setPrefWidth(230);
        card.getStyleClass().add("video-card");

        StackPane preview = new StackPane(new Label("Превью"));
        preview.setMinHeight(120);
        preview.getStyleClass().add("video-preview");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("video-title");

        Label authorLabel = new Label(author);
        authorLabel.getStyleClass().add("video-author");

        Label statsLabel = new Label(stats);
        statsLabel.getStyleClass().add("video-stats");

        card.getChildren().addAll(preview, titleLabel, authorLabel, statsLabel);
        return card;
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("nav-btn");
        return button;
    }
}
