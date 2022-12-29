package SocialNetwork;

import java.util.*;
import java.util.Map.Entry;

import SocialNetwork.Interfaces.ISocialNetwork;
import SocialNetwork.CustomException.AuthorORTextCannotBeNullException;
import SocialNetwork.CustomException.EmptyUsernameException;
import SocialNetwork.CustomException.ExplicitLanguageException;
import SocialNetwork.CustomException.MustBeRegisterToAddLikeException;
import SocialNetwork.CustomException.MustBeRegisterToPublishException;
import SocialNetwork.CustomException.UserCannotBeEqualException;
import SocialNetwork.CustomException.TextTooLongException;
import SocialNetwork.CustomException.UserCannotSelfLikeException;
import SocialNetwork.CustomException.UserCannotPutLikeTwiceException;

public class SocialNetwork implements ISocialNetwork {
    /**
     * OVERVIEW: Tipo di dato modificabile che rappresenta una retesociale
     * determinata come un HashMap e un HashMap che invece contiene i Post
     * appartenenti alla retescoiale in particolare questi Post sono identificati
     * univocamente da un IDPost
     * 
     * TYPICAL ELEMENT: <retesociale, postMap>
     * 
     * con: retesociale = NomeUtente=[UtenteSeguito1,..., UtenteSeguitoN]
     * 
     * con: postMap = IDPost = [Post]
     * 
     * AF: a(retesociale) = (retesociale:(String)) -> (seguiti) f(u) = { user
     * seguiti da u } se u ∈ networkMap.keySet()
     * 
     * follow(a,b) se ∃post1 ∈ postMap.values && user != post1.getAuthor() con user
     * ∈ retesociale.getKey() | post1.likes.contains(user) user segue autore del
     * post
     * 
     * RI: retesocile non contiene duplicati nei valori di una stessa chiava e non
     * ha duplicati nel Set di chiavi
     * 
     * postMap non ha duplicati nei valori
     * 
     */

    private HashMap<String, ArrayList<String>> retesociale;
    private HashMap<Integer, Post> postMap;
    int idPostCount = 1;

    /**
     * @EFFECTS crea un nuovo SocialNetwork inizializzando le HashMap retesociale e
     *          postMap
     */
    public SocialNetwork() {
        retesociale = new HashMap<String, ArrayList<String>>();
        postMap = new HashMap<Integer, Post>();
    }

    /**
     * 
     * @REQUIRES ps != null
     * @THROWS PostListNullException (Unchecked) se ps == null
     * @EFFECTS Restituisce un HashaMap che ha come chiave i nomi degli autori dei
     *          post senza ripetizioni e come valori i nomi delle persone che
     *          l'autore del post segue
     */

    public HashMap<String, ArrayList<String>> guessFollowers(List<Post> ps) {
        HashMap<String, ArrayList<String>> socialDerivateFromPost = new HashMap<String, ArrayList<String>>();
        for (Post pst : ps) {
            for (String follower : pst.getFollowers()) {
                if (socialDerivateFromPost.containsKey(follower)) {
                    if (!socialDerivateFromPost.get(follower).contains(pst.getAuthor()))
                        socialDerivateFromPost.get(follower).add(pst.getAuthor());
                } else {
                    socialDerivateFromPost.put(follower, new ArrayList<String>());
                    socialDerivateFromPost.get(follower).add(pst.getAuthor());
                }
            }
        }
        return socialDerivateFromPost;
    }

    /**
     * @REQUIRES followers != null
     * @EFFECTS Restituisce una Lista di Stringhe contenente le persone della rete
     *          sociale in ordine di Influenza, ovvero ordinati in modo tale che le
     *          persone che hanno più followers sono in testa e quelle meno
     *          influenti sono in coda alla Lista
     * @THROWS NullPointerException (Unchecked) if followers == null
     * 
     */

