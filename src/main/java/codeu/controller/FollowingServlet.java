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

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the conversations page. */
public class FollowingServlet extends HttpServlet {

    /** Store class that gives access to Users. */
    private UserStore userStore;


    /**
     * Set up state for handling conversation-related requests. This method is only called when
     * running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        setUserStore(UserStore.getInstance());
    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }


    /**
     * This function fires when a user navigates to the following page. It gets all of the
     * users that this user follows from the model and forwards to following.jsp for
     * rendering the list.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = (String) request.getSession().getAttribute("user");
        List<String> following = new ArrayList<>();
        if(username!=null && !username.isEmpty())
            following = userStore.getFollowing(username);
        request.setAttribute("following", following);
        request.getRequestDispatcher("/WEB-INF/view/following.jsp").forward(request, response);
    }

    /**
     * This function fires when a user submits the form on the following page. It gets the
     * logged-in username from the session and the user to follow from the submitted form
     * data. It uses this to edit the following user in the model.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	String username = (String) request.getSession().getAttribute("user");
			List<String> following = new ArrayList<>();
			if(username!=null && !username.isEmpty()) {
				following = userStore.getFollowing(username);
			}
			request.setAttribute("following", following);

			if (username == null) {
				// user is not logged in, don't let them follow anybody
				response.sendRedirect("/login");
				return;
			}

			User user = userStore.getUser(username);
			if (user == null) {
				// user was not found, don't let them follow anybody
				response.sendRedirect("/login");
				return;
			}

			String followUser = request.getParameter("followUserName");
      if(!userStore.isUserRegistered(followUser)) {
        //if the target user is not registered, send error message
				request.setAttribute("error", "That username was not found.");
				request.getRequestDispatcher("/WEB-INF/view/following.jsp").forward(request, response);
				return;
      }

			if(following.contains(followUser)) {
				//if the target user is already followed, send error message
				request.setAttribute("error", "User already followed.");
				request.getRequestDispatcher("/WEB-INF/view/following.jsp").forward(request, response);
				return;
			}

			String newFollowingList;
      if(user.getFollowingUsersString().equals("") || user.getFollowingUsersString() == null) {
				newFollowingList = followUser;
			}
			else {
      	newFollowingList = user.getFollowingUsersString() + "," + followUser;
      }

      User newUser = new User(user.getId(), user.getName(), user.getPassword(), user.getCreationTime(), newFollowingList);
      userStore.addUser(newUser);
      response.sendRedirect("/following");
    }
}
