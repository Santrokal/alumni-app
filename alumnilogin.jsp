<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
    <style>
        body {
            background: linear-gradient(45deg, #ff0000, #0000ff);
            background-size: 200% 200%;
            animation: gradientAnimation 5s infinite alternate;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
        }

        @keyframes gradientAnimation {
            0% { background: linear-gradient(45deg, #ff0000, #0000ff); }
            25% { background: linear-gradient(45deg, #00ff00, #ff00ff); }
            50% { background: linear-gradient(45deg, #ffff00, #00ffff); }
            75% { background: linear-gradient(45deg, #ff6600, #6600ff); }
            100% { background: linear-gradient(45deg, #ff0000, #0000ff); }
        }

        .login-box {
            background: rgba(255, 255, 255, 0.1);
            padding: 30px;
            border-radius: 15px;
            box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
            text-align: center;
            width: 350px;
            backdrop-filter: blur(10px);
            border: 2px solid rgba(255, 255, 255, 0.2);
            transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        }

        .login-box:hover {
            transform: translateY(-10px) scale(1.03);
            box-shadow: 0px 10px 25px rgba(255, 255, 255, 0.2);
        }

        h2 {
            color: white;
            font-size: 24px;
        }

        .user-box {
            position: relative;
            margin-bottom: 20px;
        }

        .user-box input, .user-box select {
            width: 100%;
            padding: 12px;
            border: 2px solid transparent;
            border-radius: 5px;
            font-size: 16px;
            outline: none;
            background: rgba(255, 255, 255, 0.2);
            color: black;
            transition: all 0.3s ease-in-out;
        }

        .user-box input:focus, .user-box select:focus {
            border-color: #00c6ff;
            box-shadow: 0 0 10px rgba(0, 198, 255, 0.7);
        }

        .toggle-password {
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            font-size: 18px;
            color: white;
            background: none;
            border: none;
            outline: none;
        }

        .toggle-password:hover {
            color: #00c6ff;
        }

        .button-89 {
            background: linear-gradient(to right, #00c6ff, #007bff);
            color: white;
            padding: 12px 25px;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease-in-out;
            box-shadow: 0 4px 6px rgba(0, 198, 255, 0.3);
            font-weight: bold;
        }

        .button-89:hover {
            transform: scale(1.1);
            box-shadow: 0 8px 15px rgba(0, 198, 255, 0.5);
            background: linear-gradient(to right, #007bff, #003f7f);
        }

        .button-89:active {
            transform: scale(0.95);
            box-shadow: 0 3px 5px rgba(0, 198, 255, 0.7);
        }

        @keyframes floating {
            0% { transform: translateY(0px); }
            50% { transform: translateY(-5px); }
            100% { transform: translateY(0px); }
        }

        .login-box {
            animation: floating 3s ease-in-out infinite;
        }

        #error-message {
            color: red;
            text-align: center;
            margin-bottom: 10px;
        }
    </style>
    <script>
        function toggleFields() {
            const role = document.getElementById("role").value;
            const passwordBox = document.getElementById("password-box");
            const dobBox = document.getElementById("dob-box");
            const emailOrPhoneInput = document.getElementById("emailaddressOrphoneno");

            if (role === "admin") {
                passwordBox.style.display = "block";
                dobBox.style.display = "none";
                emailOrPhoneInput.type = "email";
                emailOrPhoneInput.placeholder = "Enter Email Address";
            } else if (role === "alumni") {
                passwordBox.style.display = "none";
                dobBox.style.display = "block";
                emailOrPhoneInput.type = "text";
                emailOrPhoneInput.placeholder = "Enter Email or Phone Number";
            } else {
                passwordBox.style.display = "none";
                dobBox.style.display = "none";
            }
        }

        function togglePassword() {
            const passwordField = document.getElementById("password");
            const eyeIcon = document.getElementById("eye-icon");
            if (passwordField.type === "password") {
                passwordField.type = "text";
                eyeIcon.classList.replace("fa-eye", "fa-eye-slash");
            } else {
                passwordField.type = "password";
                eyeIcon.classList.replace("fa-eye-slash", "fa-eye");
            }
        }

        function submitForm() {
            const role = document.getElementById("role").value;
            const emailOrPhone = document.getElementById("emailaddressOrphoneno").value.trim();
            const password = document.getElementById("password").value;
            const dob = document.getElementById("dob").value;
            const errorMessage = document.getElementById("error-message");

            if (!role) {
                errorMessage.innerText = "Please select a role.";
                return;
            }

            if (!emailOrPhone) {
                errorMessage.innerText = "Please enter Email or Phone Number.";
                return;
            }

            if (role === "admin" && !password) {
                errorMessage.innerText = "Please enter your password.";
                return;
            }

            if (role === "alumni" && !dob) {
                errorMessage.innerText = "Please enter your Date of Birth.";
                return;
            }

            const payload = { role, emailaddressOrphoneno: emailOrPhone };
            if (role === "admin") payload.password = password;
            if (role === "alumni") payload.dob = dob;

            fetch("<%= request.getContextPath() %>/", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            })
            .then(response => {
                if (!response.ok) throw new Error("Network response was not ok");
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    window.location.href = data.redirectUrl;
                } else {
                    errorMessage.innerText = data.message;
                }
            })
            .catch(error => {
                console.error("Error:", error);
                errorMessage.innerText = "An error occurred. Please try again.";
            });
        }
    </script>
</head>
<body>
    <div class="login-box">
        <h2>Login</h2>
        <form id="loginForm">
            <div class="user-box">
                <label>Select Role:</label>
                <select id="role" name="role" onchange="toggleFields()" required>
                    <option value="" disabled selected>Select Role</option>
                    <option value="admin">Admin</option>
                    <option value="alumni">Alumni</option>
                </select>
            </div>

            <div class="user-box">
                <input type="text" id="emailaddressOrphoneno" name="emailaddressOrphoneno" placeholder="Email Address or Phone Number" required>
            </div>

            <div class="user-box" id="password-box" style="display: none;">
                <input type="password" id="password" name="password" placeholder="Password">
                <button type="button" class="toggle-password" onclick="togglePassword()">
                    <i id="eye-icon" class="fa fa-eye"></i>
                </button>
            </div>

            <div class="user-box" id="dob-box" style="display: none;">
                <input type="date" id="dob" name="dob">
            </div>

            <div id="error-message"></div>

            <button type="button" class="button-89" onclick="submitForm()">Log In</button>
        </form>
        <div>
            <p>Don't Have an Account? <a href="<%= request.getContextPath() %>/register" class="self-register">Self Register</a></p>
        </div>
    </div>
</body>
</html>