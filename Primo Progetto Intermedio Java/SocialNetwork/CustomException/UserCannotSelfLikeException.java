package SocialNetwork.CustomException;

public class UserCannotSelfLikeException extends Exception {
    public UserCannotSelfLikeException(String msg) {
        super(msg);
    }
}
