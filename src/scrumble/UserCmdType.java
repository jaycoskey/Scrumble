package scrumble;

/**
 * Enumeration of the commands available to the user (supported + unsupported).
 */
public enum UserCmdType {
    None,
    AddEffort, AddFeatureDependency, AddFeature, AddMemberTask, AddOwner,
        AddStatus, AddTaskDependency, AddTask, AddError,
    DeleteEffort, DeleteFeatureDependency, DeleteFeature, DeleteMemberTask, DeleteOwner,
        DeleteStatus, DeleteTaskDependency, DeleteTask, DeleteError,
    EditFeature, EditOwner, EditStatus, EditTask, EditError,
    ListEfforts, ListFeatureDependencies, ListFeatures, ListMemberTasks, ListOwners,
        ListStatuses, ListTaskDependencies, ListTasks, ListError
}
