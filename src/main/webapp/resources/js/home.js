$(document).ready(function () {
    homeModule().bindAll();
});

homeModule = (function () {

    let bindAllComponents = function () {
        goBackToTop();
        clearLoginModalErrors();
        showLoginModalIfErrorsArePresent();
    };

    let goBackToTop = function () {
        let backToTopButton = $('#backToTop');
        $(window).scroll(function () {
            if ($(this).scrollTop() > 50) {
                backToTopButton.fadeIn();
            } else {
                backToTopButton.fadeOut();
            }
        });
        backToTopButton.click(function () {
            $('#backToTop').tooltip('hide');
            $('html,body').animate({
                scrollTop: 0
            }, 400);
            return false;
        });
        backToTopButton.tooltip('show');
    };

    let clearLoginModalErrors = function () {
        $("#loginButton").on("click", function () {
            showLoginModal();
            document.getElementById("errorBar").innerHTML = null;
        });
    };

    let showLoginModalIfErrorsArePresent = function () {
        let error = document.getElementById("hiddenError");
        if (error) {
            showLoginModal();
        }
    };

    let showLoginModal = function () {
        $('#loginModal').modal('show');
    };

    return {
        bindAll: bindAllComponents
    }

});
