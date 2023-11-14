package mk.ukim.finki.mk.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.mk.lab.model.Movie;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.service.MovieService;
import mk.ukim.finki.mk.lab.service.TicketOrderService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = "")
public class MovieListServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final MovieService movieService;
    private final TicketOrderService ticketOrderService;

    public MovieListServlet(SpringTemplateEngine springTemplateEngine, MovieService movieService, TicketOrderService ticketOrderService){
        this.movieService = movieService;
        this.springTemplateEngine = springTemplateEngine;
        this.ticketOrderService = ticketOrderService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        context.setVariable("ipAddress", req.getRemoteAddr());

        String search = "";
        String rating = "";
        if(req.getQueryString() != null){
            if(req.getQueryString().split("&")[0].split("=").length == 2){
                search = req.getQueryString().split("&")[0].split("=")[1];
            }

            if(req.getQueryString().split("&")[1].split("=").length == 2){
                rating = req.getQueryString().split("&")[1].split("=")[1];
            }
        }
        List<Movie> movies;

        if(!search.isEmpty() && !rating.isEmpty()){
            movies = movieService.searchMoviesByTextAndRating(search, Double.parseDouble(rating));
        }
        else if(!search.isEmpty()){
            movies = movieService.searchMoviesByText(search);
        }
        else if (!rating.isEmpty()){
            movies = movieService.searchMoviesByRating(Double.parseDouble(rating));
        }else {
            movies = movieService.listAll();
        }

        Set<String> clients = ticketOrderService.getAllClients();

        context.setVariable("movies", movies);
        context.setVariable("clients", clients);

        springTemplateEngine.process(
                "listMovies.html",
                context,
                resp.getWriter()
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chosenMovie = req.getParameter("chosenMovie");
        String numTickets = req.getParameter("numTickets");
        String address = req.getLocalAddr();
        String name = req.getParameter("name");
        TicketOrder order = ticketOrderService.placeOrder(chosenMovie, name, address, Integer.parseInt(numTickets));
        resp.sendRedirect("/ticketOrder?clientName="+order.getId());
    }




}
