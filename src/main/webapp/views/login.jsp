<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h2 class="card-title text-center">Login</h2>
                    <form id="loginForm">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Login</button>
                    </form>

                    <button id="logoutButton" class="btn btn-danger btn-block mt-3" style="display: none;">Logout</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="adminContent" class="container mt-5" style="display: none;">
    <div class="card">
        <div class="card-body">
            <h2 class="card-title">Admin Content</h2>
            <button id="createUserButton" class="btn btn-success mb-3" data-toggle="modal" data-target="#createUserModal">Create User</button>
            <table id="adminUserTable" class="table table-striped">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody id="adminUserTableBody">
                </tbody>
            </table>
        </div>
    </div>
</div>

<div id="userContent" class="container mt-5" style="display: none;">
    <div class="card">
        <div class="card-body">
            <h2 class="card-title">User Content</h2>
            <table id="userTable" class="table table-striped">
                <thead>
                <tr>
                    <th>Username</th>
                </tr>
                </thead>
                <tbody id="userTableBody">
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="createUserModal" tabindex="-1" role="dialog" aria-labelledby="createUserModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createUserModalLabel">Create New User</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="createUserForm">
                    <div class="form-group">
                        <label for="createUsername">Username:</label>
                        <input type="text" id="createUsername" name="username" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="createEmail">Email:</label>
                        <input type="email" id="createEmail" name="email" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="createPassword">Password:</label>
                        <input type="password" id="createPassword" name="password" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="createName">Name:</label>
                        <input type="text" id="createName" name="name" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="createPhone">Phone:</label>
                        <input type="text" id="createPhone" name="phone" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Create User</button>
                    <div id="message" class="mt-3"></div>
                    <input id="resetform" type="reset" hidden="true" disabled/>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="../js/script.js"></script>
</body>
</html>