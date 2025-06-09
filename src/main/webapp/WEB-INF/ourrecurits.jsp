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
    animation: scrollAnimation 10s linear infinite;
}

/* Smooth Infinite Scroll Animation */
@keyframes scrollAnimation {
    0% {
        transform: translateX(0);
    }
    100% {
        transform: translateX(-50%);
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


/* Scrolling News Container */
.scrolling-container {
    overflow: hidden;
    position: relative;
    width: 100%;
    background: #f9f9f9;
    padding: 20px 0;
    border: 2px solid #ddd;  /* Outer border */
    border-radius: 10px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}

/* Scrolling Wrapper */
.scrolling-wrapper {
    display: flex;
    animation: scroll 15s linear infinite;
    padding: 10px;
}

/* Individual News Card */
.scrolling-wrapper .news-card {
    display: inline-block;
    width: 300px;  /* Adjust card width */
    margin-right: 20px;
    background: white;
    border: 2px solid #007bff; /* Square box border */
    border-radius: 0px;  /* Strict square edges */
    padding: 10px;
    box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

.scrolling-wrapper .news-card:hover {
    transform: scale(1.05);
    box-shadow: 6px 6px 12px rgba(0, 0, 0, 0.2);
}

/* Image */
.news-card img {
    width: 100%;
    height: 180px;
    object-fit: cover;
    border-bottom: 2px solid #007bff; /* Bottom border for separation */
}

/* Content */
.news-content {
    padding: 10px;
}

/* News Title */
.news-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 8px;
    color: #333;
}

/* News Text - Limit to 4-5 lines */
.news-text {
    font-size: 14px;
    color: #666;
    line-height: 1.5;
    height: 4.5em; /* 4-5 lines of text */
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 4; /* Limits to 4 lines */
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;
}

/* Read More Link */
.news-read-more {
    display: inline-block;
    margin-top: 10px;
    color: #007bff;
    text-decoration: none;
    font-weight: 500;
}

.news-read-more:hover {
    color: #0056b3;
}


/* Keyframes for Scrolling Animation */
@keyframes scroll {
    from {
        transform: translateX(50%);
    }
    to {
        transform: translateX(-50%);
    }
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

/*********** Team CSS **********/
/*******************************/
.team {
    position: relative;
    width: 100%;
    padding: 45px 0 15px 0;
}

.team .team-item {
    margin-bottom: 30px;
}

.team .team-img {
    position: relative;
    font-size: 0;
    clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
    -webkit-clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
}

.team .team-img img {
    width: 100%;
    height: auto;
}

.team .team-text {
    padding: 150px 15px 30px 15px;
    margin-top: -130px;
    text-align: center;
    background: #ffffff;
    border: 1px solid #eeeeee;
    transition: .3s;
}

.team .team-text:hover {
    background: #FFD662;
    border: 1px solid #FFD662;
}

.team .team-text h2 {
    margin-bottom: 5px;
    font-size: 20px;
    font-weight: 400;
    color: #00539C;
}

.team .team-text h3 {
    margin-bottom: 20px;
    font-size: 16px;
    font-weight: 300;
}

.team .team-social {
    position: relative;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 0;
}

.team .team-social a {
    display: inline-block;
    width: 40px;
    height: 40px;
    margin-right: 10px;
    padding: 11px 0 10px 0;
    font-size: 16px;
    font-weight: 300;
    line-height: 16px;
    text-align: center;
    color: #ffffff;
    border-radius: 5px;
    transition: .3s;
}

.team .team-social a.social-tw {
    background: #00acee;
}

.team .team-social a.social-fb {
    background: #3b5998;
}

.team .team-social a.social-li {
    background: #0e76a8;
}

.team .team-social a.social-in {
    background: #3f729b;
}

.team .team-social a.social-yt {
    background: #c4302b;
}

.team .team-social a:last-child {
    margin-right: 0;
}

.team .team-social a:hover {
    color: #ffffff;
    background: #00539C;
}
.section-header {
    text-align: center;
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
				</ul>
			</div>
		</div>
	</nav>
	 <!-- Recurits Start -->
            <div class="team">
                <div class="container">
                    <div class="section-header">
                        <h2>MEET OUR RECRUITERS</h2>
                    </div>
                    <br><br><br><br><br><br><br><br>
                    <div class="row">
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/1b.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/2b.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/3b.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/4a.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><br><br><br><br><br>
                    <div class="row">
                    <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/5a.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/6a.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/7.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/8.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><br><br><br><br><br>
                           <div class="row">
                    <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/9.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/10.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/11.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/12.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><br><br><br><br><br>
                           <div class="row">
                    <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/13.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/14.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/15.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/16.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><br><br><br><br><br>
                           <div class="row">
                    <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/17.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/18.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/19.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/20.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><br><br><br><br><br>
                           <div class="row">
                    <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/21.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/22.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/23.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/24.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><br><br><br><br><br>
                    <div class="row">
                    <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/25.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/26.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/27.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/28.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><br><br><br><br><br>
                         <div class="row">
                    <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/29.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/30.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/31.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="team-item">
                                <div class="team-img">
                                    <img src="assets/img/brand-logo/32.png" alt="Team Image">
                                </div>
                                <div class="team-text">
                                    <h2>Josh Dunn</h2>
                                    <div class="team-social">
                                        <a class="social-tw" href=""><i class="fab fa-twitter"></i></a>
                                        <a class="social-fb" href=""><i class="fab fa-facebook-f"></i></a>
                                        <a class="social-li" href=""><i class="fab fa-linkedin-in"></i></a>
                                        <a class="social-in" href=""><i class="fab fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Recurits End -->
	<br>
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


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
