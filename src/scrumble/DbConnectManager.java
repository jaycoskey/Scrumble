package scrumble;

/**
 * Manager class for the user entry DB connection info.
 */
public class DbConnectManager {

    private ScrumbleGuiController guiController;

    public DbConnectManager() {
    }

    public void onSetConnectionParameters(String connMessage) {
        showConnectionMessage(connMessage);
    }

    public void setConnectionInfo(
            String connectionString, String user, String password) {
        guiController.setConnectionString(connectionString);
        guiController.setUser(user);
        guiController.setPassword(password);
    }

    public void setScrumbleGuiController(ScrumbleGuiController guiController) {
        this.guiController = guiController;
    }

    private void showConnectionMessage(String connMessage) {
        guiController.showConnectionMessage(connMessage);
    }
}
