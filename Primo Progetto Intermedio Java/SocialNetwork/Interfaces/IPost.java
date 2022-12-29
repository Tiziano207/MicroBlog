package SocialNetwork.Interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;

import SocialNetwork.CustomException.UserCannotPutLikeTwiceException;
import SocialNetwork.CustomException.UserCannotSelfLikeException;

public interface IPost {

    int getIDPost();

    String getAuthor();

    String getText();

    Timestamp getTimeStamp();

    ArrayList<String> getFollowers();

    void addLike(String like) throws UserCannotSelfLikeException, UserCannotPutLikeTwiceException;

}
