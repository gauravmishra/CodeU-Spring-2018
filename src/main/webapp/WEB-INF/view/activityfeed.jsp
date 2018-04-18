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
	<title>Activity Feed</title>
	<link rel="stylesheet" href="/css/main.css">
	<style>
		.activityfeed {
			padding: 5px;
			font: 24px/36px;
			height: 600px;
			overflow-y:scroll;
		}
	</style>
</head>
<body>
	<nav>
    	<a id="navTitle" href="/">CodeU Chat App</a>
    	<a href="/conversations">Conversations</a>
    	<% if(request.getSession().getAttribute("user") != null){ %>
      		<a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    	<% } else{ %>
      		<a href="/login">Login</a>
    	<% } %>
    	<a href="/register">Register</a>
    	<a href="/about.jsp">About</a>
    	<a href="/activityfeed">Activity Feed</a>
  	</nav>
  	<div id="container">
  		<h1>Activity Feed</h1>
  		<div id="activityfeed">
  			<ul>
  				<%
  				User user = (User) UserStore.getInstance().getUser((String) request.getSession().getAttribute("user"));
  				for (Event event : events) {
  					String eventType = event.getEventType();
  					String message = "";
  					if (eventType == "register-event") {
  						message = event.toString();
  				}
  					else if(eventType == "login-event") {
  						message = event.toString();
  				}
  					else if (eventType == "conversation-event") {
  						message = event.toString();
  				}
  					else if (eventType == "register-event") {
  						message = event.toString();
  				}
  				%>
  				<li>
  					<strong><%= message%> </strong>
  				</li>
  				<%
  			}
  			%>
  		</ul>
  	</div>
  </div>
</body>
</html>




