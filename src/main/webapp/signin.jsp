<%--
  Created by IntelliJ IDEA.
  User: маня
  Date: 29.05.2022
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SpaceX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/components/main.css">
</head>
<body>
    <%@ include file="/components/head.jsp"%>
    <section class="profile1">
        <h1> </h1>
        <div class="flex-box">
        <div class="form-box">
        <h2 class="headline2">Sign in</h2>
     <c:if test="${sessionScope.user eq null}">
        <form class="signin-form" method="post" action="<c:url value="/api/signIn"/>">
                <label for="email">Email</label> <br>
                <input name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" title="Email must contain @ and point" type="email" class="form-control"
                       placeholder="name@example.com" id="email" required autofocus><br>
                <label for="password">Password</label><br>
                <input name="password" type="password"  class="form-control" id="password"
                       placeholder="Password" required><br>
            <button class="signup_btn" type="submit">
                Sign in
            </button>
        </form>
    </c:if>
        </div>
        </div>
        <c:if test="${requestScope.error ne null}">
            <div class="error" >
                <p>Error: ${requestScope.error}!</p>
            </div>
        </c:if>
    </section>

    <%@ include file="/components/footer.jsp"%>
</body>
</html>
