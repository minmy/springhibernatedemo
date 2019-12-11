<html>
<body>
<h2>login page</h2>
<form action="/doLogin.action" method="post">
    <input type="text" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <br>
    username:<input type="text" name="username">
    <br>
    password:<input type="password" name="password">
    <br>
    <button type="submit">submit</button>
</form>
</body>
</html>
