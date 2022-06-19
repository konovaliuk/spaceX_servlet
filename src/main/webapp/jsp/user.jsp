<%--
  Created by IntelliJ IDEA.
  User: маня
  Date: 04.06.2022
  Time: 17:21
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
<c:if test="${sessionScope.user ne null and sessionScope.user.role.id ne 2}">
<section class="profile">
    <h1>My profile</h1>
    <div class="flex-box">
    <div class="form-box">
        <p id="name">Own page ${sessionScope.user.firstName} ${sessionScope.user.lastName}</p>

        <form id="edit-form1" name="edit profile form" method="post"
              action="${pageContext.request.contextPath}/api/userEdit">
            <label for="firstname">First name: </label><br>
            <input type="text" name="firstname" value="${sessionScope.user.firstName}"
                   id="firstname" placeholder="First name" required><br>
            <label for="lastname">Last name: </label><br>
            <input type="text" name="lastname" value="${sessionScope.user.lastName}"
                   id="lastname" placeholder="Last name" required><br>
            <label for="email">Email: </label><br>
            <input type="text" name="email" maxlength="14" value="${sessionScope.user.email}" id="email" placeholder="name@example.com" required ><br>
            <label for="phone">Phone: </label><br>
            <input type="tel" name="phone" id="phone" value="${sessionScope.user.phone}"
                   placeholder="+0000000000"><br><br>
            <button class="signup_btn" type="submit" >
                Edit profile
            </button>
            <br><br>

        </form>

        <form id="edit-form2" action="${pageContext.request.contextPath}/api/changePassword"
              oninput='password2.setCustomValidity(password2.value !== password.value ? "Passwords isn`t the same" : "")'>
            <div>
                <label for="password">Change password: </label><br>
                <input type="password" pattern="/^[a-zA-Z0-9!@#\$%\^\&*_=+-]$/g"
                       title="Password must contain at least eight characters,
                       at least one number and both lower and uppercase letters
                        and special characters" name="password" id="password" placeholder="new password" required><br>
                <label for="password2">Enter password again: </label><br>
                <input type="password" name="password2" id="password2" placeholder="repeat password" required><br><br>
                <button class="signup_btn" type="submit">
                    Change password
                </button>
            </div>
        </form>
    </div>
    </div>
        <form name="delete" id="delete" action="${pageContext.request.contextPath}/api/deleteAccount">
            <input name="password" type="password" class="form-control" id="password3"
                   placeholder="Enter password" required><br><br>
            <button type="submit">Delete Profile</button>
        </form>

    <c:if test="${requestScope.error ne null}">
        <div class="error" >
            <p>Error: ${requestScope.error}!</p>
        </div>
    </c:if>
</section>
</c:if>

<c:if test="${not empty requestScope.users and sessionScope.user ne null and sessionScope.user.role.id eq 2}">
    <section class="users_page">
        <h1>Registered accounts</h1>
        <div class="users">
            <c:forEach items="${requestScope.users}" var="user">
                <div class="user">
                    <span>
                        User: ${user.fullName}<br>
                        Email: ${user.email}<br>
                        Phone: ${user.phone}<br>
                        Role: ${user.role.role}<br>
                    </span>
                    <form name="set_status" action="api/userRole" method="post">
                        <input name="email" type="hidden" value="${user.email}">
                        <select name="role" id="role">
                            <option value="1">Customer</option>
                            <option value="2">Admin</option>
                            <option value="3">Curator</option>
                        </select>
                        <button class="signup_btn" type="submit" >
                            Change role
                        </button>
                    </form>
                </div>
            </c:forEach>
        </div>

        <div class="pagination">
            <table>
                <c:if test="${requestScope.pageNum != 1}">
                    <a href="${pageContext.request.contextPath}/api/users?pageNum=${requestScope.pageNum - 1}">Previous</a>
                </c:if>
                <c:forEach begin="1" end="${requestScope.numOfPages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.pageNum eq i}">
                            <span  id="offset">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/api/users?pageNum=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${requestScope.pageNum lt requestScope.numOfPages}">
                    <a href="${pageContext.request.contextPath}/api/users?pageNum=${requestScope.pageNum + 1}">Next</a>
                </c:if>
            </table>
        </div>
        <c:if test="${requestScope.error ne null}">
            <div class="error" >
                <p>Error: ${requestScope.error}</p>
            </div>
        </c:if>
    </section>
</c:if>

<%@ include file="/components/footer.jsp"%>
</body>
</html>
