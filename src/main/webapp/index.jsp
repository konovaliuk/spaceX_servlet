<%--
  Created by IntelliJ IDEA.
  User: маня
  Date: 24.05.2022
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>SpaceX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/components/main.css">
    <script src="${pageContext.request.contextPath}/components/slider.js"></script>
<%--    <link rel="script" href="${pageContext.request.contextPath}/components/slider.js">--%>
</head>
<body>
    <%@ include file="/components/head.jsp"%>

    <div id="home_page">
        <h1 id="index_head">MAKING HUMANITY MULTIPLANETARY</h1>
        <c:if test="${sessionScope.user ne null}">
        <a id="create_mission_btn" href="${pageContext.request.contextPath}/jsp/create_mission.jsp">
            <h2 >CREATE MISSION</h2>
        </a>
        </c:if>
        <c:if test="${sessionScope.user eq null}">
            <a id="create_mission_signin" href="${pageContext.request.contextPath}/signin.jsp">
                <h2 >SIGN IN</h2>
            </a>
        </c:if>
        <div id="text">
            <p>SpaceX is proud of our sophisticated and constantly improving design, test,
                and operational approach to improve space sustainability and safety, which are
                critical towards accelerating space exploration while bringing Internet connectivity
                to the globe. We urge all satellite owner/operators to make similar investments
                in sustainability and safety and make their operations transparent.
                We encourage all owner/operators to generate high quality propagated ephemeris and covariance for screening
                by the 18th Space Control Squadron and to openly share this information with others
                to maximize coordination to ensure a sustainable and safe space environment for the future.
                Ultimately, space sustainability is a technical challenge that can be effectively managed
                with the appropriate assessment of risk, the exchange of information, and the proper
                implementation of technology and operational controls.
            </p>
            <p>SpaceX has high fidelity location and prediction data for our satellites from deployment
                through end-of-life disposal, and we share this information continuously with the U.S.
                Space Force, LeoLabs and other operators for tracking and collision avoidance screening.
                SpaceX satellites regularly downlink accurate orbital information from onboard GPS.
                We use this orbital information, combined with planned maneuvers, to accurately predict
                future ephemerides, which are uploaded to Space-Track.org three times per day.
                LeoLabs downloads our ephemerides from Space-Track.org and along with the U.S.
                Space Force's 18th Space Control Squadron screens these trajectories against other
                satellites and debris to predict any potential conjunctions. Such conjunctions are
                communicated back to SpaceX and other satellite owners/operators as Conjunction Data
                Messages.</p>
        </div>
    </div>
<div class="slider">
    <div class="mySlides vehicle falcon9">
        <img  src="components/imgs/slider0.png" alt="">
        <h1 class="sliderHead">FALCON 9</h1>
        <span>FIRST ORBITAL CLASS ROCKET CAPABLE OF REFLIGHT</span></div>
    <div class="mySlides vehicle falconheavy">
        <img  src="components/imgs/slider4.png" alt="">

        <h1 class="sliderHead">FALCON HEAVY</h1>
        <span>THE WORLD’S MOST POWERFUL ROCKET</span></div>
    <img class="mySlides" src="components/imgs/slider1.png" alt="">

    <img class="mySlides" src="components/imgs/slider3.png" alt="">
    <div class="mySlides vehicle dragon">
        <img  src="components/imgs/slider5.png" alt="">
        <h1 class="sliderHead">GRAGON</h1>
        <span>SENDING HUMANS AND CARGO INTO SPACE</span></div>
    <img class="mySlides" src="components/imgs/slider6.png" alt="">
    <div class="mySlides vehicle starship">
        <img  src="components/imgs/slider8.png" alt="">
        <h1 class="sliderHead">STARSHIP</h1>
        <span>SENDING SATELLITES AND LANDING ON MARS</span></div>
    <img class="mySlides" src="components/imgs/slider7.png" alt="">

    <button class="slide-button display-left" onclick="plusDivs(-1)">&#10094;</button>
    <button class="slide-button display-right" onclick="plusDivs(+1)">&#10095;</button>

    <div class="w3-display-topright w3-large w3-container w3-padding-16 w3-black">
</div>
<%--    <a href="jsp/error.jsp">ERROR!</a>--%>
    <c:if test="${requestScope.error ne null}">
        <div class="error" >
            <p>Error: ${requestScope.error}</p>
        </div>
    </c:if>
    <%@ include file="/components/footer.jsp"%>
    <script>showDivs(1)</script>
</body>
</html>
