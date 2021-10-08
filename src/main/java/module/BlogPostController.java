package module;

import model.Blog;
import model.BlogPost;
import model.BlogPostStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Manages Queries related to BlogPost Class
 * */
public class BlogPostController {
    BlogController blogController = new BlogController();

    private DBConnector dbConnector = new DBConnector();

    public List<BlogPost> listAllBlogposts(){
        List<BlogPost> blogpostsInDB = new ArrayList<>();

        String query = "SELECT * FROM  blog_post";

        try {
            Statement statement = dbConnector.getConnection().createStatement();
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

    public BlogPost findBlogpostById(long id){
        BlogPost blogPost = null;

        String query = "SELECT * FROM  blog_post  WHERE id = ?";

        try {
            PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //id = resultSet.getLong("id");
                String blogPostTitle = resultSet.getString("title");
                String postText = resultSet.getString("post_text");
                BlogPostStatus status = BlogPostStatus.valueOf(resultSet.getString("status"));
                LocalDateTime pubTime = resultSet.getTimestamp("pub_time").toLocalDateTime();
                int readed = resultSet.getInt("readed");
                long blogId = resultSet.getLong("blog_id");

                blogPost = new BlogPost(id, blogPostTitle, postText, status, pubTime, readed);
                blogPost.setBlog(blogController.findBlogById(blogId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return blogPost;
    }

    public List<BlogPost> findBlogpostsOfBlog(Blog blog){
        List<BlogPost> blogPosts = new ArrayList<>();

        String query = "SELECT * FROM  blog_post  WHERE blog_id = ?";

        try {
            PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, blog.getBlogId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String blogPostTitle = resultSet.getString("title");
                String postText = resultSet.getString("post_text");
                BlogPostStatus status = BlogPostStatus.valueOf(resultSet.getString("status"));
                LocalDateTime pubTime = resultSet.getTimestamp("pub_time").toLocalDateTime();
                int readed = resultSet.getInt("readed");
                // blogId = resultSet.getLong("blog_id");

                BlogPost blogPost = new BlogPost(id, blogPostTitle, postText, status, pubTime, readed);
                blogPost.setBlog(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blogPosts;
    }

    /**
     * Add elements to database*/

    public boolean addBlogPostToDB(BlogPost blogPost) {

        String query = "INSERT INTO blog_post (blog_id, title, post_text, pub_time, status) VALUES (?,?,?,?,?);";

        try {
            PreparedStatement ps = dbConnector.getConnection().prepareStatement(query,  Statement.RETURN_GENERATED_KEYS );

            ps.setLong(1, blogPost.getBlog().getBlogId());
            ps.setString(2, blogPost.getBlogPostTitle());
            ps.setString(3, blogPost.getText());
            ps.setTimestamp(4, Timestamp.valueOf(blogPost.getPubTime()));
            ps.setString(5, blogPost.getBlogPostStatus().toString());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
                blogPost.setBlogPostId(rs.getInt(1));

            ps.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
