package module;

import model.Blog;
import model.BlogPost;
import model.BlogPostStatus;
import model.BlogTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BlogPostController {
    BlogController blogController = new BlogController();

    private DBEngine dbEngine = new DBEngine();

    public List<BlogPost> listAllBlogposts(){
        List<BlogPost> blogpostsInDB = new ArrayList<>();

        String query = "SELECT * FROM  blog_post";

        try {
            Statement statement = dbEngine.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String blogPostTitle = resultSet.getString("title");
                String postText = resultSet.getString("post_text");
                BlogPostStatus status = BlogPostStatus.valueOf(resultSet.getString("status"));
                LocalDateTime pubTime = resultSet.getTimestamp("pub_time").toLocalDateTime();
                int readed = resultSet.getInt("readed");
                long blogId = resultSet.getLong("blog_id");

                BlogPost blogPost = new BlogPost(id, blogPostTitle, postText, status, pubTime, readed);

                blogPost.setBlog(blogController.findBlogById(blogId));
                blogpostsInDB.add(blogPost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return blogpostsInDB;
    }

    public List<BlogPost> findBlogpostsOfBlog(long blogId){
        List<BlogPost> blogposts = new ArrayList<>();

        String query = "SELECT * FROM  blog_post  WHERE owner_id = ?";

        try {
            PreparedStatement preparedStatement = dbEngine.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, blogId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String blogPostTitle = resultSet.getString("title");
                String postText = resultSet.getString("post_text");
                BlogPostStatus status = BlogPostStatus.valueOf(resultSet.getString("status"));
                LocalDateTime pubTime = resultSet.getTimestamp("pub_time").toLocalDateTime();
                int readed = resultSet.getInt("readed");
                // blogId = resultSet.getLong("blog_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return blogposts;
    }
}
