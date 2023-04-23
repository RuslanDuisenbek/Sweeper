package com.example.sweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;

public class GameOver extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: black");
        Text text = new Text("Game is Over");
        Text text1 = new Text("You Lose");
        Button button = new Button("Repeat \uD83D\uDD01");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        text.setVisible(true);
        text.setFill(Color.RED);
        text.setX(130);
        text.setY(130);
       text1.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, 40));
        text1.setVisible(true);
        text1.setFill(Color.RED);
        text1.setX(115);
        text1.setY(60);
        button.setLayoutX(175);
        button.setLayoutY(140);
        button.setStyle("-fx-font-weight: bold");
        pane.getChildren().addAll(text , button , text1);
        Scene scene = new Scene(pane , 400 , 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello");
        primaryStage.show();

    }
}
