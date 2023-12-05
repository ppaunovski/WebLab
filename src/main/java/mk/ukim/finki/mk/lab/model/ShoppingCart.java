package mk.ukim.finki.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.mk.lab.model.enumerations.ShoppingCartStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    private LocalDateTime dateCreated;
    @OneToMany(mappedBy = "shoppingCart")
    private List<TicketOrder> ticketOrders;
    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;


    public ShoppingCart(User user) {
        this.user = user;
        dateCreated = LocalDateTime.now();
        ticketOrders = new ArrayList<>();
        status = ShoppingCartStatus.CREATED;
    }

    public ShoppingCart() {

    }


}
