```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Alumni Error Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error-container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            text-align: center;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        .error-code {
            font-size: 4rem;
            color: #dc3545;
            font-weight: bold;
        }
        .error-message {
            font-size: 1.25rem;
            margin: 20px 0;
        }
        .details {
            margin-top: 20px;
            text-align: left;
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1 class="error-code">${statusCode}</h1>
        <p class="error-message">${errorMessage}</p>
        <div class="details">
            <p><strong>Request URI:</strong> ${requestUri}</p>
            <p><strong>Timestamp:</strong> ${timestamp}</p>
        </div>
        <a href="<%= request.getContextPath() %>/home" class="btn btn-primary mt-4">Back to Home</a>
        <p class="mt-3 text-muted">If this issue persists, please contact support.</p>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```