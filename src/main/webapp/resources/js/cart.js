$(document).ready(function () {
    cartModule().bindAll();
});

cartModule = (function () {

    let bindAllComponents = function () {
        showCart();
        addProductToCart();
        removeFromCart();
        getCartCount();
    };

    //add product to the cart
    let addProductToCart = function () {
        $(document).on("click", "#addProductToCart", function () {
            $.ajax({
                type: "POST",
                url: "/addToCart",
                data: {
                    productName: $(this).val()
                },
                success: function (responseText) {
                    let addProductSuccess = $('#addProductSuccess');
                    addProductSuccess.text(responseText);
                    addProductSuccess.addClass("success-msg-border");
                    addProductSuccess.show().delay(4000).fadeOut();
                    getCartCount();
                }
            });
            $(this).blur();
        });
    };

    //get cart
    let showCart = function () {
        $("#showCart").on("click", function () {
            $.ajax({
                type: "GET",
                url: "/cart",
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    let cartEntries = data;
                    $("#cartTable tbody").remove();
                    editTable(cartEntries);
                    showCartModal();
                }
            });
            $(this).blur();
        });
    };

    //remove item from cart
    let removeFromCart = function () {
        $(document).on("click", "#removeFromCart", function () {
            $.ajax({
                type: "DELETE",
                url: "/removeFromCart",
                data: {
                    productName: $(this).val()
                },
                success: function (responseText) {
                    getCartCount();
                    let removeSuccess = $('#removeSuccess');
                    removeSuccess.text(responseText);
                    removeSuccess.addClass("success-remove-msg-border");
                    removeSuccess.show().delay(2500).fadeOut();
                    $.ajax({
                        type: "GET",
                        url: "/cart",
                        success: function (data) {
                            let cartEntries = data;
                            $("#cartTable tbody").remove();
                            editTable(cartEntries);
                        }
                    });
                }
            });
            $(this).blur();
        });
    };

    //number of items from cart
    let getCartCount = function () {
        $(document).ready(function () {
            $.ajax({
                type: "GET",
                url: "/cartCount",
                success: function (data) {
                    let count = $('#ordersCount');
                    count.text(data);
                }
            });
        });
    };

    //create/update table in cart modal(with list of cart entries)
    let editTable = function (cartEntries) {
        let tableHeader = $('#cartTable thead');
        if (cartEntries.length === 0) {
            tableHeader.hide();
            hideTotalPrice();
            hideSendButton();
            if (!document.body.contains(document.getElementById("noItems"))) {
                $('<h3 id="noItems">No items in cart...</h3>').appendTo('#cartTable');
            }
        } else {
            tableHeader.show();
            removeNoItemsMessage();
            let totalPrice = 0;
            $.each(cartEntries, function (index, item) {
                $('<tr>').append(
                    $('<td>').text(item.product.name),
                    $('<td>').text(item.quantity),
                    $('<td class="price">').text(item.product.price + " Lei").append(
                        $('<button id="removeFromCart" value="' + item.product.name + '" class="btn btn-danger rightElement">').append(
                            $('<img class="buttonImage" src="resources/images/removeFromCart.png" title="Remove from cart" alt="-">'))))
                    .appendTo('#cartTable');
                totalPrice += item.product.price * item.quantity;
            });
            showTotalPrice(totalPrice);
            showSendButton();
        }
    };

    let showTotalPrice = function (subTotal) {
        let totalPrice = $('#totalPrice');
        totalPrice.text("Total price: " + subTotal.toFixed(2).toString() + " Lei");
        totalPrice.addClass("totalPrice");
        totalPrice.show();
    }

    let hideTotalPrice = function () {
        let totalPrice = document.getElementById("totalPrice");
        if (totalPrice !== null) {
            totalPrice.style.display = "none";
        }
    }

    let showSendButton = function () {
        let forSend = $('#forSend');
        forSend.show();
    }

    let hideSendButton = function () {
        let forSend = document.getElementById("forSend");
        if (forSend !== null) {
            forSend.style.display = "none";
        }
    }

    let removeNoItemsMessage = function () {
        let noItems = document.getElementById("noItems");
        if (noItems !== null) {
            noItems.remove();
        }
    }

    let showCartModal = function () {
        $('#cartModal').modal('show');
    }

    return {
        bindAll: bindAllComponents
    }

});