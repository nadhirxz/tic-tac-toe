package xo;

import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game {
    boolean player;
    boolean xo = false;
    public void setInfo(Scene scene1, HBox one, HBox two, Stage window, boolean mode) {
        Text x = new Text("X");
        Text o = new Text("O");
        x.setFont(Font.font(28));
        o.setFont(Font.font(28));
        x.setFill(Color.RED);
        o.setFill(Color.BLUE);
        if (mode) {
            Text p = new Text("Player : ");
            Text c = new Text("Computer : ");
            p.setFont(Font.font(28));
            c.setFont(Font.font(28));
            if (xo) {
                one.getChildren().addAll(p,o);
                two.getChildren().addAll(c,x);
            } else {
                one.getChildren().addAll(p,x);
                two.getChildren().addAll(c,o);
            }
        } else {
            Text p1 = new Text("Player 1 : ");
            Text p2 = new Text("Player 2 : ");
            p1.setFont(Font.font(28));
            p2.setFont(Font.font(28));
            if (player) {
                one.getChildren().addAll(p1,o);
                two.getChildren().addAll(p2,x);
            } else {
                one.getChildren().addAll(p1,x);
                two.getChildren().addAll(p2,o);
            }
        }
    }
    public Game(boolean player) {
        this.player = player;
    }

    public void startMulti(Stage window, Scene scene1) {
        this.start(window, scene1, false, false);
    }

    public void startSingle(Stage window, Scene scene1, Boolean easy) {
        this.start(window, scene1, true, easy); // Easy = true --- Hard = false
    }

    public void start(Stage window, Scene scene1, boolean mode, boolean easy) {
        VBox rootLayout = new VBox();
        Pane layout2 = new Pane();
        Scene scene2 = new Scene(rootLayout, 450, 494);
        Tile[] tiles = new Tile[9];
        StackPane layout3 = new StackPane();
        Scene end = new Scene(layout3, 420, 160);
        Stage endGame = new Stage();
        endGame.setTitle("End of the game");
        endGame.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));

        HBox bottom = new HBox(30);
        bottom.setAlignment(Pos.CENTER);
        bottom.setTranslateY(302);
        HBox one = new HBox();
        HBox two = new HBox();
        Random random = new Random();
        int r = random.nextInt(2);
        if (r == 0) {
            xo = true;
        }

        Button close = new Button("Close");
        close.setMinWidth(80);
        close.setOnMouseClicked(e -> {
            layout2.getChildren().clear();
            bottom.getChildren().clear();
            layout3.getChildren().clear();
            window.setScene(scene1);
        });

        setInfo(scene1, one, two, window, mode);

        bottom.getChildren().addAll(one,two,close);
        rootLayout.getChildren().addAll(layout2,bottom);
        
        endGame.setScene(end);
        endGame.setResizable(false);
        endGame.setOnCloseRequest(e -> {
            layout2.getChildren().clear();
            bottom.getChildren().clear();
            layout3.getChildren().clear();
            window.setScene(scene1);
        });
        layout2.setDisable(false);
        window.setScene(scene2);
        int row = 1;
        int column = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 150);
                tile.setTranslateY(i * 150);
                tile.id = i + j + row + column;
                layout2.getChildren().add(tile);
                tiles[tile.id - 1] = tile;
                tile.setOnMouseClicked(event -> {
                    if (!tile.clicked) {
                        if (mode) {
                            if (xo) {
                                tile.drawO();
                            } else {
                                tile.drawX();
                            }
                            if(checkState(tiles,true,layout2,layout3,endGame)) {
                                computerPlayer(tiles, easy, layout2, layout3, endGame);
                            }
                        } else {
                            if (player) {
                                tile.drawO();
                            } else {
                                tile.drawX();
                            }
                            player = !player;
                            finish(layout2, layout3, endGame, tiles, mode);
                        }
                    }
                });
            }
            column++;
            row++;
        }
        r = random.nextInt(2);
        if (r == 0 && mode) {
            computerPlayer(tiles, easy, layout2, layout3, endGame);
        }
        if (mode) {
            if(easy) window.setTitle("Single Player - Easy");
            else window.setTitle("Single Player - Hard");
        } else {
            window.setTitle("Multiplayer");
        }
    }

    public boolean finish(Pane layout2, StackPane layout3, Stage endGame, Tile[] tiles, boolean mode) {
        if (check(tiles, player) != "CONTINUE") {
            endTheGame(layout2, layout3, endGame, tiles, check(tiles, player));
            return true;
        }
        return false;
    }

    public void endTheGame(Pane layout2, StackPane layout3, Stage endGame, Tile[] tiles, String text) {
        Text endMessage = new Text("No Winner");
        if (text!="No winner"&&text!="NOWINNER") {
            endMessage.setText(text+" Wins !");
        }
        endMessage.setFont(Font.font(50));
        layout3.getChildren().add(endMessage);
        layout2.setDisable(true);
        endGame.show();
    }

    public String check(Tile[] tiles, boolean player) {
        String[] txt = new String[10];
        txt[0] = "CONTINUE";
        for (int i = 1; i < 10; i++) {
            txt[i] = tiles[i - 1].getText();
        }
        boolean[] check = new boolean[] {
                txt[1] == txt[2] && txt[1] == txt[3] && txt[1] != "" && txt[2] != "" && txt[3] != "",
                txt[1] == txt[4] && txt[1] == txt[7] && txt[1] != "" && txt[4] != "" && txt[4] != "",
                txt[3] == txt[9] && txt[3] == txt[6] && txt[3] != "" && txt[6] != "" && txt[9] != "",
                txt[7] == txt[8] && txt[7] == txt[9] && txt[7] != "" && txt[8] != "" && txt[9] != "",
                txt[2] == txt[5] && txt[2] == txt[8] && txt[2] != "" && txt[5] != "" && txt[8] != "",
                txt[4] == txt[5] && txt[4] == txt[6] && txt[4] != "" && txt[5] != "" && txt[6] != "",
                txt[1] == txt[5] && txt[1] == txt[9] && txt[1] != "" && txt[5] != "" && txt[9] != "",
                txt[3] == txt[5] && txt[3] == txt[7] && txt[3] != "" && txt[5] != "" && txt[7] != "" };
        for (int i = 0; i < 8; i++) {
            if (check[i]) {
                if (player) {
                    return ("X");
                } else {
                    return ("O");
                }
            } else if (allTilesClicked(tiles)) {
                txt[0]="NOWINNER";
            }
        }
        return txt[0];
    }

    public boolean allTilesClicked(Tile[] tiles) {
        if(tilesClicked(tiles)==9) {
            return true;
        }
        return false;
    }
    public int tilesClicked(Tile[] tiles) {
        int a = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].clicked) {
                a++;
            }
        }
        return a;
    }

    public boolean checkState(Tile[] tiles, boolean state, Pane layout2, StackPane layout3, Stage endGame) {
        String text = "No winner";
        if (check(tiles, xo)=="X"||check(tiles, xo)=="O") {
            if (state) {
                text = "Player";
            } else {
                text = "Computer";
            }
            endTheGame(layout2, layout3, endGame, tiles, text);
            return false;
        } else if(check(tiles, xo)=="NOWINNER") {
            endTheGame(layout2, layout3, endGame, tiles, text);
            return false;
        } else {
            return true;
        }
    }

    public void computerPlayer(Tile[] tiles, boolean easy, Pane layout2, StackPane layout3, Stage endGame) {
        Random random = new Random();
        int r;
        if (easy) { // Easy
            r = random.nextInt(9);
            if (!allTilesClicked(tiles)) {
                do {
                    r = random.nextInt(9);
                } while (tiles[r].clicked == true && !allTilesClicked(tiles));
                tiles[r].click(xo);
                tiles[r].clicked = true;
            }
        } else { // Hard
            int[] corners = {0,2,6,8};
            int[] noncorners = {1,3,5,7};
            if (tilesClicked(tiles)==0) { // IF THE COMPUTER IS THE ONE STARTING
                r = random.nextInt(9);
                if (r>3) { // 70% of the time
                    tiles[4].click(xo); // click the middle
                } else {
                    r = random.nextInt(9); // 30% of the time computer clicks randomly
                    tiles[r].click(xo);
                }
            } else if (tilesClicked(tiles)==1) { // WHEN THE PLAYER CLICKED ONLY ONCE AND IT'S COMPUTER'S TURN
                if (tiles[4].clicked) { // if player clicked middle
                    r = random.nextInt(9);
                    if (r>3) { // 70% of the time
                        r = random.nextInt(4);
                        tiles[corners[r]].click(xo); // click one of the corners
                    } else {
                        r = random.nextInt(4);
                        tiles[noncorners[r]].click(xo); // click one of the noncorners
                    }
                } else { // if player didn't click middle
                    r = random.nextInt(9);
                    if (r>0){
                        tiles[4].click(xo); // then simply click the middle 90% of the time
                    } else {
                        do {
                            r = random.nextInt(9);
                        } while (r==4);
                        tiles[r].click(xo);
                    }
                }
            } else if (tilesClicked(tiles)==2) { // WHEN COMPUTER PLAYED FIRST THEN PLAYER CLICKED
                if (!tiles[4].clicked) { // if middle not clicked then click it
                    tiles[4].click(xo);
                } else { // if middle clicked then aim for corners
                    ArrayList<Integer> k = freeCorners(tiles);
                    r = random.nextInt(k.size());
                    tiles[k.get(r)].click(xo);
                }
            } else { // WHEN PLAYER CLICKED AT LEAST TWICE
                if ((boolean)howToWin(tiles, xo)[0]) { // checking if there's an oppurtunity to win this round
                    tiles[(int)howToWin(tiles, xo)[1]].click(xo); // and computer wins .. ez
                } else if((boolean)howToLose(tiles, xo)[0]) { // checking if there's an oppurtunity to lose this round
                    tiles[(int)howToLose(tiles, xo)[1]].click(xo);
                } else { // no win no lose , click randomly ..
                    do {
                        r = random.nextInt(9);
                    } while (tiles[r].clicked);
                    tiles[r].click(xo);
                }
            }
        }
        checkState(tiles,false,layout2,layout3,endGame);
    }

    public ArrayList<Integer> freeCorners(Tile[] tiles) {
        int[] corners = {0,2,6,8};
        ArrayList<Integer> a = new ArrayList<Integer>();
        for (int i = 0; i < corners.length; i++) {
            if (!tiles[corners[i]].clicked) {
                a.add(corners[i]);
            }
        }
        return a;
    }

    public Object[] howToWin(Tile[] tiles, boolean xo) {
        String txt = xo ? "X" : "O"; // if xo=true then player is O se we return X for computer and look for X
        Object[] a = new Object[2];
        a[0] = new Boolean(false);
        a[1] = new Integer(0);
        for (int i = 0; i < tiles.length; i++) {
            if (!tiles[i].clicked) {
                tiles[i].clicked = true;
                tiles[i].text.setVisible(false);
                tiles[i].text.setText(txt);
                if (check(tiles, xo)=="X"||check(tiles, xo)=="O") {
                    a[0] = true;
                    a[1] = i;
                    tiles[i].clicked = false;
                    tiles[i].text.setText("");
                    tiles[i].text.setVisible(true);
                    break;
                }
                tiles[i].clicked = false;
                tiles[i].text.setText("");
                tiles[i].text.setVisible(true);
            }
        }
        return a;
    }

    public Object[] howToLose(Tile[] tiles, boolean xo) {
        String txt = xo ? "O" : "X"; // if xo=true then player is O and we look for it
        Object[] a = new Object[2];
        a[0] = new Boolean(false);
        a[1] = new Integer(0);        
        for (int i = 0; i < tiles.length; i++) {
            if (!tiles[i].clicked) {
                tiles[i].clicked = true;
                tiles[i].text.setVisible(false);
                tiles[i].text.setText(txt);
                if (check(tiles, xo)=="X"||check(tiles, xo)=="O") {
                    a[0] = true;
                    a[1] = i;
                    tiles[i].clicked = false;
                    tiles[i].text.setText("");
                    tiles[i].text.setVisible(true);
                    break;
                }
                tiles[i].clicked = false;
                tiles[i].text.setText("");
                tiles[i].text.setVisible(true);
            }
        }
        return a;
    }

}