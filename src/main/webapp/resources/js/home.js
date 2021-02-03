//back top top button
$(document).ready(function () {
    let backToTop = $('#backToTop');
    $(window).scroll(function () {
        if ($(this).scrollTop() > 50) {
            backToTop.fadeIn();
        } else {
            backToTop.fadeOut();
        }
    });
    backToTop.click(function () {
        $('#backToTop').tooltip('hide');
        $('html,body').animate({
            scrollTop: 0
        }, 400);
        return false;
    });
    backToTop.tooltip('show');
});

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

function showCartModal() {
    $('#cartModal').modal('show');
}

function showLoginModal() {
    $('#loginModal').modal('show');
}