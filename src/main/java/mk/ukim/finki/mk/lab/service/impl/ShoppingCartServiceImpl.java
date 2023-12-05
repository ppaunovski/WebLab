package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.ShoppingCart;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.model.User;
import mk.ukim.finki.mk.lab.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.mk.lab.model.exceptions.OrderNotFound;
import mk.ukim.finki.mk.lab.model.exceptions.UserNotFoundException;
import mk.ukim.finki.mk.lab.repository.ShoppingCartRepository;
import mk.ukim.finki.mk.lab.repository.TicketOrderRepository;
import mk.ukim.finki.mk.lab.repository.UserRepository;
import mk.ukim.finki.mk.lab.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final TicketOrderRepository ticketOrderRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, TicketOrderRepository ticketOrderRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.ticketOrderRepository = ticketOrderRepository;
    }

    @Override
    public ShoppingCart getCart(String username) throws UnknownServiceException {
        return shoppingCartRepository
                .findByUserUsernameAndStatus(username, ShoppingCartStatus.CREATED)
                .orElseGet( () ->{
                            User user = this.userRepository.findByUsername(username)
                                    .orElseThrow(UserNotFoundException::new);
                            ShoppingCart shoppingCart = new ShoppingCart(user);
                            return this.shoppingCartRepository.save(shoppingCart);
                }
                );
    }

    @Override
    public ShoppingCart addToCart(String username, Long orderId) throws UnknownServiceException {
        TicketOrder order = ticketOrderRepository
                .findById(orderId)
                .orElseThrow(OrderNotFound::new);

        ShoppingCart shoppingCart = getCart(username);

        shoppingCart.getTicketOrders().add(order);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart updateDate(String username, LocalDateTime date) throws UnknownServiceException {
        ShoppingCart cart = this.getCart(username);
        cart.setDateCreated(date);
        return shoppingCartRepository.save(cart);
    }
}
