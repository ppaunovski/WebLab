package mk.ukim.finki.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.mk.lab.model.Movie;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.model.User;
import mk.ukim.finki.mk.lab.service.MovieService;
import mk.ukim.finki.mk.lab.service.TicketOrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;
    private final MovieService movieService;

    public TicketOrderController(TicketOrderService ticketOrderService, MovieService movieService) {
        this.ticketOrderService = ticketOrderService;
        this.movieService = movieService;
    }

    @GetMapping("/all/{clientName}")
    public String getTicketOrder(@PathVariable String clientName, Model model){

        List< TicketOrder > orders = ticketOrderService.getAllOrders(clientName);
        List<Movie> movies = movieService.listAll();


        model.addAttribute("tickets", orders);
        model.addAttribute("movies", movies);

        return "listAllTickets";
    }

    @GetMapping("/bought/{orderId}")
    public String getLastBoughtTicket(@PathVariable Long orderId, Model model, HttpServletRequest request){
        TicketOrder order;
        try {
            order = ticketOrderService.getOrderById(orderId);
        }
        catch (RuntimeException e){
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage());
            return "redirect:/movies?error=" + e.getMessage();
        }

        model.addAttribute("chosenMovie", order.getMovieTitle());
        model.addAttribute("numTickets", order.getNumberOfTickets());
        model.addAttribute("name", ((User)request.getSession().getAttribute("user")).getUsername());

        return "orderConfirmation";
    }

    @PostMapping("/all")
    public String listTicketForClient(HttpServletRequest request){
        String clientName = request.getParameter("clientToList");
        return "redirect:/tickets/all/" + clientName;
    }

    @PostMapping("/edit")
    public String getTicketEdit(@RequestParam Long ticketId,
                                @RequestParam String movieTitle,
                                @RequestParam Long numberOfTickets,
                                @RequestParam("orderDate") @DateTimeFormat(
                                        iso = DateTimeFormat.ISO.DATE_TIME)
                                    LocalDateTime orderDate,
                                Model model){
        TicketOrder order;
        try{
            order = ticketOrderService.editTicketOrder(ticketId, movieTitle, numberOfTickets, orderDate);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            return "redirect:movies?error=" + e.getMessage();
        }

        return "redirect:/cart";
    }
}
