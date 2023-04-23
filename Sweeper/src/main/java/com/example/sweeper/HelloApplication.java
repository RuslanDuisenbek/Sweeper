import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private Media media;
    private Pane gameOVER = new Pane();
    private MediaPlayer mediaPlayer;
    private int way = 0;
    private Scene scene;
    private Scene gameOverScene;
    private static final int TILE_SIZE = 40;
    private static final int W = 800;
    private static final int H = 600;

    private static final int X_TITLES = W / TILE_SIZE;
    private static final int Y_TITLES = H / TILE_SIZE;

    private boolean b;

    private Tile[][] grid = new Tile[X_TITLES][Y_TITLES];
    private ForGame[][] gridGame = new ForGame[X_TITLES][Y_TITLES];

    private Parent createCont() {
        Pane root = new Pane();
        gameOVER.setPrefSize(800 , 600);
        root.setPrefSize(800, 600);
        for (int i = 0; i < Y_TITLES; i++) {
            for (int j = 0; j < X_TITLES; j++) {
                b = Math.random() < 0.2;
                ForGame forGame = new ForGame(j , i ,b );
                Tile tile = new Tile(j, i, b);
                grid[j][i] = tile;
                gridGame[j][i] = forGame;
                root.getChildren().add(tile);
                gameOVER.getChildren().add(forGame);
            }
        }
        for (int i = 0; i < Y_TITLES; i++) {
            for (int j = 0; j < X_TITLES; j++) {
                Tile tile = grid[j][i];
                ForGame fo= gridGame[j][i];
                if (tile.hasBOOM ) {
                    continue;
                }
                if (fo.hasBOOM)
                    continue;
                long bombs = getNeighbor(tile).stream().filter(t -> t.hasBOOM).count();
                if (bombs > 0) {
                    tile.value.setText(String.valueOf(bombs));
                    fo.value.setText(String.valueOf(bombs));
                }
            }
        }
        return root;
    }
    public Parent gm(){
        return gameOVER;
    }


    private List<Tile> getNeighbor(Tile tile) {
        List<Tile> neighbord = new ArrayList<>();
        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];
            int newX = tile.x + dx;
            int newY = tile.y + dy;
            if (newX >= 0 && newX < X_TITLES
                    && newY >= 0 && newY < Y_TITLES) {
                neighbord.add(grid[newX][newY]);
            }
        }
        return neighbord;
    }
    private List<ForGame> openGame(ForGame forGame) {
        List<ForGame> neighbord = new ArrayList<>();
        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };
        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];
            int newX = forGame.x + dx;
            int newY = forGame.y + dy;
            if (newX >= 0 && newX < X_TITLES
                    && newY >= 0 && newY < Y_TITLES) {
                neighbord.add(gridGame[newX][newY]);
            }
        }
        return neighbord;
    }
    private class ForGame extends StackPane{
        private int x, y;
        private boolean hasBOOM;
        private boolean isOpen = false ;
        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text value = new Text();
        public ForGame(int x, int y, boolean hasBOOM ) {
            this.x = x;
            this.y = y;
            this.hasBOOM = hasBOOM;
            value.setVisible(false);
            this.border.setStroke(Color.LIGHTGRAY);
            this.value.setFont(Font.font(18));
            this.value.setText(hasBOOM ? "❌" : "");
            if(this.value.getText().equals("❌")){
                this.value.setFill(Color.WHITE);
                this.value.setVisible(true);
            }
            getChildren().addAll(border ,value );
            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);
            setOnMouseClicked(e -> scene.setRoot(createCont()));
        }
        public ForGame(int x , int y , boolean hasBOOM , Text value , Rectangle border){
            this.x = x;
            this.y = y;
            this.hasBOOM = hasBOOM;
            this.value = value;
            this.border = border;

        }

    }
    private class Tile extends StackPane {
        private int x, y;
        private boolean hasBOOM;

        private boolean isOpen = false;

        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text value = new Text();
        private Text text = new Text();

        public Tile(int x, int y, boolean hasBOOM) {
            this.x = x;
            this.y = y;
            this.hasBOOM = hasBOOM;
            value.setVisible(false);
            border.setStroke(Color.LIGHTGRAY);
            value.setFont(Font.font(18));
            value.setText(hasBOOM ? "❌" : "");

            getChildren().addAll(border, value, text);
            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);
            ForGame forGame = new ForGame(x , y , hasBOOM   , this.value , this.border);
            setOnMouseClicked(e -> {
                        if (e.getButton() == MouseButton.SECONDARY) {
                            text.setLayoutX(e.getX());
                            text.setLayoutY(e.getY());
                            if (text.getText().isEmpty()) {
                                text.setFill(Color.WHITE);
                                text.setFont(Font.font(18));
                                text.setVisible(true);
                                text.setText("¡n!");
                            } else if (text.getText().equals("¡n!")) {
                                text.setFill(Color.WHITE);
                                text.setFont(Font.font(18));
                                text.setVisible(true);
                                text.setText("?");
                            } else
                                text.setText("");
                        } else {
                            open();
                        }
                    }
            );
        }
        public void gameOver() {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: black");
            Text text = new Text("Game is Over");
            Text text1 = new Text("You Lose");
            Button button = new Button("Repeat \uD83D\uDD01");
            button.setOnAction(e -> {
                scene.setRoot(createCont());
                gameOverScene.getWindow().hide();
            });
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
            gameOverScene = new Scene(pane, 400, 300);
            Stage stage = new Stage();
            stage.setScene(gameOverScene);
            stage.setTitle("Game is Over");
            stage.show();
        }

        public void open() {
            if(!hasBOOM) {
                way++;
            }
            if (this.isOpen) {
                return;
            }
            if (this.hasBOOM) {
                media = new Media(new File("C:/Users/Evrika/IdeaProjects/Sweeper/src/main/java/songs/way_om.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                gameOver();
                System.out.println("Game is Over");
                scene.setRoot(gameOVER);
                return;
            }
            media = new Media(new File("C:/Users/Evrika/IdeaProjects/Sweeper/src/main/java/songs/line_open.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            way++;
            this.isOpen = true;
            value.setVisible(true);
            border.setFill(null);
            if (value.getText().isEmpty()) {
                media = new Media(new File("C:/Users/Evrika/IdeaProjects/Sweeper/src/main/java/songs/line_open.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                getNeighbor(this).forEach(Tile::open);
            }
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(createCont());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Hello ¡n!");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
