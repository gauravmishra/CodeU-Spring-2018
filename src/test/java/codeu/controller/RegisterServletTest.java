// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

public class RegisterServletTest {

  private RegisterServlet registerServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;

  @Before
  /**
   * creates new registerservlet object and then creates mock objects
   */
  public void setup() throws IOException {
    registerServlet = new RegisterServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/register.jsp")).thenReturn(mockRequestDispatcher);
  }

  @Test
  /**
   * Prints a test message to verify whether or not the registerServlet is receiving
   * the valid responses and requests
   */
  public void testDoGet() throws IOException, ServletException {
    registerServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
  /**
   * Prints a test message whenever the username that is being entered into the registration
   * page contains invalid characters.
   */
  @Test
  public void testDoPost_BadUsername() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("bad !@#$% username");

    registerServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest)
        .setAttribute("error", "Please enter only letters, numbers, and spaces.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
  /**
   * Prints test message whenever the user attempts to register with a username that
   * is already being used.
   */
  @Test
  public void testDoPost_ExistingUser() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("existing username");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("existing password");

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    Mockito.when(mockUserStore.isUserRegistered("existing username")).thenReturn(true);
    registerServlet.setUserStore(mockUserStore);

    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    registerServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockUserStore, Mockito.never()).addUser(Mockito.any(User.class));

    Mockito.verify(mockRequest).setAttribute("error", "That username is already taken.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest,mockResponse);

  }
}