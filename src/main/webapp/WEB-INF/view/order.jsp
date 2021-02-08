<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<head>
    <title>young.culture</title>

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/footer.css"/>
    <link rel="stylesheet" href="/resources/css/login.css"/>
    <link rel="stylesheet" href="/resources/css/cart-order.css"/>
    <link rel="stylesheet" href="/resources/css/header.css"/>
    <link rel="stylesheet" href="/resources/css/home.css"/>
    <link rel="shortcut icon" type="image/x-icon" href="/resources/images/y.png"/>

    <script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
    <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script src="/resources/js/order.js"></script>
    <script src="/resources/js/cart.js"></script>
    <script src="/resources/js/home.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>
<jsp:include page="cart.jsp"/>

<div class="wrapper">
    <div class="container marginButton">
        <h1 class="title"><span class="glyphicon glyphicon-list"></span> My orders</h1>
        <c:choose>
            <c:when test="${orders.size() == 0}">
                <h3>No orders sent...</h3>
            </c:when>
            <c:otherwise>
                <c:forEach items="${orders}" var="order">
                    <h3>Order reference: ${order.reference}</h3>
                    <c:set var="orderStatus" value="${order.status}"/>
                    <table class="table table-striped">
                        <col width='60%'>
                        <col width='20%'>
                        <col width='20%'>
                        <thead class="thead">
                        <tr>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                        </thead>
                        <c:forEach items="${order.orderEntries}" var="orderEntry">
                            <tr>
                                <td>${orderEntry.product.name}</td>
                                <td>${orderEntry.quantity}</td>
                                <td class="price">${orderEntry.price} Lei</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <c:set var="subTotal">
                        <fmt:formatNumber type="number" maxFractionDigits="2"
                                          value="${order.subTotal}"/>
                    </c:set>
                    <h4 class="totalPrice">Total price: ${subTotal} Lei</h4>
                    <h4 class="status">Status: ${orderStatus}</h4>
                    <br><br>
                    <hr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <a id="backToTop" href="#" class="btn btn-primary btn-lg back-to-top" role="button"
           title="Click to return on the top of the page" data-toggle="tooltip" data-placement="left">
            <span id="backToToSpan" class="glyphicon glyphicon-chevron-up"></span>
        </a>
    </div>
    <jsp:include page="footer.jsp"/>
</div>

</body>

</html>