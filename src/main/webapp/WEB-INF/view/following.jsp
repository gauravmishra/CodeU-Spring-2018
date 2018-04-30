<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <title>Followings</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>

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

    <% if(request.getSession().getAttribute("user") != null){ %>
    <h1>New Following</h1>
    <form action="/following" method="POST">
        <div class="form-group">
            <label class="form-control-label">Follow a user:</label>
            <input type="text" name="followUserName">
        </div>

        <button type="submit">Follow</button>
    </form>
    <hr/>
    <% } %>
    <% if(request.getAttribute("error") != null){ %>
    <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
    <h1>Following</h1>
    <%
        List<String> followings = (List<String>) request.getAttribute("following");
        if(followings == null || followings.isEmpty()){
    %>
    <p>Users you follow will appear here.</p>
    <%
    }
    else{
    %>
    <ul class="mdl-list">
        <%
            for(String follow : followings){
        %>
        <li><a>
            <%= follow%></a></li>
        <%
            }
        %>
    </ul>
    <%
        }
    %>
    <hr/>
</div>
</body>
</html>
