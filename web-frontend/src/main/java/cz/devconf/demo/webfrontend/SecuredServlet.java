package cz.devconf.demo.webfrontend;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/securedServlet")
@ServletSecurity(
        @HttpConstraint(rolesAllowed = {"Admin", "Remover"})
)
public class SecuredServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.getOutputStream().println(
                "Hello world!\n" +
                "Your name: " + req.getRemoteUser() + "\n" +
                "You are admin: " + req.isUserInRole("Admin")
        );

    }

}
