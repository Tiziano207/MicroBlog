package SocialNetwork.Interfaces;

import SocialNetwork.CustomException.ExplicitLanguageException;
import SocialNetwork.CustomException.DoubleSegnalationException;

import java.util.*;

public interface ISocialNetworkReportOffensivePost extends ISocialNetwork {
    public void addPost(String author, String text) throws ExplicitLanguageException;

    void addReport(String reporter, int IDPost) throws DoubleSegnalationException;

    void controlReportMap();

    List<String> getBlackList();

    void addBlackList(String badWord);

    void removeWordBlacklist(String badWord);

}
