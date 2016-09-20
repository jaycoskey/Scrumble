package scrumble;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Info about the set of commands available to the user (supported +
 * unsupported).
 */
public class UserCmdTypeInfoMap implements Iterable {

    public static UserCmdTypeInfoMap infoMap = new UserCmdTypeInfoMap();

    private final LinkedHashMap<UserCmdType, UserCmdTypeInfo> map;

    public UserCmdTypeInfoMap() {
        map = new LinkedHashMap<UserCmdType, UserCmdTypeInfo>();

        // Note: Order is significant here.
        //   (1) FooError must follow all other Foo subcommands,
        //       so that the bare command word at the end of the list catches
        //       unrecognized subcommands.
        //   (2) ["foo", "task", "dependency"] must follow ["foo", "task"].
        // ===== ADD =====
        addInfo(UserCmdType.AddEffort,
                true, false,
                new String[]{"add", "effort"},
                "TODO: Usage for the 'add effort' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.AddFeatureDependency,
                true, false,
                new String[]{"add", "feature", "dependency"},
                "TODO: Usage for the 'add feature dependency' command",
                ResultsTab.Features);
        addInfo(UserCmdType.AddFeature,
                true, false,
                new String[]{"add", "feature"},
                "TODO: Usage for the 'add feature' command",
                ResultsTab.Features);
        addInfo(UserCmdType.AddOwner,
                true, false,
                new String[]{"add", "owner"},
                "TODO: Usage for the 'add owner' command",
                ResultsTab.Owners);
        addInfo(UserCmdType.AddStatus,
                true, false,
                new String[]{"add", "status"},
                "TODO: Usage for the 'add status' command",
                ResultsTab.Statuses);
        addInfo(UserCmdType.AddTaskDependency,
                true, false,
                new String[]{"add", "task", "dependency"},
                "TODO: Usage for the 'add task dependency' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.AddTask,
                true, false,
                new String[]{"add", "task"},
                "TODO: Usage for the 'add task' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.AddError,
                false, false,
                new String[]{"add"},
                "add",
                ResultsTab.None);

        // ===== DELETE =====
        addInfo(UserCmdType.DeleteEffort,
                true, false,
                new String[]{"delete", "effort"},
                "TODO: Usage for the 'delete effort' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.DeleteFeatureDependency,
                true, false,
                new String[]{"delete", "feature", "dependency"},
                "TODO: Usage for the 'delete feature dependency' command",
                ResultsTab.Features);
        addInfo(UserCmdType.DeleteFeature,
                true, false,
                new String[]{"delete", "feature"},
                "TODO: Usage for the 'delete feature' command",
                ResultsTab.Features);
        addInfo(UserCmdType.DeleteOwner,
                true, false,
                new String[]{"delete", "owner"},
                "TODO: Usage for the 'delete owner' command",
                ResultsTab.Owners);
        addInfo(UserCmdType.DeleteStatus,
                true, false,
                new String[]{"delete", "status"},
                "TODO: Usage for the 'delete status' command",
                ResultsTab.Statuses);
        addInfo(UserCmdType.DeleteTaskDependency,
                true, false,
                new String[]{"delete", "task", "dependency"},
                "TODO: Usage for the 'delete task dependency' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.DeleteTask,
                true, false,
                new String[]{"delete", "task"},
                "TODO: Usage for the 'delete task' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.DeleteError,
                false, false,
                new String[]{"delete"},
                "delete",
                ResultsTab.None);

        // ===== EDIT =====
        addInfo(UserCmdType.EditFeature,
                true, false,
                new String[]{"edit", "feature"},
                "TODO: Usage for the 'edit feature' command",
                ResultsTab.Features);
        addInfo(UserCmdType.EditOwner,
                true, false,
                new String[]{"edit", "owner"},
                "TODO: Usage for the 'edit owner' command",
                ResultsTab.Owners);
        addInfo(UserCmdType.EditStatus,
                true, false,
                new String[]{"edit", "status"},
                "TODO: Usage for the 'edit status' command",
                ResultsTab.Statuses);
        addInfo(UserCmdType.EditTask,
                true, false,
                new String[]{"edit", "task"},
                "TODO: Usage for the 'edit task' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.EditError,
                false, false,
                new String[]{"edit"},
                "edit",
                ResultsTab.None);

        // ===== LIST =====
        addInfo(UserCmdType.ListEfforts,
                true, false,
                new String[]{"list", "efforts"},
                "TODO: Usage for the 'list efforts' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.ListFeatureDependencies,
                true, false,
                new String[]{"list", "feature", "dependencies"},
                "TODO: Usage for the 'list feature dependencies' command",
                ResultsTab.Features);
        addInfo(UserCmdType.ListFeatures,
                true, true,
                new String[]{"list", "features"},
                "TODO: Usage for the 'list features' command",
                ResultsTab.Features);
        addInfo(UserCmdType.ListOwners,
                true, true,
                new String[]{"list", "owners"},
                "TODO: Usage for the 'list owners' command",
                ResultsTab.Owners);
        addInfo(UserCmdType.ListStatuses,
                true, true,
                new String[]{"list", "statuses"},
                "TODO: Usage for the 'list statuses' command",
                ResultsTab.Statuses);
        addInfo(UserCmdType.ListTaskDependencies,
                true, false,
                new String[]{"list", "task", "depedencies"},
                "TODO: Usage for the 'list task dependencies' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.ListTasks,
                true, true,
                new String[]{"list", "tasks"},
                "TODO: Usage for the 'list tasks' command",
                ResultsTab.Tasks);
        addInfo(UserCmdType.ListError,
                false, false,
                new String[]{"list"},
                "list",
                ResultsTab.None);
    }

    private void addInfo(
            UserCmdType type,
            boolean isCommand,
            boolean isSupported,
            String[] signature,
            String briefDescription,
            ResultsTab tab) {
        addInfo(type, isCommand, isSupported, signature, briefDescription, tab, UserCmdType.None);
    }

    private void addInfo(
            UserCmdType type,
            boolean isCommand,
            boolean isSupported,
            String[] signature,
            String briefDescription,
            ResultsTab tab,
            UserCmdType followOnAction) {
        map.put(
                type,
                new UserCmdTypeInfo(
                        isCommand, isSupported, signature, briefDescription, tab, followOnAction
                )
        );
    }

    public UserCmdTypeInfo getInfo(UserCmdType type) {
        return map.get(type);
    }

    @Override
    public Iterator<Map.Entry<UserCmdType, UserCmdTypeInfo>> iterator() {
        return map.entrySet().iterator();
    }

    public boolean hasNext(Iterator<Map.Entry<UserCmdType, UserCmdTypeInfo>> it) {
        return it.hasNext();
    }

    public Map.Entry<UserCmdType, UserCmdTypeInfo> next(Iterator<Map.Entry<UserCmdType, UserCmdTypeInfo>> it) {
        return it.next();
    }
}
