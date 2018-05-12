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
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="java.util.UUID" %>

<!DOCTYPE html>
<html>
<head>
  <title>Conversations</title>
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
  </nav>

  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <h1>New Conversation</h1>
      <form action="/conversations" method="POST">
        <div class="form-group">
          <label class="form-control-label">Title:</label>
          <input type="text" name="conversationTitle">
        </div>

        <div class="form-group">
          <label class="form-control-label">To:</label>
          <input type="text" name="recipient">
        </div>

        <button type="submit">Create</button>
      </form>

      <hr/>
    <% } %>

    <h1>Conversations</h1>

    <%
      int numConvos = 0;
      if (request.getSession().getAttribute("user") == null) {
    %>

    <p>Please <a href="/login">login</a> to view your conversations.</p>

    <%
      } else {
    %>

    <%
      List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");
      if (conversations != null && !conversations.isEmpty()) {
    %>
      <ul class="mdl-list">
    <%
      String username = (String) request.getSession().getAttribute("user");
      UUID userId = UserStore.getInstance().getUser(username).getId();

      for(Conversation conversation : conversations){
          if (conversation.hasUser(userId)) {
              numConvos = numConvos + 1;
    %>
      <li><a href="/chat/<%= conversation.getTitle() %>">
        <%= conversation.getTitle() %></a></li>
    <%
        }
      }
    %>
      </ul>

    <%
      }
    %>

    <%
      if (numConvos == 0) {
    %>

    <p>You are not a member of any conversations. Either create a new one or wait for a friend to message you.</p>

    <%
        }
      }
    %>

    <hr/>
  </div>
</body>
</html>
