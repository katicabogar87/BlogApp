package module;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages Queries related to Blog Class (and also templates)
 * */

public class BlogController {

    private DBConnector dbConnector = new DBConnector();
    private UserController userController = new UserController();
    private BlogPostController blogPostController = new BlogPostController();

    public List<Blog> listAllBlogs() {
        String query = "SELECT * FROM blog";
        List<Blog> blogsInDB = new ArrayList<>();

        try {
            Statement statement = dbConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                long blogId = resultSet.getLong("id");
                String blogTitle = resultSet.getString("title");
                long blogOwnerId = resultSet.getLong("owner_id");
                long templateId = resultSet.getLong("template_id");
                LocalDateTime creationTime = resultSet.getTimestamp("reg_date").toLocalDateTime();

                Blog blog = new Blog(blogId, blogTitle, creationTime);
                blogsInDB.add(blog);

                blog.setBlogOwner(userController.findUserById(blogOwnerId));
                blog.setBlogPosts(blogPostController.findBlogpostsOfBlog(blog));
                blog.setBlogTemplate(findTemplateById(templateId));

            }
        } catch (SQLException e) {
            System.out.println("???");
            e.printStackTrace();
        }
        return blogsInDB;
    }

    public List<Blog> findBlogsOfUser(User user){
        List<Blog> blogs = new ArrayList<>();

        String query = "SELECT * FROM  blog  WHERE owner_id = ?";

        try {
            PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, user.getUserId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long blogId = resultSet.getLong("id");
                String blogTitle = resultSet.getString("title");
                // ownerId = resultSet.getLong("owner_id");
                long templateId = resultSet.getLong("template_id");
                LocalDateTime creationTime = resultSet.getTimestamp("reg_date").toLocalDateTime();

                Blog blog = new Blog(blogId, blogTitle, creationTime);
                blogs.add(blog);

                blog.setBlogOwner(user);
                blog.setBlogPosts(blogPostController.findBlogpostsOfBlog(blog));
                blog.setBlogTemplate(findTemplateById(templateId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    public Blog findBlogById(long blogId) {

            String query = "SELECT * FROM  blog  WHERE blog_id = ?";
           Blog blog = null;

            try {
                PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query);
                preparedStatement.setLong(1, blogId);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    //blogId = resultSet.getLong("id");
                    String blogTitle = resultSet.getString("title");
                    long blogOwnerId = resultSet.getLong("owner_id");
                    long templtateId = resultSet.getLong("template_id");
                    LocalDateTime creationTime = resultSet.getTimestamp("reg_date").toLocalDateTime();

                    blog = new Blog(blogId, blogTitle, creationTime);

                    blog.setBlogOwner(userController.findUserById(blogOwnerId));
                    blog.setBlogPosts(blogPostController.findBlogpostsOfBlog(blog));
                    blog.setBlogTemplate(findTemplateById(templtateId));

                }
            } catch (SQLException e) {
                System.out.println("???");
                e.printStackTrace();
            }

        return blog;
    }

    public List<BlogTemplate> listAllTemplates() {
        String query = "SELECT * FROM template";
        List<BlogTemplate> templatesInDB = new ArrayList<>();

        try {
            Statement statement = dbConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                long templateId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String fontColor = resultSet.getString("font_color");
                Category category = Category.valueOf(resultSet.getString("category"));

                BlogTemplate blogTemplate = new BlogTemplate(templateId, name, fontColor, category);
                templatesInDB.add(blogTemplate);
            }
        } catch (SQLException e) {
            System.out.println("???");
            e.printStackTrace();
        }
        return templatesInDB;
    }

    public BlogTemplate findTemplateById(long id) {
        String query = "SELECT * FROM  blog  WHERE blog_id = ?";
        BlogTemplate blogTemplate = null;

        try {
            PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                //id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String fontColor = resultSet.getString("font_color");
                Category category = Category.valueOf(resultSet.getString("category"));

                blogTemplate = new BlogTemplate(id, name, fontColor, category);

            }
        } catch (SQLException e) {
            System.out.println("???");
            e.printStackTrace();
        }
        return blogTemplate;
    }

}
