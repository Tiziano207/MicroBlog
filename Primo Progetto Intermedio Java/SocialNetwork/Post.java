package SocialNetwork;

import java.sql.Timestamp;
import java.util.ArrayList;

import SocialNetwork.CustomException.TextTooLongException;
import SocialNetwork.CustomException.UserCannotPutLikeTwiceException;
import SocialNetwork.CustomException.UserCannotSelfLikeException;
import SocialNetwork.Interfaces.IPost;

public class Post implements IPost {
    /**
     * OVERVIEW: Tipo di dato modificabile che rappresenta un post al quale si
     * associa: ID univoco, testo, autore del post e data/ora di creazione. Inoltre
     * il post contiene una lista di nomi (stringhe) che rappresentano le persone a
     * cui è "piaciuto" il Post quindi il nome di coloro che seguono il Post.
     * 
     * TYPICAL ELEMENT: <IDPost, author, timestamp, likes> con likes =
     * {like1,...,likeN}
     *
     */

    /**
     * Rappresenta l'ID unico del post
     */
    private final int postID;
    /**
     * Rappresenta l'autore del post anch'esso unico
     */
    private final String author;
    /**
     * Rappresenta il testo all'interno del post
     */
    private final String text;
    /**
     * Rappresenta quando il post è stato creto
     */
    private final Timestamp timestamp;
    /**
     * Rappresenta una lista di Stringhe contenente tutte le persone a cui è
     * "piaciuto" il post ovvero tutte le persone che seguono quel post senza
     * ripetizioni
     */
    private ArrayList<String> likes;

    /**
     * @REQUIRES author != null && author != "" (Stringa Vuota) && text != null &&
     *           text != "" (Stringa vuota) && text.lenght < 140
     * @THROWS TextTooLongException (Checked) if text.lenght > 140
     * @MODIFIES this
     * @EFFECTS crea un nuovo Post con autore 'author', testo 'text' e con un ID
     *          "IDPost" (passati come parametri del metodo). Il Post avrà ancheun
     *          Timestamp il quale indicherà la data e l'ora di pubblicazione del
     *          Post
     */
    public Post(int postID, String author, String text) throws TextTooLongException {

        this.postID = postID;
        this.author = author;

        if (text.length() > 140)
            throw new TextTooLongException("Text is too long");
        this.text = text;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.likes = new ArrayList<String>();
    }

    /**
     * @REQUIRES like deve essere uguale al nome di un utente registrato
     * @THROWS UserCannotSelfLikeException (Checked) if author == like
     *         UserCannotPutLikeTwiceException (Checked) if likes.contains(like)
     * @EFFECTS aggiunge un nome (stringa) alla lista dell persone a cui piace il
     *          post e vuole seguirlo
     */
    public void addLike(String like) throws UserCannotSelfLikeException, UserCannotPutLikeTwiceException {
        try {
            if (author == like)
                throw new UserCannotSelfLikeException("You can't like yourself");
            if (likes.contains(like))
                throw new UserCannotPutLikeTwiceException("You can't put like twice");
            likes.add(like);
        } catch (UserCannotPutLikeTwiceException e) {
            System.out.println(e);
        } catch (UserCannotSelfLikeException e) {
            System.out.println(e);
        }
        return;
    }

    /**
     * @EFFECTS restituisce l'ID univoco del Post
     */
    public int getIDPost() {
        return postID;
    }

    /**
     * @EFFECTS restituisce l'autore univoco del Post
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @EFFECTS restituisce il testo del Post
     */
    public String getText() {
        return text;
    }

    /**
     * @EFFECTS restituisce quando il post è sttao pubblicato, data e ora
     */
    public Timestamp getTimeStamp() {
        return timestamp;
    }

    /**
     * @EFFECTS restituisce la lista dei like riferiti al post
     */
    public ArrayList<String> getFollowers() {
        return likes;
    }
}
