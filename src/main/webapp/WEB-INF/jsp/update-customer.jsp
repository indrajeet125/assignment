<html>
<head>
    <title>update page </title>
    <style>
        <%@include file="../css/styles.css" %>
    </style>

</head>

<body>
<h1 class="header"> Update Customer details  </h1>

<h2> ${msg}</h2>
<div class="form-container">
    <form action="${pageContext.request.contextPath}/updateCustomer"
          method="post">
        <div class="form-field">
            <input type="text" name="first_name" placeholder="first name" value="${customer.first_name}" required>
            <input type="text" name="last_name" placeholder="last name" value="${customer.last_name}" required>
        </div>
        <div class="form-field">
            <input type="text" name="street" placeholder="street" value="${customer.street}">
            <input type="text" name="address" placeholder="address" value="${customer.address}">
        </div>
        <div class="form-field">
            <input type="text" name="city" placeholder="city" value="${customer.city}">
            <input type="text" name="state" placeholder="state" value="${customer.state}">
        </div>
        <div class="form-field">
            <input type="text" name="email" placeholder="Email" value="${customer.email}">
            <input type="text" name="phone" placeholder="phone" value="${customer.phone}">
        </div>
        <div class="form-field update-btn">
            <input type="submit" value="update">
        </div>
    </form>

    <div class="show-customer  ">
        <a href="${pageContext.request.contextPath}/customer-list" class="show-customer-link btn">show customer</a>
    </div>

</div>
</body>

</html>