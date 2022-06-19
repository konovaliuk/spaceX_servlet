package me.braun.spacex.persistence.dao.sql;


import me.braun.spacex.connection.ConnectionPool;
import me.braun.spacex.persistence.dao.*;
import me.braun.spacex.persistence.entity.*;
import me.braun.spacex.util.exception.MissionNotFoundException;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SQLMissionDAO implements IMissionDAO {
    private final ISpacecraftDAO spacecraftDAO = DAOFactory.getSpaceCraftDAO();
    private final IStatusDAO statusDAO = DAOFactory.getStatusDAO();
    private final IAccountDAO accountDAO = DAOFactory.getAccountDAO();
    private final IServiceTypeDAO serviceTypeDAO = DAOFactory.getServiceTypeDAO();

    private static final Logger log = LoggerFactory.getLogger(SQLMissionDAO.class);
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CUSTOMER_ID = "cust_id";
    private static final String COLUMN_CRAFT_ID = "craft_id";
    private static final String COLUMN_STATUS_ID = "status_id";
    private static final String COLUMN_CURATOR_ID = "curator_id";
    private static final String COLUMN_PAYLOAD_WEIGHT = "payload_weight";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_MISSION_PRICE = "mision_price";
    private static final String COLUMN_SERVICE = "service_type_id";
    private static final String COLUMN_SERVICE_PRICE = "service_price";
    private static SQLMissionDAO instance;
    public static IMissionDAO getInstance() {
        if (instance == null){
            instance = new SQLMissionDAO();
        }
        return instance;
    }

    private @NotNull Mission getMission(@NotNull ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(COLUMN_ID);
        String name = resultSet.getString(COLUMN_NAME);
        String description = resultSet.getString(COLUMN_DESCRIPTION);
        Account customer = accountDAO.findById(resultSet.getLong(COLUMN_CUSTOMER_ID)).get();
        Spacecraft spacecraft = spacecraftDAO.findById(resultSet.getLong(COLUMN_CRAFT_ID)).get();
        Status status = statusDAO.findById(resultSet.getShort(COLUMN_STATUS_ID)).get();
        ServiceType service = serviceTypeDAO.findById(resultSet.getByte(COLUMN_SERVICE)).get();
        Account curator = accountDAO.findById(resultSet.getLong(COLUMN_CURATOR_ID)).get();
        float payload = resultSet.getFloat(COLUMN_PAYLOAD_WEIGHT);
        Date date = resultSet.getDate(COLUMN_DATE);
        int duration = resultSet.getInt(COLUMN_DURATION);
        long missionPrice = resultSet.getLong(COLUMN_MISSION_PRICE);
        long servicePrice = resultSet.getLong(COLUMN_SERVICE_PRICE);
        return new Mission(id, name, description, customer, spacecraft, status, service,
                curator, payload, date, duration, missionPrice, servicePrice);

    }


    @Override
    public @NotNull List<Mission> findAll() {
        @Language("MariaDB")String query = "select * from misions;";
        List<Mission> missionList = new ArrayList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Mission mission = getMission(resultSet);
                missionList.add(mission);

            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        log.info(missionList.size() + " missions were found");
        return missionList;
    }
    public @NotNull List<Mission> findAllPagination(int offset, int limit) {
        @Language("MariaDB")String query = "select * from misions order by date desc limit " + limit + " offset " + offset + ";";
        List<Mission> missionList = new LinkedList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Mission mission = getMission(resultSet);
                missionList.add(mission);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        log.info(missionList.size() + " missions were found");
        return missionList;
    }
    public int customerMissionsAmount(Long custId){
        @Language("MariaDB")String query = "select * from misions where cust_id = "+ custId + " ;";
        List<Mission> missionList = new LinkedList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Mission mission = getMission(resultSet);
                missionList.add(mission);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        }catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        log.info(missionList.size() + " missions were found (amount)");
        return missionList.size();
    }

    public @NotNull List<Mission> findByCustomer(Long custId, int offset, int limit ){
        @Language("MariaDB")String query = "select * from misions where cust_id = " +
                custId + " order by date desc limit " + limit + " offset " + offset + ";";
        List<Mission> missionList = new LinkedList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Mission mission = getMission(resultSet);
                missionList.add(mission);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        log.info(missionList.size() + " missions were found");
        return missionList;

    }

    @Override
    public Optional<Mission> findById(Long id) {
        @Language("MariaDB")String query = "select * from misions where id= '" + id + "';";
        Mission mission = null;
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                mission = getMission(resultSet);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        assert mission != null;
        //log.info(mission.getTitle() + " was found\ndescription: " + mission.getDescription());
        return Optional.of(mission);
    }


    @Override
    public Optional<Mission> findByTitle(String title) {
        @Language("MariaDB")String query = "select * from misions where name= '" + title + "';";
        Mission mission = null;
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                mission = getMission(resultSet);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        //assert mission != null;
        //log.info(mission.getTitle() + " was found\ndescription: " + mission.getDescription());
        return Optional.ofNullable(mission);
    }

    @Override
    public Mission save(@NotNull Mission mission) throws IllegalStateException {
        Mission newMission = null;
        @Language("MariaDB")String query = "call insert_mission(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            var connection = ConnectionPool.getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, mission.getTitle());
            callableStatement.setString(2, mission.getDescription());
            callableStatement.setLong(3, mission.getCustomer().getId());
            callableStatement.setLong(4, mission.getSpaceCraft().getId());
            callableStatement.setShort(5, mission.getStatus().getId());
            callableStatement.setByte(6, mission.getServiceType().getId());
            callableStatement.setLong(7, mission.getCurator().getId());
            callableStatement.setDouble(8, mission.getPayloadWeigh());
            callableStatement.setDate(9, mission.getDate());
            callableStatement.setInt(10, mission.getDuration());

            callableStatement.execute();
            newMission = findByTitle(mission.getTitle()).orElseThrow(MissionNotFoundException::new);

            callableStatement.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        assert newMission != null;
        log.info("mission created\nid: " + newMission.getId()
                + "\ntitle: " + newMission.getTitle()
                + "\ndescription: " + newMission.getDescription()
                + "\ncustomer id: " + newMission.getCustomer().getFullName()
                + "\ncurator id: " + newMission.getCurator().getFullName()
                + "\nstatus id: " + newMission.getStatus().getStatus()
                + "\nservice id: " + newMission.getServiceType().getService()
                + "\nspacecraft id: " + newMission.getSpaceCraft().getCraftTitle()
                + "\npayload weight: " + newMission.getPayloadWeigh()
                + "\nmission start day: " + newMission.getDate()
                + "\nduration: " + newMission.getDuration()
                + "\nmission price: " + newMission.getMissionPrice());
        return newMission;
    }


    @Override
    public @NotNull Mission update(@NotNull Mission mission) {
        @Language("MariaDB") String query = "call update_mission(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            var connection = ConnectionPool.getConnection();
            assert mission.getId() != null;
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setLong(1, mission.getId());
            callableStatement.setString(2, mission.getTitle());
            callableStatement.setString(3, mission.getDescription());
            callableStatement.setLong(4, mission.getCustomer().getId());
            callableStatement.setLong(5, mission.getSpaceCraft().getId());
            callableStatement.setShort(6, mission.getStatus().getId());
            callableStatement.setByte(7, mission.getServiceType().getId());
            callableStatement.setLong(8, mission.getCurator().getId());
            callableStatement.setDouble(9, mission.getPayloadWeigh());
            callableStatement.setDate(10, mission.getDate());
            callableStatement.setInt(11, mission.getDuration());

            callableStatement.execute();
            callableStatement.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        } catch (AssertionError e) {
            log.error("Mission id must not be null!", e);
        }

        log.info("mission updated\nid: " + mission.getId()
                + "\ntitle: " + mission.getTitle()
                + "\ndescription: " + mission.getDescription()
                + "\ncustomer id: " + mission.getCustomer().getFullName()
                + "\ncurator id: " + mission.getCurator().getFullName()
                + "\nstatus id: " + mission.getStatus().getStatus()
                + "\nservice id: " + mission.getServiceType().getService()
                + "\nspacecraft id: " + mission.getSpaceCraft().getCraftTitle()
                + "\npayload weight: " + mission.getPayloadWeigh()
                + "\nmission start day: " + mission.getDate()
                + "\nduration: " + mission.getDuration()
                + "\nmission price: " + mission.getMissionPrice());
        return mission;
    }

    @Override
    public void delete(Long id) {
        @Language("MariaDB") String query = "call delete_mission(" + id + ")";
        log.info("mission deleted");
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            statement.execute(query);
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }

    }
}
