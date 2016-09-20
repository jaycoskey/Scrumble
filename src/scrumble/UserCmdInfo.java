package scrumble;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Info about the user-entered command that is needed for execution.
 */
public class UserCmdInfo {

    final public String inputText;
    final public Boolean isValid;
    final public ResultsTab tab;
    final public String validationErrorMessage;
    final public UserCmdType primaryAction;
    final public String primaryActionBriefDescription;
    final public String primaryActionSql;
    final public UserCmdType followOnAction;

    public UserCmdInfo(
            String inputText, Boolean isValid, ResultsTab tab, String validationErrorMessage, UserCmdType primaryAction, String primaryActionBriefDescription, String primaryActionSql, UserCmdType followOnAction //  None => Display results
    ) {
        this.inputText = inputText;
        this.isValid = isValid;
        this.tab = tab;
        this.validationErrorMessage = validationErrorMessage;

        this.primaryAction = primaryAction;
        this.primaryActionBriefDescription = primaryActionBriefDescription;
        this.primaryActionSql = primaryActionSql;

        this.followOnAction = followOnAction;
    }

    static UserCmdInfo fromInputText(String inputText) {
        ArrayList<String> userWords = new ArrayList<>();
        userWords.addAll(Arrays.asList(inputText.split(" ")));
        UserCmdType cmdType = UserCmdTypeInfo.findUserCmdType(userWords);
        UserCmdInfo result;
        if (cmdType == UserCmdType.None) {
            result = new UserCmdInfo(
                    inputText // userText
                    , false // isValid
                    , ResultsTab.None // tab
                    , "Invalid command.  See Help for more info." // validationErrorMessage
                    , cmdType // primaryAction
                    , "invalid command" // primaryActionBriefDescription
                    , "invalid command" // primaryActionSql
                    , UserCmdType.None // followOnAction
            );
        } else {
            String sql = UserCmdInfo.getSqlCmd(cmdType, userWords);
            result = new UserCmdInfo(
                    inputText // userText
                    , true // isValid
                    , UserCmdTypeInfoMap.infoMap.getInfo(cmdType).tab // tab
                    , null // validationErrorMessage
                    , cmdType // primaryAction 
                    , "TODO" //  primaryActionBriefDescription
                    , sql //  primaryActionSql
                    , UserCmdTypeInfoMap.infoMap.getInfo(cmdType).followOnAction
            );
        }
        return result;
    }

    public static String getSqlCmd(UserCmdType cmdType, ArrayList<String> userWords) {
        String sql;
        String unsupportedCmd = "Unsupported command";

        switch (cmdType) {
            case AddEffort:
                sql = unsupportedCmd;
                break;
            case AddFeatureDependency:
                sql = unsupportedCmd;
                break;
            case AddFeature:
                sql = unsupportedCmd;
                break;
            case AddOwner:
                sql = unsupportedCmd;
                break;

            case AddStatus:
                sql = unsupportedCmd;
                break;
            case AddTask:
                sql = unsupportedCmd;
                break;
            case AddTaskDependency:
                sql = unsupportedCmd;
                break;
            case AddError:
                sql = unsupportedCmd;
                break;

            case DeleteEffort:
                sql = unsupportedCmd;
                break;
            case DeleteFeatureDependency:
                sql = unsupportedCmd;
                break;
            case DeleteFeature:
                sql = unsupportedCmd;
                break;
            case DeleteOwner:
                sql = unsupportedCmd;
                break;
            case DeleteStatus:
                sql = unsupportedCmd;
                break;
            case DeleteTaskDependency:
                sql = unsupportedCmd;
                break;
            case DeleteTask:
                sql = unsupportedCmd;
                break;
            case DeleteError:
                sql = unsupportedCmd;
                break;

            case EditFeature:
                sql = unsupportedCmd;
                break;
            case EditOwner:
                sql = unsupportedCmd;
                break;
            case EditStatus:
                sql = unsupportedCmd;
                break;
            case EditTask:
                sql = unsupportedCmd;
                break;
            case EditError:
                sql = unsupportedCmd;
                break;

            case ListEfforts:
                sql = unsupportedCmd;
                break;
            case ListFeatureDependencies:
                sql = unsupportedCmd;
                break;
            case ListFeatures:
                sql = Util.getResourceFileContent("userCmd_ListFeatures.sql");
                break;
            case ListOwners:
                sql = Util.getResourceFileContent("userCmd_ListOwners.sql");
                break;
            case ListStatuses:
                sql = Util.getResourceFileContent("userCmd_ListStatuses.sql");
                break;
            case ListTaskDependencies:
                sql = unsupportedCmd;
                break;
            case ListTasks:
                sql = Util.getResourceFileContent("userCmd_ListTasks.sql");
                break;
            case ListError:
                sql = unsupportedCmd;
                break;
            default:
                sql = unsupportedCmd;
                break;
        }
        return sql;
    }

    public static void removeN(ArrayList<String> strs, int n) {
        for (int i = 0; i < n; i++) {
            strs.remove(0);
        }
    }
}
