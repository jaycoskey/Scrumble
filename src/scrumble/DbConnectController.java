package scrumble;

import java.io.IOException;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller class for the user entry DB connection info.
 */
public class DbConnectController {

    @FXML
    private TextField connectionString;
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button connectButton;
    private DbConnectManager dbConnectManager;

    public void initialize() {
    }

    public void initManager(final DbConnectManager dbConnectManager) throws IOException {
        this.dbConnectManager = dbConnectManager;
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String connMsg = setConnectionParameters();
                if (connMsg != null && !connMsg.equals("")) {
                    dbConnectManager.onSetConnectionParameters(connMsg);
                }
            }
        });
    }

    private String setConnectionParameters() {
        System.out.println("Entering setConnectionParameters");
        dbConnectManager.setConnectionInfo(
                connectionString.getText(), user.getText(), password.getText()
        );
        return "Connection parameters set";
    }
}
