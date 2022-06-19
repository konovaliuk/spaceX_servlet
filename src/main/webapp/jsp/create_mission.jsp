<%@ page import="me.braun.spacex.service.MissionService" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<section id="mission_page">
    <h1>Mission creating</h1>
    <div class="flex-box">
    <div class="form-box-mission">
    <c:if test="${sessionScope.user ne null}">
        <form class="create-mission-form" method="post" action="${pageContext.request.contextPath}/api/missionCreate">
            <table>
                <tr>
                   <td> <label for="title">Title</label></td>
                    <td><input name="title" type="text" class="form-control"
                           placeholder="Mission title" id="title" required autofocus><br></td>
                </tr>
                <tr>
                  <td> <label for="description">Description</label></td>
                   <td> <input name="description"  class="form-control" type="text"
                           placeholder="Describe mission" id="description" autofocus><br></td>
                </tr>
                  <tr>
                <td><label for="spacecrafts">Spacecrafts</label></td>
                <td><select name="spacecraftId" id="spacecrafts">
                    <option value="1">Falcon 9</option>
                    <option value="2">Falcon 9 block 5</option>
                    <option value="5">Falcon 9 Full Trust</option>
                    <option value="6">Falcon Heavy</option>
                    <option value="7">Starship BFR</option>
                    <option value="41">Dragon</option>
                    <option value="42">Dragon 2</option>
                </select></td>
                </tr>
                <tr>
            <td><label for="services">Services</label></td>
            <td><select name="serviceId" id="services">
                <option value="1">launching a satterline into orbit</option>
                <option value="2">launching space station</option>
                <option value="3">launching space telescope</option>
                <option value="4">delivering aparat/cargo to space</option>
                <option value="5">private orbital flight</option>
                <option value="6">flight to Moon</option>
                <option value="7">flight to Mercury</option>
                <option value="8">flight to Venus</option>
                <option value="9">flight to Mars</option>
                <option value="10">flight to Jupiter</option>
                <option value="11">flight to Saturn</option>
                <option value="12">flight to other planets</option>
                <option value="13">delivering cargo beyong the solar system</option>
            </select></td></tr>
            <tr>
            <td><label for="payload">Payload</label></td>
            <td><input name="payloadWeight" type="number" min="0" max="3500" class="form-control" id="payload"
                   placeholder="payload weight"></td>
            </tr>
            <tr>
            <td><label for="datestart">Start date</label></td>
            <td><input name="datestart" type="date" class="form-control" id="datestart"
                   placeholder="date start"></td></tr>
            <tr>
            <td><label for="dateend">End date</label></td>
            <td><input name="dateend" type="date" class="form-control" id="dateend"
                   placeholder="date end"></td></tr>
            <tr>
            <td><button class="cancel_btn" type="reset">
                Cancel
            </button></td>
            <td><button class="create_btn" type="submit">
                Create
            </button></td></tr>
            </table>
        </form>
    </c:if>
    </div>
    </div>
    <c:if test="${requestScope.error ne null}">
        <div class="error" >
            <p>Error: ${requestScope.error}</p>
        </div>
    </c:if>
    </section>

<%@ include file="/components/footer.jsp"%>

</body>
</html>
