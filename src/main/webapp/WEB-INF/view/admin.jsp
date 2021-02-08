<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<head>
    <title>young.culture</title>

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/footer.css"/>
    <link rel="stylesheet" href="/resources/css/home.css"/>
    <link rel="stylesheet" href="/resources/css/cart-order.css"/>
    <link rel="stylesheet" href="/resources/css/login.css"/>
    <link rel="stylesheet" href="/resources/css/header.css"/>
    <link rel="stylesheet" href="/resources/css/admin.css"/>
    <link rel="shortcut icon" type="image/x-icon" href="/resources/images/y.png"/>

    <script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
    <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script src="/resources/js/cart-order.js"></script>
    <script src="/resources/js/home.js"></script>
    <script src="/resources/js/admin.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div id="wrapper">
    <div id="adminContainer" class="container marginButton">
        <h1 class="title"><span class="glyphicon glyphicon-list"></span> All user's sent orders</h1>
        <span class="dropDownEmail">
            <div class="btn-group dropDownEmail">
            <button class="btn btn-default btn-lg dropdown-toggle" type="button" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                Select a user to see his orders <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="user" items="${users}">
                    <b><a class="dropdown-item userDropDownItem" href="#" title="Click to see user's orders!"
                          onclick="getAllOrders('${user.email}');return false;">${user.email}</a></b>
                    <br>
                </c:forEach>
            </ul>
            </div>
            <span id="chosenEmail" hidden></span>
            <span id="ordersCount" hidden class="badge"></span>
        </span>

        <div id="infos"></div>

        <a id="backToTop" href="#" class="btn btn-primary btn-lg back-to-top" role="button"
           title="Click to return on the top of the page" data-toggle="tooltip" data-placement="left">
            <span id="backToToSpan" class="glyphicon glyphicon-chevron-up"></span>
        </a>
    </div>
    <jsp:include page="footer.jsp"/>
</div>

</body>

</html>