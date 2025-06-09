<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Alumni Job Feed Page</title>
<link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
	rel="stylesheet">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Icons (Optional) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
	

<!-- Font Awesome CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
	rel="stylesheet">


<style>
        /* Custom Styles */
        body {
            background-color: #f1f9fb;
            font-family: Arial, sans-serif;
        }
        
              /* Fade-in Animation */
        @keyframes fadeIn {
            0% { opacity: 0; }
            100% { opacity: 1; }
        }
        
    .container-1 {
            max-width: 500px;
            margin: auto;
            background: #ffffff;
            padding: 20px;
            align:center;
            border-radius: 8px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            animation: fadeIn 1s forwards; 
            opacity: 0; /* Initially hidden */
        }

        
        input[type="text"],
        textarea,
        input[type="file"] {
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        input[type="text"]:focus,
        textarea:focus,
        input[type="file"]:focus {
            border-color: #fca311;
            box-shadow: 0 0 5px rgba(252, 163, 17, 0.7);
        }

        label {
            font-size: 1.1em;
            font-weight: bold;
            color: #333;
            transition: color 0.3s ease;
        }

        input[type="text"]:hover,
        textarea:hover {
            color: #fca311;
        }

        .btn-custom {
            background-color: #fca311;
            color: #14213d;
            align:center;
            transition: transform 0.3s ease, background-color 0.3s ease;
        }

        .btn-custom:hover {
            transform: scale(1.05);
            
            background-color: #e89d08;
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
/* 3D Animation and Transition Styles for Career Job Post */
.career-job-post-section {
    perspective: 1000px; /* 3D perspective */
    margin-top: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.career-job-post {
    transform: rotateY(-15deg); /* Initial rotation */
    transition: transform 0.6s ease, box-shadow 0.4s ease;
    box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.3);
    background: #ffffff;
}

.career-job-post:hover {
    transform: rotateY(0deg) translateY(-10px) scale(1.03);
    box-shadow: 0px 15px 25px rgba(0, 0, 0, 0.5);
}

.animated-heading {
    text-align: center;
    font-size: 1.8em;
    margin-bottom: 20px;
    background: linear-gradient(to right, #6a11cb, #2575fc);
    color: white;
    padding: 10px 15px;
    border-radius: 8px;
    transform: rotateX(90deg); /* Initial animation position */
    opacity: 0;
    animation: fadeInSlideDown 1.2s ease-out forwards;
}

.animated-form {
    transform: rotateX(-20deg) translateZ(100px); /* 3D effect */
    animation: formEntrance 1.5s ease-out forwards;
    margin: 0 auto; /* Centers the form horizontally */
    /* Ensures form content can align properly */
    flex-direction: column; /* Align children in a column */
    align-items: center; /* Centers child elements horizontally */
    justify-content: center; /* Centers child elements vertically */
}

.animated-form input[type="text"],
.animated-form textarea,
.animated-form input[type="file"] {
    width: 100%; /* Full width of the form container */
    max-width: 460px; /* Limit the maximum width */
    height: 50px; /* Increase height for larger text boxes */
    padding: 10px; /* Add padding for better readability */
    font-size: 16px; /* Larger font size */
    border: 1px solid #ccc; /* Border styling */
    border-radius: 5px; /* Rounded corners */
    transition: border-color 0.3s ease, box-shadow 0.3s ease; /* Smooth transition */
}

.animated-form input[type="text"]:focus,
.animated-form textarea:focus,
.animated-form input[type="file"]:focus {
    border-color: #fca311; /* Highlight on focus */
    box-shadow: 0 0 5px rgba(252, 163, 17, 0.7); /* Glow effect */
}



.animated-button {
    background-color: #6a11cb;
    color: white;
    border: none;
    padding: 10px 20px;
    font-size: 1em;
    border-radius: 5px;
    cursor: pointer;
    transition: transform 0.3s ease, background-color 0.3s ease;
    display: flex; /* Ensure flexbox behavior */
    align-items: center; /* Center content vertically */
    justify-content: center; /* Center content horizontally */
    margin: 0 auto; /* Center the button horizontally within its container */
}

.animated-button:hover {
    transform: scale(1.1) rotate(-2deg);
    background-color: #2575fc;
    box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.2);
}

.center-container {
    display: flex; /* Enable flexbox for parent container */
    align-items: center; /* Center the button vertically */
    justify-content: center; /* Center the button horizontally */
    height: 100vh; /* Full viewport height for vertical centering */
}


/* Keyframes for Animations */
@keyframes fadeInSlideDown {
    0% {
        opacity: 0;
        transform: rotateX(90deg) translateY(-20px);
    }
    100% {
        opacity: 1;
        transform: rotateX(0deg);
    }
}

@keyframes formEntrance {
    0% {
        transform: rotateX(-20deg) translateZ(100px);
        opacity: 0;
    }
    100% {
        transform: rotateX(0deg) translateZ(0);
        opacity: 1;
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
	<!-- Header Section Starts -->
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
	<!-- Header Section Ends -->
	<!-- Carrer Job Post Starts -->
<section class="career-job-post-section">
    <div class="container-1 career-job-post">
        <h2 class="animated-heading">Add Company Details</h2>
        <form action="<%= request.getContextPath() %>/submitCompany" method="POST" enctype="multipart/form-data" class="animated-form">
            <label for="position"><span style="color: red;">*</span>Position:</label><br>
            <input type="text" id="position" name="position" required><br><br>
            
            <label for="about">About Company:</label><br>
            <input type="text" id="about" name="about" ><br><br>
            
            <label for="jobdetails">Job Details:</label><br>
            <input type="text" id="jobdetails" name="jobdetails" ><br><br>
            
            <label for="skills">Skills & Requirements:</label><br>
            <input type="text" id="skills" name="skills" ><br><br>

            <label for="location">Location:</label><br>
            <input type="text" id="location" name="location"><br><br>

            <label for="role">Role:</label><br>
            <input type="text" id="role" name="role"><br><br>
            
            <label for="companyemailid"><span style="color: red;">*</span>Company E-Mail:</label><br>
            <input type="text" id="companyemailid" name="companyemailid" required><br><br>

            <label for="image">Upload Company Logo:</label><br>
            <input type="file" id="image" name="image" accept="image/*"><br><br>

            <label for="fileUpload">Job Posture/Job Details PDF/etc..:</label><br>
            <input type="file" id="fileUpload" name="fileUpload" accept=".docx, .pdf, .xlsx, .txt, .jpeg ,.jpg, .png"><br><br>

    <button class="animated-button">Submit</button>	

        </form>
    </div>
</section>
	<!-- Carrer Job Post Ends -->



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
	
	<script src="https://kit.fontawesome.com/a076d05399.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
