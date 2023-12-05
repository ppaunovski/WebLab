package mk.ukim.finki.mk.lab.repository;

import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long> {
    Optional<TicketOrder> findByUserUsername(String username);
    List<TicketOrder> findAllByUserUsername(String username);
    List<TicketOrder> findAllByOrderDateAfterAndOrderDateBefore(LocalDateTime after, LocalDateTime before);
}
