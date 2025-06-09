<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PayU Payment</title>
</head>
<body>
    <h2>Redirecting to PayU Payment Gateway...</h2>
    <form id="payuForm" method="post" action="https://test.payu.in/_payment">
        <input type="hidden" name="key" value="${key}">
        <input type="hidden" name="txnid" value="${txnid}">
        <input type="hidden" name="amount" value="${amount}">
        <input type="hidden" name="productinfo" value="${productinfo}">
        <input type="hidden" name="firstname" value="${firstname}">
        <input type="hidden" name="email" value="${email}">
        <input type="hidden" name="phone" value="${phone}">
        <input type="hidden" name="surl" value="${surl}">
        <input type="hidden" name="furl" value="${furl}">
        <input type="hidden" name="hash" value="${hash}">
    </form>
    <p>Form Values: key=${key}, txnid=${txnid}, amount=${amount}, productinfo=${productinfo}, firstname=${firstname}, email=${email}, phone=${phone}, surl=${surl}, furl=${furl}, hash=${hash}</p>
    <script>
        console.log("Submitting to PayU with values:", {
            surl: "${surl}",
            furl: "${furl}"
        });
        document.getElementById("payuForm").submit();
    </script>
</body>
</html>