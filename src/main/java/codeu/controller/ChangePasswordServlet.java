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
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

/** Servlet class responsible for the change password page. */
public class ChangePasswordServlet extends HttpServlet {

  /** Store class that gives access to Users and Messages. */
  private UserStore userStore;

  private MessageStore messageStore;

  /**
   * Set up state for handling password-changing-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * This function fires when a user requests the /changepassword URL. It simply forwards
   * the request to changepassword.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/changepassword.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the change password form. It gets the
   * username and password from the submitted form data, checks that they're valid, and
   * either adds the user to the session so we know the user is logged in or shows an error
   * to the user.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String oldpassword = request.getParameter("oldpassword");
    String username = (String) request.getSession().getAttribute("user");

    if (userStore.isUserRegistered(username)) {
      User user = userStore.getUser(username);
      if (BCrypt.checkpw(oldpassword, user.getPassword())) {
        if (request.getParameter("update") != null) {
          String newPassword = request.getParameter("newpassword");
          user.setPassword(newPassword);
          userStore.addUser(user);
          response.sendRedirect("/conversations");
        }
      } else {
        request.setAttribute("error", "Invalid password.");
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
      }
    } else {
      request.setAttribute("error", "No user logged in.");
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }
  }
}
