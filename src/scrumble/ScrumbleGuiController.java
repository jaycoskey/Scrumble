package scrumble;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * Main GUI controller. Initializes the controls and defines callback functions.
 */
public class ScrumbleGuiController {

    // FXML data
    @FXML
    private MenuBar menuBar;
    @FXML
    private TextArea userCmdInput;
    @FXML
    private Button userCmdExecuteBtn;
    @FXML
    private Button userCmdHelpBtn;
    @FXML
    private TextArea sqlCmdText;
    @FXML
    private TextField userCmdStatusDisplay;
    @FXML
    private Button dbConnectBtn;

    @FXML
    private StackPane lowerPane;

    // Tab Pane data
    private TabPane resultsTabPane;
    private ResultsTabPaneController resultsTabPaneController;
    private ResultsTabPaneManager resultsTabPaneManager;

    // DB Connection pane
    private StackPane dbConnect;
    private DbConnectController dbConnectController;
    private DbConnectManager dbConnectManager;

    // DB connection parameters, set by the dbConnect control
    private String connectionString = null;
    private String user = null;
    private String password = null;

    private void clearOutput() {
        sqlCmdText.clear();
        userCmdStatusDisplay.clear();
    }

    // TODO: Execute DB commands in background thread.
    private void executeSqlCmd(UserCmdInfo userCmdBuilder) {
        Connection con = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (connectionString == null || user == null || password == null) {
                throw new IllegalArgumentException("Connection parameters must first be set.");
            }
            con = DriverManager.getConnection(connectionString, user, password);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(userCmdBuilder.primaryActionSql);
            userCmdStatusDisplay.setText("Command executed successfully!");
            userCmdStatusDisplay.setStyle("-fx-text-inner-color: black;");

            // Add columns to table
            getResultsTable(userCmdBuilder).getColumns().clear();
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnLabel(i + 1));
                col.setCellValueFactory(
                        new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<ObservableList, String> param
                    ) {
                        ObservableList<String> paramValue = param.getValue();
                        String str = paramValue.get(j);
                        return new SimpleStringProperty(str);
                    }
                });
                getResultsTable(userCmdBuilder).getColumns().add(col);
                // System.out.println("INFO: Column [" + i + "] added");
            }

            // Add data to ObservableList
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            int numRows = 0;
            while (rs.next()) {
                numRows++;
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int rowNum = 1; rowNum <= rs.getMetaData().getColumnCount(); rowNum++) {
                    row.add(rs.getString(rowNum));
                }
                // System.out.println("INFO: Row " + numRows + " added " + row );
                data.add(row);
            }
            getResultsTable(userCmdBuilder).setItems(data);
            resultsTabPane.getSelectionModel().select(getResultsTab(userCmdBuilder));

            userCmdStatusDisplay.setText(
                    userCmdStatusDisplay.getText()
                    + " (" + Integer.toString(numRows) + " results)"
            );
        } catch (SQLException ex) {
            userCmdStatusDisplay.setText("Cannot execute SQL.  Database prepared and connection parameters set?");
            userCmdStatusDisplay.setStyle("-fx-text-inner-color: red;");
        } catch (ClassNotFoundException | IllegalArgumentException ex) {
            userCmdStatusDisplay.setText(ex.getMessage());
            userCmdStatusDisplay.setStyle("-fx-text-inner-color: red;");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                // No-op
            }
        }
    }

    private Tab getResultsTab(UserCmdInfo userCmdBuilder) {
        Tab result;
        switch (userCmdBuilder.tab) {
            case Features:
                result = resultsTabPaneController.featuresTab;
                break;
            case Tasks:
                result = resultsTabPaneController.tasksTab;
                break;
            case Owners:
                result = resultsTabPaneController.ownersTab;
                break;
            case Statuses:
                result = resultsTabPaneController.statusesTab;
                break;
            default:
                throw new IllegalArgumentException("getResultsTab(): Invalid ResultsTab");
        }
        return result;
    }

    private TableView getResultsTable(UserCmdInfo userCmdBuilder) {
        TableView result;
        switch (userCmdBuilder.tab) {
            case Features:
                result = resultsTabPaneController.featuresTable;
                break;
            case Tasks:
                result = resultsTabPaneController.tasksTable;
                break;
            case Owners:
                result = resultsTabPaneController.ownersTable;
                break;
            case Statuses:
                result = resultsTabPaneController.statusesTable;
                break;
            default:
                throw new IllegalArgumentException("getResultsTable(): Invalid ResultsTab");
        }
        return result;
    }

    public void hideConnectionControl() {
        assert (lowerPane.getChildren().size() == 2);
        lowerPane.getChildren().remove(1);
        dbConnectBtn.setDisable(false);
    }

    public void initialize() {
        initialize_dbConnect();
        initialize_menuBar();
        initialize_resultsTabPane();

        userCmdInput.getStyleClass().add("textAreaDisplay");
        sqlCmdText.getStyleClass().add("textAreaDisplay");
        userCmdStatusDisplay.getStyleClass().add("textFieldDisplay");

        userCmdExecuteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearOutput();

                String inputText = userCmdInput.getText();
                UserCmdInfo userCmdBuilder = UserCmdInfo.fromInputText(inputText);
                if (!userCmdBuilder.isValid) {
                    userCmdStatusDisplay.setText(userCmdBuilder.validationErrorMessage);
                    userCmdStatusDisplay.setStyle("-fx-text-inner-color: red;");
                } else {
                    sqlCmdText.setText(userCmdBuilder.primaryActionSql);
                    sqlCmdText.setStyle("-fx-text-inner-color: black;");
                    executeSqlCmd(userCmdBuilder);
                }
            }
        });

        userCmdHelpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HelpWindow usageWindow = new HelpWindow();
                usageWindow.initialize();
            }
        });
    }

    public void initialize_dbConnect() {
        FXMLLoader dbConnectLoader = new FXMLLoader(
                getClass().getResource("/resources/db_connect.fxml")
        );
        try {
            dbConnect = (StackPane) dbConnectLoader.load();
            dbConnectController = dbConnectLoader.<DbConnectController>getController();
            dbConnectManager = new DbConnectManager();
            dbConnectController.initManager(dbConnectManager);
            dbConnectManager.setScrumbleGuiController(this);
        } catch (IOException ex) {
            Logger.getLogger(ScrumbleGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbConnectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dbConnectBtn.setDisable(true);
                showConnectionControl();
            }
        });
    }

    public void initialize_menuBar() {
        Menu fileMenu = new Menu("_File");
        fileMenu.mnemonicParsingProperty().set(true);
        MenuItem exitMenuItem = new MenuItem("_Exit");
        exitMenuItem.mnemonicParsingProperty().set(true);
        fileMenu.getItems().add(exitMenuItem);

        Menu helpMenu = new Menu("_Help");
        helpMenu.mnemonicParsingProperty().set(true);
        MenuItem helpMenuItem = new MenuItem("_Help");
        helpMenuItem.mnemonicParsingProperty().set(true);
        helpMenu.getItems().add(helpMenuItem);

        menuBar.getMenus().setAll(fileMenu, helpMenu);

        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        helpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HelpWindow usageWindow = new HelpWindow();
                usageWindow.initialize();
            }
        });
    }

    public void initialize_resultsTabPane() {
        FXMLLoader resultsTabPaneLoader = new FXMLLoader(
                getClass().getResource("/resources/scrumble_results_tab_pane.fxml")
        );
        try {
            resultsTabPane = (TabPane) resultsTabPaneLoader.load();
            resultsTabPaneController = resultsTabPaneLoader.<ResultsTabPaneController>getController();
            resultsTabPaneManager = new ResultsTabPaneManager();
            resultsTabPaneController.initManager(resultsTabPaneManager);
            lowerPane.getChildren().add(resultsTabPane);
        } catch (IOException ex) {
            Logger.getLogger(ScrumbleGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void showConnectionControl() {
        assert (lowerPane.getChildren().size() == 1);
        lowerPane.getChildren().add(dbConnect);
    }

    public void showConnectionMessage(String connMessage) {
        hideConnectionControl();
        userCmdStatusDisplay.setText(connMessage);
        userCmdStatusDisplay.setStyle("-fx-text-inner-color: black;");
    }
}
