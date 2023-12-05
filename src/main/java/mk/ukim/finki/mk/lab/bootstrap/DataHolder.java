package mk.ukim.finki.mk.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.mk.lab.model.*;
import mk.ukim.finki.mk.lab.repository.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DataHolder {
    public static List<Movie> movies = null;
    public static List<TicketOrder> ticketOrderList = null;
    public static List<Production> productions = null;
    public static List<User> users;
    public static List<ShoppingCart> shoppingCarts;

    private final MovieRepository movieRepository;
    private final ProductionRepository productionRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final TicketOrderRepository ticketOrderRepository;

    public DataHolder(MovieRepository movieRepository,
                      ProductionRepository productionRepository,
                      UserRepository userRepository,
                      ShoppingCartRepository shoppingCartRepository,
                      TicketOrderRepository ticketOrderRepository) {
        this.movieRepository = movieRepository;
        this.productionRepository = productionRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.ticketOrderRepository = ticketOrderRepository;
    }

    @PostConstruct
    public void init(){
        ticketOrderList = new ArrayList<>();
        productions = new ArrayList<>();
        users = new ArrayList<>();
        shoppingCarts = new ArrayList<>();
        movies = new ArrayList<>();

        if(userRepository.count() == 0){
            users.add(new User("pavel.paunovski", "pp", "Pavel", "Paunovski", LocalDate.of(2002, 10, 16)));
            users.add(new User("test.test", "tt", "Test", "Testovski", LocalDate.of(2002, 10, 16)));

            userRepository.saveAll(users);
        }

        if(productionRepository.count() == 0){
            productions.add(new Production("20th Century Fox", "USA", "str. fox"));
            productions.add(new Production("Paramount Pictures", "USA", "str. para"));
            productions.add(new Production("Warner Bros", "USA", "str. bros"));
            productions.add(new Production("DreamWorks Pictures", "USA", "str. dream"));
            productions.add(new Production("Lionsgate", "USA", "str. lion"));

            productionRepository.saveAll(productions);
        }

        if(movieRepository.count() == 0){
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

            movieRepository.saveAll(movies);
        }

    }
}
