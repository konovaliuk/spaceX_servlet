package me.braun.spacex.persistence.dao.sql;

import lombok.NoArgsConstructor;
import me.braun.spacex.connection.ConnectionPool;
import me.braun.spacex.persistence.dao.DAOFactory;
import me.braun.spacex.persistence.dao.IAccountDAO;
import me.braun.spacex.persistence.dao.IRoleDAO;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.persistence.entity.Role;
import me.braun.spacex.util.exception.AccountNotFoundException;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class SQLAccountDAO implements IAccountDAO {
    private static SQLAccountDAO instance;
    private final IRoleDAO roleDAO = DAOFactory.getRoleDAO();
    private static final Logger log = LoggerFactory.getLogger(SQLAccountDAO.class);
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PASSWORD_HASH = "password_hash";
    private static final String COLUMN_ROLE = "role_id";

    private @NotNull Account getAccount(@NotNull ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(COLUMN_ID);
        String firstName = resultSet.getString(COLUMN_FIRST_NAME);
        String lastname = resultSet.getString(COLUMN_LAST_NAME);
        String email = resultSet.getString(COLUMN_EMAIL);
        String phone = resultSet.getString(COLUMN_PHONE);
        String passwordHash = resultSet.getString(COLUMN_PASSWORD_HASH);
        Role role = roleDAO.findById(resultSet.getByte(COLUMN_ROLE)).get();
        return new Account(id, firstName, lastname, email, phone, role, passwordHash);
    }

    public static IAccountDAO getInstance() {
        if (instance == null) {instance = new SQLAccountDAO();}
        return instance;
    }

    @Override
    public @NotNull List<Account> findAll() {
        @Language("MariaDB") String query = "select * from accounts;";
        List<Account> accountList = new ArrayList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet =statement.executeQuery(query);
            while (resultSet.next()) {
                Account account = getAccount(resultSet);
                accountList.add(account);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        //log.info(accountList.size() + " users were found");
        return accountList;
    }
    public @NotNull List<Account> findAllPagination(int offset, int limit) {
        @Language("MariaDB")String query = "select * from accounts limit " + limit + " offset " + offset + ";";
        List<Account> accountList = new LinkedList<>();
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Account account = getAccount(resultSet);
                accountList.add(account);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        //log.info(accountList.size() + " users were found");
        return accountList;
    }

    @Override
    public Optional<Account> findById(Long id) {
        @Language("MariaDB")  String query = "select * from accounts where id= '" + id + "';";
        Account account = null;
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                account = getAccount(resultSet);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        assert account != null;
//        log.info("user " + account.getFirstName()
//                + " " + account.getLastName() + " was found");
        return Optional.of(account);
    }

    @Override
    public Account save(@NotNull Account account) throws IllegalStateException {
        Account newAccount = null;
        @Language("MariaDB") String query = "call insert_user(?, ?, ?, ?, ?, ?)";
        try {

            var connection = ConnectionPool.getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, account.getFirstName());
            callableStatement.setString(2, account.getLastName());
            String email = account.getEmail();
            callableStatement.setString(3, email);
            callableStatement.setString(4, account.getPhone());
            callableStatement.setByte(5, account.getRole().getId());
            callableStatement.setString(6, account.getPasswordHash());
            callableStatement.execute();
            newAccount = findByEmail(email).orElseThrow(AccountNotFoundException::new);

            callableStatement.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        assert newAccount != null;
        log.info("created account\nid: " + newAccount.getId()
                + "\nfirstname: " + newAccount.getFirstName()
                + "\nlastname: " + newAccount.getLastName()
                + "\nemail: " + newAccount.getEmail()
                + "\nphone: " + newAccount.getPhone()
                + "\npassword hash: " + newAccount.getPasswordHash()
                + "\nrole id: " + newAccount.getRole());
        return newAccount;
    }

    public Optional<Account> findByEmail(String email) {
        @Language("MariaDB") String query = "select * from accounts where email = '" + email + "';";
        Account account = null;
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                account = getAccount(resultSet);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        return Optional.ofNullable(account);
    }

    public Optional<Account> findByPhone(String phone) {
        @Language("MariaDB")String query = "select * from accounts where phone = '" + phone + "';";
        Account account = null;
        try {
            var connection = ConnectionPool.getConnection();
            var statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                account = getAccount(resultSet);
            }
            resultSet.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        }
        return Optional.ofNullable(account);
    }

    @Override
    public Account update(@NotNull Account account) {
        @Language("MariaDB") String query = "call update_user(?, ?, ?, ?, ?, ?, ?)";
        try {
            var connection = ConnectionPool.getConnection();
            assert account.getId() != null;
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setLong(1, account.getId());
            callableStatement.setString(2, account.getFirstName());
            callableStatement.setString(3, account.getLastName());
            callableStatement.setString(4, account.getEmail());
            callableStatement.setString(5, account.getPhone());
            callableStatement.setByte(6, account.getRole().getId());
            callableStatement.setString(7, account.getPasswordHash());
            callableStatement.execute();
            callableStatement.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQL Error!", e);
        } catch (AssertionError e) {
            log.error("Account id must not be null!", e);
        }
        log.info("updated account\nid: " + account.getId()
                + "\nfirstname: " + account.getFirstName()
                + "\nlastname: " + account.getLastName()
                + "\nemail: " + account.getEmail()
                + "\nphone: " + account.getPhone()
                + "\nrole id: " + account.getRole());
        return account;
    }

    @Override
    public void delete(Long id) {
        @Language("MariaDB") String query = "call delete_user('" + id + "')";
        log.info("user deleted");
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
