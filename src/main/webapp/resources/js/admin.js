function getAllOrders(email) {
    const chosenEmail = $('#chosenEmail');
    const ordersCount = $('#ordersCount')
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
            addInformation(orders);
        }
    });
}

$(document).on("click", ".manageButton", function () {
    $.ajax({
        type: "POST",
        url: "/manageOrder",
        data: {
            id: $(this).val(),
            action: $(this).text()
        },
        success: function (responseText) {
            const matches = $('#chosenEmail').text().split('"');
            getAllOrders(matches[1]);
            alert(responseText);
        }
    });
    $(this).blur();
});

function addInformation(orders) {
    if (orders.length === 0) {
        $('<h1>').text('No orders for this user...').appendTo('#infos');
    } else {
        $.each(orders, function (index, order) {
            $('<h3>').text('Order reference: ' + order.reference).appendTo('#infos');
            const date = setDateAndTime(order.reference)
            $('<span>').text('Date: ' + date).appendTo('#infos');
            createAndPopulateTable(order);
            addActionButtons(order);
            $('<hr>').appendTo('#infos')
        });
    }
}

function createAndPopulateTable(order) {
    const table = createTemplate();

    $.each(order.orderEntries, function (index, orderEntry) {
        $('<tr>').append(
            $('<td>').text(orderEntry.product.name),
            $('<td>').text(orderEntry.quantity),
        ).appendTo(table);
    });

    table.appendTo('#infos');
}

function createTemplate() {
    const table = $('<table class="table table-striped">');

    table.append(
        $('<col width=\'70%\'>'),
        $('<col width=\'30%\'>'));
    const thead = $('<thead class="thead">');
    const tr = $('<tr>');
    $('<th>Product</th>').appendTo(tr);
    $('<th>Quantity</th>').appendTo(tr);
    tr.appendTo(thead);
    thead.appendTo(table);

    return table;
}

function addActionButtons(order) {
    $('<div class="btn-group" role="group">').append(
        $('<button value="' + order.id + '" class="btn btn-success manageButton">Confirm</button>'),
        $('<button value="' + order.id + '" class="btn btn-danger manageButton">Decline</button>')
    ).appendTo('#infos');
}

function setCount(orderEntries, ordersCount) {
    var count = orderEntries.length;
    if (count === 1) {
        ordersCount.text(count + " order");
    } else {
        ordersCount.text(count + " orders");
    }
    ordersCount.show();
}

function setDateAndTime(timestamp) {
    const theDate = new Date(timestamp * 1000);
    return theDate.toGMTString();
}