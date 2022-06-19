package me.braun.spacex.persistence.dao.sql;

import me.braun.spacex.connection.ConnectionPool;
import me.braun.spacex.persistence.dao.IServiceTypeDAO;
import me.braun.spacex.persistence.entity.ServiceType;
import me.braun.spacex.util.exception.RoleNotFoundException;
import me.braun.spacex.util.exception.UnImplementedException;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SQLServiceTypeDAO implements IServiceTypeDAO {
    private static final Logger log = LoggerFactory.getLogger(SQLAccountDAO.class);
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SERVICE = "service";
    private static SQLServiceTypeDAO instance;

    public static IServiceTypeDAO getInstance() {
        if (instance == null) {
            instance = new SQLServiceTypeDAO();
        }
        return instance;
    }

    private ServiceType getService(ResultSet resultSet) throws SQLException {
        byte id = resultSet.getByte(COLUMN_ID);
        String service = resultSet.getString(COLUMN_SERVICE);
        return new ServiceType(id, service);
    }

    @Override
    public @NotNull List<String> findAllServices() {
        @Language("MariaDB")String query = "select service from servicetypes;";
        List<String> serviceList = new LinkedList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                serviceList.add(resultSet.getString(1));
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        //log.info(serviceList.size() + "services were found");
        return serviceList;
    }

    @Override
    public @NotNull List<ServiceType> findAll() {
        @Language("MariaDB")String query = "select * from servicetypes;";
        List<ServiceType> serviceList = new ArrayList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ServiceType serviceType = getService(resultSet);
                serviceList.add(serviceType);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
       // log.info(serviceList.size() + "services were found");
        return serviceList;
    }

    @Override
    public Optional<ServiceType> findById(Byte id) {
        @Language("MariaDB") String query = "select * from servicetypes where id= '" + id + "';";
        ServiceType serviceType = null;
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                serviceType = getService(resultSet);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        assert serviceType != null;
        return Optional.of(serviceType);
    }

    @Override
    public ServiceType save(ServiceType serviceType) throws IllegalStateException {
        ServiceType newService = null;
        @Language("MariaDB") String query = "insert into spacex_agency_system.servicetypes(service) " +
                "values (?);";
        try {
            var connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareCall(query);
            preparedStatement.setString(1, serviceType.getService());
            newService = findById(serviceType.getId())
                    .orElseThrow(RoleNotFoundException::new);

            preparedStatement.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        return newService;
    }

    @Override
    public ServiceType update(ServiceType entity) throws UnImplementedException {
        return null;
    }

    @Override
    public void delete(Byte id) {
        @Language("MariaDB")String query = "delete servicetypes from " +
                "spacex_agency_system.servicetypes " +
                "where servicetypes.id ='" + id + "';";
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
