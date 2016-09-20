package scrumble;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Enumeration of display tabs that display post-query results info.
 */
public class ResultsTabPaneController {

    @FXML
    public Tab featuresTab;
    @FXML
    public TableView featuresTable;
    @FXML
    public Tab tasksTab;
    @FXML
    public TableView tasksTable;
    @FXML
    public Tab ownersTab;
    @FXML
    public TableView ownersTable;
    @FXML
    public Tab statusesTab;
    @FXML
    public TableView statusesTable;
    private ResultsTabPaneManager resultsTabPaneManager;  // Currently unused

    public void initialize() {
    }

    public void initManager(final ResultsTabPaneManager resultsTabPaneManager) {
        this.resultsTabPaneManager = resultsTabPaneManager;
    }
}
