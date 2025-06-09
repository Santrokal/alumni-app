<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration Success</title>
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #6a11cb, #2575fc);
            color: white;
            font-family: 'Arial', sans-serif;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0;
        }
        .message-container {
            background: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            padding: 20px;
            width: 400px;
            text-align: center;
            color: #000;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5);
        }
        h2 {
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.6);
        }
        .loader {
            border: 4px solid #f3f3f3;
            border-top: 4px solid #6a11cb;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            animation: spin 1s linear infinite;
            margin: 20px auto;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
    <script>
        // Redirect to home page after 5 seconds
        setTimeout(() => {
            window.location.href = "<%= request.getContextPath() %>/";
        }, 5000);
    </script>
</head>
<body>
    <div class="message-container">
        <h2>Registration Successful!</h2>
        <p>${message}</p>
        <div class="loader"></div>
        <a href="<%= request.getContextPath() %>/register" class="btn btn-primary">Go Back</a>
        <p>You will be redirected to the login page shortly...</p>
    </div>
</body>
</html>
