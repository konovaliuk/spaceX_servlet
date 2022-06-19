package me.braun.spacex.persistence.dao;

import me.braun.spacex.persistence.dao.sql.*;

public class DAOFactory {

    public static IAccountDAO getAccountDAO() {
        return SQLAccountDAO.getInstance();
    }

    public static IMissionDAO getMissionDAO() {
        return SQLMissionDAO.getInstance();
    }

    public static IRoleDAO getRoleDAO() {
        return SQLRoleDAO.getInstance();
    }

    public static IServiceTypeDAO getServiceTypeDAO() {
        return SQLServiceTypeDAO.getInstance();
    }

    public static ISpacecraftDAO getSpaceCraftDAO() {
        return SQLSpacecraftDAO.getInstance();
    }

    public static IStatusDAO getStatusDAO() {
        return SQLStatusDAO.getInstance();
    }

}
