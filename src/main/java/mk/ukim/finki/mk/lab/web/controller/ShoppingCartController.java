package mk.ukim.finki.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.mk.lab.model.Movie;
import mk.ukim.finki.mk.lab.model.ShoppingCart;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.model.User;
import mk.ukim.finki.mk.lab.service.MovieService;
import mk.ukim.finki.mk.lab.service.ShoppingCartService;
import mk.ukim.finki.mk.lab.service.TicketOrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownServiceException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    private final TicketOrderService ticketOrderService;
    private final MovieService movieService;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(TicketOrderService ticketOrderService, MovieService movieService, ShoppingCartService shoppingCartService) {
        this.ticketOrderService = ticketOrderService;
        this.movieService = movieService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/add")
    String addToCart(@RequestParam Long chosenMovie,
                     @RequestParam String numTickets,
                     @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                     HttpServletRequest request){
        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        TicketOrder order = ticketOrderService.placeOrder(movieService.findById(chosenMovie).getTitle(), username , Integer.parseInt(numTickets), localDateTime);
        try {
            ShoppingCart cart = shoppingCartService.addToCart(username, order.getId());
        } catch (UnknownServiceException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/cart";
    }



    @GetMapping()
    public String getCart(HttpServletRequest request, Model model){
        String username = ((User) request.getSession().getAttribute("user")).getUsername();

        ShoppingCart cart = null;
        List< TicketOrder > orders;
        try {
            cart = shoppingCartService.getCart(username);
            orders = cart.getTicketOrders();
            List<Movie> movies = movieService.listAll();


            model.addAttribute("tickets", orders);
            model.addAttribute("movies", movies);
            model.addAttribute("datetime", cart.getDateCreated());
        } catch (UnknownServiceException e) {
            System.out.println(e.getMessage());
        }



        return "listAllTickets";
    }

//    @PostMapping("/update-date")
//    String updateDate(@RequestParam("date")
//                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
//                      Model model,
//                      HttpServletRequest request){
//        String username = ((User) request.getSession().getAttribute("user")).getUsername();
//
//        ShoppingCart cart = null;
//        List< TicketOrder > orders;
//        try {
//            cart = shoppingCartService.updateDate(username, date);
//            orders = cart.getTicketOrders();
//            List<Movie> movies = movieService.listAll();
//
//
//            model.addAttribute("tickets", orders);
//            model.addAttribute("movies", movies);
//            model.addAttribute("date", cart.getDateCreated().toLocalDate());
//            model.addAttribute("time", cart.getDateCreated().toLocalTime());
//            model.addAttribute("datetime", cart.getDateCreated());
//        } catch (UnknownServiceException e) {
//            System.out.println(e.getMessage());
//        }
//
//
//
//        return "listAllTickets";
//    }

}
