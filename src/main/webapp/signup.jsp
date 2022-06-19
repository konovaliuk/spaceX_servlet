<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: маня
  Date: 25.05.2022
  Time: 14:32
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
        <h2 class="headline2">Sign Up</h2>
        <c:if test="${sessionScope.user eq null}">
            <form name="signin-form" method="post"
                  action="<c:url value="/api/signUp"/>">

                <label for="firstname">First name: </label><br>
                <input type="text" name="firstname" id="firstname"  placeholder="First name" required><br>
                <label for="lastname">Last name: </label><br>
                <input type="text" name="lastname" id="lastname" placeholder="Last name" required><br>
                <label for="email">Email: </label><br>
                <input type="text" name="email" id="email"  placeholder="name@example.com" required ><br>
                <label for="phone">Phone: </label><br>
                <input type="tel" name="phone" id="phone"    placeholder="+11000000000"><br>
                <label for="password">Password: </label><br>
                <input pattern="/^[a-zA-Z0-9!@#\$%\^\&*_=+-]$/g"
                       title="Password must contain at least eight characters, at least one number and both lower and uppercase letters and special characters" type="password" name="password"
                       id="password" placeholder="Password" required><br>

                <button class="signup_btn" type="submit">
                    Sign up
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
