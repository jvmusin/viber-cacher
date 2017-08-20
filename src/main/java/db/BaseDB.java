package db;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntFunction;

abstract class BaseDB {
    private final String dbPath;

    BaseDB(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() {
        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    <T> Map<Integer, T> loadAllFromDisc(String query, ItemPicker<T> itemPicker, ToIntFunction<T> idPicker) {
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            Map<Integer, T> items = new HashMap<>();
            while (rs.next()) {
                T item = itemPicker.getSingleItem(rs);
                items.put(idPicker.applyAsInt(item), item);
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    void executeWithPreparedStatement(String query, StatementPreparer preparer) {
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
            preparer.prepare(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void execute(String query) {
        try (Connection conn = connect();
             Statement statement = conn.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected interface ItemPicker<T> {
        T getSingleItem(ResultSet rs) throws SQLException;
    }

    protected interface StatementPreparer {
        void prepare(PreparedStatement ps) throws SQLException;
    }
}