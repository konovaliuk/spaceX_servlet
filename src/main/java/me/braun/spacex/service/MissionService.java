package me.braun.spacex.service;

import me.braun.spacex.persistence.dao.*;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.persistence.entity.Mission;
import me.braun.spacex.persistence.entity.enams.EStatus;
import me.braun.spacex.util.exception.MissionNotFoundException;
import me.braun.spacex.util.exception.ServiceException;

import java.sql.Date;
import java.util.List;

public class MissionService {
    private final IMissionDAO missionDAO = DAOFactory.getMissionDAO();
    private final ISpacecraftDAO spacecraftDAO = DAOFactory.getSpaceCraftDAO();
    private final IStatusDAO statusDAO = DAOFactory.getStatusDAO();
    private final IAccountDAO accountDAO = DAOFactory.getAccountDAO();
    private final IServiceTypeDAO serviceTypeDAO = DAOFactory.getServiceTypeDAO();
    public Mission createMission(String title, String description, long customer,
                                 long spacecraft, byte serviceType,
                                 double payloadWeigh, Date date, int duration) {


        missionDAO.findByTitle(title).ifPresent(
                    mission -> {throw new ServiceException("This title already exists!");});

        return missionDAO.save(Mission.builder()
                .title(title)
                .description(description)
                .customer(accountDAO.findById(customer).get())
                .spaceCraft(spacecraftDAO.findById(spacecraft).get())
                .status(statusDAO.findById(EStatus.PROCESS.getId()).get())
                .serviceType(serviceTypeDAO.findById(serviceType).get())
                .curator(accountDAO.findById(8L).get())
                .payloadWeigh(payloadWeigh)
                .date(date)
                .duration(duration)
                .build());
    }

    public Mission editMission(Long id, String title, String description, long customer,
                               long spacecraft, byte serviceType,
                               double payloadWeigh,
                               Date date,
                               int duration) {
        missionDAO.findByTitle(title).orElseThrow(MissionNotFoundException::new);

        return missionDAO.update(Mission.builder()
                .id(id)
                .title(title)
                .description(description)
                .customer(accountDAO.findById(customer).get())
                .spaceCraft(spacecraftDAO.findById(spacecraft).get())
                .status(statusDAO.findById(EStatus.PROCESS.getId()).get())
                .serviceType(serviceTypeDAO.findById(serviceType).get())
                .curator(accountDAO.findById(8L).get())
                .payloadWeigh(payloadWeigh)
                .date(date)
                .duration(duration)
                .build());
    }

    public void deleteMission(Long id) {
        missionDAO.delete(id);
    }

    public List<Mission> getUserMissions(Account account, int pageNum) {
        int limit = 6;
        return missionDAO.findByCustomer(account.getId(),(pageNum - 1)*limit, limit );
    }
    public int getUserMissionsAmount(Account account){
       return missionDAO.customerMissionsAmount(account.getId());
    }
    public List<Mission> getMissions(int pageNum) {
        int limit = 6;
        return missionDAO.findAllPagination((pageNum - 1)*limit, limit);
    }

    public int getMissionsAmount() {
        return  missionDAO.findAll().size();
    }

    public Mission getMissionByTitle(Account account, String title, int pageNum) {
        int limit = 6;
        if (missionDAO.findByCustomer(account.getId(),(pageNum - 1)*limit, limit ).stream()
                .anyMatch(mission -> mission.getTitle().equals(title))) {
            return missionDAO.findByTitle(title).orElseThrow(MissionNotFoundException::new);
        }
        throw new ServiceException("This mission is unavailable for this user!");
    }

    public List<String> getSpacecraftTitles() {
        return DAOFactory.getSpaceCraftDAO().getTitles();
    }

    public List<String> getServiceTypes() {
        return DAOFactory.getServiceTypeDAO().findAllServices();
    }

}
