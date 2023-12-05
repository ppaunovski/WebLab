package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.model.User;
import mk.ukim.finki.mk.lab.model.exceptions.OrderNotFound;
import mk.ukim.finki.mk.lab.repository.InMemory.InMemTicketOrderRepository;
import mk.ukim.finki.mk.lab.repository.ShoppingCartRepository;
import mk.ukim.finki.mk.lab.repository.TicketOrderRepository;
import mk.ukim.finki.mk.lab.repository.UserRepository;
import mk.ukim.finki.mk.lab.service.TicketOrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TicketOrderServiceImpl implements TicketOrderService {
    private final TicketOrderRepository ticketOrderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    public TicketOrderServiceImpl(TicketOrderRepository ticketOrderRepository, ShoppingCartRepository shoppingCartRepository, UserRepository userRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TicketOrder placeOrder(String movieTitle, String username, int numberOfTickets, LocalDateTime orderDate) {
        Optional<User> user = userRepository.findByUsername(username);
        LocalDateTime date = orderDate == null ? LocalDateTime.now() : orderDate;
        TicketOrder ticketOrder = new TicketOrder(movieTitle, (long) numberOfTickets, date.minusNanos(date.getNano()));
        return ticketOrderRepository.save(ticketOrder);
    }

    @Override
    public TicketOrder getOrder(String username){
        Optional<TicketOrder> ticketOrder = ticketOrderRepository.findByUserUsername(username);
        return ticketOrder.orElseThrow(OrderNotFound::new);

    }

    public List<TicketOrder> getAllOrders(String username){
        return ticketOrderRepository.findAllByUserUsername(username);
    }

    @Override
    public Set<String> getAllClients() {
        return new HashSet<>();
    }

    @Override
    public TicketOrder getOrderById(Long orderId) throws RuntimeException {
        return ticketOrderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Invalid order id"));
    }

    @Override
    public TicketOrder editTicketOrder(Long ticketId, String movieTitle, Long numberOfTickets, LocalDateTime orderDate) {
        TicketOrder ticketOrder = ticketOrderRepository.findById(ticketId).orElseThrow(OrderNotFound::new);
        ticketOrder.setNumberOfTickets(numberOfTickets);
        ticketOrder.setMovieTitle(movieTitle);
        ticketOrder.setOrderDate(orderDate);
        return ticketOrderRepository.save(ticketOrder);
    }

}
