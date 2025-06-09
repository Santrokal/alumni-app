<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>AGM Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Error</h2>
        <p>
            <% 
                String error = (String) request.getAttribute("error");
                if (error != null) {
                    out.print(error);
                } else {
                    out.print("Something went wrong. Please try again.");
                }
            %>
        </p>
        <a href="<%= request.getContextPath() %>/agm-form" class="btn btn-primary">Back to AGM Form</a>
        <a href="<%= request.getContextPath() %>/" class="btn btn-secondary">Back to Home</a>
    </div>
</body>
</html>