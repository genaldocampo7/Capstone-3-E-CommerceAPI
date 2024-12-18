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

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Profiles WHERE user_id = ?")) {

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                 return mapRow(rs);
            }
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

    private Profile mapRow(ResultSet row) throws SQLException {
        int userId = row.getInt("user_id");
        String first_name = row.getString("first_name");
        String last_name = row.getString("last_name");
        String phone = row.getString("phone");
        String email = row.getString("email");
        String address = row.getString("address");
        String city = row.getString("city");
        String state = row.getString("state");
        String zip = row.getString("zip");

        Profile profile = new Profile() {
            {
                    setUserId(userId);
                    setFirstName(first_name);
                    setLastName(last_name);
                    setPhone(phone);
                    setEmail(email);
                    setAddress(address);
                    setCity(city);
                    setState(state);
                    setZip(zip);

                }};
        return profile;

    }
}


