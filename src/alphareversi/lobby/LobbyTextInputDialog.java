/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package alphareversi.lobby;

import com.sun.javafx.scene.control.skin.resources.ControlResources;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;



public class LobbyTextInputDialog extends Dialog<String[]> {

    /**************************************************************************
     *
     * Fields
     *
     **************************************************************************/

    private final GridPane grid;
    private final Label serverLabel;
    private final TextField serverTextField;
    private final Label usernameLabel;
    private final TextField usernameTextField;
    private final String defaultServerValue;
    private final String defaultUsernameValue;
    private SimpleStringProperty serverText;


    /**************************************************************************
     *
     * Constructors
     *
     **************************************************************************/

    /**
     * Creates a new TextInputDialog without a default value entered into the
     * dialog {@link TextField}.
     */
    public LobbyTextInputDialog() {
        this("", "");
    }

    /**
     * Creates a new TextInputDialog with the default value entered into the
     * dialog {@link TextField}.
     */
    public LobbyTextInputDialog(@NamedArg("defaultUsernameValue") String defaultUsernameValue, String defaultServerValue) {
        this.serverText = new SimpleStringProperty("");
        final DialogPane dialogPane = getDialogPane();

        // -- textfield
        this.usernameTextField = new TextField(defaultUsernameValue);
        this.usernameTextField.setMaxWidth(Double.MAX_VALUE);
        this.serverTextField = new TextField(defaultServerValue);
        this.serverTextField.setMaxWidth(Double.MAX_VALUE);

        GridPane.setHgrow(usernameTextField, Priority.ALWAYS);
        GridPane.setFillWidth(usernameTextField, true);
        GridPane.setHgrow(serverTextField, Priority.ALWAYS);
        GridPane.setFillWidth(serverTextField, true);

        // -- label
        usernameLabel = new Label();
        usernameLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        usernameLabel.textProperty().bind(dialogPane.contentTextProperty());

        serverLabel = new Label();
        serverLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        serverLabel.textProperty().bind(serverText);

        this.defaultUsernameValue = defaultUsernameValue;
        this.defaultServerValue = defaultServerValue;

        this.grid = new GridPane();
        this.grid.setHgap(10);
        this.grid.setVgap(10);
        this.grid.setMaxWidth(Double.MAX_VALUE);
        this.grid.setAlignment(Pos.CENTER_LEFT);

        dialogPane.contentTextProperty().addListener(o -> updateGrid());

        setTitle(ControlResources.getString("Dialog.confirm.title"));
        dialogPane.setHeaderText(ControlResources.getString("Dialog.confirm.header"));
        dialogPane.getStyleClass().add("text-input-dialog");
        dialogPane.getButtonTypes().addAll(ButtonType.OK);

        updateGrid();

        setResultConverter((dialogButton) -> {
            ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
            return data == ButtonData.OK_DONE ? getTextFieldsText() : null;
        });
    }

    private String[] getTextFieldsText () {
        String[] result = new String[2];
        result[0] = usernameTextField.getText();
        result[1] = serverTextField.getText();
        return result;
    }



    /**************************************************************************
     *
     * Public API
     *
     **************************************************************************/

    /**
     * Returns the {@link TextField} used within this dialog.
     */
    public final TextField getUsernameEditor() {
        return usernameTextField;
    }

    /**
     * Returns the default value that was specified in the constructor.
     */
    public final String getDefaultUsernameValue() {
        return defaultUsernameValue;
    }

    /**
     * Returns the {@link TextField} used within this dialog.
     */
    public final TextField getServerEditor() {
        return serverTextField;
    }

    /**
     * Returns the default value that was specified in the constructor.
     */
    public final String getDefaultServerValue() {
        return defaultServerValue;
    }



    /**************************************************************************
     *
     * Private Implementation
     *
     **************************************************************************/

    private void updateGrid() {
        grid.getChildren().clear();

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameTextField, 1, 0);
        grid.add(serverLabel, 0, 1);
        grid.add(serverTextField, 1, 1);
        getDialogPane().setContent(grid);

        Platform.runLater(() -> usernameTextField.requestFocus());
    }

    public void setServerText(String serverText) {
        this.serverText.set(serverText);
    }
}
