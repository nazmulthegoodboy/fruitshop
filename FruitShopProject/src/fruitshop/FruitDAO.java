package fruitshop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitDAO {
    public static List<Fruit> getAll() {
        List<Fruit> list = new ArrayList<>();
        String sql = "SELECT * FROM fruits";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Fruit(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static boolean create(Fruit f) {
        String sql = "INSERT INTO fruits (name, price, stock) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getName());
            ps.setDouble(2, f.getPrice());
            ps.setInt(3, f.getStock());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public static boolean update(Fruit f) {
        String sql = "UPDATE fruits SET name=?, price=?, stock=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getName());
            ps.setDouble(2, f.getPrice());
            ps.setInt(3, f.getStock());
            ps.setInt(4, f.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public static boolean delete(int id) {
        String sql = "DELETE FROM fruits WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}