package module;


import model.Blog;
import model.BlogPost;
import model.BlogPostStatus;
import model.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages Queries related to Comment Class (and also templates)
 * */
public class CommentController {

    UserController userController = new UserController();
    BlogPostController blogPostController = new BlogPostController();

    private DBConnector dbConnector = new DBConnector();

    public List<Comment> findCommentsOfBlogPost(BlogPost blogPost){
        List<Comment> comments = new ArrayList<>();

        String query = "SELECT * FROM  comment  WHERE blog_post_id = ?";

        try {
            PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, blogPost.getBlogPostId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long authorId = resultSet.getLong("author_id");
                long precedingId = resultSet.getLong("preceding_id");
                String commentText = resultSet.getString("comment_text");
                boolean isVisible = resultSet.getBoolean("is_visible");
                LocalDateTime pubTime = resultSet.getTimestamp("pub_time").toLocalDateTime();



                Comment comment = new Comment(id, commentText, isVisible, pubTime);
                comment.setBlogPost(blogPost);
                comment.setCommenter(userController.findUserById(authorId));
                if(precedingId!=0){
                comment.setPreceding(findCommentById(precedingId));}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    private Comment findCommentById(long id) {

               Comment comment = null;

                String query = "SELECT * FROM  comment  WHERE preceding_id = ?";

                try {
                    PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query);
                    preparedStatement.setLong(1, id);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        // id = resultSet.getLong("id");
                        long authorId = resultSet.getLong("author_id");
                        long blogPostID = resultSet.getLong("blog_post_id");
                        long precedingId = resultSet.getLong("preceding_id");
                        String commentText = resultSet.getString("comment_text");
                        boolean isVisible = resultSet.getBoolean("is_visible");
                        LocalDateTime pubTime = resultSet.getTimestamp("pub_time").toLocalDateTime();



                        comment = new Comment(id, commentText, isVisible, pubTime);
                        comment.setBlogPost(blogPostController.findBlogpostById(blogPostID));
                        comment.setCommenter(userController.findUserById(authorId));
                        comment.setPreceding(findCommentById(precedingId));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return comment;


            }



}
