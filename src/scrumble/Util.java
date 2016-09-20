package scrumble;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Define general purpose methods used by other classes.
 */
public class Util {

    final static String RESOURCES_DIR = "/resources";

    public static String getResourceFileContent(String localPath) {
        String path = RESOURCES_DIR + "/" + localPath;
        URL fileUrl = UserCmdInfo.class.getResource(path);
        String result;
        try (
                InputStream sqlTextStream = fileUrl.openStream();
                InputStreamReader isr = new InputStreamReader(sqlTextStream);
                BufferedReader reader = new BufferedReader(isr);) {
            StringBuilder sb = new StringBuilder();
            String lineSep = System.getProperty("line.separator");
            boolean doPrependLineSep = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (doPrependLineSep) {
                    sb.append(lineSep);
                }
                sb.append(line);
                doPrependLineSep = true;
            }
            result = sb.toString();
        } catch (IOException ex) {
            Logger.getLogger(UserCmdInfo.class.getName()).log(Level.SEVERE, null, ex);
            result = "TODO: Handle IO Error: " + ex.getMessage();
        }
        return result;
    }
}
