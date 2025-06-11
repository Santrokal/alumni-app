<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ page import="java.util.List"%>
<%@ page import="com.shc.alumni.springboot.entity.News"%>
<%@ page import="com.shc.alumni.springboot.entity.CompanyEntity"%>
<%@ page import="com.shc.alumni.springboot.entity.StoryEntity" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Alumni Home Page</title>
<link rel="icon" type="image/x-icon"
	href="<%= request.getContextPath() %>/logo.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Bootstrap Icons (Optional) -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Bootstrap JS (for dropdowns, modals, etc.) -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Tailwind CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
<!-- Add Animate.css for Smooth Animations -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/tailwindcss@3.4.3/dist/tailwind.min.css"
	rel="stylesheet">
	
<style>

/* Custom Styles */
body {
	background-color: #f8f9fa;
	font-family: Arial, sans-serif;
}

.navbar {
	background-color: #343a40;
}

#scrollTopArrow {
	position: fixed;
	top: -40px; /* Start fully hidden above */
	right: 30px;
	font-size: 40px;
	color: #007bff;
	cursor: pointer;
	transition: top 0.8s ease-in-out, opacity 0.5s ease;
	z-index: 1000;
	opacity: 0;
}

#scrollTopArrow.visible {
	top: 500px; /* Drop down to this position */
	opacity: 1;
}

#scrollTopArrow:hover {
	color: #0056b3;
	transform: scale(1.2);
}

.animate-slide-in-right {
	opacity: 0;
	transform: translateX(50px);
	animation: slideInRight 1s forwards ease-out;
}

@keyframes slideInRight {from { opacity:0;
	transform: translateX(50px);}
to {
	opacity: 1;
	transform: translateX(0);
}
}

/* Delayed Animations */
.delay-100 {animation-delay: 0.1s;}

.delay-200 {animation-delay: 0.2s;}

.delay-300 {animation-delay: 0.3s;}

.delay-400 {animation-delay: 0.4s;}

.delay-500 {animation-delay: 0.5s;}

.delay-600 {animation-delay: 0.6s;}

.delay-700 {animation-delay: 0.7s;}

.delay-800 {animation-delay: 0.8s;}

.navbar-brand, .nav-link {
	color: white !important;
}

.navbar-brand:hover, .nav-link:hover {
	color: #ffc107 !important;
}

.navbar a {
	color: white !important;
}

