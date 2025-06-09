<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ page import="com.shc.alumni.springboot.entity.News" %>
<%@ page import="java.util.List, java.util.Map, java.util.LinkedHashMap, java.util.ArrayList" %>
<%@ page import="com.shc.alumni.springboot.entity.FormField" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGM Form Register</title>
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    
               <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Icons (Optional) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
	
    <!-- Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        /* Custom Styles */
        body {
            background-color: #f1f9fb;
            font-family: Arial, sans-serif;
        }

        .navbar {
            background-color: #343a40;
        }

        .navbar-brand, .nav-link {
            color: white !important;
        }

        .navbar-brand:hover, .nav-link:hover {
            color: #ffc107 !important;
        }

        .carousel-item {
            height: 500px;
            background-size: cover;
            background-position: center;
        }

        .carousel-caption {
            background-color: rgba(0, 0, 0, 0.5);
            padding: 10px;
            border-radius: 5px;
        }

        .btn-custom {
            background-color: #fca311;
            color: #14213d;
        }

        .login-button {
            transition: all 0.3s ease-in-out;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        .login-button:hover {
            transform: translateY(-3px);
            box-shadow: 0px 6px 8px rgba(0, 0, 0, 0.2);
            background-color: #e89d08;
        }

        .hover-effect {
            transition: transform 0.3s ease, color 0.3s ease;
        }

        .hover-effect:hover {
            transform: scale(1.05);
            color: #007bff;
        }

        table {
            width: 100%;
            margin-top: 20px;
            background-color: white;
            color: black;
            border-collapse: collapse;
            border: 1px solid #ddd;
        }

        table th, table td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }

        table th {
            background-color: #6a11cb;
            color: white;
        }

        .alumni-photo {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 50%;
        }
        .news-card {
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            border: none;
            border-radius: 10px;
            overflow: hidden;
        }

        .news-card img {
            height: 100px;
            object-fit: cover;
        }

        .news-card .card-body {
            padding: 15px;
        }

        .news-card-title {
            font-size: 1.5rem;
            font-weight: bold;
            color: #343a40;
        }

        .news-card-text {
            color: #6c757d;
        }

        .news-card-date {
            font-size: 0.9rem;
            color: #adb5bd;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        }
            .news-title {
        cursor: pointer;
        transition: color 0.3s ease;
    }

    .news-title:hover {
        color: blue;
    }
.btn.btn-success {
    display: inline-block;
    text-align: center; /* Centering the text inside the button */
}

    .content-preview {
        display: -webkit-box;
        -webkit-line-clamp: 5; /* Limit to 5 lines */
        -webkit-box-orient: vertical;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .card {
        transition: transform 0.3s ease-in-out;
        cursor: pointer;
    }

    .card:hover {
        transform: scale(1.05);
    }
    /* Container Styling */
        .form-container {
            max-width: 800px;
            margin: 40px auto;
            padding: 30px;
            background: linear-gradient(135deg, #ffffff, #f5f7fa);
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            transform: perspective(1000px) rotateX(2deg);
            transition: transform 0.3s ease;
        }

        .form-container:hover {
            transform: perspective(1000px) rotateX(0deg) translateY(-5px);
        }

        /* Title Styling */
        .form-title {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 25px;
            font-family: 'Arial', sans-serif;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
            animation: titleFloat 3s ease-in-out infinite;
        }

        /* Alert Styling */
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            transform: translateZ(10px);
        }

        .alert-success {
            background: #dff0d8;
            color: #3c763d;
        }

        .alert-danger {
            background: #f2dede;
            color: #a94442;
        }

        /* Form Elements */
        .form-label {
            color: #2c3e50;
            font-weight: 500;
            margin-bottom: 5px;
            display: block;
        }

        .form-control, .form-select {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 10px;
            background: #fff;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            transform: translateZ(0);
        }

        .form-control:focus, .form-select:focus {
            outline: none;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
            transform: translateZ(10px);
        }

        .form-check-input {
            margin-right: 10px;
            transform: translateZ(0);
            transition: all 0.3s ease;
        }

        .form-check-input:hover {
            transform: translateZ(5px);
        }

        /* Button Styling */
        .btn {
            padding: 12px 35px;
            border: none;
            border-radius: 25px;
            color: white;
            font-size: 16px;
            cursor: pointer;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            transition: all 0.3s ease;
            transform: translateZ(0);
            display: inline-block;
            text-decoration: none;
        }

        .btn-primary {
            background: linear-gradient(45deg, #3498db, #2980b9);
        }

        .btn:hover {
            transform: translateY(-3px) translateZ(15px);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
        }

        /* Note Styling */
        .note {
            margin-bottom: 20px;
            color: #7f8c8d;
            transform: translateZ(5px);
        }

        /* Animation */
        @keyframes titleFloat {
            0%, 100% { transform: translateY(0) translateZ(0); }
            50% { transform: translateY(-10px) translateZ(20px); }
        }

        /* Responsive Design */
        @media (max-width: 600px) {
            .form-container {
                margin: 20px;
                padding: 20px;
            }
            .btn { width: 100%; margin-bottom: 10px; }
        }
        
/* Custom Translate Icon and Dropdown */
        .translate-wrapper { position: relative; display: inline-block; }
        .translate-icon { font-size: 1.2rem; cursor: pointer; color: #fff; }
        #google_translate_element { display: none; position: absolute; top: 100%; right: 0; z-index: 1000; background: #fff; border: 1px solid #ccc; border-radius: 4px; box-shadow: 0 2px 5px rgba(0,0,0,0.2); }
        #google_translate_element.show { display: block; }
        .nav-item:hover #google_translate_element { display: block; }
        #google_translate_element { 
    display: none; 
    position: absolute; 
    top: 100%; 
    right: 0; 
    z-index: 1000; 
    background: #fff; 
    border: 1px solid #ccc; 
    border-radius: 4px; 
    box-shadow: 0 2px 5px rgba(0,0,0,0.2); 
    max-height: 200px; 
    overflow-y: auto; 
}
    </style>
</head>
<body>
    <!-- Navbar -->
			<nav class="navbar navbar-expand-lg navbar-dark">
		<div class="container">
			<a class="navbar-brand" href="<%= request.getContextPath() %>/home">Sacred Heart College
				Autonomous</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="aboutDropdown"
						role="button" data-bs-toggle="dropdown">About Us</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="<%= request.getContextPath() %>/contactus">Contact</a></li>
							<li><a class="dropdown-item" href="<%= request.getContextPath() %>/directory">Directory</a></li>
							<li><a class="dropdown-item" href="#">Gallery</a></li>
							<li><a class="dropdown-item" href="<%= request.getContextPath() %>/news">News</a></li>
						</ul></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="programDropdown"
						role="button" data-bs-toggle="dropdown">Program & Events</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="<%= request.getContextPath() %>/stories">Alumni
									Story</a></li>
							<li><a class="dropdown-item" href="#">Latest Members</a></li>
							<li><a class="dropdown-item" href="<%= request.getContextPath() %>/profile">Alumni
									Profile</a></li>
						</ul></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="storiesDropdown" role="button" data-bs-toggle="dropdown">Alumni
							Stories</a>
						<ul class="dropdown-menu">
							<!-- <li><a class="dropdown-item" href="#">Required Materials</a></li> -->
							<!-- <li><a class="dropdown-item" href="#">World Level
									Mentors</a></li> -->
							<li><a class="dropdown-item" href="<%= request.getContextPath() %>/ourrecut">Our Recurits</a></li>
						</ul></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="careerDropdown"
						role="button" data-bs-toggle="dropdown">Career Opportunity</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="<%= request.getContextPath() %>/applyjob">Apply To
									Job</a></li>
						</ul></li>
					<li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/profile">Profile</a>
					</li>
					<li class="nav-item">
                        <div class="translate-wrapper">
                            <i class="fas fa-globe translate-icon" onclick="toggleTranslateDropdown()"></i>
                            <div id="google_translate_element"></div>
                        </div>
                    </li>
				</ul>
			</div>
		</div>
	</nav>
    <!-- Content Section -->
    <div class="container d-flex flex-column align-items-center my-1">
<!-- News Section -->
<div class="form-container">
        <h2 class="form-title">AGM Registration Form</h2>
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) { 
        %>
            <div class="alert alert-danger" role="alert">
                <%= error %>
            </div>
            <a href="<%= request.getContextPath() %>/home" class="btn btn-primary">Back to Home</a>
        <% 
            } else { 
                Boolean hasResponded = (Boolean) request.getAttribute("hasResponded");
                if (hasResponded != null && hasResponded) { 
        %>
            <div class="alert alert-success" role="alert">
                Thank you for your response! You have already submitted the AGM form.
            </div>
            <a href="<%= request.getContextPath() %>/home" class="btn btn-primary">Back to Home</a>
        <% 
                } else { 
        %>
            <p class="note">Note: Registration is free for members. Non-members will be charged ₹150.</p>
            <form id="agmForm" action="<%= request.getContextPath() %>/agm-form" method="POST">
                <%
                    List<FormField> fields = (List<FormField>) request.getAttribute("fields");
                    String phoneNumber = (String) request.getAttribute("phoneNumber");
                    if (fields != null) {
                        for (FormField field : fields) {
                            String fieldName = field.getName();
                            String fieldType = field.getType();
                %>
                    <div class="mb-3">
                        <label class="form-label"><%= fieldName %></label>
                        <%
                            if ("text".equals(fieldType) || "email".equals(fieldType)) {
                        %>
                            <input type="<%= fieldType %>" class="form-control" name="<%= fieldName %>">
                        <%
                            } else if ("textarea".equals(fieldType)) {
                        %>
                            <textarea class="form-control" name="<%= fieldName %>"></textarea>
                        <%
                            } else if ("number".equals(fieldType)) {
                        %>
                            <input type="number" class="form-control" name="<%= fieldName %>">
                        <%
                            } else if ("date".equals(fieldType)) {
                        %>
                            <input type="date" class="form-control" name="<%= fieldName %>">
                        <%
                            } else if ("select".equals(fieldType)) {
                        %>
                            <select class="form-select" name="<%= fieldName %>">
                                <option value="">Select <%= fieldName %></option>
                                <%
                                    if ("Gender".equals(fieldName)) {
                                %>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Other">Other</option>
                                <%
                                    } else if ("Marital Status".equals(fieldName)) {
                                %>
                                    <option value="Single">Single</option>
                                    <option value="Married">Married</option>
                                    <option value="Divorced">Divorced</option>
                                <%
                                    } else {
                                %>
                                    <option value="Yes">Yes</option>
                                    <option value="No">No</option>
                                <%
                                    }
                                %>
                            </select>
                        <%
                            } else if ("checkbox".equals(fieldType)) {
                        %>
                            <input type="checkbox" class="form-check-input" name="<%= fieldName %>" value="Yes">
                        <%
                            }
                        %>
                    </div>
                <%
                        }
                    }
                %>
                <input type="hidden" id="formData" name="formData">
                <button type="button" class="btn btn-primary" onclick="submitForm()">Submit</button>
            </form>
        <% 
                } 
            } 
        %>
    </div>
</div>

    <br>
    <!-- Footer Section -->
	<footer class="bg-light py-5">
		<div class="container">
			<div class="row text-center text-md-start">
				<!-- Logo and Description -->
				<div class="col-md-3 mb-4 mb-md-0">
					<img src="<%= request.getContextPath() %>/images/logo.png" alt="Hello" class="mb-3 logo-animate" style="max-width: 150px;">
					<p>Alumni Association of the Sacred Heart College.</p>
				</div>
				<!-- University Links -->
				<div class="col-md-3 mb-4 mb-md-0">
					<h5 class="fw-bold">UNIVERSITY</h5>
					<ul class="list-unstyled">
						<li><a href="#"
							class="text-dark text-decoration-none animated-link">Events</a></li>
						<li><a href="<%= request.getContextPath() %>/directory"
							class="text-dark text-decoration-none animated-link">Gallery</a></li>
						<li><a href="<%= request.getContextPath() %>/news"
							class="text-dark text-decoration-none animated-link">News</a></li>
					</ul>
				</div>
				<!-- Alumni Links -->
				<div class="col-md-3 mb-4 mb-md-0">
					<h5 class="fw-bold">ALUMNI</h5>
					<ul class="list-unstyled">
						<li><a href="<%= request.getContextPath() %>/contactus"
							class="text-dark text-decoration-none animated-link">Contacts</a></li>
						<li><a href="<%= request.getContextPath() %>/applyjob"
							class="text-dark text-decoration-none animated-link">Career</a></li>
						<li><a href="<%= request.getContextPath() %>/about"
							class="text-dark text-decoration-none animated-link">About Us</a></li>
					</ul>
				</div>
				<!-- Account Links -->
				<div class="col-md-3 mb-4 mb-md-0">
					<h5 class="fw-bold">ACCOUNT</h5>
					<ul class="list-unstyled">
						<li><a href="<%= request.getContextPath() %>/profile"
							class="text-dark text-decoration-none animated-link">Profile</a></li>
						<li><a href="<%= request.getContextPath() %>/stories"
							class="text-dark text-decoration-none animated-link">Stories</a></li>
						<li><a href="#"
							class="text-dark text-decoration-none animated-link">Downloads</a></li>
					</ul>
				</div>
			</div>
			
			
			<div class="row mt-4 align-items-center">
				<!-- Alumni Account Button -->
				<div class="col-md-3 text-center text-md-start"></div>
				<!-- Social Media Icons -->
<div class="col-md-6 text-center my-3 gap-7 my-md-0 space-x-4">
    <a href="#" class="text-[#1877F2] text-4xl hover:scale-125 gap-6 transition-transform duration-300 hover:drop-shadow-lg">
<i class="bi bi-facebook" style="font-size: 2rem; color: #1877F2;"></i>
    </a> 
    <a href="#" class="text-[#25D366] text-4xl gap-6 hover:scale-125 transition-transform duration-300 hover:drop-shadow-lg">
        <i class="bi bi-youtube" style="font-size: 2rem; color: red;"></i>

    </a>
    <a href="#" class="text-[#E4405F] text-4xl hover:scale-125 transition-transform duration-300 hover:drop-shadow-lg">
<i class="bi bi-instagram" style="font-size: 2rem; color: #C13584;"></i>
    </a> 
    <a href="#" class="text-black text-4xl hover:scale-125 transition-transform duration-300 hover:drop-shadow-lg">
        <i class="fa-brands fa-x-twitter" style="font-size: 2rem;"></i>
    </a>
    <a href="#" class="text-[#0A66C2] text-4xl hover:scale-125 transition-transform duration-300 hover:drop-shadow-lg">
        <i class="fa-brands fa-linkedin" style="font-size: 2rem;"></i>
    </a>
</div>


				<!-- Copyright -->
				<div class="col-md-3 text-center text-md-end">
					<p class="text-muted mb-0">&copy;2024 Alumni Association</p>
				</div>
			</div>
		</div>
	</footer>
	
					<!-- Google Translate Script -->
    <script type="text/javascript">
    function googleTranslateElementInit() {
        new google.translate.TranslateElement({
            pageLanguage: 'en', // Default language (English)
            includedLanguages: 'en,es,fr,de,hi,ar,zh-CN,ja,pt,it,ru,ko,bn,ta,te,ml,gu,pa,ur,vi,th,ms,id,nl,sv,no,da,fi,el,he,hu,pl,cs,sk,uk,tr,sq,am,az,be,bg,ca,et,fa,gl,ka,kn,km,lo,lt,lv,mk,mn,my,ne,si,sl,sr,sw,tg,uz,zu',
            layout: google.translate.TranslateElement.InlineLayout.SIMPLE,
            autoDisplay: false
        }, 'google_translate_element');
    }

    function toggleTranslateDropdown() {
        var element = document.getElementById('google_translate_element');
        element.classList.toggle('show');
    }
    </script>
    <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>

<script>
        function submitForm() {
            const form = document.getElementById('agmForm');
            const inputs = form.querySelectorAll('input, select, textarea');
            const formData = {};
            
            inputs.forEach(input => {
                if (input.type === 'checkbox') {
                    formData[input.name] = input.checked ? 'Yes' : 'No';
                } else {
                    formData[input.name] = input.value;
                }
            });
            
            document.getElementById('formData').value = JSON.stringify(formData);
            form.submit();
        }
    </script>
    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
