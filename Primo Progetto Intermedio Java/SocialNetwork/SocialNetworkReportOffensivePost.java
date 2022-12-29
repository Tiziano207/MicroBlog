package SocialNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import SocialNetwork.CustomException.DoubleSegnalationException;
import SocialNetwork.CustomException.ExplicitLanguageException;
import SocialNetwork.CustomException.YouMustBeRegisteredToReportPostException;
import SocialNetwork.Interfaces.ISocialNetworkReportOffensivePost;

public class SocialNetworkReportOffensivePost extends SocialNetwork implements ISocialNetworkReportOffensivePost {

    private List<String> blackList;
    private HashMap<Integer, ArrayList<String>> offensivePostSegnalation;

    /**
     * OVERVIEW: Tipo di dato modificabile che rappresenta una retesociale
     * determinata come un HashMap e un HashMap che invece contiene i Post
     * appartenenti alla retescoiale in particolare questi Post sono identificati
     * univocamente da un IDPost. Sottoclasse del tipo SocialNetwork, si differenzia
     * nel tipo di dato Post nella presenza di metodi per la segnalazione di Post e
     * la relativa segnalazione. Inoltre il tipo di dato SocialNetwork contiene una
     * BlackList, una lista, nella quale sono contenute le parole proibite nel
     * SocialNetwork con la relativa possibilità di rimuovere le parole da essa o
     * aggiungerle. Prevede un metodo che rimuove tutti i post segnalati almeno 2
     * volte da due persone diverse.
     * 
     * TYPICAL ELEMENT: <retesociale, postMap, blackList, offensivePostSegnalation>
     * 
     * con: retesociale = NomeUtente=[UtenteSeguito1,..., UtenteSeguitoN]
     * 
     * con: postMap = IDPost = [Post]
     * 
     * con: blackList = [BadWord1,...,BadWordN]
     * 
     * con: offensivePostSegnalation = Post = [UtenteCheHaSegnalatoIlPost1, ...,
     * UtenteCheHaSegnalatoIlPostN]
     * 
     * AF: a(retesociale) = (retesociale:(String)) -> (seguiti) f(u) = { user
     * seguiti da u } se u ∈ networkMap.keySet()
     * 
     * follow(a,b) se ∃post1 ∈ postMap.values && user != post1.getAuthor() con user
     * ∈ retesociale.getKey() | post1.likes.contains(user) user segue autore del
     * post
     * 
     */
    public SocialNetworkReportOffensivePost() {
        blackList = new ArrayList<String>();
        offensivePostSegnalation = new HashMap<Integer, ArrayList<String>>();
    }

    /**
     * @EFFECTS aggiunge una parola alla BlackList
     */
    public void addBlackList(String badWord) {
        if (!blackList.contains(badWord)) {
            blackList.add(badWord);
        }
        return;
    }

    /**
     * @EFFECTS rimuove una parola dalla BlackList ovvero non considerato più
     *          offensiva
     */
    public void removeWordBlacklist(String badWord) {
        blackList.remove(badWord);
    }

    /**
     * @EFFECTS Restituisce la lista delle parole considerate offensive
     */
    public List<String> getBlackList() {
        return blackList;
    }

    /**
     * @EFFECTS aggiunge un Post nel SocialNetwork se e solo se non contiene nessuna
     *          delle parole nella BlackList
     * @THORWS ExplicitLanguageException if ps.getText().contains(badWord)
     */
    @Override
    public void addPost(String author, String text) {
        try {
            for (String badWord : blackList) {
                if (text.contains(badWord)) {
                    throw new ExplicitLanguageException("You can't use strong Language");
                }
            }
            super.addPost(author, text);
        } catch (ExplicitLanguageException e) {
            System.out.println(e);
        }
        return;

    }

    /**
     * @REQUIRES ps != null
     * @EFFECTS aggiunge una segnalazione ad un Post aggiungendolo in un'HashMap
     *          formata con chiave postID e valori array di liste di stringhe
     *          contenti i nomi di coloro hanno mandato una segnalazione al post
     * @THORWS NullPointerException (Unchecked) if ps == null
     *         DoubleSegnalationException (Checked) if
     *         offensivePostSegnalation.get(ps.getIDPost()).contains(reporter)
     *         YouMustBeRegisteredToReportPostException if
     *         !super.getSocial().keySet().contains(reporter)
     */
    public void addReport(String reporter, int postID) {
        try {
            if (!super.getSocial().keySet().contains(reporter))
                throw new YouMustBeRegisteredToReportPostException("You have to be registered to Report Post");
            if (!offensivePostSegnalation.containsKey(postID)) {
                offensivePostSegnalation.put(postID, new ArrayList<String>());
                offensivePostSegnalation.get(postID).add(reporter);
            } else {
                if (offensivePostSegnalation.get(postID).contains(reporter)) {
                    throw new DoubleSegnalationException("You cannot add two Report at this Post ");
                } else {
                    offensivePostSegnalation.get(postID).add(reporter);
                }
            }
        } catch (YouMustBeRegisteredToReportPostException e) {
            System.out.println(e);
        } catch (DoubleSegnalationException e) {
            System.out.println(e);
        }
        return;
    }

    /**
     * @EFFECTS elimina tutti i Post segnalati almeno due volte
     */
    public void controlReportMap() {

        for (Integer i : offensivePostSegnalation.keySet()) {
            if (offensivePostSegnalation.get(i).size() >= 2) {
                offensivePostSegnalation.remove(i);
                super.getPost().remove(i);
            }
        }
    }

}