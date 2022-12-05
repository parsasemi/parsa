package Graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;

public class Tile extends Pane {
    public static final Image BG = new Image(Tile.class.getResource("Ground.jpg").toExternalForm());
    ImageView ground;
    ChoiceBox<String> animal;
    ChoiceBox<String> ingredient;
    ImageView grass;
    int x, y;
    Label grassLabel;
    ImageView chicken;
    ImageView turkey;
    ImageView buffalo;
    Label cCounter;
    Label tCounter;
    Label bCounter;
    ImageView alert;


    {
        this.setWidth(188);
        this.setHeight(152);
    }

    public Tile(int x, int y) {
        setStyle("-fx-border-color: #000000; -fx-broder-width: 1;");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cell.fxml"));
        try {
            Pane parent = loader.load();
            TileWrapper tileWrapper = (TileWrapper) loader.getController();
            grass = tileWrapper.getGrass00();
            grassLabel = tileWrapper.getGrassLabel00();
            chicken = tileWrapper.getC00();
            turkey = tileWrapper.getT00();
            buffalo = tileWrapper.getB00();
            cCounter = tileWrapper.getCCounter00();
            bCounter = tileWrapper.getBCounter00();
            tCounter = tileWrapper.getTCounter00();
            alert = tileWrapper.getAlert00();
            ingredient = tileWrapper.getIngeridient00();
            animal = tileWrapper.getAnimal00();
            ground = tileWrapper.getGround00();
            this.x = x;
            this.y = y;
            parent.getChildren().clear();
            super.setBackground(new Background(new BackgroundImage(BG, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
            super.getChildren().addAll(alert, grass, ingredient, animal, grassLabel, chicken, turkey, buffalo, cCounter, bCounter, tCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
