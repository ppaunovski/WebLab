package mk.ukim.finki.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.service.TicketOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;

    public TicketOrderController(TicketOrderService ticketOrderService) {
        this.ticketOrderService = ticketOrderService;
    }

    @GetMapping("/all/{clientName}")
    public String getTicketOrder(@PathVariable String clientName, Model model){

        List< TicketOrder > orders = ticketOrderService.getAllOrders(clientName);

        model.addAttribute("tickets", orders);

        return "listAllTickets";
    }

    @GetMapping("/bought/{orderId}")
    public String getLastBoughtTicket(@PathVariable Long orderId, Model model){
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
        model.addAttribute("address", order.getClientAddress());
        model.addAttribute("name", order.getClientName());

        return "orderConfirmation";
    }

    @PostMapping("/all")
    public String listTicketForClient(HttpServletRequest request){
        String clientName = request.getParameter("clientToList");
        return "redirect:/tickets/all/" + clientName;
    }
}
