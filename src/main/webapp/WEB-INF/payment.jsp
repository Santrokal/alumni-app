<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Payment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>AGM Registration Fee Payment</h2>
        <p>Please complete the payment of ₹150 to register for the AGM</p>
        
        <form id="payuForm" action="${payuUrl}" method="POST">
            <%
                java.util.Map<String, String> paymentParams = 
                    (java.util.Map<String, String>) request.getAttribute("paymentParams");
                if (paymentParams != null) {
                    for (java.util.Map.Entry<String, String> entry : paymentParams.entrySet()) {
            %>
                <input type="hidden" name="<%= entry.getKey() %>" value="<%= entry.getValue() %>">
            <%
                    }
                }
            %>
            <button type="submit" class="btn btn-primary">Pay ₹150 via PayU</button>
        </form>
    </div>

    <script>
        window.onload = function() {
            document.getElementById('payuForm').submit();
        }
    </script>
</body>
</html>