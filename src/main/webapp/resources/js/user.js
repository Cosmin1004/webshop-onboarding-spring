$(document).on("click", "#loginButton", function () {
    showLoginModal();
    document.getElementById("errorBar").innerHTML = null;
});

$(document).ready(function () {
    let error = document.getElementById("hiddenError");
    if (error) {
        showLoginModal();
    }
});

function showLoginModal() {
    $('#loginModal').modal('show');
}

