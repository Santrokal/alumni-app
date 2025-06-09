<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Registration</title>
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 500px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        input[type="text"], input[type="password"], input[type="date"], input[type="file"], input[type="email"] {
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        .message {
            text-align: center;
            margin-top: 10px;
        }
        .error {
            color: red;
        }
        .success {
            color: green;
        }
        .login-link {
            text-align: center;
            margin-top: 20px;
        }
        .login-link a {
            color: #4CAF50;
            text-decoration: none;
        }
        .login-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Admin Registration</h2>
        <form action="<%= request.getContextPath() %>/admin/register" method="post" enctype="multipart/form-data">
            <!-- Full Name -->
            <input type="text" name="fullName" placeholder="Full Name" required>
            
            <!-- Email Address -->
            <input type="email" name="emailAddress" placeholder="Email Address" required>
            
            <!-- Password -->
            <input type="password" name="password" placeholder="Password" required>
            
            <!-- Date of Birth -->
            <input type="date" name="dob" placeholder="Date of Birth" required>
            
            <!-- Profile Image -->
            <label for="image">Profile Image:</label>
            <input type="file" id="image" name="image" accept="image/*" required>
            
            <!-- Phone Number -->
            <input type="text" name="phoneNo" placeholder="Phone Number" required>
            
            <!-- Submit Button -->
            <button type="submit">Register</button>
        </form>
        <div class="message">
            <!-- Display error or success messages dynamically -->
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
            <c:if test="${not empty success}">
                <p class="success">${success}</p>
            </c:if>
        </div>
        <div class="login-link">
            <p>Already registered? <a href="<%= request.getContextPath() %>/">Please login</a></p>
        </div>
    </div>
</body>
</html>
