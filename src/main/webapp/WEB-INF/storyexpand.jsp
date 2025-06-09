<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>

<%@ page import="java.util.List"%>
<%@ page import="com.shc.alumni.springboot.entity.StoryEntity"%>
<%@ page import="com.shc.alumni.springboot.entity.StoryComment"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Alumni Story Page</title>
<link rel="icon" type="image/x-icon"
	href="<%= request.getContextPath() %>/logo.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Bootstrap Icons (Optional) -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>

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
@keyframes fadeIn { 0% {
	opacity: 0;
}

100
%
{
opacity
:
1;
}
}
@keyframes spin-in {from { transform:rotate(-360deg);
	opacity: 0;
}

to {
	transform: rotate(0deg);
	opacity: 1;
}

}
.spin-text {
	display: inline-block;
	opacity: 0;
}

.container-1 {
	max-width: 500px;
	margin: auto;
	background: #ffffff;
	padding: 20px;
	align: center;
	border-radius: 8px;
	box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
	animation: fadeIn 1s forwards;
	opacity: 0; /* Initially hidden */
}

input[type="text"], textarea, input[type="file"] {
	transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

input[type="text"]:focus, textarea:focus, input[type="file"]:focus {
	border-color: #fca311;
	box-shadow: 0 0 5px rgba(252, 163, 17, 0.7);
}

label {
	font-size: 1.1em;
	font-weight: bold;
	color: #333;
	transition: color 0.3s ease;
}

input[type="text"]:hover, textarea:hover {
	color: #fca311;
}

.btn-custom {
	background-color: #fca311;
	color: #14213d;
	align: center;
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

.story-pre {
    white-space: pre-wrap; /* Ensures text wraps properly */
    word-wrap: break-word; /* Breaks long words */
    overflow: auto; /* Enables scroll only if needed */
    background-color: #f8f9fa; /* Light background for readability */
    border-radius: 8px; /* Rounded corners */
    padding: 12px; /* Spacing inside the box */
    font-size: 16px; /* Readable font size */
    line-height: 1.6; /* Good line spacing */
    max-width: 100%; /* Prevents overflow */
    min-height: 10px; /* Minimum height */
    max-height: 400px; /* Limits max height to avoid too much space */
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
	<br>
	
	<div class="container mx-auto max-w-3xl">
		<!-- Story Details -->
		<div
			class="bg-white rounded-lg shadow-lg overflow-hidden animate-fade-in-up">
			<% 
        StoryEntity stories = (StoryEntity) request.getAttribute("stories"); 
        List<StoryComment> storycomments = (List<StoryComment>) request.getAttribute("comments");
        if (stories == null) {
            response.sendRedirect(request.getContextPath() + "/404");
            return;
        }
        String imageUrl = stories.getStoryImagePath() != null ? request.getContextPath() + "/story_media/" + stories.getStoryImagePath() : request.getContextPath() + "/images/default_image.jpg";
        System.out.println("Image URL for story " + stories.getId() + ": " + imageUrl);
    %>
    <div class="container mx-auto max-w-3xl">
        <!-- Story Details -->
        <div class="bg-white rounded-lg shadow-lg overflow-hidden animate-fade-in-up">
            <img class="w-full h-64 object-cover" 
                 src="<%= imageUrl %>" 
                 alt="Story Image" 
                 onerror="this.src='<%= request.getContextPath() %>/images/default_image.jpg'">
            <div class="p-6">
                <h1 class="story-title"><%= stories.getTitle() != null ? stories.getTitle() : "No Title Available" %></h1>
                <p class="story-content">
                    <pre class="story-pre"><%= stories.getContent() != null ? stories.getContent() : "Content not available." %></pre>
                </p>
                <p class="text-sm text-gray-500" id="date">
                    Published on: <%= stories.getCreatedAt() != null ? stories.getCreatedAt() : "Unknown Date" %>
                </p>
            </div>
        </div>

        <!-- Fun Fact Section -->
        <div class="mt-6 p-4 bg-blue-100 border-l-4 border-blue-500 text-blue-700 rounded-md shadow-md flex-col items-center">
            <div class="flex justify-center">
                <dotlottie-player
                    src="https://lottie.host/524c6f40-6123-48b1-a3fe-87a2b2d2975c/WErlkytiC9.lottie"
                    background="transparent" speed="1"
                    style="width: 300px; height: 300px" loop autoplay></dotlottie-player>
            </div>
            <h4 class="text-lg font-semibold" id="fun-fact">Fun Fact of the Day</h4>
            <p class="" id="fact-text"><%= request.getAttribute("funFact") != null ? request.getAttribute("funFact") : "No fun fact available today." %></p>
        </div>

        <!-- Share Section -->
        <div class="mt-6 text-center animate-slide-in-right flex flex-col items-center">
            <h3 class="text-lg font-semibold mb-2">Share the Story</h3>
            <div class="flex justify-center">
                <dotlottie-player
                    src="https://lottie.host/2516f5ec-e644-429c-800f-29c47a9f0222/PkgDi7oZbt.lottie"
                    background="transparent" speed="1"
                    style="width: 300px; height: 300px" loop autoplay>
                </dotlottie-player>
            </div>
            <div class="flex gap-4 justify-center mt-4">
                <a href="#" class="text-green-500 text-3xl hover:scale-110 transition-transform duration-300" onclick="shareNews('whatsapp')"><i class="fa-brands fa-square-whatsapp"></i></a>
                <a href="#" class="text-pink-500 text-3xl hover:scale-110 transition-transform duration-300" onclick="shareNews('instagram')"><i class="fa-brands fa-square-instagram"></i></a>
                <a href="#" class="text-blue-600 text-3xl hover:scale-110 transition-transform duration-300" onclick="shareNews('facebook')"><i class="fa-brands fa-square-facebook"></i></a>
                <a href="#" class="text-blue-400 text-3xl hover:scale-110 transition-transform duration-300" onclick="shareNews('twitter')"><i class="fa-brands fa-square-x-twitter"></i></a>
                <a href="#" class="text-blue-700 text-3xl hover:scale-110 transition-transform duration-300" onclick="shareNews('linkedin')"><i class="fa-brands fa-linkedin"></i></a>
            </div>
        </div>

        <!-- Comment Section -->
        <div class="mt-6 bg-white p-6 rounded-lg shadow-md animate-slide-in-left">
            <h3 class="text-xl font-semibold mb-3">Comments</h3>
            <form action="<%= request.getContextPath() %>/stories/<%= stories.getId() %>/storycomment" method="POST">
                <textarea class="w-full p-3 border rounded-md shadow-sm focus:ring-2 focus:ring-blue-400 transition-all duration-300" 
                          name="storycommentText" rows="3" placeholder="Add your comment here..." required></textarea>
                <button type="submit" class="mt-3 px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-all duration-300">
                    Submit Comment
                </button>
            </form>
            <ul class="mt-4">
                <% if (storycomments != null && !storycomments.isEmpty()) { %>
                    <% for (StoryComment storycomment : storycomments) { %>
                        <li class="p-3 border-b text-gray-700 spin-text">
                            <%= storycomment.getText() %>
                            <br>
                            <small class="text-gray-500">Posted on: <%= storycomment.getCreatedAt() != null ? storycomment.getCreatedAt() : "Unknown" %></small>
                        </li>
                    <% } %>
                <% } else { %>
                    <p class="text-gray-500 spin-text">No comments yet. Be the first to comment!</p>
                <% } %>
            </ul>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            let elements = ["title", "content", "date", "fun-fact", "fact-text"];
            elements.forEach((id, index) => {
                setTimeout(() => {
                    let el = document.getElementById(id);
                    if (el) {
                        el.style.animation = "spin-in 1s ease-in-out forwards";
                    }
                }, index * 500);
            });
        });

        function shareNews(platform) {
            var url = window.location.href;
            var shareLink = "";

            switch (platform) {
                case 'whatsapp':
                    shareLink = "https://wa.me/?text=" + encodeURIComponent(url);
                    break;
                case 'instagram':
                    navigator.clipboard.writeText(url);
                    alert("Instagram does not support direct link sharing. The link has been copied to your clipboard!");
                    return;
                case 'twitter':
                    shareLink = "https://twitter.com/intent/tweet?url=" + encodeURIComponent(url);
                    break;
                case 'facebook':
                    shareLink = "https://www.facebook.com/sharer/sharer.php?u=" + encodeURIComponent(url);
                    break;
                case 'linkedin':
                    shareLink = "https://www.linkedin.com/shareArticle?mini=true&url=" + encodeURIComponent(url);
                    break;
                default:
                    console.error("Invalid platform selected");
                    return;
            }

            window.open(shareLink, "_blank", "width=600,height=400");
        }
    </script>
</div>
</div>
<br><br>
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
	<script src="https://kit.fontawesome.com/a076d05399.js"
		crossorigin="anonymous"></script>
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
		src="https://unpkg.com/@dotlottie/player-component@2.7.12/dist/dotlottie-player.mjs"
		type="module"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
