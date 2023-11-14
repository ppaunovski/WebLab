package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.repository.TicketOrderRepository;
import mk.ukim.finki.mk.lab.service.TicketOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TicketOrderServiceImpl implements TicketOrderService {
    private final TicketOrderRepository ticketOrderRepository;

    public TicketOrderServiceImpl(TicketOrderRepository ticketOrderRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
    }

    @Override
    public TicketOrder placeOrder(String movieTitle, String clientName, String address, int numberOfTickets) {
        TicketOrder ticketOrder = new TicketOrder(movieTitle, clientName, address, (long) numberOfTickets);
        return ticketOrderRepository.add(ticketOrder);
    }

    @Override
    public TicketOrder getOrder(String clientName){
        Optional<TicketOrder> ticketOrder = ticketOrderRepository.get(clientName);
        return ticketOrder.get();

    }

    public List<TicketOrder> getAllOrders(String clientName){
        return ticketOrderRepository.getAll(clientName);
    }

    @Override
    public Set<String> getAllClients() {
        return ticketOrderRepository.getAllClients();
    }

    @Override
    public TicketOrder getOrderById(Long orderId) throws RuntimeException {
        return ticketOrderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Invalid order id"));
    }

}
