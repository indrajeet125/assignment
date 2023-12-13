<%@ page import="com.ex.model.Customer" %>
<%@ page import="com.ex.service.ApiService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title> Customer List page </title>
    <script src="https://kit.fontawesome.com/f4037b0331.js" crossorigin="anonymous"></script>
    <style>
        <%@include file="../css/styles.css" %>
    </style>

</head>
<body>
<%
    ApiService apiService = new ApiService();
    List<Customer> userList = apiService.getCustomerList(session);
%>

<button class="addUser btn" onclick="location.href='addCustomer'">Add User</button>
<button class="logout btn" onclick="location.href='logout'">Logout</button>
<section>
    <div class="msg">
        ${msg}
    </div>
    <table class="userList">
        <tr class="first-row">
            <th> First Name</th>
            <th> Last Name</th>
            <th>Street </th>
            <th> Address</th>
            <th> City</th>
            <th> State</th>
            <th> Email</th>
            <th> Phone</th>
            <th colspan="2"> action</th>
        </tr>
        <%

            for (Customer user : userList) {
//                System.out.println(user);

        %>
        <tr>
            <td><%= user.getFirst_name() %></td>
            <td><%= user.getLast_name() %></td>
            <td><%= user.getStreet() %></td>
            <td><%= user.getAddress() %></td>
            <td><%= user.getCity() %></td>
            <td><%= user.getState() %></td>
            <td><%= user.getPhone()%></td>
            <td><%= user.getPhone() %></td>


            <td>
                <a href="deleteCustomer?uuid= <%= user.getUuid()%> ">
                    <i class="fa-solid fa-minus trace"></i>
                </a>
            </td>
            <%--            <td>--%>
            <%--                <a href="updateCustomer?uuid=<%= user.getUuid()%>--%>
            <%--                        &first_name=<%= user.getFirst_name().%>--%>
            <%--                        &last_name=<%= user.getLast_name()%>--%>
            <%--                        &street= <%= user.getStreet()%>--%>
            <%--                        &address=<%= user.getAddress()%>--%>
            <%--                        &city=<%= user.getCity()%>--%>
            <%--                        &state=<%= user.getState()%>--%>
            <%--                        &email=<%= user.getEmail()%>--%>
            <%--                        &phone=<%= user.getPhone()%>">--%>
            <%--                    <i class="fa-solid fa-pen"></i>--%>
            <%--                </a>--%>
            <%--            </td>--%>

            <td>
                <%--               issue when add name with % that create proble in url --%>
                <a href="updateCustomer?uuid=<%= user.getUuid() %>
                &first_name=<%= URLEncoder.encode(user.getFirst_name().trim(), "UTF-8") %>
                &last_name=<%= URLEncoder.encode(user.getLast_name(), "UTF-8") %>
                &street=<%= URLEncoder.encode(user.getStreet(), "UTF-8") %>
                &address=<%= URLEncoder.encode(user.getAddress(), "UTF-8") %>
                &city=<%= URLEncoder.encode(user.getCity(), "UTF-8") %>
                &state=<%= URLEncoder.encode(user.getState(), "UTF-8") %>
                &email=<%= URLEncoder.encode(user.getEmail(), "UTF-8") %>
                &phone=<%= URLEncoder.encode(user.getPhone(), "UTF-8") %>">
                    <i class="fa-solid fa-pen"></i>
                </a>
            </td>
        </tr>
        <%
            }
        %>

    </table>
</section>
</body>
</html>
