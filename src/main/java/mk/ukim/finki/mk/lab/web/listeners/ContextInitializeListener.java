package mk.ukim.finki.mk.lab.web.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextInitializeListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("numberOfVisits", 0);
    }
}