    public List<String> influencers(HashMap<String, ArrayList<String>> followers) {
        HashMap<String, ArrayList<String>> followMap = new HashMap<String, ArrayList<String>>();
        // creo la mappa contenete utente -> gente che segue l'utente
        for (String utente : followers.keySet()) {
            for (String follow : followers.get(utente)) {
                if (followMap.keySet().contains(follow)) {
                    followMap.get(follow).add(utente);
                } else {
                    followMap.put(follow, new ArrayList<String>());
                    followMap.get(follow).add(utente);
                }
            }
        }
        HashMap<String, Integer> influencersMap = new HashMap<String, Integer>();
        // creo hashmap con influencer e numero di followers
        for (String influencer : followMap.keySet()) {
            influencersMap.put(influencer, followMap.get(influencer).size());
        }

        Set<Entry<String, Integer>> entrySet = influencersMap.entrySet();
        List<Entry<String, Integer>> sortedInfluencersMap = new ArrayList<>(entrySet);
        // faccio il sort delle key per valore, quindi mettendo le persone con il
        // maggior numero di follower alla fine delle chiavi
        sortedInfluencersMap.sort(Map.Entry.comparingByValue());

        List<String> sortedInfluencersList = new ArrayList<String>();

        // creo una lista di appoggio per andare a mettere le chiavi in ordine crescente
        // di folllowers quindi scorro le chiavi al contrario e aggiungo i valori alla
        // mia lista per avere in ordine decrescente gli utenti per numero di followers
        for (Integer i = sortedInfluencersMap.size() - 1; i >= 0; i--) {
            sortedInfluencersList.add(sortedInfluencersMap.get(i).getKey());
        }

        return sortedInfluencersList;

    }

    /**
     * @EFFECTS Restitutisce gli utenti attualmente presenenti nella retesociale,
     *          restituendo il Set di chiavi della HashMap retesociale
     */
    public Set<String> getMentionedUsers() {
        return retesociale.keySet();
    }

    /**
     * @EFFECTS Restitutisce gli utenti attualmente presenenti nella retesociale,
     *          restituendo il Set di Stringhe contenente tutti gli aautori dei post
     *          senza ripetizioni
     * @THROWS NullPointerException (Unchecked) if ps == null
     */
    public Set<String> getMentionedUsers(List<Post> ps) {

        if (ps == null)
            throw new NullPointerException();

        Set<String> mentionedUsersFromPost = new HashSet<String>();

        for (Post post : ps) {
            if (!mentionedUsersFromPost.contains(post.getAuthor()))
                mentionedUsersFromPost.add(post.getAuthor());
        }
        return mentionedUsersFromPost;
    }

    /**
     * @EFFECTS Restitutisce una lista di Post contenente tutti i Post scritti da
     *          quella persona senza ripetizioni all'interno della Lista
     */
    public List<Post> writtenBy(String username) {
        List<Post> postFromAuthor = new ArrayList<Post>();
        for (Post post : postMap.values()) {
            if (post.getAuthor().equals(username))
                postFromAuthor.add(post);
        }

        return postFromAuthor;
    }

    /**
     * @REQUIRES ps != null
     * @EFFECTS Restitutisce una lista di Post contenente tutti i Post scritti da
     *          quella persona senza ripetizioni all'interno della Lista. La lista
     *          di Post che viene restituita viene derivata da una lista di Post
     *          data in input al metodo
     * @THROWS NullPointerException (Unchecked) if ps == null
     */

    public List<Post> writtenBy(List<Post> ps, String username) {

        if (ps == null)
            throw new NullPointerException();

        List<Post> postFromAuthor = new ArrayList<Post>();
        for (Post post : ps) {
            if (post.getAuthor().equals(username) && !postFromAuthor.contains(post))
                postFromAuthor.add(post);
        }
        return postFromAuthor;
    }

    /**
     * @EFFECTS Restitutisce una lista di Post contenente tutti i Post che
     *          contengono all'interno del parametro text almeno una delle parole
     *          contenute nella lista di stringhe passate per parametro, senza
     *          ripetizioni
     */
    public List<Post> containing(List<String> words) {

        List<Post> postWwords = new ArrayList<Post>();
        for (Post post : postMap.values()) {
            for (String word : words)
                if (post.getText().contains(word) && !postWwords.contains(post))
                    postWwords.add(post);
        }
        return postWwords;
    }

    /**
     * 
     * @REQUIRES ps != null
     * @THROWS PostListNullException (Unchecked) se ps == null
     * @EFFECTS Restituisce un HashaMap che ha come chiave i nomi degli autori dei
     *          post senza ripetizioni e come valori i nomi delle persone che
     *          seguono i post dell'autore del post
     */
    public HashMap<String, ArrayList<String>> guessFollows(List<Post> ps) {

        if (ps == null)
            throw new NullPointerException();

        HashMap<String, ArrayList<String>> socialDerivateFromPost = new HashMap<String, ArrayList<String>>();
        for (Post post : ps) {
            if (socialDerivateFromPost.containsKey(post.getAuthor())) {
                ArrayList<String> stringTemp = new ArrayList<>();
                for (String s : post.getFollowers()) {
                    if (!stringTemp.contains(s))
                        stringTemp.add(s);
                }

                socialDerivateFromPost.get(post.getAuthor()).addAll(stringTemp);
            } else {
                // inizio lista vuota quindi nessun duplicato
                socialDerivateFromPost.put(post.getAuthor(), new ArrayList<String>());
                socialDerivateFromPost.get(post.getAuthor()).addAll(post.getFollowers());
            }
        }

        return socialDerivateFromPost;
    }

