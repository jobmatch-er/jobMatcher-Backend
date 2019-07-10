package de.wecodeit.jobmatcher;

import com.mysql.cj.jdbc.MysqlDataSource;
import de.jakobniklas.util.Exceptions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database
{
    private Connection connection;

    public Database(String username, String password, String ip)
    {
        try
        {
            MysqlDataSource source = new MysqlDataSource();
            source.setServerName(ip);
            source.setUser(username);
            source.setPassword(password);
            source.setPortNumber(3306);
            source.setNoDatetimeStringSync(true);
            source.setServerTimezone("GMT+1");
            connection = source.getConnection();
            execute("USE Praktikumsql1");

        }
        catch(SQLException e)
        {
            Exceptions.handle(e);
        }
    }

    public void disconnect()
    {
        try
        {
            connection.close();
            connection = null;
        }
        catch(SQLException e)
        {
            Exceptions.handle(e);
        }
    }

    public void execute(String command)
    {
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(command);
            statement.close();
        }
        catch(SQLException e)
        {
            Exceptions.handle(e);
        }
    }

    public ResultSet executeQuery(String command)
    {
        try
        {
            Statement statement = connection.createStatement();

            return statement.executeQuery(command);
        }
        catch(SQLException e)
        {
            Exceptions.handle(e);
        }

        return null;
    }

    public void executeArray(ArrayList<String> commands)
    {
        for(String command : commands)
        {
            execute(command);
        }
    }

    public void switchDatabase(String name)
    {
        try
        {
            connection.setCatalog(name);
        }
        catch(SQLException e)
        {
            Exceptions.handle(e);
        }
    }
}
