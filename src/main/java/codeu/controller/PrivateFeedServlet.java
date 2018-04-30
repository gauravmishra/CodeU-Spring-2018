package codeu.controller;

import codeu.model.data.Event;
import java.util.List;
import java.util.ArrayList;
import codeu.model.store.basic.EventStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet class responsible for the Activity Feed page. */
public class PrivateFeedServlet extends HttpServlet {
	private EventStore eventStore;
	/**
   * Set up state for handling conversation-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setEventStore(EventStore.getInstance());
  }

  /**
   * Sets the EventStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setEventStore(EventStore eventStore) {
    this.eventStore = eventStore;
  }


  /**
   * This function fires when a user navigates to the activity feed page. It gets all of the
   * events from the model and forwards to conversations.jsp for rendering the list.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {    
    List<Event> events = eventStore.getAllEvents();
    request.setAttribute("events", events);
    request.getRequestDispatcher("/WEB-INF/view/privateactivityfeed.jsp").forward(request, response);
    
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException {

    response.sendRedirect("/privateactivityfeed");

 }
}
