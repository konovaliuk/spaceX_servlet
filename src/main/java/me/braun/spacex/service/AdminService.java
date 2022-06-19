package me.braun.spacex.service;

import me.braun.spacex.persistence.dao.*;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.persistence.entity.Mission;
import me.braun.spacex.persistence.entity.enams.EStatus;
import me.braun.spacex.util.exception.AccountNotFoundException;
import me.braun.spacex.util.exception.MissionNotFoundException;
import me.braun.spacex.util.exception.RoleNotFoundException;

import java.sql.Date;
import java.util.List;

public class AdminService {

    private final IMissionDAO missionDAO = DAOFactory.getMissionDAO();
    private final IAccountDAO accountDAO = DAOFactory.getAccountDAO();
    private final IRoleDAO roleDAO = DAOFactory.getRoleDAO();
    private final IStatusDAO statusDAO = DAOFactory.getStatusDAO();
    private final ISpacecraftDAO spacecraftDAO = DAOFactory.getSpaceCraftDAO();


    public Mission editMissionStatus(long missionId, long spacecraft, short missionStatus) {
        missionDAO.findById(missionId).orElseThrow(MissionNotFoundException::new);
        Mission mission = missionDAO.findById(missionId).orElseThrow(MissionNotFoundException::new);
        mission.setSpaceCraft(spacecraftDAO.findById(spacecraft).get());
        mission.setStatus(statusDAO.findById(missionStatus).get());
        return missionDAO.update(mission);
    }
    public Account editUserRole(String email, byte roleId){
        Account user = accountDAO.findByEmail(email).orElseThrow(AccountNotFoundException::new);
        user.setRole(roleDAO.findById(roleId).orElseThrow(RoleNotFoundException::new));
        accountDAO.update(user);
        return user;
    }

    public Account getUserByEmail(String email) throws AccountNotFoundException {
        return accountDAO.findByEmail(email).orElseThrow(AccountNotFoundException::new);
    }

    public Account getUserByPhone(String phone) throws AccountNotFoundException {
        return accountDAO.findByPhone(phone).orElseThrow(AccountNotFoundException::new);
    }

    public int getUsersAmount(){
        return accountDAO.findAll().size();
    }

    public List<Account> getUsers(int pageNum) {
        int limit = 4;
        return accountDAO.findAllPagination((pageNum - 1)*limit, limit);
    }

    public List<Mission> getUserMissions(Account account, int pageNum) {
        int limit = 6;
        return missionDAO.findByCustomer(account.getId(),(pageNum - 1)*limit, limit );
    }

    public Mission getMissionByTitle(String title) throws MissionNotFoundException{
        return missionDAO.findByTitle(title).orElseThrow(MissionNotFoundException::new);
    }

    public List<Mission> getMissions() {
        return missionDAO.findAll();
    }
}
