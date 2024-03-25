package edu.sdccd.cisc191.aFinalBossBattle;

import edu.sdccd.cisc191.GameButton;
import edu.sdccd.cisc191.GameLabel;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ActTwo extends ActOne {

    private final PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
    private int currentDialogueIndex = 0;
    private GameLabel rockyText = new GameLabel(null, 416, 96, 14);
    private GameLabel elMonoText = new GameLabel(null, 416, 96, 14);
    private int rockyCount = 0;
    private int elMonoCount = 0;
    private final String[] rockyDialogues = {
            "Father...",
            "No. Father...I've grown stronger. \n"+ adventurer.getPlayerName() + " helped me get here, now I will end this.",
            "Yes, father. I will show you true strength."
    };
    private final String[] monoDialogues = {
            "You dare come back, Rocky? \nWith a friend, I see?",
            "You are still as pathetic as ever.\nA disgrace to this village.",
            "Do you really think you can beat me?"
    };
    private static final Pane root = new Pane();

    public void createScene() {
        root.setPrefSize(1000, 700);
        BackgroundImage bgImage = new BackgroundImage(backgrounds[count], BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1000, 700, false, false, false, false));
        root.setBackground(new Background(bgImage));

        ImageView rockyImageView = new ImageView(new Image("CharacterImages/rockyProfile.png"));
        rockyImageView.setFitWidth(219);
        rockyImageView.setFitHeight(252);
        rockyImageView.setLayoutX(130);
        rockyImageView.setLayoutY(176);

        ImageView monoImageView = new ImageView(new Image("CharacterImages/elMono.png"));
        monoImageView.setFitWidth(450);
        monoImageView.setFitHeight(462);
        monoImageView.setLayoutX(529);
        monoImageView.setLayoutY(199);

        elMonoText.setLayoutX(551);
        elMonoText.setLayoutY(65);
        elMonoText.setStyle("-fx-background-radius: 20%; -fx-font-size: 18");
        rockyText.setLayoutX(31);
        rockyText.setLayoutY(65);
        rockyText.setStyle("-fx-background-radius: 20%; -fx-font-size: 18");

        root.getChildren().addAll(rockyImageView, monoImageView, elMonoText, rockyText);

        currentStage.setScene(new Scene(root));

        animateText();
    }

    /**
     * animate text
     */
    private void animateText() {
        int totalDialogue = rockyDialogues.length + monoDialogues.length;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            if (currentDialogueIndex % 2 == 0) {
                elMonoText.setText(monoDialogues[elMonoCount]);
                rockyText.setText(null);
                elMonoCount++;
            } else {
                rockyText.setText(rockyDialogues[rockyCount]);
                elMonoText.setText(null);
                rockyCount++;
            }

            currentDialogueIndex++;
        }));
        timeline.setCycleCount(totalDialogue); // Each character speaks once in a cycle
        timeline.play();
        timeline.setOnFinished(e -> {
            pauseTransition.play();
            pauseTransition.setOnFinished(event -> {
                elMonoText.setText("Hah! We'll see about that.");
                pauseTransition.play();
                pauseTransition.setOnFinished(event1 -> {
                    elMonoText.setText("Say your goodbyes to your friend\nwhile you still can.");
                    pauseTransition.play();
                    pauseTransition.setOnFinished(event2 -> {
                        createPlayerChat();
                    });
                });
            });
        });
    } //end animateText()

    private void createPlayerChat() {
        root.getChildren().clear();

        ImageView rockImage = new ImageView(new Image("CharacterImages/rockyProfile.png"));
        rockImage.setFitWidth(386);
        rockImage.setFitHeight(491);
        rockImage.setLayoutX(316);
        root.getChildren().add(rockImage);

        rockyText = new GameLabel(null, 457, 92, 18);
        rockyText.setText(adventurer.getPlayerName() + ", thank you for getting me this far. \nFrom now on, this will be my own fight.");
        rockyText.setStyle("-fx-background-radius: 20%; -fx-font-size: 18");
        rockyText.setLayoutX(272);
        rockyText.setLayoutY(468);
        root.getChildren().add(rockyText);

        GameButton responseButton = new GameButton("Will you be fine on your own?", 250, 64, 14);
        responseButton.setStyle("-fx-background-radius: 15%; -fx-font-size: 14; -fx-background-color: #6F4E37");
        responseButton.setOnMouseEntered(e -> responseButton.setStyle("-fx-background-radius: 15%; -fx-font-size: 14; -fx-background-color: #5C4033; -fx-font-family: Elephant"));
        responseButton.setOnMouseExited(e -> responseButton.setStyle("-fx-background-color: #6F4E37; -fx-background-radius: 15%; -fx-font-family: Elephant; -fx-font-size: 14"));        responseButton.setLayoutX(375);
        responseButton.setLayoutY(568);
        responseButton.setOnMouseClicked(e -> {
            rockyText.setText("My father might give me some trouble.");
            responseButton.setText("But would you lose?");
            responseButton.setOnAction(event -> {
                createCutScenes(1);
            });
        });
        root.getChildren().add(responseButton);
    } //end createPlayerChat()

    /**
     * begins the boss battle
     */
    private void beginBossBattle() {
        root.getChildren().clear();
    } //end beginBattle()

    /**
     * creates the cutscenes prior to entering the boss battle
     */
    private void createCutScenes(int cutscene) {
        root.getChildren().clear();
        Image cutsceneImage;
        if (cutscene == 1) {
            cutsceneImage = new Image("CharacterImages/rockyGojo.png");
        } else {
            cutsceneImage = new Image("CharacterImages/elMonoBattle.png");
        }

        BackgroundImage bgImage = new BackgroundImage(cutsceneImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1000, 700, false, false, false, false));
        root.setBackground(new Background(bgImage));

        pauseTransition.play();
        pauseTransition.setOnFinished(e -> beginBossBattle());
    } //end createCutScenes()
}
