package scrumble;

import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Display a window with helpful information about the app.
 */
public class HelpWindow extends Stage {

    final private WebView browser = new WebView();
    private WebEngine webEngine;

    public HelpWindow() { }

    private String commandsHtml() {
        StringBuilder sbSupported = new StringBuilder();
        StringBuilder sbUnsupported = new StringBuilder();
        for (Object entry : UserCmdTypeInfoMap.infoMap) {
            UserCmdTypeInfo mapItem
                    = ((Map.Entry<UserCmdType, UserCmdTypeInfo>) entry).getValue();
            String leftLiTag = "          <li> ";
            String rightLiTag = "\n"; //          </li>\n";
            if (mapItem.isCommand) {
                String itemHtml
                        = "<b>" + String.join(" ", mapItem.signature) + "</b>"
                        + ".  "
                        + mapItem.briefDescription;
                if (mapItem.isSupported) {
                    sbSupported.append(leftLiTag)
                            .append(itemHtml)
                            .append(rightLiTag);
                } else {
                    sbUnsupported.append(leftLiTag)
                            .append("<strike>")
                            .append(itemHtml)
                            .append("</strike>")
                            .append(rightLiTag);
                }
            }
        }
        String result = sbSupported.toString() + sbUnsupported.toString();
        return result;
    }

    public void initialize() {
        String rawHtml = Util.getResourceFileContent("usage.html");
        String cmdsTag = "%%COMMANDS%%";
        String cmdsHtml = commandsHtml();
        String html = rawHtml.replaceAll(cmdsTag, cmdsHtml);
        webEngine = browser.getEngine();
        webEngine.loadContent(html);
        Scene scene = new Scene(browser);
        this.setScene(scene);
        this.setTitle("Scrumble Help");
        this.show();
    }
}
