<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: маня
  Date: 04.06.2022
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SpaceX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/components/main.css">
    <link rel="stylesheet" href=
            "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%@ include file="/components/head.jsp"%>

<c:if test="${sessionScope.user ne null and sessionScope.user.role.id ne 2}">
<section id="edit_mission_page">
    <h1 >My missions</h1>
    <div>
        <a id="create_new_btn" href="${pageContext.request.contextPath}/jsp/create_mission.jsp">
            CREATE NEW
        </a>
    </div>
    <div class="missions user_">
    <c:forEach items="${requestScope.missions}" var="mission">
    <div class="mission">

        <a href="${pageContext.request.contextPath}/api/getMission?title=${mission.title}">
            <h3 class="missionHead">${mission.title}</h3></a>
        <p>
            Created by ${sessionScope.user.firstName}  ${sessionScope.user.lastName}<br>
            Mission status ${mission.status.status}<br>
        </p>
        <details>
            <summary>About mission</summary>
            ${mission.description} <br>
            ${mission.serviceType.service}<br>
            Spacecraft ${mission.spaceCraft.craftTitle}<br>
            Payload weight ${mission.payloadWeigh} kg<br>
            Date start ${mission.date}<br>
            duration ${mission.duration} days<br>
            summary price $${mission.missionPrice}
        </details>
    </div>
    </c:forEach>
    </div>
    <div class="pagination">
        <table>
            <c:if test="${requestScope.pageNum != 1}">
                <a href="${pageContext.request.contextPath}/api/userMissions?pageNum=${requestScope.pageNum - 1}">Previous</a>
            </c:if>
            <c:forEach begin="1" end="${requestScope.numOfPages}" var="i">
                <c:choose>
                    <c:when test="${requestScope.pageNum eq i}">
                        <span  id="offset">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/api/userMissions?pageNum=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${requestScope.pageNum lt requestScope.numOfPages}">
                <a href="${pageContext.request.contextPath}/api/userMissions?pageNum=${requestScope.pageNum + 1}">Next</a>
            </c:if>
        </table>
    </div>
</section>
</c:if>
<%--    <div class="mission">--%>
<%--        <h2 class="headline2">Mission 2022</h2>--%>
<%--        <p>--%>
<%--            Created by Mariana Braun<br>--%>
<%--            Mission status execution<br>--%>
<%--        </p>--%>
<%--        <details>--%>
<%--            <summary>About mission</summary>--%>
<%--            SpaceX was founded to revolutionize space technology towards making life multiplanetary.--%>
<%--            SpaceX is the world’s leading provider of launch services and is proud to be the first private--%>
<%--            company to have delivered astronauts to and from the International Space Station (ISS), and the--%>
<%--            first and only company to complete an all-civilian crewed mission to orbit.--%>

<%--            <br>--%>
<%--            Spacecraft Falcon heavy<br>--%>
<%--            Date start 20.06.2022<br>--%>
<%--            duration 68 days<br>--%>
<%--            summary price $21 340 000--%>
<%--        </details>--%>
<%--    </div>--%>

<%--    <div class="mission">--%>
<%--        <h2 class="headline2">Mission Mars 1.2</h2>--%>
<%--        <p>--%>
<%--            Created by Mariana Braun<br>--%>
<%--            Mission status processing<br>--%>
<%--        </p>--%>
<%--        <details>--%>
<%--            <summary>About mission</summary>--%>
<%--            As such, SpaceX is deeply committed to maintaining a safe orbital environment, protecting human--%>
<%--            spaceflight, and ensuring the environment is kept sustainable for future missions to Earth orbit and beyond.--%>
<%--            <br>--%>
<%--            Spacecraft Falcon Block 5<br>--%>
<%--            Date start 04.07.2022<br>--%>
<%--            duration 32 days<br>--%>
<%--            summary price $17 894 000--%>
<%--        </details>--%>
<%--    </div>--%>




<c:if test="${not empty requestScope.missions and sessionScope.user ne null and sessionScope.user.role.id eq 2}">

    <section id="edit_mission_page">
        <h1 >Missions</h1>

        <div class="search">
            <form action="${pageContext.request.contextPath}/api/getMission">
                <input type="text"
                       placeholder="Search Mission"
                       name="title" >
                <button type="submit" onsubmit="if (this.title == null){console.log('Field must not be null')}">
                    <i class="fa fa-search"
                       style="font-size: 18px;">
                    </i>
                </button>
            </form>
        </div>
        <div class="missions all">
            <c:forEach items="${requestScope.missions}" var="mission">
                <div class="mission">

                    <a href="${pageContext.request.contextPath}/api/getMission?title=${mission.title}">
                        <h3 class="missionHead">${mission.title}</h3></a>
                    <p>
                        Created by ${mission.customer.fullName}<br>
                        Mission status: ${mission.status.status}<br>
                    </p>
                    <details>
                        <summary>About mission</summary>
                            ${mission.description} ${mission.serviceType.service}<br>
                        Spacecraft: ${mission.spaceCraft.craftTitle}<br>
                        Payload weight: ${mission.payloadWeigh} kg<br>
                        Date start: ${mission.date}<br>
                        Duration: ${mission.duration} days<br>
                        Summary price: $${mission.missionPrice}
                    </details>
                </div>
            </c:forEach>

        </div>


        <div class="pagination">
            <table>
                <c:if test="${requestScope.pageNum != 1}">
                    <a href="${pageContext.request.contextPath}/api/getMissions?pageNum=${requestScope.pageNum - 1}">Previous</a>
                </c:if>
                <c:forEach begin="1" end="${requestScope.numOfPages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.pageNum eq i}">
                            <span  id="offset">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/api/getMissions?pageNum=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${requestScope.pageNum lt requestScope.numOfPages}">
                    <a href="${pageContext.request.contextPath}/api/getMissions?pageNum=${requestScope.pageNum + 1}">Next</a>
                </c:if>
            </table>
        </div>
    </section>


</c:if>

<c:if test="${requestScope.error ne null}">
    <div class="error" >
            <p>Error: ${requestScope.error}</p>
    </div>
</c:if>
<%@ include file="/components/footer.jsp"%>
</body>
</html>
