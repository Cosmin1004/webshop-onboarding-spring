<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="WEB-INF/tag/formatText.tld" prefix="yc" %>


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

<body onload="getCartCount()">

<jsp:include page="header.jsp"/>
<jsp:include page="login.jsp"/>
<jsp:include page="cart.jsp"/>

<div id="wrapper">
    <c:choose>
        <c:when test="${! currentSessionUser.admin}">
            <div class="container" style="margin-bottom: 100px">
                <div class="success-msg" id="addProductSuccess" hidden></div>
                <div class="success-msg" id="sentOrderSuccess" hidden></div>
                <c:if test="${fn:length(products) == 0}">
                    <img id="noProducts" alt="No product found..."
                         src="resources/images/noProducts.png">
                </c:if>
                <c:if test="${fn:length(products) > 0}">
                    <div class="table-responsive">
                        <table class="table table-hover table-sm table-striped">
                            <c:choose>
                                <c:when test="${categoryRendered == true}">
                                    <col width='20%'>
                                    <col width='10%'>
                                    <col width='55%'>
                                    <col width='15%'>
                                </c:when>
                                <c:otherwise>
                                    <col width='25%'>
                                    <col width='60%'>
                                    <col width='15%'>
                                </c:otherwise>
                            </c:choose>
                            <thead class="thead">
                            <tr>
                                <th scope="col">Product</th>
                                <c:if test="${categoryRendered == true}">
                                    <th scope="col">Category</th>
                                </c:if>
                                <th scope="col">Description</th>
                                <th scope="col">Price</th>
                            </tr>
                            </thead>
                            <c:forEach var="product" items="${products}">
                                <tr>
                                    <td><b>${product.name}</b></td>
                                    <c:if test="${categoryRendered == true}">
                                        <td><yc:formatText text="${product.category.name}"/></td>
                                    </c:if>
                                    <td>${product.description}</td>
                                    <td class="price">${product.price} Lei
                                        <div class="right">
                                            <button class="btn btn-success" id="addProductToCart"
                                                    value="${product.name}" name="productName">
                                                <img class="icon" src="resources/images/addToCart.png"
                                                     title="Add to cart">
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>
                <a id="backToTop" href="#" class="btn btn-primary btn-lg back-to-top" role="button"
                   title="Click to return on the top of the page" data-toggle="tooltip" data-placement="left">
                    <span id="backToToSpan" class="glyphicon glyphicon-chevron-up"></span>
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <img id="imageAdmin" class="center" alt="Admin image..."
                 src="resources/images/adminUser.png">
            <h2 class="title">
                This is the admin page. You can view and confirm orders by pressing the admin button from the
                header.</h2>
        </c:otherwise>
    </c:choose>

    <jsp:include page="footer.jsp"/>
</div>

</body>

</html>