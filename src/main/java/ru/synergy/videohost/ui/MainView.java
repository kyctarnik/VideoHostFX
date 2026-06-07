package ru.synergy.videohost.ui;

import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import javafx.util.Duration;
import ru.synergy.videohost.model.VideoItem;

public class MainView {

    private static final Duration INTRO_DURATION = Duration.millis(420);
    private static final Duration HOVER_DURATION = Duration.millis(180);
    private static final Duration CLICK_DURATION = Duration.millis(140);

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
        List<VBox> cardNodes = popularVideos.stream()
                .map(this::createCard)
                .toList();
        cards.getChildren().addAll(cardNodes);
        playCardsIntro(cardNodes);

        content.getChildren().addAll(section, cards);
        return content;
    }

    private VBox createCard(VideoItem videoItem) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setPrefWidth(230);
        card.getStyleClass().add("video-card");
        card.setCursor(Cursor.HAND);
        card.setOpacity(0);
        card.setTranslateY(26);
        card.setOnMouseEntered(event -> animateHover(card, -6, 1.02));
        card.setOnMouseExited(event -> animateHover(card, 0, 1.0));
        card.setOnMouseClicked(event -> animateCardClick(card, videoItem));

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

    private void playCardsIntro(List<VBox> cards) {
        for (int i = 0; i < cards.size(); i++) {
            VBox card = cards.get(i);

            FadeTransition fade = new FadeTransition(INTRO_DURATION, card);
            fade.setFromValue(0);
            fade.setToValue(1);

            TranslateTransition slide = new TranslateTransition(INTRO_DURATION, card);
            slide.setFromY(26);
            slide.setToY(0);

            ParallelTransition animation = new ParallelTransition(fade, slide);
            animation.setDelay(Duration.millis(i * 120.0));
            animation.play();
        }
    }

    private void animateHover(VBox card, double toY, double toScale) {
        TranslateTransition move = new TranslateTransition(HOVER_DURATION, card);
        move.setToY(toY);

        ScaleTransition scale = new ScaleTransition(HOVER_DURATION, card);
        scale.setToX(toScale);
        scale.setToY(toScale);

        new ParallelTransition(move, scale).play();
    }

    private void animateCardClick(VBox card, VideoItem videoItem) {
        ScaleTransition clickIn = new ScaleTransition(CLICK_DURATION, card);
        clickIn.setToX(0.97);
        clickIn.setToY(0.97);

        ScaleTransition clickOut = new ScaleTransition(CLICK_DURATION, card);
        clickOut.setToX(1.0);
        clickOut.setToY(1.0);

        clickIn.setOnFinished(event -> {
            clickOut.play();
            PlayerDialog.open(card.getScene().getWindow(), videoItem);
        });

        clickIn.play();
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
