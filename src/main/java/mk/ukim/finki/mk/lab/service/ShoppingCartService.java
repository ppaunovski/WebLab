package mk.ukim.finki.mk.lab.service;

import mk.ukim.finki.mk.lab.model.ShoppingCart;
import mk.ukim.finki.mk.lab.model.TicketOrder;

import java.net.UnknownServiceException;
import java.time.LocalDateTime;

public interface ShoppingCartService {
    ShoppingCart getCart(String username) throws UnknownServiceException;
    ShoppingCart addToCart(String username, Long orderId) throws UnknownServiceException;

    ShoppingCart updateDate(String username, LocalDateTime date) throws UnknownServiceException;
}
