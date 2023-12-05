package mk.ukim.finki.mk.lab.web.controller;

import mk.ukim.finki.mk.lab.model.ShoppingCart;
import mk.ukim.finki.mk.lab.model.User;
import mk.ukim.finki.mk.lab.service.MovieService;
import mk.ukim.finki.mk.lab.service.ShoppingCartService;
import mk.ukim.finki.mk.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.UnknownServiceException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final MovieService movieService;

    public UserController(UserService userService, ShoppingCartService shoppingCartService, MovieService movieService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.movieService = movieService;
    }

    @GetMapping("/all")
    String getAllUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "listAllUsers";
    }

    @GetMapping("/{userId}")
    String getOrdersForUser(@PathVariable Long userId, Model model){
        User user = null;
        try{
            user = userService.findById(userId);
            ShoppingCart cart = shoppingCartService.getCart(user.getUsername());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("cart", cart);
            model.addAttribute("movies", movieService.listAll());
            model.addAttribute("tickets", cart.getTicketOrders());
            model.addAttribute("datetime", cart.getDateCreated());
        } catch (UnknownServiceException e) {
            System.out.println(e.getMessage());
            return "redirect:/user/" + userId + "?error=" + e.getMessage();
        }

        return "listAllTickets";

    }
}
