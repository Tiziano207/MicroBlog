package SocialNetwork;

public class BatteriaDiTest4 {
    // Batteria in cui tre utenti si iscrivono postano e aggiungono like
    // verrà utilizzato il filtro del linguaggio e la possibilità di segnalare i
    // Post
    public static void main(String[] args) throws Exception {
        SocialNetworkReportOffensivePost microBlogFiltered = new SocialNetworkReportOffensivePost();
        // gli utenti si iscrivono al SocialNetwork
        microBlogFiltered.addUser("Zio Paperone");
        microBlogFiltered.addUser("Paperino");
        microBlogFiltered.addUser("Paperoga");
        microBlogFiltered.addUser("Giornalista Insistente");
        // creo una BlackList di parole che non possono essere usate
        microBlogFiltered.addBlackList("perdincibacco");
        microBlogFiltered.addBlackList("accidenti");
        microBlogFiltered.addBlackList("perdindirindina");
        // gli utenti pubblicanop Post nella rete Sociale
        microBlogFiltered.addPost("Giornalista Insistente", "Mi concede l'intervista?");
        microBlogFiltered.addPost("Zio Paperone", "accidenti!");
        microBlogFiltered.addPost("Paperino", "Forse!");
        microBlogFiltered.addPost("Paperoga", "Bene zio il tuo addetto stampa, ti offro la possibilità di ...");
        microBlogFiltered.addPost("Zio Paperone", "Tu non sei il mio addetto Stampa");

        // gli utenti seguono i post di altri utenti
        microBlogFiltered.addLike("Giornalista Insistente", 2);

        // Post prima delle segnalazioni
        for (int t : microBlogFiltered.getPost().keySet()) {
            System.out.println("    " + t + ". " + microBlogFiltered.getPost().get(t).getText() + "\n");
        }
        // segnalazione del post
        microBlogFiltered.addReport("Paperino", 4);
        microBlogFiltered.addReport("Paperoga", 4);
        microBlogFiltered.addReport("Giornalista Insistente", 4);
        // Giornalista Insistente prova a segnalare due volte lo stesso post
        microBlogFiltered.addReport("Giornalista Insistente", 4);

        // un Utente non registrato prova a segnalare il Post
        microBlogFiltered.addReport("Archimede", 3);

        // controllo se ci sono abbastanza segnalazioni per poter eliminare il post
        microBlogFiltered.controlReportMap();

        // Post dopo le segnalazioni
        for (int t : microBlogFiltered.getPost().keySet()) {
            System.out.println("    " + t + ". " + microBlogFiltered.getPost().get(t).getText() + "\n");
        }

        return;
    }
}
