package mk.ukim.finki.mk.lab.service;

import mk.ukim.finki.mk.lab.model.TicketOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface TicketOrderService {
    TicketOrder placeOrder(String movieTitle, String username, int numberOfTickets, LocalDateTime orderDate);

    List<TicketOrder> getAllOrders(String username);


    TicketOrder getOrderById(Long orderId);

    TicketOrder editTicketOrder(Long ticketId, String movieTitle, Long numberOfTickets, LocalDateTime orderDate);

    //void delete(Long orderId);
}
