package mk.ukim.finki.mk.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.mk.lab.model.TicketOrder;
import mk.ukim.finki.mk.lab.service.MovieService;
import mk.ukim.finki.mk.lab.service.TicketOrderService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/AllTickets")
public class AllTicketsServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final TicketOrderService ticketOrderService;

    public AllTicketsServlet(SpringTemplateEngine springTemplateEngine, TicketOrderService ticketOrderService) {
        this.springTemplateEngine = springTemplateEngine;
        this.ticketOrderService = ticketOrderService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String clientName = req.getQueryString();

        clientName = clientName.split("=")[1];

        System.out.println(clientName);

        List<TicketOrder> ticketOrders = ticketOrderService.getAllOrders(clientName);

        context.setVariable("tickets", ticketOrders);
        springTemplateEngine.process(
                "listAllTickets.html",
                context,
                resp.getWriter()
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getParameter("clientToList"));
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String clientName = req.getParameter("clientToList");


        List<TicketOrder> ticketOrders = ticketOrderService.getAllOrders(clientName);

        context.setVariable("tickets", ticketOrders);
        springTemplateEngine.process(
                "listAllTickets.html",
                context,
                resp.getWriter()
        );
    }
}
