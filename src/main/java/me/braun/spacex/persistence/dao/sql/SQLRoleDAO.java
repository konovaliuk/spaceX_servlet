package me.braun.spacex.persistence.dao.sql;

import me.braun.spacex.connection.ConnectionPool;
import me.braun.spacex.persistence.dao.IRoleDAO;
import me.braun.spacex.persistence.entity.Role;
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
import java.util.List;
import java.util.Optional;

public class SQLRoleDAO implements IRoleDAO {
    private static final Logger log = LoggerFactory.getLogger(SQLAccountDAO.class);
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ROLE = "role";
    private static SQLRoleDAO instance;
    public static IRoleDAO getInstance() {
        if (instance == null){
            instance = new SQLRoleDAO();
        }
        return instance;
    }

    private @NotNull Role getRole(@NotNull ResultSet resultSet) throws SQLException {
        byte id = resultSet.getByte(COLUMN_ID);
        String role = resultSet.getString(COLUMN_ROLE);
        return new Role(id, role);
    }

    @Override
    public @NotNull List<Role> findAll() {
        @Language("MariaDB") String query = "select * from roles;";
        List<Role> roleList = new ArrayList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Role userRole = getRole(resultSet);
                roleList.add(userRole);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        return roleList;
    }

    @Override
    public Optional<Role> findById(Byte id) {
        @Language("MariaDB") String query = "select * from roles where id= '" + id + "';";
        Role userRole = null;
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                userRole = getRole(resultSet);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }

        return Optional.of(userRole);
    }

    @Override
    public Role save(Role userRole) throws IllegalStateException {
        Role newRole = null;
        @Language("MariaDB")String query = "insert into spacex_agency_system.roles(role) " +
                "values (?);";
        try {
            var connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareCall(query);
            preparedStatement.setString(1, userRole.getRole());
            newRole = findById(userRole.getId())
                    .orElseThrow(RoleNotFoundException::new);

            preparedStatement.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        return newRole;
    }

    @Override
    public Role update(Role entity) throws UnImplementedException {
        return null;
    }

    @Override
    public void delete(Byte id) {

        @Language("MariaDB")String query = "delete roles from " +
                "spacex_agency_system.roles where roles.id ='" + id + "';";
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
