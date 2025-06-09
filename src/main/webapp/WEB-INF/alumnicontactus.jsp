<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Alumni Contact Us Page</title>
<link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">

<!-- Bootstrap CSS -->
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
    .font-montserrat-light {
    font-family: 'Montserrat', sans-serif;
    font-weight: 300;
}

.font17 {
    font-size: 17px;
}

.font25 {
    font-size: 25px;
}

.text-primary {
    color: #007bff !important; /* Adjust as needed for branding */
}


    /* General animation for 3D text effect */
    .animated-text {
        display: inline-block;
        position: relative;
        font-size: 32px;
        text-transform: uppercase;
        color: #0044cc;
        transition: transform 0.3s ease-in-out;
        letter-spacing: 3px;
    }

    /* Add 3D hover effect */
    .animated-text:hover {
        transform: rotateY(20deg) scale(1.1);
        color: #ff4081;
        text-shadow: 5px 5px 15px rgba(0, 0, 0, 0.2);
    }

    /* Hover effect for links */
    .hover-link {
        text-decoration: none;
        color: #333;
        transition: color 0.3s ease, text-shadow 0.3s ease;
    }

    .hover-link:hover {
        color: #ff4081;
        text-shadow: 2px 2px 10px rgba(255, 64, 129, 0.8);
    }

    /* Button hover effect */
    .hover-btn {
        transition: all 0.3s ease;
        font-weight: bold;
    }

    .hover-btn:hover {
        background-color: #ff4081;
        color: white;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    }

    /* Shadow effect on card */
    .card {
        transition: box-shadow 0.3s ease;
    }

    .card:hover {
        box-shadow: 0 12px 20px rgba(0, 0, 0, 0.2);
    }
    
    .button-86 {
  all: unset;
  width: 130px;
  height: 30px;
  font-size: 16px;
  background: transparent;
  border: none;
  position: relative;
  color: #f0f0f0;
  cursor: pointer;
  z-index: 1;
  padding: 10px 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  white-space: nowrap;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

.button-86::after,
.button-86::before {
  content: '';
  position: absolute;
  bottom: 0;
  right: 0;
  z-index: -99999;
  transition: all .4s;
}

.button-86::before {
  transform: translate(0%, 0%);
  width: 100%;
  height: 100%;
  background: #28282d;
  border-radius: 10px;
}

.button-86::after {
  transform: translate(10px, 10px);
  width: 35px;
  height: 35px;
  background: #ffffff15;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border-radius: 50px;
}

.button-86:hover::before {
  transform: translate(5%, 20%);
  width: 110%;
  height: 110%;
}

.button-86:hover::after {
  border-radius: 10px;
  transform: translate(0, 0);
  width: 100%;
  height: 100%;
}

.button-86:active::after {
  transition: 0s;
  transform: translate(0, 5%);
}

.button-container {
  display: flex;
  justify-content: center; /* Centers horizontally */
  align-items: center;    /* Centers vertically */
  height: 05vh;          /* Full viewport height */
  width: 100%;            /* Full width of the container */
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

    <!-- Google Maps Section -->
    <div class="map-contact text-center my-4">
    <iframe 
        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3895.203987464917!2d78.5672802151673!3d12.502634291164775!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3bac5477791c6209%3A0xb2d10c9de806db50!2sSacred%20Heart%20College%20(Autonomous)!5e0!3m2!1sen!2sin!4v1624955995531!5m2!1sen!2sin" 
        width="100%" 
        height="500" 
        style="border:0; display:block; max-width:100%;" 
        allowfullscreen 
        loading="lazy" 
        referrerpolicy="no-referrer-when-downgrade">
    </iframe>
</div>

    <!-- Google Maps Section Ends -->
<!-- Visit Us Address Section Starts -->
<div class="container my-5">
    <div class="row justify-content-center">
        <!-- Visit Us Column -->
        <div class="col-lg-5 col-md-6 mb-4">
            <div class="card border-0 shadow-lg h-100">
                <div class="card-body p-4">
                    <h3 class="font-montserrat-light font-weight-bold text-capitalize text-primary mb-3 animated-text">Visit Us</h3>
                    <p class="font-montserrat-light font17 mb-4">
                        Sacred Heart College (Autonomous) Tirupattur-635 601, Tirupattur District, Tamil Nadu, India. <br>
                        <span class="text-muted">Office Hours:</span> Monday to Friday, 10am - 7pm
                    </p>
                    <a href="<%= request.getContextPath() %>/contactus" class="btn btn-outline-primary btn-sm text-uppercase hover-btn">Get Directions</a>
                </div>
            </div>
        </div>
        <!-- Get In Touch Column -->
        <div class="col-lg-5 col-md-6 mb-4">
            <div class="card border-0 shadow-lg h-100">
                <div class="card-body p-4">
                    <h3 class="font-montserrat-light font-weight-bold text-capitalize text-primary mb-3 animated-text">Get In Touch</h3>
                    <div class="mb-3">
                        <span class="d-block font-montserrat-light font17 text-uppercase text-muted">Tel:</span>
                        <a href="tel:+12345678900" class="font-montserrat-light font17 text-dark hover-link">+91 417 922 0553</a>
                    </div>
                    <div class="mb-3">
                        <span class="d-block font-montserrat-light font17 text-uppercase text-muted">Email:</span>
                        <a href="mailto:iff.salman366@gmail.com" class="font-montserrat-light font17 text-dark hover-link">alumni@shctpt.edu</a>
                    </div>
                    <div>
                        <span class="d-block font-montserrat-light font17 text-uppercase text-muted">Website:</span>
                        <a href="#" class="font-montserrat-light font17 text-dark hover-link">https://shctpt.edu</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Visit Us Address Section Ends -->

<!-- Send Message Section Starts -->
<div class="container my-5">
    <div class="row">
        <div class="col-12">
            <h3 class="page-subheading font-montserrat-light font25 text-center">We’d love to hear from you</h3>
            <form id="contactForm" class="wpcf7-form init mt-4" novalidate>
                <div class="row g-3">
                    <!-- Full Name -->
                    <div class="col-md-6">
                        <label for="fullName" class="form-label"><span style="color: red;">*</span>Full Name</label>
                        <input id="fullName" type="text" name="fullName" placeholder="Enter your full name"
                            class="form-control wpcf7-form-control wpcf7-validates-as-required" required>
                    </div>
                    <!-- Phone Number -->
                    <div class="col-md-6">
                        <label for="phoneNumber" class="form-label"><span style="color: red;">*</span>Phone Number</label>
                        <input id="phoneNumber" type="text" name="phoneNo" placeholder="Enter your phone number"
                            class="form-control wpcf7-form-control wpcf7-validates-as-required" required>
                    </div>
                    <!-- Email -->
                    <div class="col-md-6">
                        <label for="email" class="form-label"><span style="color: red;">*</span>Email Address</label>
                        <input id="email" type="email" name="emailAddress" placeholder="Enter your email address"
                            class="form-control wpcf7-form-control wpcf7-validates-as-email" required>
                    </div>
                    <!-- Additional Information -->
                    <div class="col-md-6">
                        <label for="additionalInfo" class="form-label"><span style="color: red;">*</span>Additional Information</label>
                        <input id="additionalInfo" type="text" name="addinfo" placeholder="Enter any additional details"
                            class="form-control wpcf7-form-control">
                    </div>
                </div>
                <br>
                <!-- Submit Button -->
<div class="button-container">
  <button type="submit" class="button-86">
    SEND MESSAGE
  </button>
</div>
	
                <!-- Response Message -->
                <div id="responseMessage" class="mt-4 text-center"></div>
            </form>
        </div>
    </div>
</div>
<!-- Send Message Section Ends -->




<!-- Send Message Section Script Starts -->
<script>
function submitForm(event) {
    event.preventDefault(); // Prevent default form submission

    // Collect form inputs
    const form = document.getElementById("contactForm");
    const fullName = document.getElementById("fullName");
    const phoneNumber = document.getElementById("phoneNumber");
    const email = document.getElementById("email");

    // Validation: Check for empty fields
    if (!fullName.value.trim()) {
        alert("Please enter your full name.");
        fullName.focus();
        return;
    }

    if (!phoneNumber.value.trim()) {
        alert("Please enter your phone number.");
        phoneNumber.focus();
        return;
    }

    if (!email.value.trim()) {
        alert("Please enter your email address.");
        email.focus();
        return;
    }
    if (!additionalInfo.value.trim()) {
        alert("Please enter the Info.");
        additionalInfo.focus();
        return;
    }

    // Optional validation for email format (simple regex)
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(email.value)) {
        alert("Please enter a valid email address.");
        email.focus();
        return;
    }

    // Collect form data
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value.trim(); // Trim inputs for cleaner data
    });

    // Send the form data as JSON
    fetch('<%= request.getContextPath() %>/contactus', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data), // Convert data object to JSON string
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw err; });
        }
        return response.json();
    })
    .then(data => {
        // Show success message and reset the form
        alert(data.message || "Thank you! Your message has been received!");
        form.reset();
    })
    .catch(error => {
        // Show error message
        console.error("Error:", error);
        alert("Error: " + (error.error || "An unexpected error occurred."));
    });
}

// Attach the submit handler to the form
document.getElementById("contactForm").addEventListener("submit", submitForm);

</script>

<!-- Send Message Section Script Ends -->


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
	<script src="https://kit.fontawesome.com/a076d05399.js"
		crossorigin="anonymous"></script>
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB4UPbXRjn8q_aX4pf3Wv_YqPQ606KFWZ4&callback=initMap" async defer></script>
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
		
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>