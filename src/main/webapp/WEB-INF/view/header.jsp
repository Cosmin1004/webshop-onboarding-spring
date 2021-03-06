<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="WEB-INF/tag/formatText.tld" prefix="yc" %>

<a href="${pageContext.request.contextPath}/home"><img class="center" id="headerLogo"
                                                       src="resources/images/youngCulture.jpg" alt="YoungCulture"/></a>

<nav class="navbar navbar-inverse">
    <div>
        <div class="center" id="buttonsCategories">
            <c:if test="${currentSessionUser == null || currentSessionUser.admin != true}">
                <form action="${pageContext.request.contextPath}/products" method="get">
                    <button type="submit" class="btn btn-default" name="category" value="all">
                        <img src="resources/images/all.png" title="All products" id="imageAllProducts">
                    </button>
                    <div class="btn-group" role="group">
                        <c:forEach var="category" items="${categories}">
                            <button type="submit" class="btn btn-default active" name="category"
                                    value="${category.name}">
                                <yc:formatText text="${category.name}"/>
                            </button>
                        </c:forEach>
                    </div>
                </form>
                <form class="rightElement">
                    <button title="View the cart" type="button" id="showCart" class="btn btn-default active">
                        <img class="icon" src="resources/images/cart.png" title="View the cart">
                        <span id="ordersCount" class="badge" title="Number of items in cart"></span>
                    </button>
                </form>
            </c:if>
            <c:if test="${currentSessionUser != null && currentSessionUser.admin != true}">
                <form action="${pageContext.request.contextPath}/orders" method="get">
                    <button type="submit" class="btn btn-default active rightElement">
                        <img src="resources/images/myOrders.png" title="View your orders" class="icon">
                    </button>
                </form>
            </c:if>
            <c:if test="${currentSessionUser != null && currentSessionUser.admin == true}">
                <form action="${pageContext.request.contextPath}/admin" method="get">
                    <button type="submit" id="adminGetOrders" class="btn btn-default active rightElement">
                        <img src="resources/images/admin.png" title="View users orders" class="icon">
                    </button>
                </form>
            </c:if>
        </div>
        <span class="rightElement">
            <c:choose>
                <c:when test="${currentSessionUser == null}">
                     <button id="loginButton" class="btn btn-default active">
                         <img class="icon" src="resources/images/login.png" title="Login">
                     </button>
                    <c:if test="${message != null}">
                        <div hidden id="hiddenError"></div>
                    </c:if>
                </c:when>
                <c:otherwise>
                     <form action="${pageContext.request.contextPath}/logout" method="post">
                         <span id="welcomeMessage">Welcome, ${currentSessionUser.firstName}</span>
                         <button type="submit" class="btn btn-default active">
                             <img class="icon" src="resources/images/logout.png" title="Logout">
                         </button>
                     </form>
                </c:otherwise>
            </c:choose>
        </span>
    </div>
</nav>