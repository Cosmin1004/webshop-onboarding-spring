<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row">
        <div id="loginModal" class="modal fade login-register-form" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                        <ul class="nav nav-tabs">
                            <li class="active"><a data-toggle="tab" href="#login-form"> Login <span
                                    class="glyphicon glyphicon-user"></span></a></li>
                            <li><a data-toggle="tab" href="#registration-form"> Register <span
                                    class="glyphicon glyphicon-pencil"></span></a></li>
                        </ul>
                    </div>
                    <div class="modal-body">
                        <div class="tab-content">
                            <div id="login-form" class="tab-pane fade in active">
                                <form action="${pageContext.request.contextPath}/login" method="post"
                                      modelAttribute="loginForm">
                                    <div class="form-group">
                                        <label for="email">Email:</label>
                                        <input type="email" class="form-control" id="email"
                                               placeholder="Enter your email" name="email" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="pwd">Password:</label>
                                        <input type="password" class="form-control" id="pwd"
                                               placeholder="Enter your password" name="password" required>
                                    </div>
                                    <button type="submit" name="login" class="btn btn-default">Login</button>
                                </form>
                            </div>
                            <div id="registration-form" class="tab-pane fade">
                                <form action="${pageContext.request.contextPath}/register" method="post"
                                      modelAttribute="registerForm">
                                    <div class="form-group">
                                        <label for="firstName">First Name:</label>
                                        <input type="text" class="form-control" id="firstName"
                                               placeholder="Enter your first name" name="firstName" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="lastName">Last Name:</label>
                                        <input type="text" class="form-control" id="lastName"
                                               placeholder="Enter your last name" name="lastName" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="regEmail">Email:</label>
                                        <input type="email" class="form-control" id="regEmail"
                                               placeholder="Enter your email" name="email" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="regPwd">Password:</label>
                                        <input type="password" class="form-control" id="regPwd"
                                               placeholder="Enter a password" name="password" required>
                                    </div>
                                    <button type="submit" name="register" class="btn btn-default">Register</button>
                                </form>
                            </div>
                        </div>
                        <span class="errorBar" id="errorBar">${message}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>