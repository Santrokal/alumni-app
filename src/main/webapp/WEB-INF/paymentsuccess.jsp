<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        .success-message {
            color: green;
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
    <div class="success-message">Payment Successful!</div>
    <div class="details">
        <p>Thank you, ${fullName}, for your payment!</p>
        <p>Member ID: ${memberId}</p>
        <p>Payment ID: ${paymentId}</p>
        <p>Your membership has been activated successfully.</p>
        <p><a href="<%= request.getContextPath() %>/membership-details">View Membership Details</a></p>
    </div>
    <script>
        console.log("Payment success page loaded, redirecting in 5s to /success");
        setTimeout(function() {
            window.location.href = "<%= request.getContextPath() %>/success";
        }, 2000); // Redirect to /success after 5 seconds
    </script>
</body>
</html>