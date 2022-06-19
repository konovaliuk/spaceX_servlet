package me.braun.spacex.command;

import me.braun.spacex.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    public static final String LOGIN_COMMAND = "signIn";
    public static final String REG_COMMAND = "signUp";
    public static final String LOGOUT_COMMAND = "signOut";
    public static final String EDIT_USER_COMMAND = "userEdit";
    public static final String DELETE_USER_COMMAND = "deleteAccount";
    public static final String GET_USERS_COMMAND = "users";
    public static final String USER_MISSIONS_COMMAND = "userMissions";
    public static final String CREATE_MISSION_COMMAND = "missionCreate";
    public static final String EDIT_MISSION_COMMAND = "missionEdit";
    public static final String SET_STATUS_COMMAND = "missionStatus";
    public static final String SET_ROLE_COMMAND = "userRole";
    public static final String GET_MISSION_COMMAND = "getMission";
    public static final String GET_MISSIONS_COMMAND = "getMissions";
    public static final String CHANGE_PASSWORD_COMMAND = "changePassword";
    public static final String LOCALIZATION_COMMAND = "lang";

    private static final Map<String, ICommand> commands = new HashMap<>();

    static {
        commands.put(LOGIN_COMMAND, new LoginCommand());
        commands.put(REG_COMMAND, new RegisterCommand());
        commands.put(LOGOUT_COMMAND, new LogOutCommand());
        commands.put(USER_MISSIONS_COMMAND, new GetUserMissionsCommand());
        commands.put(GET_USERS_COMMAND, new GetAccountsCommand());
        commands.put(CREATE_MISSION_COMMAND, new CreateMissionCommand());
        commands.put(LOCALIZATION_COMMAND, new LocalizationCommand());
        commands.put(EDIT_USER_COMMAND, new EditAccountCommand());
        commands.put(DELETE_USER_COMMAND, new DeleteProfileCommand());
        commands.put(EDIT_MISSION_COMMAND, new EditMissionCommand());
        commands.put(SET_STATUS_COMMAND, new SetMissionStatus());
        commands.put(SET_ROLE_COMMAND, new SetRoleCommand());
        commands.put(GET_MISSION_COMMAND, new GetMissionCommand());
        commands.put(GET_MISSIONS_COMMAND, new GetAllMissionsCommand());
        commands.put(CHANGE_PASSWORD_COMMAND, new ChangePasswordCommand());
    }

    public static ICommand getCommand(String commandName) {
        if (!commands.containsKey(commandName))
            return new NoCommand();
        return commands.get(commandName);
    }

}