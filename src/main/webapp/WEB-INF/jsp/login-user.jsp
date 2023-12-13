<html>
<head>
    <title> My first HTML Page - JSP</title>
    <style>
        <%@include file="../css/styles.css" %>
    </style>
</head>
<body>
<div class="container">

    <div class="header">
        <h1>Login page </h1>
    </div>

    <section class="form section">
        <h2 class="msg"> ${msg}</h2>
        <form method="post" class="form-container" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label class="form-field">
                    <input type="text" class="input field" name="login_id" placeholder="login_id">
                </label>
                <br/>
                <label class="form-field">
                    <input type="password" class="input field" name="password" placeholder="Password">
                </label></div>
            <button type="submit" value="submit" class="btn"> submit</button>
        </form>
    </section>
</div>
</body>
</html>