package module;

import model.Blog;
import model.BlogPost;
import model.BlogTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BlogPostController {

    private DBEngine dbEngine = new DBEngine();

    public List<BlogPost> findBlogpostsOfBlog(long blogId){
        List<BlogPost> blogposts = new ArrayList<>();

        String query = "SELECT * FROM  blog_post  WHERE owner_id = ?";

        try {
            PreparedStatement preparedStatement = dbEngine.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, blogId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return blogposts;
    }
}
