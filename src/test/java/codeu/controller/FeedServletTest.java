package codeu.controller;

import codeu.model.data.Event;
import codeu.model.store.basic.EventStore;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class FeedServletTest {

 private FeedServlet feedServlet;
 private HttpServletRequest mockRequest;
 private HttpServletResponse mockResponse;
 private RequestDispatcher mockRequestDispatcher;
 private EventStore mockEventStore;

 @Before
 public void setup() {
   feedServlet = new FeedServlet();
   mockRequest = Mockito.mock(HttpServletRequest.class);
   mockResponse = Mockito.mock(HttpServletResponse.class);
   mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);

   mockEventStore = Mockito.mock(EventStore.class);
    feedServlet.setEventStore(mockEventStore);
   Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp"))
       .thenReturn(mockRequestDispatcher);
 }

 @Test
 public void testDoGet() throws IOException, ServletException {
   feedServlet.doGet(mockRequest, mockResponse);

   Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
 }
}