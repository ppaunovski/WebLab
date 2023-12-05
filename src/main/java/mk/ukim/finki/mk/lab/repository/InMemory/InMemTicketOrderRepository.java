package mk.ukim.finki.mk.lab.repository.InMemory;

import mk.ukim.finki.mk.lab.bootstrap.DataHolder;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class InMemTicketOrderRepository {

    public TicketOrder add(TicketOrder ticketOrder) {
        //DataHolder.ticketOrder = ticketOrder;
        DataHolder.ticketOrderList.add(ticketOrder);
        //DataHolder.ticketOrdersPerUsername.putIfAbsent(ticketOrder.clientName, new ArrayList<TicketOrder>());
        //DataHolder.ticketOrdersPerUsername.get(ticketOrder.clientName).add(ticketOrder);
        return ticketOrder;
    }

    public Optional<TicketOrder> get(String username){
        List<TicketOrder> ticketOrders = DataHolder.ticketOrderList.stream()
                .filter(t -> t.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
        return Optional.of(ticketOrders.get(ticketOrders.size()-1));
    }

    public List<TicketOrder> getAll(String username) {
        return DataHolder.ticketOrderList.stream()
                .filter(t -> t.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public Set<String> getAllClients() {
        return DataHolder.ticketOrderList.stream()
                .map(t -> t.getUser().getUsername())
                .collect(Collectors.toSet());
    }

    public Optional<TicketOrder> findById(Long orderId) {
        return DataHolder.ticketOrderList.stream()
                .filter(t -> t.getId().equals(orderId))
                .findFirst();
    }
}
