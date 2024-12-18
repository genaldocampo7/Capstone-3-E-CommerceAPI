package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Categories");
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_ID"),
                        rs.getString("name"),
                        rs.getString("description")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }


    @Override
    public Category getById(int categoryId) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Categories WHERE category_id = ?")) {

            preparedStatement.setInt(1, categoryId);

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
    public Category create(Category category) {

        try (Connection connection = getConnection();
            PreparedStatement preparedstatement = connection.prepareStatement("INSERT INTO Categories(name, description) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedstatement.setString(1, category.getName());
            preparedstatement.setString(2, category.getDescription());

            int rows = preparedstatement.executeUpdate();
            if (rows > 0) {
                ResultSet generatedKeys = preparedstatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int categoryId = generatedKeys.getInt(1);

                    return getById(categoryId);
                }
            }

         }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public void update(int categoryId, Category category) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE categories SET name = ?, description = ?, WHERE category_id = ?")
        ) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, categoryId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int categoryId) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Categories WHERE category_id = ?")
        ) {
            preparedStatement.setInt(1, categoryId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Category mapRow(ResultSet row) throws SQLException {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category() {
            {
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
