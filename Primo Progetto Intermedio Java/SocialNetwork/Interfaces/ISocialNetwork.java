package SocialNetwork.Interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import SocialNetwork.Post;
import SocialNetwork.CustomException.*;

public interface ISocialNetwork {

    HashMap<String, ArrayList<String>> guessFollowers(List<Post> ps);

    List<String> influencers(HashMap<String, ArrayList<String>> followers);

    Set<String> getMentionedUsers();

    Set<String> getMentionedUsers(List<Post> ps);

    List<Post> writtenBy(String username);

    List<Post> writtenBy(List<Post> ps, String username);

    List<Post> containing(List<String> words);

    HashMap<String, ArrayList<String>> guessFollows(List<Post> ps);

    void addUser(String name) throws UserCannotBeEqualException, EmptyUsernameException;

    void addPost(String author, String text)
            throws ExplicitLanguageException, TextTooLongException, AuthorORTextCannotBeNullException;

    HashMap<Integer, Post> getPost();

    HashMap<String, ArrayList<String>> getSocial();

    int getNextPostID();

}