    /**
     * @REQUIRES name != null && name != "" (Stringha vuota)
     * @EFFECTS aggiunge un utente alla retesociale, inoltre un utente se non
     *          iscritto non può né mettere like, quindi seguire POst di altri
     *          utenti, né aggiungere Post nel SocialNetwork
     * @THROWS EmptyUsernameException (Checked) if name == null
     *         UserCannotBeEqualException (Checked) if name == ""
     */
    public void addUser(String name) {
        try {
            if (name.equals("") || name.equals(null))
                throw new EmptyUsernameException("Username can't be null");
            // solo in questo caso viene aggiunto
            if (!retesociale.containsKey(name))
                retesociale.put(name, new ArrayList<String>());
            else
                throw new UserCannotBeEqualException("User can't have same Nickname");
        } catch (EmptyUsernameException e) {
            System.out.println(e);
        } catch (UserCannotBeEqualException e) {
            System.out.println(e);
        }
        return;

    }

    /**
     * @throws ExplicitLanguageException
     * @REQUIRES name != null && name != "" (Stringha vuota)
     * @EFFECTS aggiunge un utente alla retesociale, inoltre un utente se non
     *          iscritto non può né mettere like, quindi seguire POst di altri
     *          utenti, né aggiungere Post nel SocialNetwork
     * @THROWS TextTooLongException if text.lenght > 140
     * 
     *         AuthorORTextCannotBeNullException if text == "" || text == null ||
     *         author == null || author == ""
     * 
     *         MustBeRegisterToPublishException if
     *         !retesociale.keySet().contains(author) (l'utente non appartiene alla
     *         rete sociale quindi non può aggiungere un Post al SocialNetwork)
     */
    public void addPost(String author, String text) throws ExplicitLanguageException {
        try {
            if (author.equals("") || author.equals(null) || text.equals("") || text.equals(null))
                throw new AuthorORTextCannotBeNullException("Author and Text cannot be null");
            if (!retesociale.keySet().contains(author))
                throw new MustBeRegisterToPublishException("You must register on Social to post");
            Post psNew = new Post(idPostCount, author, text);
            postMap.put(idPostCount, psNew);
            idPostCount++;
        } catch (TextTooLongException e) {
            System.out.println(e);
        } catch (AuthorORTextCannotBeNullException e) {
            System.out.println(e);
        } catch (MustBeRegisterToPublishException e) {
            System.out.println(e);
        }
        return;
    }

    /**
     * @EFFECTS aggiunge un follower ad un Post (identificato con IDPost univoco)
     * @THROWS UserCannotSelfLikeException if postMap.get(IDPost).getAuthor() ==
     *         follower
     *
     *         UserCannotPutLikeTwiceException if
     *         postMap.get(IDPost).getFollowers().contains(follower)
     * 
     *         MustBeRegisterToAddLikeException if
     *         !retesociale.keySet().contains(follower)
     */
    public void addLike(String follower, int IDPost) {
        try {

            if (!retesociale.keySet().contains(follower))
                throw new MustBeRegisterToAddLikeException("You must be registered to add like");
            postMap.get(IDPost).addLike(follower);
            retesociale.get(follower).add(postMap.get(IDPost).getAuthor());
        } catch (UserCannotSelfLikeException e) {
            System.out.println(e);
        } catch (UserCannotPutLikeTwiceException e) {
            System.out.println(e);
        } catch (MustBeRegisterToAddLikeException e) {
            System.out.println(e);
        }
        return;
    }

    /**
     * @EFFECTS restituisce un'HashMap con chiave IDPost e come valori i relativi
     *          post
     */
    public HashMap<Integer, Post> getPost() {
        return postMap;
    }

    /**
     * @EFFECTS restituisce la retesociale
     */
    public HashMap<String, ArrayList<String>> getSocial() {
        return retesociale;
    }

    /**
     * @EFFECTS restituisce l'ID che avrà il prossimo Post
     */
    public int getNextPostID() {
        return idPostCount;
    }

}