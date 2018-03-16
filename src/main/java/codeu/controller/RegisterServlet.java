package codeu.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Servlet class responsible for user registration.
*/
public class RegisterServlet extends HttpServlet {
/**
* doGet function will eventually output HTML directly from servlet
*/
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException {

   response.getWriter().println("RegisterServlet GET request.");
 }
}
