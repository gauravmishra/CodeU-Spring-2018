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
<%@ page import="codeu.model.data.Profile" %>
<%@ page import="codeu.model.store.basic.ProfileStore" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.util.UUID" %>

<%
User user = (User) request.getAttribute("profileUser");
Profile profile = (Profile) request.getAttribute("profile");
%>

<!DOCTYPE html>
<html>
<head>
  <title>CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
      <a href="/register">Register</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/profile">Profile</a>
  </nav>

  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

      <h1><%= request.getAttribute("profileUser")%></h1>
      <p>
        About:
        <%= (String) request.getAttribute("about")%>
      </p>

      <ul>
        <li>Sophia Jefferson</li>
        <li>Abdo Elfaramawy</li>
        <li>Jenna Oratz</li>
        <li>Chuong Vu</li>
      </ul>

      <p>
        Features and improvements coming soon!
      </p>
    </div>
  </div>
</body>
</html>
