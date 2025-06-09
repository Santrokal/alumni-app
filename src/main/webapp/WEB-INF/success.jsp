<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Application Submitted</title>
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="text-center">
            <div class="alert alert-success" role="alert">
                <h1 class="display-4">Application Submitted Successfully!</h1>
                <p class="lead">Thank you for applying. Your application has been submitted.</p>
            </div>
            <p class="text-muted">Our team will review your application and contact you via E-mail shortly.</p>
            <p class="text-muted">If you have any questions, feel free to reach out to us.</p>
            <a href="<%= request.getContextPath() %>/home" class="btn btn-primary btn-lg mt-3">Go Back to Home</a>
        </div>
    </div>
</body>
</html>
