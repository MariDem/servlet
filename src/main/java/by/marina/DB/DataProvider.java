package by.marina.DB;

import by.marina.exception.DeleteException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/my_customer";
    static final String USER = "root";
    static final String PASSWORD = "root";

    private final List<DemoEntity> entities = new ArrayList<>();
    PreparedStatement preparedStatement = null;
    Connection connection;
    Statement statement;
    String sql;

    private static DemoEntity demoList;

    public DataProvider() {
        {
            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
                statement = connection.createStatement();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List<DemoEntity> getAllEntities() throws SQLException {
        try {
            sql = "SELECT * FROM customer";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            entities.clear();
            while (resultSet.next()) {
                demoList = new DemoEntity();
                demoList.setId(resultSet.getInt("id"));
                demoList.setName(resultSet.getString("name"));
                demoList.setAddress(resultSet.getString("address"));
                entities.add(demoList);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return entities;
    }


    public void addEntity(DemoEntity entity) {
        String sql = "INSERT INTO customer (name, address) Values (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);

            demoList.setName(entity.getName());
            demoList.setAddress(entity.getAddress());
            entities.add(demoList);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getAddress());
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void deleteEntityById(int id) throws DeleteException {
        final String sql = "DELETE FROM customer WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DeleteException(404, "Id not found");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        statement.close();
        connection.close();
    }
}
