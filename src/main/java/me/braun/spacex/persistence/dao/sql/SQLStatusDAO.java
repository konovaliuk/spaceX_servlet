package me.braun.spacex.persistence.dao.sql;

import me.braun.spacex.connection.ConnectionPool;
import me.braun.spacex.persistence.dao.IStatusDAO;
import me.braun.spacex.persistence.entity.Status;
import me.braun.spacex.util.exception.StatusNotFoundException;
import me.braun.spacex.util.exception.UnImplementedException;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLStatusDAO implements IStatusDAO {
    private static final Logger log = LoggerFactory.getLogger(SQLAccountDAO.class);
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_STATUS = "status";
    private static SQLStatusDAO instance;
    public static IStatusDAO getInstance() {
        if (instance ==null){
            instance = new SQLStatusDAO();
        }
        return instance;
    }


    private Status getStatus(ResultSet resultSet) throws SQLException {
        short id = resultSet.getShort(COLUMN_ID);
        String status = resultSet.getString(COLUMN_STATUS);
        return new Status(id, status);
    }

    @Override
    public @NotNull List<Status> findAll() {
        @Language("MariaDB") String query = "select * from statuses;";
        List<Status> statusList = new ArrayList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Status status = getStatus(resultSet);
                statusList.add(status);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        //log.info(statusList.size() + "statuses were found");
        return statusList;
    }

    @Override
    public Optional<Status> findById(Short id) {
        @Language("MariaDB")String query = "select * from statuses where id= '" + id + "';";
        Status status = null;
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                status = getStatus(resultSet);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        assert status != null;
        //log.info(status.getStatus());
        return Optional.of(status);
    }

    @Override
    public Status save(Status status) throws IllegalStateException {
        Status newStatus = null;
        @Language("MariaDB") String query = "insert into spacex_agency_system.statuses(status) " +
                "values (?);";
        try {
            var connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareCall(query);
            preparedStatement.setString(1, status.getStatus());
            newStatus = findById(status.getId())
                    .orElseThrow(StatusNotFoundException::new);

            preparedStatement.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        return newStatus;
    }

    @Override
    public Status update(Status entity) throws UnImplementedException {
        return null;
    }

    @Override
    public void delete(Short id) {

        @Language("MariaDB") String query = "delete statuses from " +
                "spacex_agency_system.statuses " +
                "where statuses.id ='" + id + "';";
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
