package module;

import model.Blog;
import model.BlogPost;
import model.BlogTemplate;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BlogController {

    private DBEngine dbEngine = new DBEngine();
    private UserController userController = new UserController();

    public List<Blog> listAllBlogs() {
        String query = "SELECT * FROM blog";
        List<Blog> blogsInDB = new ArrayList<>();

        try {
            Statement statement = dbEngine.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                long blogId = resultSet.getLong("id");
                String blogTitle = resultSet.getString("title");
                long blogOwnerId = resultSet.getLong("owner_id");
                long templateId = resultSet.getLong("template_id");
                LocalDateTime creationTime = resultSet.getTimestamp("reg_date").toLocalDateTime();

                Blog blog = new Blog(blogId, blogTitle, creationTime);
                blogsInDB.add(blog);

                User blogOwner; //blog.setOwner(findUserById());
                List<BlogPost> blogPosts; //blog.setBlogPosts(findBlogpostsOfBlog());
                BlogTemplate blogTemplate; //blog.setBlogTemplate(findBlogTemplateById());

            }
        } catch (SQLException e) {
            System.out.println("???");
            e.printStackTrace();
        }
        return blogsInDB;
    }

    public List<Blog> findBlogsOfUser(long userId){
        List<Blog> blogs = new ArrayList<>();

        String query = "SELECT * FROM  blog  WHERE owner_id = ?";

        try {
            PreparedStatement preparedStatement = dbEngine.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long blogId = resultSet.getLong("id");
                String blogTitle = resultSet.getString("title");
                long blogOwnerId = resultSet.getLong("owner_id");
                long templateId = resultSet.getLong("template_id");
                LocalDateTime creationTime = resultSet.getTimestamp("reg_date").toLocalDateTime();

                Blog blog = new Blog(blogId, blogTitle, creationTime);
                blogs.add(blog);

                blog.setBlogOwner(userController.findUserById(userId));
                List<BlogPost> blogPosts; //blog.setBlogPosts(findBlogpostsOfBlog(blogId));
                BlogTemplate blogTemplate; //blog.setBlogTemplate(findBlogTemplateById(templateId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

}
