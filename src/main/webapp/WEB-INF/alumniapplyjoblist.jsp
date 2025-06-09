<%@page import="com.shc.alumni.springboot.entity.CompanyEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shc.alumni.springboot.entity.CompanyEntity" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alumni Job List Page</title>
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
    
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Icons (Optional) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
	
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    
               <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
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

/* General Table Styling */
.table {
    width: 100%;
    border-collapse: collapse;
    background-color: #ffffff;
    border: 1px solid #ddd;
    margin: 0 auto;
    font-family: Arial, sans-serif;
    font-size: 14px;
}

/* Table Header */
.table thead th {
    background-color: #f4f4f4;
    color: #333;
    text-align: left;
    font-weight: bold;
    padding: 12px;
    border-bottom: 2px solid #ddd;
}

/* Table Body */
.table tbody td {
    padding: 12px;
    border-bottom: 1px solid #eee;
    color: #555;
}

/* Hover Effect */
.table tbody tr:hover {
    background-color: #f9f9f9;
    cursor: pointer;
}

/* Alternate Row Colors */
.table tbody tr:nth-child(odd) {
    background-color: #fefefe;
}
.table tbody tr:nth-child(even) {
    background-color: #f9f9f9;
}

/* Image Styling */
.table img {
    height: 40px;
    width: auto;
    display: block;
    margin: auto;
    border-radius: 4px;
}

/* Apply Button */
.btn-warning {
    color: #ffffff;
    background-color: #ffc107;
    border: none;
    padding: 8px 16px;
    text-transform: uppercase;
    font-weight: bold;
    border-radius: 4px;
    transition: background-color 0.3s ease, transform 0.2s ease;
    text-align: center;
    display: inline-block;
}

.btn-warning:hover {
    background-color: #e0a800;
    transform: scale(1.05);
}

/* Responsive Design */
@media (max-width: 768px) {
    .table {
        font-size: 12px;
    }
    .btn-warning {
        padding: 6px 12px;
        font-size: 12px;
    }
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
<!-- Career Job Section -->
<div class="container mt-4">
    <h1 class="text-center mb-4">Career Opportunities</h1>

    <% 
        List<CompanyEntity> companiesList = (List<CompanyEntity>) request.getAttribute("companiesList"); 
        if (companiesList != null && !companiesList.isEmpty()) { 
    %>
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead class="thead-light">
                    <tr>
                        <th>Company</th>
                        <th>Position</th>
                        <th>Location</th>
                        <th>Role</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <% for (CompanyEntity company : companiesList) { %>
                        <tr>
                            <td>
                                <img src="data:image/jpeg;base64,<%= company.getImageBase64() %>" 
                                     alt="Company Logo" style="height: 40px; width: auto;">
                            </td>
                            <td><%= company.getPosition() %></td>
                            <td><%= company.getLocation() %></td>
                            <td><%= company.getRole() %></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/job-details/<%= company.getId() %>" class="btn btn-warning btn-sm">APPLY</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    <% 
        } else { 
    %>
        <p class="text-center">No job openings available at the moment.</p>
    <% } %>
</div>

        <!-- Pagination -->
        <div class="pagination-wrapper"></div>
    </div>
    
            <!-- Add News Button -->
<div class="d-flex justify-content-center w-100 mb-6" style="max-width: 1200px;">
    <a href="<%= request.getContextPath() %>/carrer" class="btn btn-success">Add New Job</a>
</div>


    <br>

    <!-- Footer Section -->
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
	
    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
