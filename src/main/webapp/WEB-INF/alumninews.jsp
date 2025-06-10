<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ page import="com.shc.alumni.springboot.entity.News, java.util.List, java.util.Map, java.util.LinkedHashMap, java.util.ArrayList, java.io.File" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alumni News Page</title>
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
<div class="container mt-1">
    <h1 class="text-center mb-2">Featured News</h1>
    
    <% 
        List<News> newsList = (List<News>) request.getAttribute("newsList"); 
        if (newsList != null && !newsList.isEmpty()) { 
            // Sort news by createdAt in descending order (newest first)
            newsList.sort((news1, news2) -> news2.getCreatedAt().compareTo(news1.getCreatedAt()));
            
            Map<String, List<News>> categorizedNews = new LinkedHashMap<>();
            for (News news : newsList) {
                categorizedNews.computeIfAbsent(news.getCategory(), k -> new ArrayList<>()).add(news);
            }
    %>
        
<!-- Featured News Section -->
<div class="row mb-4">
    <% int featuredCount = 0; %>

    <!-- Main Featured News (Left) -->
    <% if (!newsList.isEmpty()) { 
        News mainNews = newsList.get(0);
        List<String> mainMediaPaths = mainNews.getMediaPaths();
        String mainFirstMediaFilename = (mainMediaPaths != null && !mainMediaPaths.isEmpty()) ? 
                                        new File(mainMediaPaths.get(0)).getName() : "";
        boolean mainIsVideo = !mainFirstMediaFilename.isEmpty() && 
            (mainFirstMediaFilename.toLowerCase().endsWith(".mp4") || 
             mainFirstMediaFilename.toLowerCase().endsWith(".mov") || 
             mainFirstMediaFilename.toLowerCase().endsWith(".webm"));
    %>
        <div class="col-lg-8 mb-4">
            <div class="position-relative text-white">
                <% if (!mainFirstMediaFilename.isEmpty()) { %>
                    <% if (mainIsVideo) { %>
                        <video class="img-fluid w-100" style="height: 350px; object-fit: cover;" autoplay loop muted playsinline>
                            <source src="<%= request.getContextPath() %>/news_folder/<%= mainFirstMediaFilename %>" type="video/mp4">
                            Your browser does not support the video tag.
                        </video>
                    <% } else { %>
                        <img src="<%= request.getContextPath() %>/news_folder/<%= mainFirstMediaFilename %>" 
                             class="img-fluid w-100" style="height: 350px; object-fit: cover;">
                    <% } %>
                <% } else { %>
                    <img src="<%= request.getContextPath() %>/images/news_default_image.jpg" 
                         class="img-fluid w-100" style="height: 350px; object-fit: cover;">
                <% } %>
                <div class="overlay position-absolute bottom-0 p-3" style="background: linear-gradient(to top, rgba(0, 0, 0, 0.8), transparent);">
                    <h3 class="mt-2"><a href="<%= request.getContextPath() %>/news/<%= mainNews.getId() %>" class="text-white text-decoration-none">
                        <%= mainNews.getTitle() %>
                    </a></h3>
                </div>
            </div>
        </div>
    <% featuredCount++; } %>

    <!-- Smaller Featured News (Right) -->
    <div class="col-lg-4">
        <div class="row">
            <% for (int i = 1; i < newsList.size() && featuredCount < 4; i++, featuredCount++) { 
                News news = newsList.get(i);
                List<String> mediaPaths = news.getMediaPaths();
                String firstMediaFilename = (mediaPaths != null && !mediaPaths.isEmpty()) ? 
                                            new File(mediaPaths.get(0)).getName() : "";
                boolean isVideo = !firstMediaFilename.isEmpty() && 
                    (firstMediaFilename.toLowerCase().endsWith(".mp4") || 
                     firstMediaFilename.toLowerCase().endsWith(".mov") || 
                     firstMediaFilename.toLowerCase().endsWith(".webm"));
            %>
                <div class="col-12 mb-3">
                    <div class="position-relative text-white">
                        <% if (!firstMediaFilename.isEmpty()) { %>
                            <% if (isVideo) { %>
                                <video class="img-fluid w-100" style="height: 120px; object-fit: cover;" autoplay loop muted playsinline>
                                    <source src="<%= request.getContextPath() %>/news_folder/<%= firstMediaFilename %>" type="video/mp4">
                                    Your browser does not support the video tag.
                                </video>
                            <% } else { %>
                                <img src="<%= request.getContextPath() %>/news_folder/<%= firstMediaFilename %>" 
                                     class="img-fluid w-100" style="height: 120px; object-fit: cover;">
                            <% } %>
                        <% } else { %>
                            <img src="<%= request.getContextPath() %>/images/news_default_image.jpg" 
                                 class="img-fluid w-100" style="height: 120px; object-fit: cover;">
                        <% } %>
                        <div class="overlay position-absolute bottom-0 p-2" style="background: linear-gradient(to top, rgba(0, 0, 0, 0.8), transparent);">
                            <span class="badge bg-warning text-dark"><%= news.getCreatedAt() %></span>
                            <h6 class="mt-1"><a href="<%= request.getContextPath() %>/news/<%= news.getId() %>" class="text-white text-decoration-none">
                                <%= news.getTitle() %>
                            </a></h6>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
    </div>
</div>


        
        <!-- Categorized News Sections -->
        <% for (Map.Entry<String, List<News>> entry : categorizedNews.entrySet()) { %>
            <h2 class="mb-3"><%= entry.getKey() %></h2>
            <div class="row mb-4">
                <% for (News news : entry.getValue()) { 
                    List<String> mediaPaths = news.getMediaPaths();
                    String firstMediaFilename = (mediaPaths != null && !mediaPaths.isEmpty()) ? new File(mediaPaths.get(0)).getName() : "";
                    boolean isVideo = !firstMediaFilename.isEmpty() && 
                        (firstMediaFilename.toLowerCase().endsWith(".mp4") || 
                         firstMediaFilename.toLowerCase().endsWith(".mov") || 
                         firstMediaFilename.toLowerCase().endsWith(".webm"));
                %>
                    <div class="col-lg-3 col-md-4 col-sm-6 mb-4 d-flex align-items-stretch">
                        <div class="card h-100 shadow-sm">
                            <a href="<%= request.getContextPath() %>/news/<%= news.getId() %>" style="text-decoration: none; color: inherit;">
                                <% if (!firstMediaFilename.isEmpty()) { %>
                                    <% if (isVideo) { %>
                                        <video class="card-img-top" style="object-fit: cover; height: 130px;" muted>
                                            <source src="<%= request.getContextPath() %>/news_folder/<%= firstMediaFilename %>" type="video/mp4">
                                            Your browser does not support the video tag.
                                        </video>
                                    <% } else { %>
                                        <img class="card-img-top" src="<%= request.getContextPath() %>/news_folder/<%= firstMediaFilename %>" 
                                             alt="News Image" style="object-fit: cover; height: 130px;">
                                    <% } %>
                                <% } else { %>
                                    <img class="card-img-top" src="<%= request.getContextPath() %>/images/news_default_image.jpg" 
                                         alt="Default Image" style="object-fit: cover; height: 130px;">
                                <% } %>
                                <div class="card-body d-flex flex-column">
                                    <h5 class="news-title"><%= news.getTitle() %></h5>
                                    <p class="card-text content-preview" style="flex-grow: 1;">
                                        <%= news.getContent().length() > 100 ? news.getContent().substring(0, 100) + "..." : news.getContent() %>
                                    </p>
                                    <p class="card-text">
                                        <small class="text-muted">Published on: <%= news.getCreatedAt() %></small>
                                    </p>
                                </div>
                            </a>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } %>
    <% } else { %>
        <p class="text-center">No news available at the moment.</p>
    <% } %>
</div>

<script>
    // Optional: Add hover effect to play videos
    document.addEventListener('DOMContentLoaded', function() {
        const videos = document.querySelectorAll('.card-img-top');
        videos.forEach(video => {
            if (video.tagName === 'VIDEO') {
                video.addEventListener('mouseover', function() {
                    this.play();
                });
                video.addEventListener('mouseout', function() {
                    this.pause();
                    this.currentTime = 0;
                });
            }
        });
    });
</script>        <!-- Pagination -->
        <div class="pagination-wrapper"></div>
    </div>
    
            <!-- Add News Button -->
<div class="d-flex justify-content-center w-100 mb-6" style="max-width: 1200px;">
    <a href="<%= request.getContextPath() %>/addnews" class="btn btn-success">Add News</a>
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
