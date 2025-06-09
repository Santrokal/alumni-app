<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Failed</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        .failure-message {
            color: red;
            font-size: 24px;
            margin-bottom: 20px;
        }
        .details {
            font-size: 18px;
        }
        a {
            color: blue;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <h1 class="error">Payment Failed</h1>
    <p>${errorMessage}</p>
    <p>Please try again or contact support if the issue persists.</p>
    <a href="<%= request.getContextPath() %>/membership">Retry Payment</a>
</body>
</html>