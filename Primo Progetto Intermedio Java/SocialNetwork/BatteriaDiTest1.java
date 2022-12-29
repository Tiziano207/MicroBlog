package SocialNetwork;

import java.util.*;

//Batteria in cui tre utenti si iscrivono postano e aggiungono like
public class BatteriaDiTest1 {
    public static void main(String[] args) throws Exception {
        SocialNetwork microBlog = new SocialNetwork();
        // gli utenti si iscrivono al SocialNetwork
        microBlog.addUser("Zio Paperone");
        microBlog.addUser("Paperoga");
        microBlog.addUser("Paperino");
        // gli utenti pubblicanop Post nella rete Sociale
        microBlog.addPost("Zio Paperone",
                "Va bene! Puoi andare in ferie però durante la tua assenza ti farò sostituire da un altro");
        microBlog.addPost("Paperoga", "D'accordo");
        microBlog.addPost("Paperino", "Chi sarà il sostituto Zio Paperone?");
        microBlog.addPost("Zio Paperone", "Oh, Bill Zampalunga vieni avanti");
        // gli utenti seguono i post di altri utenti
        microBlog.addLike("Paperoga", 4);
        microBlog.addLike("Paperino", 4);
        microBlog.addLike("Paperino", 2);

        ArrayList<Post> postList = new ArrayList<>();
        for (int i : microBlog.getPost().keySet()) {
            postList.add(microBlog.getPost().get(i));
        }
        // utilizzo guessFollower per capure chi seguie un utente nella rete sociale
        System.out.println("Il metodo guessFollower restituisce" + microBlog.guessFollowers(postList));

        // utilizzo influencers per trovare le persone più influenti della rete sociale
        System.out.println(
                "\nIl metodo influencers restituisce" + microBlog.influencers(microBlog.guessFollowers(postList)));

        // utilizzo il metodo getMentionedUser per trovare tutte le persone all'interno
        // della ReteSociale
        System.out.println("\nIl metodo getMentionedUser restituisce" + microBlog.getMentionedUsers());
        // utilizzo il metodo getMentionedUser per trovare tutte le persone all'interno
        // della ReteSociale, lista derivata da una lista di post (il risultato in
        // questo caso è uguale al precedente)
        System.out.println("\nIl metodo getMentionedUser derivato da una lista di Post restituisce"
                + microBlog.getMentionedUsers(postList));
        // utilizzo il metodo writteBy per trovare tutti i Post scritti da una persona
        System.out.println("\nIl metodo writtenBy restituisce: \n   Zio Paperone ha postato:\n");
        int d = 0;
        for (Post ps : microBlog.writtenBy("Zio Paperone")) {
            d++;
            System.out.println("    " + d + ". " + ps.getText() + "\n");
        }
        // utilizzo il metodo writteBy da Lista di Post per trovare tutti i Post scritti
        // da una persona il risultato non cambia dal metodo precedente in questo caso
        System.out.println("\nIl metodo writtenBy derivato da Lista di Post restituisce:\n  Paperoga ha postato:\n");
        int j = 0;
        for (Post ps : microBlog.writtenBy(postList, "Paperoga")) {
            j++;
            System.out.println("        " + j + ". " + ps.getText() + "\n");
        }
        // utlizzo il metodo containing per trovare tutti i post che contengono almeno
        // una delle parole nella lista
        // inizializzo una lista contenente alcune parole da cercare all'interno del
        // post
        ArrayList<String> words = new ArrayList<>();
        words.add("Va bene!");
        words.add("sostituto");
        words.add("Bill");
        System.out.println("Le parole da cercare sono:" + words);
        System.out.println("\nIl metodo containing restituisce:\n");
        int i = 0;
        for (Post ps : microBlog.containing(words)) {
            i++;
            System.out.println("    " + i + ". " + ps.getText() + "\n");
        }

        return;
    }
}
