<%--
  Created by IntelliJ IDEA.
  User: маня
  Date: 13.06.2022
  Time: 17:00
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
<section id="about_page">
    <h1>About Starlink</h1>
    <div id="about">

        <p>SpaceX satellites are designed and built for high reliability
            and redundancy in both supply chain and satellite design
            to successfully carry out their five-year design life.
            Rigorous part and system-level screening and testing enable
            us to reliably build and launch satellites at very high rates.
            We have the capacity to build up to 45 satellites per week, and we
            have launched up to 240 satellites in a single month. This is an
            unprecedented rate of deployment for a complex space system
            — and reflects SpaceX’s commitment to increase broadband
            accessibility around the world with Starlink as soon as feasible</p>
        <p>The reliability of the satellite network is currently higher than
            99% following the deployment of over 2,000 satellites, where only
            1% have failed after orbit raising. We de-orbit satellites that are
            at risk of becoming non-maneuverable to prevent dead satellites from
            accumulating in orbit. Although this comes at the cost of losing
            otherwise healthy
            satellites, we believe this proactive approach is the right
            thing for space sustainability and safety.</p>
        <p>Our satellites use multiple strategies to prevent debris generation
            in space: design for demise, controlled deorbit to low altitudes,
            low orbit insertion, low operating orbit, on-board collision avoidance
            system, reducing the chance small debris will damage the satellite with
            a low profile satellite chassis and using Whipple shields to protect the key components,
            reducing risk of explosion with extensive battery pack protection,
            and failure modes that do not create secondary debris.</p>
    </div>
    <div class="watch"><h2 class="headline2">WATCH VIDEO</h2></div>
</section>
<div id="iframe">

    <iframe width="720" height="480" src="https://www.youtube.com/embed/TeVbYCIFVa8"
            title="YouTube video player" frameborder="0"
            allow="accelerometer; autoplay; clipboard-write;
                encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen>
    </iframe>
</div>

<%@ include file="/components/footer.jsp"%>
</body>
</html>
