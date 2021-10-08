package module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UserInterface {
    @Getter @Setter
    private BlogController blogController = new BlogController();
    @Getter @Setter
    private BlogPostController blogPostController = new BlogPostController();
    @Getter @Setter
    private CommentController commentController = new CommentController();
    @Getter @Setter
    private DBConnector dbConnector = new DBConnector();
    @Getter @Setter
    private UserController userController = new UserController();

    public UserInterface() {
    }
}
