<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Event" %>
<%@ page import="codeu.model.data.NewConversationEvent" %>
<%@ page import="codeu.model.data.LoginLogoutEvent" %>
<%@ page import="codeu.model.data.NewUserEvent" %>
<%@ page import="codeu.model.data.NewMessageEvent" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.EventStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>



<%
List <Event> events = (List<Event>) request.getAttribute("events");
%>

<!DOCTYPE html>
<html>
<head>
	<title> Private Activity Feed</title>
	<link rel="stylesheet" href="/css/main.css">
	<style>
		#activityfeed {
      background-color: white;
			height: 500px;
			overflow-y:scroll;
		}
	</style>
  <script>
    // scroll the chat div to the bottom
    function scrollEvents() {
      var eventDiv = document.getElementById('event');
      eventDiv.scrollTop = eventtDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollEvents()">
	<nav>
   <a id="navTitle" href="/">CodeU Chat App</a>
   <a href="/conversations">Conversations</a>
   <% if(request.getSession().getAttribute("user") != null){ %>
     <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
     <a href="/following">Following</a>
     <% } else{ %>
     <a href="/login">Login</a>
     <% } %>
     <a href="/register">Register</a>
     <a href="/about.jsp">About</a>
     <a href="/activityfeed">Activity Feed</a>
  </nav>
  	<div id="container">
  		<h1>Private Activity Feed</h1>
      <hr/>
  		<div id="privateactivityfeed">
        <ul>
          <%
          User user = (User) UserStore.getInstance().getUser((String) request.getSession().getAttribute("user"));
          for (Event event : events) {
          if (event.getEventType() == "message-event") {
            NewMessageEvent tempEvent = (NewMessageEvent) event;
            if (user.getFollowing().contains(tempEvent.getUserName())) {
              String message = tempEvent.toString();
              %>
              <li>
                <strong><%= message%> </strong>
              </li>
            <%
            }
          }
          else if (event.getEventType() == "login-event") {
            LoginLogoutEvent tempEvent = (LoginLogoutEvent) event;
            if (user.getFollowing().contains(tempEvent.getUserName())) {
              String message = tempEvent.toString();
              %>
              <li>
                <strong><%= message%> </strong>
              </li>
              <%
            }
          }
        }
        %>
      </ul>
  	</div>
    <form id="personalizeForm" action="/activityfeed" method="POST">
      <button type="submit">Global Activity Feed</button>
    </form>
  </div>
</body>
</html>