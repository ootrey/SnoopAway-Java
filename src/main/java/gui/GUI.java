package gui;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import logic.Append;
import logic.Prefix;
import logic.Scramble;

import java.io.File;

public class GUI extends Application {
    public void start(Stage stage) {
        stage.setTitle(I18n.getString("GUI.title"));
        stage.setResizable(false);

        switchToStart(stage);

        stage.show();
    }

    private void switchToStart(Stage stage) {
        BorderPane borderPane = new BorderPane();
        VBox vbxCenter = new VBox();
        HBox hbxTop = new HBox();
        HBox hbxSeparator = new HBox();
        HBox hbxToggle = new HBox();
        HBox hbxBottom = new HBox();

        TextField textField = new TextField();
        Separator sprTop = new Separator();
        Button btnSelect = new Button();
        Button btnBack = new Button();
        Button btnNext = new Button();
        ToggleButton tbProtect = new ToggleButton();
        ToggleButton tbRestore = new ToggleButton();
        RadioButton rbAppend = new RadioButton();
        RadioButton rbPrefix = new RadioButton();
        RadioButton rbScramble = new RadioButton();


        ToggleGroup tgProtectOrRestore = new ToggleGroup();
        ToggleGroup tgTask = new ToggleGroup();

        textField.setPromptText(I18n.getString("GUI.textFieldPrompt"));
        btnSelect.setText(I18n.getString("GUI.buttonSelect"));
        btnBack.setText(I18n.getString("GUI.buttonBack"));
        btnNext.setText(I18n.getString("GUI.buttonNext"));
        tbProtect.setText(I18n.getString("GUI.toggleButtonProtect"));
        tbRestore.setText(I18n.getString("GUI.toggleButtonRestore"));
        rbAppend.setText(I18n.getString("GUI.radioButtonAppend.Protect"));
        rbPrefix.setText(I18n.getString("GUI.radioButtonPrefix.Protect"));
        rbScramble.setText(I18n.getString("GUI.radioButtonScramble.Protect"));

        btnBack.setDisable(true);

        btnSelect.setOnAction((ae) -> {
            Stage chooser = new Stage();

            DirectoryChooser directoryChooser = new DirectoryChooser();

            File target = directoryChooser.showDialog(chooser);

            if (target != null) {
                textField.setText(target.getAbsolutePath());
            }
        });

        tbProtect.setSelected(true);

        tbProtect.setOnAction((ae) -> {
            tbProtect.setSelected(true);
            tbRestore.setSelected(false);

            rbAppend.setText(I18n.getString("GUI.radioButtonAppend.Protect"));
            rbPrefix.setText(I18n.getString("GUI.radioButtonPrefix.Protect"));
            rbScramble.setText(I18n.getString("GUI.radioButtonScramble.Protect"));
        });

        tbRestore.setOnAction((ae) -> {
            tbProtect.setSelected(false);
            tbRestore.setSelected(true);

            rbAppend.setText(I18n.getString("GUI.radioButtonAppend.Restore"));
            rbPrefix.setText(I18n.getString("GUI.radioButtonPrefix.Restore"));
            rbScramble.setText(I18n.getString("GUI.radioButtonScramble.Restore"));
        });

        btnNext.setOnAction((ae) -> {
            File target = new File(textField.getText());
            RenameController renameController = null;

            if (textField.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(I18n.getString("GUI.alert.title"));
                alert.setHeaderText(null);
                alert.setContentText(I18n.getString("GUI.alert.noSelection"));
                alert.showAndWait();
                return;
            } else if (!target.exists()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(I18n.getString("GUI.alert.title"));
                alert.setHeaderText(null);
                alert.setContentText(I18n.getString("GUI.alert.notExist.start")
                        + target.getAbsolutePath()
                        + I18n.getString("GUI.alert.notExist.end"));
                alert.showAndWait();
                return;
            } else if (target.isFile()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(I18n.getString("GUI.alert.title"));
                alert.setHeaderText(null);
                alert.setContentText(I18n.getString("GUI.alert.notFolder.start")
                        + target.getAbsolutePath()
                        + I18n.getString("GUI.alert.notFolder.end"));
                alert.showAndWait();
                return;
            }

            if (rbAppend.isSelected() && tbProtect.isSelected()) {
                renameController = new RenameController<>(target, new Append());
                renameController.setResultIndex(0);
            }

            if (rbAppend.isSelected() && !tbProtect.isSelected()) {
                renameController = new RenameController<>(target, new Append());
                renameController.setReverse(true);
                renameController.setResultIndex(2);
            }

            if (rbPrefix.isSelected() && tbProtect.isSelected()) {
                renameController = new RenameController<>(target, new Prefix());
                renameController.setResultIndex(0);
            }

            if (rbPrefix.isSelected() && !tbProtect.isSelected()) {
                renameController = new RenameController<>(target, new Prefix());
                renameController.setReverse(true);
                renameController.setResultIndex(2);
            }

            if (rbScramble.isSelected() && tbProtect.isSelected()) {
                renameController = new RenameController<>(target, new Scramble());
                renameController.setResultIndex(0);
            }

            if (rbScramble.isSelected() && !tbProtect.isSelected()) {
                renameController = new RenameController<>(target, new Scramble());
                renameController.setReverse(true);
                renameController.setResultIndex(2);
            }

            if (renameController != null) {
                switchToRename(stage, renameController);
            }
        });

        tbProtect.setToggleGroup(tgProtectOrRestore);
        tbRestore.setToggleGroup(tgProtectOrRestore);

        rbAppend.setToggleGroup(tgTask);
        rbPrefix.setToggleGroup(tgTask);
        rbScramble.setToggleGroup(tgTask);

        hbxTop.setSpacing(5);
        hbxTop.setPadding(new Insets(5));
        hbxTop.setHgrow(textField, Priority.ALWAYS);
        hbxTop.getChildren().addAll(textField, btnSelect);

        hbxSeparator.setHgrow(sprTop, Priority.ALWAYS);
        hbxSeparator.getChildren().addAll(sprTop);

        hbxToggle.getChildren().addAll(tbProtect, tbRestore);

        hbxBottom.setSpacing(5);
        hbxBottom.setPadding(new Insets(5));
        hbxBottom.setAlignment(Pos.CENTER_RIGHT);
        hbxBottom.getChildren().addAll(btnBack, btnNext);

        vbxCenter.setSpacing(5);
        vbxCenter.setPadding(new Insets(5));
        vbxCenter.getChildren().addAll(hbxSeparator, hbxToggle, rbAppend, rbPrefix, rbScramble);

        borderPane.setPrefSize(320, 210);
        borderPane.setPadding(new Insets(5));
        borderPane.setTop(hbxTop);
        borderPane.setCenter(vbxCenter);
        borderPane.setBottom(hbxBottom);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
    }

