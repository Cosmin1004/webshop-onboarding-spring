//add product to the cart
$(document).on("click","#addProductToCart",function(){
    $.ajax({
        type : "POST",
        url : "/addToCart",
        data: {
            productName: $(this).val()
        },
        success : function(responseText) {
            const cartSuccess = $('#cartSuccess');
            cartSuccess.text(responseText);
            cartSuccess.addClass("success-msg-border");
            cartSuccess.show().delay(2500).fadeOut();
            getCartCount();
        }
    });
    $(this).blur();
});

//get cart
$(document).on("click","#showCart",function(){
    $.ajax({
        type : "GET",
        url : "/cart",
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            const cartEntries = data;
            $("#cartTable tbody").remove();
            editTable(cartEntries);
            showCartModal();
        }
    });
    $(this).blur();
});

$(document).on("click", "#loginToSendOrder", function () {
    $('#cartModal').modal('hide');
    $('#loginModal').modal('show');
});

//get cart count
function getCartCount() {
    $.ajax({
        type: "GET",
        url: "/cartCount",
        success: function (data) {
            let count = $('#ordersCount');
            count.text("(" + data + ")");
        }
    });
}

//create table in cart modal(with list of cart entries)
function editTable(cartEntries) {
    const tableHeader = $("#cartTable thead");
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
        var totalPrice = 0;
        $.each(cartEntries, function (index, item) {
            $('<tr>').append(
                $('<td>').text(item.product.name),
                $('<td>').text(item.quantity),
                $('<td class="price">').text(item.product.price + " Lei").append(
                    $('<button id="removeFromCart" class="btn btn-danger rightElement">-</button>')
                )
            ).appendTo('#cartTable');
            totalPrice += item.product.price * item.quantity;
        });
        showTotalPrice(totalPrice);
        showSendButton();
    }
}

function showCartModal() {
    $('#cartModal').modal('show');
}

function showTotalPrice(subTotal) {
    var totalPrice =  $('#totalPrice');
    totalPrice.text("Total price: " + subTotal.toFixed(2).toString() + " Lei");
    totalPrice.addClass("totalPrice");
    totalPrice.show();
}

function hideTotalPrice() {
    const totalPrice = document.getElementById("totalPrice");
    if (totalPrice !== null) {
        totalPrice.style.display = "none";
    }
}

function showSendButton() {
    var forSend =  $('#forSend');
    forSend.show();
}

function hideSendButton() {
    const forSend = document.getElementById("forSend");
    if (forSend !== null) {
        forSend.style.display = "none";
    }
}

function removeNoItemsMessage() {
    const noItems = document.getElementById("noItems");
    if (noItems !== null) {
        noItems.remove();
    }
}


