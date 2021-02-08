$(document).ready(function () {
    adminModule().bindAll();
});

adminModule = (function () {

    let bindAllComponents = function () {
        manageOrder();
    };

    //confirm or decline a order
    let manageOrder = function () {
        $(document).on("click", ".manageOrder", function () {
            $.ajax({
                type: "PATCH",
                url: "/manageOrder",
                data: {
                    id: $(this).val(),
                    action: $(this).text()
                },
                success: function (responseText) {
                    let matches = $('#chosenEmail').text().split('"');
                    //refresh the table after confirm/delete
                    getAllOrders(matches[1]);
                    alert(responseText);
                }
            });
            $(this).blur();
        });
    };

    return {
        bindAll: bindAllComponents
    }

});

//get all sent orders for a specific user(by email)
function getAllOrders(email) {
    let chosenEmail = $('#chosenEmail');
    let ordersCount = $('#ordersCount')
    $.ajax({
        type: "GET",
        url: "/allOrders",
        data: {
            email: email
        },
        contentType: 'application/json',
        dataType: "json",
        success: function (orders) {
            chosenEmail.text('Selected user: \"' + email + '\"');
            setCount(orders, ordersCount);
            chosenEmail.show();
            $('#infos').empty();
            //add specific information about the orders
            addInformation(orders);
        }
    });
}

function addInformation(orders) {
    if (orders.length === 0) {
        $('<h1>').text('No orders for this user...').appendTo('#infos');
    } else {
        $.each(orders, function (index, order) {
            $('<h3>').text('Order reference: ' + order.reference).appendTo('#infos');
            $('<span class="glyphicon glyphicon-calendar"></span>').appendTo('#infos');
            let date = setDateAndTime(order.reference)
            $('<span>').text(' Date: ' + date).appendTo('#infos');
            createAndPopulateTable(order);
            addActionButtons(order);
            $('<hr>').appendTo('#infos')
        });
    }
}

function createAndPopulateTable(order) {
    let table = createTemplate();

    $.each(order.orderEntries, function (index, orderEntry) {
        $('<tr>').append(
            $('<td>').text(orderEntry.product.name),
            $('<td>').text(orderEntry.quantity),
        ).appendTo(table);
    });

    table.appendTo('#infos');
}

function createTemplate() {
    let table = $('<table class="table table-striped">');

    table.append(
        $('<col width=\'70%\'>'),
        $('<col width=\'30%\'>'));
    let thead = $('<thead class="thead">');
    let tr = $('<tr>');
    $('<th>Product</th>').appendTo(tr);
    $('<th>Quantity</th>').appendTo(tr);
    tr.appendTo(thead);
    thead.appendTo(table);

    return table;
}

function addActionButtons(order) {
    $('<div class="btn-group" role="group">').append(
        $('<button value="' + order.id + '" class="btn btn-success manageOrder">Confirm</button>'),
        $('<button value="' + order.id + '" class="btn btn-danger manageOrder">Decline</button>')
    ).appendTo('#infos');
}

function setCount(orderEntries, ordersCount) {
    let count = orderEntries.length;
    if (count === 1) {
        ordersCount.text(count + " order");
    } else {
        ordersCount.text(count + " orders");
    }
    ordersCount.show();
}

function setDateAndTime(timestamp) {
    let theDate = new Date(timestamp);
    return theDate.toGMTString();
}