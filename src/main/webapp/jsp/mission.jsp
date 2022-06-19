<%--
  Created by IntelliJ IDEA.
  User: маня
  Date: 15.06.2022
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>SpaceX</title>
</head>
<body>
<%@ include file="/components/head.jsp" %>
<c:if test="${not empty requestScope.mission and sessionScope.user ne null and sessionScope.user.role.id ne 2}">
    <section id="edit_mission_page">
        <h1>${requestScope.mission.title}</h1>
        <div class="flex-box">
            <div class="form-box-mission">
                <form class="create-mission-form" method="post"
                      action="${pageContext.request.contextPath}/api/missionEdit">
                    <input name="missionId" type="hidden" value="${requestScope.mission.id}">
                    <table>
                        <tr>
                            <td><label for="title">Title</label></td>
                            <td><input name="title" type="text" value="${requestScope.mission.title}"
                                       class="form-control"
                                       placeholder="Mission title" required autofocus></td>
                        </tr>
                        <tr>
                            <td><label>Mission status</label></td>
                            <td><input name="status" type="text" value="${requestScope.mission.status.status}" readonly>
                            </td>
                        </tr>
                        <tr>
                            <td><label>About mission</label></td>
                            <td><input id="description" name="about" value="${requestScope.mission.description}"
                                       class="form-control"
                                       type="text"
                                       placeholder="Describe mission"></td>
                        </tr>
                        <tr>
                            <td><label>Curator</label></td>
                            <td><input name="curatorId" value="${requestScope.mission.curator.fullName}"
                                       class="form-control" disabled>
                        </tr>
                        <tr>
                            <td><p class="chosen">Spacecraft </p></td>
                            <td><select name="spacecraftId" id="spacecrafts" required>
                                <option id="1" value="1">Falcon 9</option>
                                <option id="2" value="2">Falcon 9 block 5</option>
                                <option id="5" value="5">Falcon 9 Full Trust</option>
                                <option id="6" value="6">Falcon Heavy</option>
                                <option id="7" value="7">Starship BFR</option>
                                <option id="41" value="41">Dragon</option>
                                <option id="42" value="42">Dragon 2</option>
                            </select></td>
                        </tr>
                        <script>
                            selectedCraft();

                            function selectedCraft() {
                                document.getElementById("${requestScope.mission.spaceCraft.id}").selected = true
                            }

                        </script>
                        <tr>
                            <td><p class="chosen">Service</p></td>
                            <td><select name="serviceId" id="services" required>
                                <option id="1" value="1">launching a satterline into orbit</option>
                                <option id="2" value="2">launching space station</option>
                                <option id="3" value="3">launching space telescope</option>
                                <option id="4" value="4">delivering aparat/cargo to space</option>
                                <option id="5" value="5">private orbital flight</option>
                                <option id="6" value="6">flight to Moon</option>
                                <option id="7" value="7">flight to Mercury</option>
                                <option id="8" value="8">flight to Venus</option>
                                <option id="9" value="9">flight to Mars</option>
                                <option id="10" value="10">flight to Jupiter</option>
                                <option id="11" value="11">flight to Saturn</option>
                                <option id="12" value="12">flight to other planets</option>
                                <option id="13" value="13">delivering cargo beyong the solar system</option>
                            </select></td>
                        </tr>
                        <script>
                            selectedService();

                            function selectedService() {
                                document.getElementById("${requestScope.mission.serviceType.id}").selected = true
                            }</script>
                        <tr>
                            <td><label>Payload</label></td>
                            <td><input name="payloadWeight" value="${requestScope.mission.payloadWeigh}" type="number"
                                       min="0"
                                       max="3500" class="form-control"
                                       placeholder="payload weight"></td>
                        </tr>
                        <tr>
                            <td><label>Start date</label></td>
                            <td><input name="datestart" value="${requestScope.mission.date}" type="date"
                                       class="form-control"
                                       placeholder="date start" required></td>
                        </tr>
                        <tr>
                            <td><label>Duration</label></td>
                            <td><input name="duration" value="${requestScope.mission.duration}"
                                       type="number" class="form-control" placeholder="duration"></td>
                        </tr>
                        <tr>
                            <td><label>Summary price</label></td>
                            <td><input name="price" value="$${requestScope.mission.missionPrice}"
                                       type="text" class="form-control" disabled></td>
                        </tr>
                        <tr>
                            <td>
                                <button class="cancel_btn" type="reset">
                                    Cancel
                                </button>
                            </td>
                            <td>
                                <button class="create_btn" type="submit">
                                    Edit Mission
                                </button>
                            </td>
                        </tr>
                    </table>
                </form>

            </div>
        </div>
    </section>
