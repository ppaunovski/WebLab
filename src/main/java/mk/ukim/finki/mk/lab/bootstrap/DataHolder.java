package mk.ukim.finki.mk.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.mk.lab.model.Movie;
import mk.ukim.finki.mk.lab.model.Production;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DataHolder {
    public static List<Movie> movies = null;
    public static List<TicketOrder> ticketOrderList = null;
    public static List<Production> productions;

    @PostConstruct
    public void init(){
        ticketOrderList = new ArrayList<>();
        productions = new ArrayList<>();

        productions.add(new Production("20th Century Fox", "USA", "str. 1"));
        productions.add(new Production("Paramount Pictures", "USA", "str. 2"));
        productions.add(new Production("Warner Bros", "USA", "str. 3"));
        productions.add(new Production("DreamWorks Pictures", "USA", "str. 4"));
        productions.add(new Production("Lionsgate", "USA", "str. 5"));

        movies = new ArrayList<>();
        movies.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 9.3, productions.get((int) Math.round(Math.random()*4))));
        movies.add(new Movie("The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger...", 9.2, productions.get((int) Math.round(Math.random()*4))));
        movies.add(new Movie("The Dark Knight", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.", 9.0, productions.get((int) Math.round(Math.random()*4))));
        movies.add(new Movie("The Godfather Part II", "The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.", 9.0, productions.get((int) Math.round(Math.random()*4))));
        movies.add(new Movie("12 Angry Men", "The jury in a New York City murder trial is frustrated by a single member whose skeptical caution forces them to more carefully consider the evidence before jumping to a hasty verdict.", 9.0, productions.get((int) Math.round(Math.random()*4))));
        movies.add(new Movie("Schindler's List", "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", 9.0, productions.get((int) Math.round(Math.random()*4))));
        movies.add(new Movie("The Lord of the Rings: The Return of the King", "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.", 9.0, productions.get((int) Math.round(Math.random()*4))));
        movies.add(new Movie("Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", 8.9, productions.get((int) Math.round(Math.random()*4))));
        movies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.", 8.8, productions.get((int) Math.round(Math.random()*4))));
        movies.add(new Movie("The Good, the Bad and the Ugly", "A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery.", 8.8, productions.get((int) Math.round(Math.random()*4))));

        IntStream.range(0, 10).forEach(i -> {
            IntStream.range(0, (int) (Math.round(Math.random() * 9) + 1)).forEach(j -> ticketOrderList.add(new TicketOrder(movies.get((int) Math.round(Math.random() * 9)).getTitle(), "Client" + i, "addr" + i, Math.round(Math.random() * 9) + 1 )));
        });
    }
}
