<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/components/main.css">
<header>
    <div id="header">
        <a href="${pageContext.request.contextPath}/index.jsp" id="logo">
            <img width="200" src="${pageContext.request.contextPath}/components/imgs/logo_cropp.png" alt="SpaceX"></a>

        <c:if test="${sessionScope.user eq null}">
            <nav class="nav1">
                <a href="${pageContext.request.contextPath}/index.jsp">HOME</a>
                <a href="${pageContext.request.contextPath}/about.jsp">ABOUT</a>
            </nav>
            <div class="signIn" ><a href="${pageContext.request.contextPath}/signin.jsp">SIGN IN</a>
            <a href="${pageContext.request.contextPath}/signup.jsp">SIGN UP</a></div>
        </c:if>
        <c:if test="${sessionScope.user ne null}">
            <nav class="nav2">
                <a href="${pageContext.request.contextPath}/index.jsp">HOME</a>
                <a href="${pageContext.request.contextPath}/api/userMissions">MISSIONS</a>
                <a href="${pageContext.request.contextPath}/jsp/user.jsp">PROFILE</a>
                <a href="${pageContext.request.contextPath}/about.jsp">ABOUT</a>
            </nav>
            <div class="signIn" ><a href="${pageContext.request.contextPath}/api/signOut">SIGN OUT</a></div>

        </c:if>
        <c:if test="${sessionScope.user ne null and sessionScope.user.role.id == 2}">
            <nav class="nav2">
                <a href="${pageContext.request.contextPath}/index.jsp">HOME</a>
                <a href="${pageContext.request.contextPath}/api/getMissions">MISSIONS</a>
                <a href="${pageContext.request.contextPath}/api/users">PROFILES</a>
                <a href="${pageContext.request.contextPath}/about.jsp">ABOUT</a>
            </nav>
        </c:if>
    </div>
</header>
