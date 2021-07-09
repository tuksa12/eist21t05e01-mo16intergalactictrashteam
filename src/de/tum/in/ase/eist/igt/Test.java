package de.tum.in.ase.eist.igt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        TextField textfield1 = new TextField();
        TextField textfield2 = new TextField();
        VBox root = new VBox(textfield1, textfield2);
        Scene scene = new Scene(root);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) System.exit(0);
            else System.out.println(key.getCharacter()); });
        /*scene.setOnKeyPressed((KeyEvent event) -> {
            System.out.println("Key Pressed");
        });*/
        stage.setScene(scene);
        stage.show();
    }

}
