document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("loginForm").addEventListener("submit", function(event) {
        event.preventDefault();

        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;

        fetch("/users/authenticate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username: username, password: password })
        })
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else if (response.status === 401) {
                    throw new Error("Invalid credentials. Please try again.");
                } else if (response.status === 403) {
                    throw new Error("Access denied. You do not have permission to access this resource.");
                } else if (response.status === 500) {
                    throw new Error("Server error. Please try again later.");
                } else {
                    throw new Error("An unexpected error occurred. Please try again.");
                }
            })
            .then(data => {
                document.getElementById("message").textContent = "Login successful!";
                localStorage.setItem("token", data.token);
                localStorage.setItem("role", data.role);
                document.getElementById("logoutButton").style.display = "block";
                showContentBasedOnRole(data.role);
            })
            .catch(error => {
                document.getElementById("message").textContent = error.message;
            });
    });

    document.getElementById("logoutButton").addEventListener("click", function() {
        fetch("/users/logout", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                if (response.ok) {
                    document.getElementById("message").textContent = "Logout successful!";
                    localStorage.removeItem("token");
                    localStorage.removeItem("role");
                    document.getElementById("logoutButton").style.display = "none";
                    document.getElementById("adminContent").style.display = "none";
                    document.getElementById("userContent").style.display = "none";
                } else {
                    document.getElementById("message").textContent = "Failed to logout";
                }
            })
            .catch(error => {
                console.error('Error logging out:', error);
            });
    });

    document.getElementById("createUserForm").addEventListener("submit", function(event) {
    event.preventDefault();

    var userData = {
        username: document.getElementById("createUsername").value,
        email: document.getElementById("createEmail").value,
        password: document.getElementById("createPassword").value,
        name: document.getElementById("createName").value,
        phone: document.getElementById("createPhone").value
    };

    fetch("/users/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(userData)
    })
    .then(response => {
        if (response.ok) {
            alert("User created successfully");
            $('#createUserModal').modal('hide');
            fetchUserList(true);
        } else if (response.status === 409) {
            return response.text().then(text => {
                throw new Error(text);
            });
        } else {
            throw new Error("Failed to create user");
        }
    })
    .catch(error => {
        document.getElementById("message").textContent = error.message;
    }).finally(
        document.getElementById("#createUserModal").reset()
    );
});


    function showContentBasedOnRole(role) {
        const adminContent = document.getElementById("adminContent");
        const userContent = document.getElementById("userContent");

        if (role === "ROLE_ADMIN") {
            adminContent.style.display = "block";
            userContent.style.display = "block";
            fetchUserList(true);
        } else if (role === "ROLE_USER") {
            adminContent.style.display = "none";
            userContent.style.display = "block";
            fetchUserList(false);
        }
    }

    function fetchUserList(isAdmin) {
        fetch("/users/list", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => response.json())
            .then(data => {
                renderUserList(data, isAdmin);
            })
            .catch(error => {
                console.error('Error fetching user list:', error);
            });
    }

    function renderUserList(users, isAdmin) {
        const userTableBody = isAdmin ? document.getElementById("adminUserTableBody") : document.getElementById("userTableBody");

        userTableBody.innerHTML = "";

        users.forEach(user => {
            const userRow = document.createElement("tr");

            const usernameCell = document.createElement("td");
            usernameCell.textContent = user.username;
            userRow.appendChild(usernameCell);

            if (isAdmin) {
                const actionsCell = document.createElement("td");
                const deleteButton = document.createElement("button");
                deleteButton.textContent = "Delete";
                deleteButton.className = "btn btn-danger btn-sm";
                deleteButton.addEventListener("click", () => deleteUser(user.id));
                actionsCell.appendChild(deleteButton);
                userRow.appendChild(actionsCell);
            }

            userTableBody.appendChild(userRow);
        });
    }

    function deleteUser(userId) {
        fetch(`/users/delete/${userId}`, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                if (response.ok) {
                    alert("User deleted successfully");
                    fetchUserList(true);
                } else {
                    alert("Failed to delete user");
                }
            })
            .catch(error => {
                console.error('Error deleting user:', error);
            });
    }


    const role = localStorage.getItem("role");
    if (role) {
        showContentBasedOnRole(role);
        document.getElementById("logoutButton").style.display = "block";
    }
});