.navbar .dropdown-menu {
	background-color: #fca311;
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

.btn-outline-light {
	position: relative;
	overflow: hidden;
	transition: all 0.3s ease;
}

.btn-outline-light::before {
	content: '';
	position: absolute;
	top: 0;
	left: -100%;
	width: 100%;
	height: 100%;
	background: rgba(255, 255, 255, 0.3);
	transition: all 0.3s ease;
	z-index: 1;
}

.btn-outline-light:hover::before {
	left: 0;
}

.btn-outline-light:hover {
	color: #14213d;
	background-color: #fca311;
}

/* Logo Animation */
.logo-animate {
	transition: transform 0.5s ease-in-out;
}

.logo-animate:hover {
	transform: scale(1.1) rotate(5deg);
}

/* Link Animation */
.animated-link {
	position: relative;
	transition: color 0.3s ease, transform 0.3s ease;
}

.animated-link:hover {
	color: #007bff;
	transform: scale(1.1);
}

.animated-link:active {
	animation: clickEffect 0.3s ease;
}

/* Click Animation */
@keyframes clickEffect { 0% {
	transform: scale(1.1);
	color: #0056b3;
}

50%
{
transform:scale(1.2);
color:#003d80;
}100%
{
transform :scale(1.1);
color:#0056b3;
}
}
/* Login Button Animation */
.login-button {
	transition: all 0.3s ease-in-out;
	box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.login-button:hover {
	transform: translateY(-3px);
	box-shadow: 0px 6px 8px rgba(0, 0, 0, 0.2);
	background-color: #e89d08;
}

.career-scrolling-container {
    width: 100%;
    overflow: hidden;
    position: relative;
    padding: 10px;
}

.career-scrolling-wrapper {
    display: flex;
    gap: 15px;
    padding-bottom: 10px;
    white-space: nowrap;
    scroll-behavior: smooth;
    animation: scrollAnimation 20s linear infinite;
}

/* Smooth Infinite Scroll Animation */
@keyframes scrollAnimation {
    0% {
        transform: translateX(80%);
    }
    100% {
        transform: translateX(-70%);
    }
}

/* Career Card Styling */
.career-card {
    flex: 0 0 250px; /* Prevent shrinking */
    width: 250px; /* Ensure each card has enough space */
    background: white;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease-in-out;
}

.career-card:hover {
    transform: translateY(-5px);
}

/* Scrollbar Styling (Optional) */
.career-scrolling-wrapper::-webkit-scrollbar {
    display: none; /* Hide scrollbar */
}

/* Image Styling */
.career-card img {
    width: 100%;
    height: 150px;
    object-fit: cover;
}

/* Career Content */
.career-content {
    padding: 15px;
}

.career-title {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 5px;
}

.career-text {
    font-size: 14px;
    color: #666;
    max-height: 4.5em; /* 4-5 lines */
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 4;
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;
}

/* Read More Button */
.career-read-more {
    display: inline-block;
    margin-top: 10px;
    font-size: 14px;
    color: #007bff;
    text-decoration: none;
    font-weight: bold;
}

.career-read-more:hover {
    text-decoration: underline;
}
.career-button-container {
    text-align: center;
    margin-top: 20px;
    position: relative; /* Ensure it's above the scrolling section */
    z-index: 10; /* Higher than scrolling section */
}

.career-view-all-btn {
    display: inline-block;
    padding: 12px 24px;
    font-size: 16px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    text-decoration: none;
    font-weight: bold;
    transition: background-color 0.3s ease-in-out;
    cursor: pointer;
}

.career-view-all-btn:hover {
    background-color: #0056b3;
}

/* Fix potential overlay issue */
.career-scrolling-container {
    position: relative; /* Ensure it doesn't block clicks */
    z-index: 1;
}





.news-button-container {
    text-align: center;
    margin-top: 20px;
    position: relative; /* Ensure it's not blocked by scrolling wrapper */
    z-index: 10;
}

.news-view-all-btn {
    display: inline-block;
    padding: 12px 24px;
    font-size: 16px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    text-decoration: none;
    font-weight: bold;
    transition: background-color 0.3s ease-in-out;
    cursor: pointer;
}

.news-view-all-btn:hover {
    background-color: #0056b3;
}

/* styles.css */
.carousel-item-1 {
    background-image: url('<%= request.getContextPath() %>/images/slider1.jpeg');
}
.carousel-item-2 {
    background-image: url('<%= request.getContextPath() %>/images/slider-1aa.jpg');
}
.carousel-item-3 {
    background-image: url('<%= request.getContextPath() %>/images/NAAC.jpg');
}

/* Container Styling */
.scrolling-story-container {
    overflow: hidden;
    position: relative;
    width: 100%;
    background: #f0f0f0;
    padding: 20px 0;
    border: 2px solid #ddd;
    border-radius: 10px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}

/* Wrapper: Animate Left to Right */
.scrolling-story-wrapper {
    display: flex;
    overflow:hidden;
    animation: scroll-left-to-right 20s linear infinite;
    padding: 10px;
}

/* Each Story Card */
.story-card {
    display: inline-block;
    width: 300px;
    margin-right: 20px;
    background: white;
    border: 2px solid #ffc107;
    border-radius: 8px;
    padding: 10px;
    box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

.story-card:hover {
    transform: scale(1.05);
    box-shadow: 6px 6px 12px rgba(0, 0, 0, 0.2);
}

/* Image Styling */
.story-card img {
    width: 100%;
    height: 180px;
    object-fit: cover;
    border-bottom: 2px solid #ffc107;
}

/* Text Content */
.story-content {
    padding: 10px;
}

.story-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 8px;
    color: #333;
}

.story-text {
    font-size: 14px;
    color: #666;
    line-height: 1.5;
    height: 4.5em;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 4;
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;
}

/* Keyframes: Left to Right */
@keyframes scroll-left-to-right {
    from {
        transform: translateX(-50%);
    }
    to {
        transform: translateX(60%);
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
	<!-- Scroll-to-Top Arrow -->
	<span id="scrollTopArrow" class="scroll-top-arrow"> <i
		class="bi bi-arrow-up-circle-fill"></i>
	</span>

	<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/home">Sacred Heart College Autonomous</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="aboutDropdown" role="button" data-bs-toggle="dropdown">About Us</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/contactus">Contact</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/directory">Directory</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/gallery">Gallery</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/news">News</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="programDropdown" role="button" data-bs-toggle="dropdown">Program & Events</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/stories">Alumni Story</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/latest-members">Latest Members</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/profile">Alumni Profile</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/agm-form">AGM Meeting</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="storiesDropdown" role="button" data-bs-toggle="dropdown">Alumni Stories</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/ourrecut">Our Recruits</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="careerDropdown" role="button" data-bs-toggle="dropdown">Career Opportunity</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/applyjob">Apply To Job</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/profile">Profile</a>
                </li>
<li class="nav-item">
                        <div class="translate-wrapper">
                            <i class="fas fa-globe translate-icon" onclick="toggleTranslateDropdown()"></i>
                            <div id="google_translate_element"></div>
                        </div>
                    </li>
                <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/logout">Logout
							<i class="fa-solid fa-right-from-bracket"></i>
					</a></li>
            </ul>
        </div>
    </div>
</nav>

	<!-- Carousel Section -->
	<div id="headerCarousel" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active"
            style="background-image: url('<%= request.getContextPath() %>/images/slider1.jpeg');">
            <div class="carousel-caption">
                <h1 class="display-4">Hearty Welcomes with a Touch of Rivalry</h1>
                <p class="lead">Sacred Heart College- Empowering Bright Futures</p>
                <a href="#" class="btn btn-custom btn-lg mt-3">Read Story</a>
            </div>
        </div>
        <div class="carousel-item"
            style="background-image: url('<%= request.getContextPath() %>/images/slider-1aa.jpg');">
            <div class="carousel-caption">
                <h1 class="display-4">Innovating Education for a Brighter Tomorrow</h1>
                <p class="lead">Join our journey of academic excellence.</p>
                <a href="#" class="btn btn-custom btn-lg mt-3">Learn More</a>
            </div>
        </div>
        <div class="carousel-item"
            style="background-image: url('<%= request.getContextPath() %>/images/NAAC.jpg');">
            <div class="carousel-caption">
                <h1 class="display-4">Explore Limitless Opportunities</h1>
                <p class="lead">Your future starts here at Sacred Heart College (Autonomous)</p>
                <a href="#" class="btn btn-custom btn-lg mt-3">Get Started</a>
            </div>
        </div>
    </div>
    <button class="carousel-control-prev" type="button"
        data-bs-target="#headerCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button"
        data-bs-target="#headerCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>
<!-- Carousel Section -->
	<!-- Alumni Dashboard Section -->
	<section class="py-5">
		<div class="container text-center">
			<h2 class="mb-4">My Sacred Heart College Alumni Dashboard</h2>
			<hr class="w-25 mx-auto mb-5" style="border-top: 3px solid #fca311;">
			<div class="row g-4">
				<div
					class="col-lg-3 col-sm-6 text-center d-flex flex-column align-items-center">
					<dotlottie-player
						src="https://lottie.host/a1231178-918f-450c-8026-861f1291cd3e/VXAOWmTZRF.lottie"
						background="transparent" speed="0.5"
						style="width: 100px; height: 100px" loop autoplay></dotlottie-player>
					<h5 class="mt-3 animate-slide-in-right delay-100">Sending
						Message</h5>
					<p class="text-muted text-center animate-slide-in-right delay-200">
						If you have any queries or documents to share, you can send message to the admin through the contact section. The admin will review your message and respond to you as soon as possible. You will receive a reply directly in your Gmail inbox.
				</div>

				<div
					class="col-lg-3 col-sm-6 text-center d-flex flex-column align-items-center">
					<dotlottie-player
						src="https://lottie.host/00ff43bb-06ac-4cac-afff-b022bc231793/1YgrETyYFB.lottie"
						background="transparent" speed="1"
						style="width: 100px; height: 100px" loop autoplay></dotlottie-player>
					<h5 class="animate-slide-in-right delay-300">Update My
						Information</h5>
					<p class="text-muted animate-slide-in-right delay-400">In the
						Profile Section, you can update your personal information anytime.
						This ensures your details remain accurate and up to date for easy
						connections.</p>
				</div>

				<div
					class="col-lg-3 col-sm-6 text-center d-flex flex-column align-items-center">
					<dotlottie-player
						src="https://lottie.host/b42bccd1-1297-4ea5-86db-a89ce21c7b4c/BX1j80L5iN.lottie"
						background="transparent" speed="1"
						style="width: 100px; height: 100px" loop autoplay></dotlottie-player>
					<h5 class="animate-slide-in-right delay-500">Join with Alumni
						Forum</h5>
					<p class="text-muted animate-slide-in-right delay-600">
						Join the Alumni Forum to stay connected with former members and build a strong professional network. Engage in discussions, share experiences, and receive updates on events and opportunities. This platform allows you to reconnect with peers and contribute to the growing alumni community.</p>
				</div>

				<div
					class="col-lg-3 col-sm-6 text-center d-flex flex-column align-items-center">
					<dotlottie-player
						src="https://lottie.host/fce55551-6c52-4029-96f6-7ccb8abb6f10/yFoSjnkQVR.lottie"
						background="transparent" speed="1"
						style="width: 100px; height: 100px" loop autoplay></dotlottie-player>
					<h5 class="animate-slide-in-right delay-700">Search Alumni
						Directory</h5>
					<p class="text-muted animate-slide-in-right delay-800">The
						alumni directory lets you search for former students by name or
						department. This feature helps alumni stay connected and build
						professional networks.</p>
				</div>
			</div>
		</div>
	</section>

	<!-- News, Career, and Events Section -->
<div class="container">
    <!-- Scrolling Story Controller -->
    
        <div class="scrolling-story-wrapper">
            <% 
                List<StoryEntity> storiesList = (List<StoryEntity>) request.getAttribute("storiesList"); 
                if (storiesList != null && !storiesList.isEmpty()) { 
                    for (StoryEntity story : storiesList) { 
                        String imageUrl = story.getStoryImagePath() != null 
                            ? request.getContextPath() + "/story_folder/" + story.getStoryImagePath() 
                            : request.getContextPath() + "/images/alumni_default_img.png";
            %>
            <!-- Story Card -->
            <div class="story-card">
                <img src="<%= imageUrl %>" 
                     alt="Story Image" 
                     onerror="this.src='<%= request.getContextPath() %>/images/alumni_default_img.png'">
                <div class="story-content">
                    <h3 class="story-title"><%= story.getTitle() != null ? story.getTitle() : "Untitled" %></h3>
                    <p class="story-text">
                        <%= story.getContent() != null && story.getContent().length() > 100 
                            ? story.getContent().substring(0, 100) + "..." 
                            : (story.getContent() != null ? story.getContent() : "No content available") %>
                    </p>
                    <a href="<%= request.getContextPath() %>/stories/<%= story.getId() %>" class="btn btn-warning btn-sm">Read More</a>
                </div>
            </div>
            <% 
                    } 
                } else { 
            %>
            <p class="text-center">No stories available at the moment.</p>
            <% } %>
        </div>
    </div>
</div>



<!-- View All News Button -->
<div class="news-button-container">
    <a href="<%= request.getContextPath() %>/stories" class="news-view-all-btn">View All Stories</a>
</div>




				<!-- Career Opportunity -->
						<!-- Career Opportunity -->
<div class="career-scrolling-container">
    <div class="career-scrolling-wrapper">
        <% 
        List<CompanyEntity> companiesList = (List<CompanyEntity>) request.getAttribute("companiesList"); 
        if (companiesList != null && !companiesList.isEmpty()) { 
            for (CompanyEntity company : companiesList) { 
        %>
        <!-- Career Card -->
        <div class="career-card">
            <img src="data:image/jpeg;base64,<%= company.getImageBase64() %>" alt="Career Image">
            <div class="career-content">
                <h3 class="career-title"><%= company.getPosition() %></h3>
                <p class="career-text">
                    <%= company.getAbout() %>
                </p>
                <a href="<%= request.getContextPath() %>/job-details/<%= company.getId() %>" class="btn btn-warning btn-sm">APPLY</a>
            </div>
        </div>
        <% 
            } // End of loop
        } else { 
        %>
        <p class="text-center">No career opportunities available at the moment.</p>
        <% } %>
    </div>
</div>

<!-- View All Careers Button -->
<div class="career-button-container">
    <a href="<%= request.getContextPath() %>/applyjob" class="career-view-all-btn">View All Career Opportunities</a>
</div>





				<!-- Event Calendar 
				<div class="col-lg-4">
					<div class="border rounded p-3 shadow-sm">
						<h4 class="mb-4 text-center">Event Calendar</h4>
						<div class="overflow-auto" style="max-height: 400px;">
							<div class="event-item mb-4">
								<div class="d-flex align-items-center">
									<div class="text-center me-3">
										<h5 class="mb-0">31</h5>
										<span class="text-muted small">DEC</span>
									</div>
									<div>
										<h6 class="hover-effect">Alumni Association White Hall
											Exhibition</h6>
										<p class="text-muted small">Duis autem vel eum iriure
											dolor[...]</p>
										<p class="text-muted small">
											<i class="fa fa-map-marker-alt me-1"></i> Findlancer Terrace,
											Gondosuli, California
										</p>
									</div>
								</div>
							</div>
							<div class="event-item mb-4">
								<div class="d-flex align-items-center">
									<div class="text-center me-3">
										<h5 class="mb-0">04</h5>
										<span class="text-muted small">DEC</span>
									</div>
									<div>
										<h6 class="hover-effect">Annual Meet Up And Scholarship</h6>
										<p class="text-muted small">Duis autem vel eum iriure
											dolor[...]</p>
										<p class="text-muted small">
											<i class="fa fa-map-marker-alt me-1"></i> Sayidan Street,
											Gondomanan
										</p>
									</div>
								</div>
							</div>
							<div class="event-item mb-4">
								<div class="d-flex align-items-center">
									<div class="text-center me-3">
										<h5 class="mb-0">04</h5>
										<span class="text-muted small">DEC</span>
									</div>
									<div>
										<h6 class="hover-effect">Annual Meet Up And Scholarship</h6>
										<p class="text-muted small">Duis autem vel eum iriure
											dolor[...]</p>
										<p class="text-muted small">
											<i class="fa fa-map-marker-alt me-1"></i> Sayidan Street,
											Gondomanan
										</p>
									</div>
								</div>
							</div>
							<div class="event-item mb-4">
								<div class="d-flex align-items-center">
									<div class="text-center me-3">
										<h5 class="mb-0">04</h5>
										<span class="text-muted small">DEC</span>
									</div>
									<div>
										<h6 class="hover-effect">Annual Meet Up And Scholarship</h6>
										<p class="text-muted small">Duis autem vel eum iriure
											dolor[...]</p>
										<p class="text-muted small">
											<i class="fa fa-map-marker-alt me-1"></i> Sayidan Street,
											Gondomanan
										</p>
									</div>
								</div>
							</div>
						</div>
						<a href="#" class="btn btn-outline-primary mt-3 w-100">View
							All Events</a>
					</div>
				</div>-->
			</div>
		

	<section
		class="cta-section py-5 text-center text-black position-relative justify-content-center align-items-center text-center text-black position-relative">
		<!-- Lottie Background -->
		<div
			class="position-absolute top-0 start-50 translate-middle-x w-100 h-100 d-flex justify-content-center align-items-center opacity-25">
			<dotlottie-player
				src="https://lottie.host/027dc185-d604-4284-98f4-57a1da30ac44/djsLXjBsh9.lottie"
				background="transparent" speed="1"
				style="width: 700px; height: 700px;" loop autoplay></dotlottie-player>
		</div>
		<!-- Content -->
		<div class="container position-relative z-1">
			<h2 class="mb-3 mt-4 fw-bold">Don’t Miss Awesome Stories from
				Our Alumni</h2>

			<form action="#" method="post"
				class="d-flex flex-column flex-md-row align-items-center">
				<input type="email" name="email" placeholder="Your E-mail address"
					required class="form-control me-md-2 mb-2 mb-md-0 px-3 py-2"
					style="max-width: 320px; border-radius: 8px;">
				<button type="submit"
					class="btn btn-warning text-white fw-bold px-4 py-2"
					style="border-radius: 8px;">SUBSCRIBE</button>
			</form>
		</div>
	</section>

	<br>
	<br>
	<!-- Footer Section -->
	<footer class="bg-light py-5">
		<div class="container">
			<div class="row text-center text-md-start">
				<!-- Logo and Description -->
				<div class="col-md-3 mb-4 mb-md-0">
					<img src="<%= request.getContextPath() %>/images/logo.png"
						alt="Hello" class="mb-3 logo-animate" style="max-width: 150px;">
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
					<a href="#"
						class="text-[#1877F2] text-4xl hover:scale-125 gap-6 transition-transform duration-300 hover:drop-shadow-lg">
						<i class="bi bi-facebook" style="font-size: 2rem; color: #1877F2;"></i>
					</a> <a href="#"
						class="text-[#25D366] text-4xl gap-6 hover:scale-125 transition-transform duration-300 hover:drop-shadow-lg">
						<i class="bi bi-youtube" style="font-size: 2rem; color: red;"></i>

					</a> <a href="#"
						class="text-[#E4405F] text-4xl hover:scale-125 transition-transform duration-300 hover:drop-shadow-lg">
						<i class="bi bi-instagram"
						style="font-size: 2rem; color: #C13584;"></i>
					</a> <a href="#"
						class="text-black text-4xl hover:scale-125 transition-transform duration-300 hover:drop-shadow-lg">
						<i class="fa-brands fa-x-twitter" style="font-size: 2rem;"></i>
					</a> <a href="#"
						class="text-[#0A66C2] text-4xl hover:scale-125 transition-transform duration-300 hover:drop-shadow-lg">
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

	<!-- Add Font Awesome for Social Icons -->
	<script src="https://kit.fontawesome.com/a076d05399.js"
		crossorigin="anonymous"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="https://unpkg.com/@dotlottie/player-component@2.7.12/dist/dotlottie-player.mjs"
		type="module"></script>


	<script>
document.addEventListener("DOMContentLoaded", function () {
    let scrollTopArrow = document.querySelector("#scrollTopArrow");

    window.addEventListener("scroll", function () {
        if (window.scrollY > 300) { // Start dropping after scrolling 300px
            scrollTopArrow.classList.add("visible");
        } else {
            scrollTopArrow.classList.remove("visible");
        }
    });

    scrollTopArrow.addEventListener("click", function () {
        window.scrollTo({ top: 0, behavior: "smooth" });
    });
});
</script>
<script>
document.addEventListener("DOMContentLoaded", function () {
    const wrapper = document.querySelector(".scrolling-wrapper");
    const clone = wrapper.innerHTML; // Clone news items
    wrapper.innerHTML += clone; // Duplicate content for infinite scroll

    let speed = 0.5; // Adjust scroll speed
    let position = 0;

    function scrollNews() {
        position -= speed;
        wrapper.style.transform = `translateX(${position}px)`;

        if (Math.abs(position) >= wrapper.scrollWidth / 2) {
            position = 0;
        }
        requestAnimationFrame(scrollNews);
    }

    scrollNews();
});

document.addEventListener("DOMContentLoaded", function () {
    const scrollingWrapper = document.querySelector(".career-scrolling-wrapper");
    let isPaused = false;

    function pauseScroll() {
        scrollingWrapper.style.animationPlayState = "paused";
        isPaused = true;
    }

    function resumeScroll() {
        if (isPaused) {
            scrollingWrapper.style.animationPlayState = "running";
            isPaused = false;
        }
    }

    // Pause when a career card is tapped
    scrollingWrapper.addEventListener("touchstart", pauseScroll);

    // Resume when tapping outside the cards
    document.addEventListener("touchend", function (event) {
        if (!event.target.closest(".career-card")) {
            resumeScroll();
        }
    });
});
</script>

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
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