</c:if>


<c:if test="${not empty requestScope.mission and sessionScope.user ne null and sessionScope.user.role.id eq 2}">
    <section id="edit_mission_page">
        <h1>${requestScope.mission.title}</h1>
        <div class="flex-box">
            <div class="form-box-mission">
                <form class="create-mission-form" method="post"
                      action="${pageContext.request.contextPath}/api/missionStatus">
                    <input name="missionId" type="hidden" value="${requestScope.mission.id}">
                    <table>
                        <tr>
                            <td><label for="title">Title</label></td>
                            <td><input name="title" type="text" value="${requestScope.mission.title}"
                                       class="form-control"
                                       placeholder="Mission title" id="title" disabled></td>
                        </tr>
                        <tr>
                            <td><label>Mission status</label></td>
                            <td><select name="statusId">
                                <option id="1" value="1">Processing</option>
                                <option id="2" value="2">Accepted</option>
                                <option id="3" value="3">Rejected</option>
                                <option id="4" value="4">Paid</option>
                                <option id="5" value="5">Canceled</option>
                                <option id="6" value="6">Mission preparation</option>
                                <option id="7" value="7">Ready for execution</option>
                                <option id="8" value="8">Mission launch</option>
                                <option id="9" value="9">Mission execution</option>
                                <option id="10" value="10">Mission complete</option>
                                <option id="11" value="11">Mission failed</option>
                            </select>
                            </td>
                            <script>
                                selectedStatus();

                                function selectedStatus() {
                                    document.getElementById("${requestScope.mission.serviceType.id}").selected = true
                                }</script>
                        </tr>
                        <tr>
                            <td><label for="description">About mission</label></td>
                            <td><input name="about" value="${requestScope.mission.description}" class="form-control"
                                       type="text"
                                       placeholder="Describe mission" id="description" disabled></td>
                        </tr>
                        <tr>
                            <td><label for="customer">Customer</label></td>
                            <td><input id="customer" name="customerId" value="${requestScope.mission.customer.fullName}"
                                       class="form-control" disabled></td>
                        </tr>
                        <tr>
                            <td><label>Curator </label></td>
                            <td><input id="curator" name="curatorId" value="${requestScope.mission.curator.fullName}"
                                       class="form-control" disabled></td>
                        </tr>

                        <tr>
                            <td><p class="chosen">Spacecraft </p></td>
                            <td><select name="spacecraftId">
                                <option id="1" value="1">Falcon 9</option>
                                <option id="2" value="2">Falcon 9 block 5</option>
                                <option id="5" value="5">Falcon 9 Full Trust</option>
                                <option id="6" value="6">Falcon Heavy</option>
                                <option id="7" value="7">Starship BFR</option>
                                <option id="41" value="41">Dragon</option>
                                <option id="42" value="42">Dragon 2</option>
                            </select></td>
                            <script>selectedCraft()</script>
                        </tr>
                        <tr>
                            <td>
                                <lable>Service</lable>
                            </td>
                            <td><input type="text" name="serviceId" value="${requestScope.mission.serviceType.service}"
                                       disabled>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="payload">Payload</label></td>
                            <td><input name="payloadWeight" value="${requestScope.mission.payloadWeigh}" type="number"
                                       min="0"
                                       max="3500" class="form-control" id="payload"
                                       placeholder="payload weight" disabled></td>
                        </tr>
                        <tr>
                            <td><label for="datestart">Start date</label></td>
                            <td><input name="datestart" value="${requestScope.mission.date}" type="date"
                                       class="form-control" id="datestart"
                                       placeholder="date start" disabled></td>
                        </tr>
                        <tr>
                            <td><label for="customer">Duration</label></td>
                            <td><input id="duration" name="duration" value="${requestScope.mission.duration}"
                                       type="number" class="form-control" placeholder="duration" disabled></td>
                        </tr>
                        <tr>
                            <td><label>Summary price</label></td>
                            <td><input name="price" value="$${requestScope.mission.missionPrice}"
                                       type="text" class="form-control" disabled></td>
                        </tr>
                        <tr>
                            <td>
                                <button class="cancel_btn" type="reset">
                                    Cancel
                                </button>
                            </td>
                            <td>
                                <button class="create_btn" type="submit">
                                    Edit Mission
                                </button>
                            </td>
                        </tr>
                    </table>
                </form>

            </div>
        </div>
    </section>

</c:if>

<c:if test="${requestScope.error ne null}">
    <div class="error">
        <p>Error: ${requestScope.error}!</p>
    </div>
</c:if>
<%@ include file="/components/footer.jsp" %>
</body>
</html>
