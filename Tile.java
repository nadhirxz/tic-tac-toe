package xo;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    boolean clicked = false;
    Text text = new Text();
    int id = 0;
    public Tile() {
        text.setFont(Font.font(100));
        Rectangle border = new Rectangle(150,150);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        setAlignment(Pos.CENTER);
        getChildren().addAll(border,text);
    }
    public void drawX() {
        text.setText("X");
        text.setFill(Color.RED);
        clicked = true;
    }
    public void drawO() {
        text.setText("O");
        text.setFill(Color.BLUE);
        clicked = true;
    }
    public void click(boolean xo) {
        if (xo) {
            drawX();
        } else {
            drawO();
        }
    }
    public String getText() {
        return text.getText();
    }
}