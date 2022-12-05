package Graphics;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TileWrapper {
    @FXML private ChoiceBox<String> Ingeridient00;
    @FXML private ChoiceBox<String> Animal00;
    @FXML private ImageView C00;
    @FXML private ImageView T00;
    @FXML private ImageView B00;
    @FXML private ImageView Grass00;
    @FXML private Label GrassLabel00;
    @FXML private Label BCounter00;
    @FXML private Label TCounter00;
    @FXML private Label CCounter00;
    @FXML private ImageView Alert00;
    @FXML private ImageView Ground00;

    public ImageView getGround00() {
        return Ground00;
    }

    public ChoiceBox<String> getIngeridient00() {
        return Ingeridient00;
    }

    public ChoiceBox<String> getAnimal00() {
        return Animal00;
    }

    public ImageView getC00() {
        return C00;
    }

    public ImageView getT00() {
        return T00;
    }

    public ImageView getB00() {
        return B00;
    }

    public ImageView getGrass00() {
        return Grass00;
    }

    public Label getGrassLabel00() {
        return GrassLabel00;
    }

    public Label getBCounter00() {
        return BCounter00;
    }

    public Label getTCounter00() {
        return TCounter00;
    }

    public Label getCCounter00() {
        return CCounter00;
    }

    public ImageView getAlert00() {
        return Alert00;
    }
}
