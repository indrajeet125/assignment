<html>
<head>
    <title>add page </title>
    <style> <%@include file="../css/styles.css"%></style>

</head>
<body>
<div class="header">
    <h1>Customer Details </h1>
</div>
<h2 class="msg"> ${msg}</h2>
<div class="form-container">
    <form action="${pageContext.request.contextPath}/customer"
          method="post"
    >
        <div class="form-field">
            <input type="text" name="first_name" id="first_name" placeholder="first name" required>
            <input type="text" name="last_name" id="last_name" placeholder="last name" required>
        </div>
        <div class="form-field">
            <input type="text" name="street" placeholder="street">
            <input type="text" name="address" placeholder="address">
        </div>
        <div class="form-field">
            <input type="text" name="city" placeholder="city">
            <input type="text" name="state" placeholder="state">
        </div>
        <div class="form-field">
            <input type="text" name="email" placeholder="Email">
            <input type="text" name="phone" placeholder="phone">
        </div>
        <div class="form-field submit-btn">

            <input type="submit" value="submit">
        </div>
        <div class="show-customer  ">
            <a href="${pageContext.request.contextPath}/customer-list" class="show-customer-link btn">show customer</a>
        </div>

    </form>


</div>
</body>

</html>