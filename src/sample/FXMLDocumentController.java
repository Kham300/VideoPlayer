package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.beans.binding.Bindings.*;

public class FXMLDocumentController implements Initializable {

    private MediaPlayer mediaPlayer;
    private String filePath;

    @FXML
    private Slider slider;

    @FXML
    private Slider seekSlider;

    @FXML
    private MediaView mediaView;

    @FXML
    public void handleButtonAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select File (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();

        if (filePath != null) {
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty hight = mediaView.fitHeightProperty();

            width.bind(selectDouble(mediaView.sceneProperty(), "width"));
            hight.bind(selectDouble(mediaView.sceneProperty(), "hight"));

            slider.setValue(mediaPlayer.getVolume() * 100);
            slider.valueProperty().addListener(observable -> {
                mediaPlayer.setVolume(slider.getValue() / 100);

            });

            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue)
                    -> seekSlider.setValue(newValue.toSeconds()));

            seekSlider.setOnMouseClicked(event -> mediaPlayer.seek(Duration.seconds(seekSlider.getValue())));

            mediaPlayer.play();
        }
    }

    @FXML
    private void pauseVideo(ActionEvent event){
        mediaPlayer.pause();
    }

    @FXML
    private void playVideo(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    @FXML
    private void stopVideo(ActionEvent event){
        mediaPlayer.stop();
    }

    @FXML
    private void fastVideo(ActionEvent event){
        mediaPlayer.setRate(1.5);
    }

    @FXML
    private void fasterVideo(ActionEvent event){
        mediaPlayer.setRate(2.0);
    }

    @FXML
    private void slowVideo(ActionEvent event){
        mediaPlayer.setRate(0.75);
    }
    @FXML
    private void slowerVideo(ActionEvent event){
        mediaPlayer.setRate(0.5);
    }
    @FXML
    private void exitVideo(ActionEvent event){
        System.exit(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

