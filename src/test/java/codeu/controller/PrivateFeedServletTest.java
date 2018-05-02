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

public class PrivateFeedServletTest {

 private PrivateFeedServlet privateFeedServlet;
 private HttpServletRequest mockRequest;
 private HttpServletResponse mockResponse;
 private RequestDispatcher mockRequestDispatcher;
 private EventStore mockEventStore;

 @Before
 public void setup() throws IOException {
   privateFeedServlet = new PrivateFeedServlet();
   mockRequest = Mockito.mock(HttpServletRequest.class);
   mockResponse = Mockito.mock(HttpServletResponse.class);
   mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
   mockEventStore = Mockito.mock(EventStore.class);
    privateFeedServlet.setEventStore(mockEventStore);
   Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/privateactivityfeed.jsp"))
       .thenReturn(mockRequestDispatcher);

 }

 @Test
 public void testDoGet() throws IOException, ServletException {
   privateFeedServlet.doGet(mockRequest, mockResponse);
   Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
 }
}