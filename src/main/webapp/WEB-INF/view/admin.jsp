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
    <link rel="shortcut icon" type="image/x-icon" href="resources/images/y.png"/>

    <script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
    <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script src="/resources/js/user.js"></script>
    <script src="/resources/js/cart-order.js"></script>
    <script src="/resources/js/home.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div id="wrapper">
    <div class="container" style="margin-bottom: 100px">
        <h1 class="title">All users orders</h1>
        <a id="backToTop" href="#" class="btn btn-primary btn-lg back-to-top" role="button"
           title="Click to return on the top of the page" data-toggle="tooltip" data-placement="left">
            <span id="backToToSpan" class="glyphicon glyphicon-chevron-up"></span>
        </a>
    </div>
    <jsp:include page="footer.jsp"/>
</div>

</body>

</html>