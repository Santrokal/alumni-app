<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shc.alumni.springboot.entity.BillPdfEntity" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Membership Details</title>
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
    

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        .membership-details {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
            padding: 20px;
            max-width: 500px;
            margin: 20px;
            transform: perspective(1500px) rotateY(20deg);
            opacity: 0;
            animation: fadeInUp 1s ease-out forwards, rotateIn 1.5s ease-in-out;
        }

        /* Fade and slide in effect */
        @keyframes fadeInUp {
            0% {
                opacity: 0;
                transform: translateY(50px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* 3D rotation animation */
        @keyframes rotateIn {
            0% {
                transform: perspective(1500px) rotateY(90deg);
            }
            100% {
                transform: perspective(1500px) rotateY(0deg);
            }
        }

        .details p {
            font-size: 16px;
            margin: 10px 0;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        a:hover {
            background-color: #45a049;
        }

    </style>
</head>
<body>

    <h1>Membership Details</h1>

    <!-- Check if membership exists -->
    <% if(request.getAttribute("message") != null) { %>
        <div class="membership-details">
            <div class="details">
                <h2><%= request.getAttribute("message") %></h2>
                <p><strong>Full Name:</strong> <%= request.getAttribute("fullName") %></p>
                <p><strong>Member ID:</strong> <%= request.getAttribute("memberId") %></p>
                <p><strong>Phone Number:</strong> <%= request.getAttribute("emailAddress") %></p>
                <p><strong>Created At:</strong> <%= request.getAttribute("createdAt") %></p>
            </div>
        </div>
    <% } else { %>
        <p>No membership details available.</p>
    <% } %>
    <a href="<%= request.getContextPath() %>/">Go back to Home</a>
    <!-- 
    <%
    List<BillPdfEntity> bills = (List<BillPdfEntity>) request.getAttribute("bills");
    if (bills != null && !bills.isEmpty()) {
        for (BillPdfEntity bill : bills) {
%>
            <tr>
                <td><%= bill.getDate() %></td>
                <td><%= bill.getPaymentId() %></td>
                <td><%= bill.getMemberId() %></td>
                <td class="<%= "PAID".equalsIgnoreCase(bill.getStatus()) ? "paid-status" : "unpaid-status" %>">
                    <%= bill.getStatus() %>
                </td>
                <td>
                    <% if ("PAID".equalsIgnoreCase(bill.getStatus())) { %>
                        <a class="btn btn-sm btn-primary" href="<%= request.getContextPath() %>/download-bill/<%= bill.getId() %>">
                            Download PDF
                        </a>
                    <% } else { %>
                        <span class="text-danger">Unpaid</span>
                    <% } %>
                </td>
            </tr>
<%
        }
    } else {
%>
    <tr>
        <td colspan="5" class="text-center">No membership details available</td>
    </tr>
<% } %>
    
     -->

</body>
</html>
