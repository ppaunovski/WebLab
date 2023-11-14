package mk.ukim.finki.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.mk.lab.model.Movie;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.model.exceptions.InvalidIdException;
import mk.ukim.finki.mk.lab.service.MovieService;
import mk.ukim.finki.mk.lab.service.ProductionService;
import mk.ukim.finki.mk.lab.service.TicketOrderService;
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

    public MovieController(MovieService movieService, TicketOrderService ticketOrderService, ProductionService productionService) {
        this.movieService = movieService;
        this.ticketOrderService = ticketOrderService;
        this.productionService = productionService;
    }

    @GetMapping
    public String getMoviesPage(@RequestParam(required = false) String error, @RequestParam(required = false) String search, @RequestParam(required = false) String rating , Model model){
        Set<String> clients = ticketOrderService.getAllClients();
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

        return "listMovies";
    }

    @PostMapping
    public String buyTickets (@RequestParam Long chosenMovie,
                              @RequestParam String numTickets,
                              @RequestParam String name){

        TicketOrder order = ticketOrderService.placeOrder(movieService.findById(chosenMovie).getTitle(), name, "addr", Integer.parseInt(numTickets));
        return "redirect:/tickets/bought/" + order.getId();
    }

    @GetMapping("/add")
    public String getAddMoviePage(@RequestParam(required = false) String error,
                              Model model){
        //producers
        model.addAttribute("hasError", false);
        model.addAttribute("error", "");
        model.addAttribute("producers", productionService.findAll());
        model.addAttribute("action", "add");

        return "add-movie";
    }

    @PostMapping("/add")
    public String saveMovie(
            HttpServletRequest request,
            Model model
                            ){

        try{
            movieService.add(request.getParameter("movieTitle"), request.getParameter("summary"), request.getParameter("rating"), request.getParameter("productionId"));
        }
        catch (RuntimeException e){
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/movies/add";
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
    public String editMovie(@PathVariable Long movieId,HttpServletRequest request, Model model){
        String title = request.getParameter("movieTitle");
        String summary = request.getParameter("summary");
        String rating = request.getParameter("rating");
        String productionId = request.getParameter("productionId");
        try{
            movieService.editMovieById(movieId, title, summary, rating, productionId);
        }
        catch (RuntimeException e){
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage());
            return "redirect:/edit/" + movieId;
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
