package scrumble;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Info about each command available to the user (supported + unsupported).
 */
public class UserCmdTypeInfo {

    public final boolean isCommand;
    public final boolean isSupported;
    public final String briefDescription;
    public final String[] signature;    // Initial word in the command.
    public final ResultsTab tab;
    public final UserCmdType followOnAction;

    public UserCmdTypeInfo(
            boolean isCommand,
            boolean isSupported,
            String[] signature,
            String briefDescription,
            ResultsTab tab,
            UserCmdType followOnAction) {
        this.isCommand = isCommand;
        this.isSupported = isSupported;
        this.signature = signature;
        this.briefDescription = briefDescription;
        this.tab = tab;
        this.followOnAction = followOnAction;
    }

    // Note: Traverse in reverse, so "add task dependency" precedes "add task".
    // TODO: Improve efficiency of a string of calls to matching*
    public static Boolean matchesUserCmdSignature(
            ArrayList<String> userWords, String[] keywords
    ) {
        Boolean result = userWords.size() >= keywords.length
                && IntStream.range(0, keywords.length).allMatch(
                i
                -> userWords.get(i).toLowerCase().equals(keywords[i])
        );
        return result;
    }

    public static UserCmdType findUserCmdType(ArrayList<String> userWords) {
        for (Object entry : UserCmdTypeInfoMap.infoMap) {
            Map.Entry<UserCmdType, UserCmdTypeInfo> mapEntry = (Map.Entry<UserCmdType, UserCmdTypeInfo>) entry;
            UserCmdTypeInfo mapItem = mapEntry.getValue();
            if (mapItem.isSupported
                    && matchesUserCmdSignature(userWords, mapItem.signature)) {
                return mapEntry.getKey();
            }
        }
        return UserCmdType.None;
    }
}
