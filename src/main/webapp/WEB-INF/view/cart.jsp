<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="container">
    <div class="modal" id="cartModal">
        <div class="modal-dialog" id="modalDialog">
            <div class="modal-content" id="modalBody">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h1 class="title">Shopping cart</h1>
                </div>
                <div class="modal-body">
                    <div class="success-remove-msg" id="removeSuccess" hidden></div>
                    <table id="cartTable" class="table table-striped">
                        <col width='50%'>
                        <col width='20%'>
                        <col width='30%'>
                        <thead class="thead">
                        <tr>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="modal-footer">
                    <h4 id="totalPrice" hidden></h4>
                    <span hidden id="forSend">
                        <c:choose>
                            <c:when test="${currentSessionUser != null}">
                                <button class="btn btn-success" id="sendOrder">
                                    <img class="sendButton" src="resources/images/send.png" title="Send">
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-link" id="loginToSendOrder">
                                    To send an order you must log in!
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </span>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>


