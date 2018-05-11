<!DOCTYPE html>
<html>
<head>
 <title>Register</title>
 <link rel="stylesheet" href="/css/main.css">
 <style>
   label {
	 display: inline-block;
	 width: 100px;
   }
 </style>
</head>
<body>

 <nav>
   <a id="navTitle" href="/">CodeU Chat App</a>
   <a href="/conversations">Conversations</a>
   <a href="/users">Users</a>
   <% if(request.getSession().getAttribute("user") != null){ %>
	 <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
	 <a href="/changepassword">Change Password</a>
   <% } else{ %>
	 <a href="/login">Login</a>
	 <a href="/register">Register</a>
   <% } %>
   <a href="/about.jsp">About</a>
 </nav>

 <div id="container">
   <h1>Change Password</h1>
   <% if(request.getAttribute("error") != null){ %>
	   <h2 style="color:red"><%= request.getAttribute("error") %></h2>
   <% } %>
   <form action="/changepassword" method="POST">
	 <label for="oldpassword">Old Password: </label>
	 <input type="password" name="oldpassword" id="oldpassword">
	 <br/>
	 <label for="newpassword">New Password: </labelpp>
	 <input type="password" name="newpassword" id="newpassword">
	 <br/><br/>
	 <button type="submit">Submit</button>
   </form>
 </div>
</body>
</html>
