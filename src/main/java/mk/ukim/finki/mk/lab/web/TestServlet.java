package mk.ukim.finki.mk.lab.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/test/servlet")
public class TestServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;

    public TestServlet(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        ServletConfig servletConfig = getServletConfig();
        HttpSession session = req.getSession();
        ServletContext servletContext = getServletContext();

        session.setAttribute("size", 100);
        servletContext.setAttribute("page", new Date());
        servletConfig.getInitParameter("day");
        Integer size = (Integer) session.getAttribute("size");

        context.setVariable("page", servletContext.getAttribute("page"));
        context.setVariable("day", servletConfig.getInitParameter("day"));
        context.setVariable("size", size);
        springTemplateEngine.process(
                "testServlet.html",
                context,
                resp.getWriter()
        );
    }
}
