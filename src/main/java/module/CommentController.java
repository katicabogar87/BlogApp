package module;


import model.*;

import java.sql.*;
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

    public List<Comment> findCommentsOfBlogPost(BlogPost blogPost) {
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
                if (precedingId != 0) {
                    comment.setPreceding(findCommentById(precedingId));
                }
                comment.setReplies(findCommentsReplies(comment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    private Comment findCommentById(long id) {

        Comment comment = null;

        String query = "SELECT * FROM  comment  WHERE id = ?";

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
                comment.setReplies(findCommentsReplies(comment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comment;
    }

    private List<Comment> findCommentsReplies(Comment inputComment) {

        List<Comment> replies = new ArrayList<>();

        String query = "SELECT * FROM  comment  WHERE preceding_id = ?";

        try {
            PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, inputComment.getCommentId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long authorId = resultSet.getLong("author_id");
                String commentText = resultSet.getString("comment_text");
                boolean isVisible = resultSet.getBoolean("is_visible");
                LocalDateTime pubTime = resultSet.getTimestamp("pub_time").toLocalDateTime();


                Comment comment = new Comment(id, commentText, isVisible, pubTime);
                comment.setBlogPost(inputComment.getBlogPost());
                comment.setCommenter(userController.findUserById(authorId));
                comment.setPreceding(inputComment);
                comment.setReplies(findCommentsReplies(comment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return replies;
    }

    /**
     * Add elements to database
     */

    public boolean addCommentToDB(BlogPost blogPost, User user, Comment comment) {

        String query = "INSERT INTO comment (blog_post_id, author_id, preceding_id, comment_text, is_visible, pub_time) " +
                            "VALUES (?,?,?,?,?,?);";

        try {
            PreparedStatement ps = dbConnector.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, blogPost.getBlogPostId());
            ps.setLong(2, user.getUserId());
            if (comment.getPreceding()==null){ps.setLong(3, 0);}
            else {ps.setLong(3, comment.getPreceding().getCommentId());}
            ps.setString(4, blogPost.getBlogPostStatus().toString());
            ps.setBoolean(5, comment.isVisible());
            ps.setTimestamp(6, Timestamp.valueOf(blogPost.getPubTime()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                comment.setCommentId(rs.getInt(1));

            ps.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
