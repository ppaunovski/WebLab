package mk.ukim.finki.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.mk.lab.model.Movie;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.model.User;
import mk.ukim.finki.mk.lab.model.exceptions.InvalidIdException;
import mk.ukim.finki.mk.lab.service.MovieService;
import mk.ukim.finki.mk.lab.service.ProductionService;
import mk.ukim.finki.mk.lab.service.TicketOrderService;
import mk.ukim.finki.mk.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final TicketOrderService ticketOrderService;
    private final ProductionService productionService;
    private final UserService userService;

    public MovieController(MovieService movieService, TicketOrderService ticketOrderService, ProductionService productionService, UserService userService) {
        this.movieService = movieService;
        this.ticketOrderService = ticketOrderService;
        this.productionService = productionService;
        this.userService = userService;
    }

    @GetMapping
    public String getMoviesPage(HttpServletRequest request, @RequestParam(required = false) String error, @RequestParam(required = false) String search, @RequestParam(required = false) String rating , Model model){
        Integer visits = (Integer) request.getServletContext().getAttribute("numberOfVisits");
        request.getServletContext().setAttribute("numberOfVisits", ++visits);
        Set<String> clients = userService.getAllUsernames();
        //Set<String> clients = ticketOrderService.getAllClients();
        model.addAttribute("clients", clients);
        if(search != null && !search.isEmpty() && rating != null && !rating.isEmpty()){
            model.addAttribute("movies", movieService.searchMoviesByTextAndRating(search, Double.parseDouble(rating)));
        }
        else if(search != null && !search.isEmpty()){
            model.addAttribute("movies", movieService.searchMoviesByText(search));
        }
        else if(rating != null && !rating.isEmpty()){
            model.addAttribute("movies", movieService.searchMoviesByRating(Double.parseDouble(rating)));
        }
        else {
            model.addAttribute("movies", movieService.listAll());
        }
        model.addAttribute("hasError", false);
        model.addAttribute("error", error);
        model.addAttribute("visits", visits);

        return "listMovies";
    }

//    @PostMapping
//    public String buyTickets (@RequestParam Long chosenMovie,
//                              @RequestParam String numTickets,
//                              HttpServletRequest request){
//
//        TicketOrder order = ticketOrderService.placeOrder(movieService.findById(chosenMovie).getTitle(), ((User) request.getSession().getAttribute("user")).getUsername() , Integer.parseInt(numTickets));
//        return "redirect:/tickets/bought/" + order.getId();
//    }

    @GetMapping("/add")
    public String getAddMoviePage(@RequestParam(required = false) String error,
                              Model model){
        //producers
        model.addAttribute("hasError", false);
        model.addAttribute("error", error);
        model.addAttribute("producers", productionService.findAll());
        model.addAttribute("action", "add");

        return "add-movie";
    }

    @PostMapping("/add")
    public String saveMovie(
            @RequestParam String movieTitle,
            @RequestParam String summary,
            @RequestParam Double rating,
            @RequestParam Long productionId,
            HttpServletRequest request,
            Model model
                            ){

        try{
            movieService.add(movieTitle, summary, rating, productionId);
        }
        catch (RuntimeException e){
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/movies/add?error=" + e.getMessage() ;
        }
        model.addAttribute("hasError", false);
        model.addAttribute("error", "");

        return "redirect:/movies";
    }

    @GetMapping("/edit-form/{movieId}")
    public String getEditMovieForm(@PathVariable Long movieId, Model model){
        Movie movie;
        try{
            movie = movieService.findById(movieId);
        }
        catch (InvalidIdException e){
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage());
            return "redirect:/movies?error=" + e.getMessage();
        }
        model.addAttribute("hasError", false);
        model.addAttribute("error", "");
        model.addAttribute("movie", movie);
        model.addAttribute("producers", productionService.findAll());
        model.addAttribute("action", "edit/" + movieId);
        return "add-movie";
    }

    // kako bi bilo so put?????
    @PostMapping("/edit/{movieId}")
    public String editMovie(@PathVariable Long movieId,
                            @RequestParam String movieTitle,
                            @RequestParam String summary,
                            @RequestParam Double rating,
                            @RequestParam Long productionId,
                            HttpServletRequest request, Model model){

        try{
            movieService.editMovieById(movieId, movieTitle, summary, rating, productionId);
        }
        catch (RuntimeException e){
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/movies/edit-form/" + movieId + "?error="+e.getMessage();
        }
        model.addAttribute("hasError", false);
        model.addAttribute("error", "");

        return "redirect:/movies";
    }

    // kako da bide DeleteMapping? kako da ispratam delete od html?
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id, Model model){

        try{
            movieService.deleteById(id);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("hasError", false);
        model.addAttribute("error", "");


        return "redirect:/movies";
    }

}
