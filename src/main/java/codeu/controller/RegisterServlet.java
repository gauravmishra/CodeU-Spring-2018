package codeu.controller;

import codeu.model.store.basic.UserStore;
import codeu.model.data.User;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Instant;
import java.util.UUID;
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
  * Store class that gives access to Users.
  */
 private UserStore userStore;
 
 /**
  * Set up state for handling registration-related requests. This method is only called when
  * running in a server, not when running in a test.
  */
 @Override
 public void init() throws ServletException {
   super.init();
   setUserStore(UserStore.getInstance());
 }
 
 /**
  * Sets the UserStore used by this servlet. This function provides a common setup method
  * for use by the test framework or the servlet's init() function.
  */
 void setUserStore(UserStore userStore) {
   this.userStore = userStore;
 }
/**
* doGet function will eventually output HTML directly from servlet
*/
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException {
   request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request,response);
 }

  @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException {

   String username = request.getParameter("username");
   String password = request.getParameter("password");
   String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
   /** Error checks whether or not the username contains valid characters. */
   if (!username.matches("[\\w*\\s*]*")) {
     request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
     request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
     return;
   }
   /** Error checks whether or not the username is already in use. */
   if (userStore.isUserRegistered(username)) {
     request.setAttribute("error", "That username is already taken.");
     request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
     return;
   }

   /** Adds user to the userStore database. Then redirects the user to page. */
   User user = new User(UUID.randomUUID(), username, passwordHash, Instant.now());
   userStore.addUser(user);

   response.sendRedirect("/login");
 }
}

