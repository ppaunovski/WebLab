package mk.ukim.finki.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
public class TicketOrder {
    public String movieTitle;
    public Long numberOfTickets;
    @ManyToOne
    private ShoppingCart shoppingCart;
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    private LocalDateTime orderDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public TicketOrder(String movieTitle, Long numberOfTickets) {
        this.movieTitle = movieTitle;
        this.numberOfTickets = numberOfTickets;
        this.orderDate = LocalDateTime.now();
    }

    public TicketOrder(String movieTitle, Long numberOfTickets, LocalDateTime orderDate) {
        this.movieTitle = movieTitle;
        this.numberOfTickets = numberOfTickets;
        this.orderDate = orderDate;
    }

    public TicketOrder() {

    }
}