    private void switchToRename(Stage stage, RenameController renameController) {
        BorderPane borderPane = new BorderPane();
        VBox vbxCenter = new VBox();
        HBox hbxTop = new HBox();
        HBox hbxSeparator = new HBox();
        HBox hbxBottom = new HBox();

        Label label = new Label();
        Button btnBack = new Button();
        Button btnExit = new Button();
        ProgressBar progressBar = new ProgressBar();
        Separator sprTop = new Separator();

        btnBack.setText(I18n.getString("GUI.buttonBack"));
        btnExit.setText(I18n.getString("GUI.buttonExit"));

        btnBack.setDisable(true);
        btnExit.setDisable(true);

        progressBar.setMaxWidth(Double.MAX_VALUE);

        btnBack.setOnAction((ae) -> {
            switchToStart(stage);
        });

        btnExit.setOnAction((ae) -> {
            stage.close();
        });

        hbxTop.setSpacing(5);
        hbxTop.setPadding(new Insets(5));
        hbxTop.setHgrow(progressBar, Priority.ALWAYS);
        hbxTop.getChildren().addAll(progressBar);

        hbxSeparator.setHgrow(sprTop, Priority.ALWAYS);
        hbxSeparator.getChildren().addAll(sprTop);

        hbxBottom.setSpacing(5);
        hbxBottom.setPadding(new Insets(5));
        hbxBottom.setAlignment(Pos.CENTER_RIGHT);
        hbxBottom.getChildren().addAll(btnBack, btnExit);

        vbxCenter.setSpacing(5);
        vbxCenter.setPadding(new Insets(5));
        vbxCenter.getChildren().addAll(hbxSeparator, label);

        progressBar.progressProperty().bind(renameController.progressProperty());

        label.textProperty().bind(renameController.messageProperty());

        borderPane.setPrefSize(320, 210);
        borderPane.setPadding(new Insets(5));
        borderPane.setTop(hbxTop);
        borderPane.setCenter(vbxCenter);
        borderPane.setBottom(hbxBottom);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);

        renameController.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                (EventHandler<WorkerStateEvent>) t -> {
                    btnBack.disableProperty().bind(new BooleanBinding() {
                        @Override
                        protected boolean computeValue() {
                            return false;
                        }
                    });

                    btnExit.disableProperty().bind(new BooleanBinding() {
                        @Override
                        protected boolean computeValue() {
                            return false;
                        }
                    });
                });

        new Thread(renameController).start();
    }
}
