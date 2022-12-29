package SocialNetwork;

import java.util.*;

public class BatteriaDiTest3 {
    // Batteria in cui tre utenti si iscrivono postano e aggiungono like
    // Un Utente prova a inserire un Username già preso si sbaglia e immette il
    // valore stringa vuota come Username
    // Un utente prova a scrivere un post con più di 140 caratteri
    public static void main(String[] args) throws Exception {
        SocialNetwork microBlog = new SocialNetwork();
        // gli utenti si iscrivono al SocialNetwork
        microBlog.addUser("Zio Paperone");
        microBlog.addUser("Paperino");
        microBlog.addUser("Paperinik");
        microBlog.addUser("Paperinik");
        microBlog.addUser("");
        // gli utenti pubblicanop Post nella rete Sociale
        microBlog.addPost("Zio Paperone", "TSK! Non posso certo dilapidare il mio patrimonio in corrente elettrica!");
        microBlog.addPost("Paperino", "La tua tirchieria sta raggiungendo livelli da record!");
        microBlog.addPost("Paperinik",
                "Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah");
        // gli utenti seguono i post di altri utenti

        microBlog.addLike("Zio Paperone", 2);

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
        System.out.println("\nIl metodo writtenBy derivato da Lista di Post restituisce:\n  Paperino ha postato:\n");
        int j = 0;
        for (Post ps : microBlog.writtenBy(postList, "Paperino")) {
            j++;
            System.out.println("        " + j + ". " + ps.getText() + "\n");
        }
        // utlizzo il metodo containing per trovare tutti i post che contengono almeno
        // una delle parole nella lista
        // inizializzo una lista contenente alcune parole da cercare all'interno del
        // post
        ArrayList<String> words = new ArrayList<>();
        words.add("TS");

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