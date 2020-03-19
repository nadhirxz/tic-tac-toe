package xo;

import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class App extends Application {
    boolean player = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;
        window.setTitle("Tic-Tac-Toe");
        window.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));

        VBox layout1 = new VBox(20);
        Scene scene1 = new Scene(layout1,640,320);
        layout1.setPrefWidth(150);

        Button button1 = new Button("Single Player");
        Button button2 = new Button("Multiplayer");
        button1.setMinWidth(layout1.getPrefWidth());
        button2.setMinWidth(layout1.getPrefWidth());

        Button aboutBtn = new Button("About");
        aboutBtn.setMinWidth(layout1.getPrefWidth());

        layout1.getChildren().addAll(button1,button2,aboutBtn);
        layout1.setAlignment(Pos.CENTER);

        window.setScene(scene1);
        window.setResizable(false);
        window.show();


        VBox layout4 = new VBox(20);
        Scene scene4 = new Scene(layout4,640,320);
        layout4.setPrefWidth(150);

        Button button3 = new Button("Easy");
        Button button4 = new Button("Hard");
        button3.setMinWidth(layout4.getPrefWidth());
        button4.setMinWidth(layout4.getPrefWidth());

        layout4.getChildren().addAll(button3,button4);
        layout4.setAlignment(Pos.CENTER);

        Random random = new Random();
        int r = random.nextInt(2);
        if(r==0) this.player = !player;
        
        Game game = new Game(player);
        button1.setOnAction(e -> {
            window.setScene(scene4);
        });
        button2.setOnAction(e -> {
            game.startMulti(window, scene1);
        });
        aboutBtn.setOnAction(e -> {
            VBox abt = new VBox(20);
            Scene about = new Scene(abt,640,320);
            Text text = new Text("So, it's March 2020 and we're quarantined\ndue to COVID-19 (not that I actually leave home lol).\nAnyway I had a lot of spare time so I made this thing.");
            text.setFont(Font.font(18));
            //text.setTextAlignment(TextAlignment.CENTER);;
            Hyperlink link = new Hyperlink();
            link.setText("GitHub");
            link.setFont(Font.font(18));
            link.setBorder(Border.EMPTY);
            link.setOnAction(event -> {
                getHostServices().showDocument("https://github.com/mamokaze");
            });
            Button close = new Button("Go Back");
            close.setMinWidth(100);
            close.setOnAction(event -> {
                window.setScene(scene1);
            });
            abt.getChildren().addAll(text,link,close);
            abt.setAlignment(Pos.CENTER);
            window.setScene(about);
        });
        button3.setOnAction(e -> {
            game.startSingle(window, scene1, true); // Easy = true
        });
        button4.setOnAction(e -> {
            game.startSingle(window, scene1, false); // Hard = false
        });
    }
    //123 147 369 789 258 456 159 357
    public static void main(String[] args) {
        launch(args);
    }
}