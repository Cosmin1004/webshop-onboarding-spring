$(document).ready(function () {
    orderModule().bindAll();
});

orderModule = (function () {

    let bindAllComponents = function () {
        sendOrder();
        redirectToLogin();
    };

    //send order
    let sendOrder = function () {
        $(document).on("click", "#sendOrder", function () {
            $.ajax({
                type: "POST",
                url: "/sendOrder",
                success: function (responseText) {
                    let sentOrderSuccess = $('#sentOrderSuccess');
                    sentOrderSuccess.text(responseText);
                    sentOrderSuccess.addClass("success-msg-border");
                    $('#cartModal').modal('hide');
                    //clear cart after sending the order
                    removeAllFromCart();
                    //set cart count on 0 (no need to call getCount method)
                    let count = $('#ordersCount');
                    count.text("0");
                    sentOrderSuccess.show().delay(3000).fadeOut();
                }
            });
            $(this).blur();
        });
    };

    let redirectToLogin = function () {
        $(document).on("click", "#loginToSendOrder", function () {
            $('#cartModal').modal('hide');
            $('#loginModal').modal('show');
        });
    };

    let removeAllFromCart = function () {
        $.ajax({
            type: "DELETE",
            url: "/removeAll",
        });
    };

    return {
        bindAll: bindAllComponents
    }

});
