package mk.ukim.finki.mk.lab.service;

import mk.ukim.finki.mk.lab.model.TicketOrder;

import java.util.List;
import java.util.Set;

public interface TicketOrderService {
    TicketOrder placeOrder(String movieTitle, String clientName, String address, int numberOfTickets);
    TicketOrder getOrder(String clientName);
    List<TicketOrder> getAllOrders(String clientName);

    Set<String> getAllClients();

    TicketOrder getOrderById(Long orderId);

    TicketOrder editTicketOrder(Long ticketId, String movieTitle, Long numberOfTickets);
}
