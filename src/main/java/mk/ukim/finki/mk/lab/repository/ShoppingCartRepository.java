package mk.ukim.finki.mk.lab.repository;

import mk.ukim.finki.mk.lab.model.ShoppingCart;
import mk.ukim.finki.mk.lab.model.User;
import mk.ukim.finki.mk.lab.model.enumerations.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserUsername(String username);
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus created);

    Optional<ShoppingCart> findByUserUsernameAndStatus(String username, ShoppingCartStatus created);

}
