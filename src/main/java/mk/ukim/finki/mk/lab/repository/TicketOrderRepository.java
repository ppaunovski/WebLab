package mk.ukim.finki.mk.lab.repository;

import mk.ukim.finki.mk.lab.bootstrap.DataHolder;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TicketOrderRepository {

    public TicketOrder add(TicketOrder ticketOrder) {
        //DataHolder.ticketOrder = ticketOrder;
        DataHolder.ticketOrderList.add(ticketOrder);
        //DataHolder.ticketOrdersPerUsername.putIfAbsent(ticketOrder.clientName, new ArrayList<TicketOrder>());
        //DataHolder.ticketOrdersPerUsername.get(ticketOrder.clientName).add(ticketOrder);
        return ticketOrder;
    }

    public Optional<TicketOrder> get(String clientName){
        List<TicketOrder> ticketOrders = DataHolder.ticketOrderList.stream()
                .filter(t -> t.getClientName().equals(clientName))
                .collect(Collectors.toList());
        return Optional.of(ticketOrders.get(ticketOrders.size()-1));
    }

    public List<TicketOrder> getAll(String clientName) {
        return DataHolder.ticketOrderList.stream()
                .filter(t -> t.getClientName().equals(clientName))
                .collect(Collectors.toList());
    }

    public Set<String> getAllClients() {
        return DataHolder.ticketOrderList.stream()
                .map(TicketOrder::getClientName)
                .collect(Collectors.toSet());
    }

    public Optional<TicketOrder> findById(Long orderId) {
        return DataHolder.ticketOrderList.stream()
                .filter(t -> t.getId().equals(orderId))
                .findFirst();
    }
}
