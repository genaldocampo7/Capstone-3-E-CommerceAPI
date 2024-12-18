package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao {
    public MySqlProfileDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile) {

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setInt(1, profile.getUserId());
            preparedStatement.setString(2, profile.getFirstName());
            preparedStatement.setString(3, profile.getLastName());
            preparedStatement.setString(4, profile.getPhone());
            preparedStatement.setString(5, profile.getEmail());
            preparedStatement.setString(6, profile.getAddress());
            preparedStatement.setString(7, profile.getCity());
            preparedStatement.setString(8, profile.getState());
            preparedStatement.setString(9, profile.getZip());

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }

    @Override
    public Profile getByUserId(int id) {
        Profile profile = null;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Profiles WHERE user_id = ?")) {

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                profile = new Profile(
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("zip")
                );
            } return profile;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int userId, Profile profile) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("""
               UPDATE Profiles
               SET first_name = ?,
               last_name = ?
               phone = ?
               email = ?
               address = ?
               city = ?
               state = ?
               zip = ?
               WHERE user_id = ?""")
        ) {
            preparedStatement.setString(1, profile.getFirstName());
            preparedStatement.setString(1, profile.getLastName());
            preparedStatement.setString(1, profile.getPhone());
            preparedStatement.setString(1, profile.getEmail());
            preparedStatement.setString(1, profile.getAddress());
            preparedStatement.setString(1, profile.getCity());
            preparedStatement.setString(1, profile.getState());
            preparedStatement.setString(1, profile.getZip());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
