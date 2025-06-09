<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Alumni Registration</title>
<link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">

<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
    rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>

<style>
body {
    background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
    color: white;
    font-family: 'Arial', sans-serif;
    animation: fadeIn 2s ease-in-out;
}

@keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
}

.form-container {
    background: rgba(255, 255, 255, 0.9);
    border-radius: 10px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5);
    padding: 20px;
    margin-top: 30px;
    color: #000;
    transform: perspective(1000px) rotateY(0deg);
    transition: transform 0.5s ease, box-shadow 0.5s ease;
}

.form-container:hover {
    transform: perspective(1000px) rotateY(10deg);
    box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.7);
}

h2 {
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.6);
    animation: pulse 1.5s infinite;
}

@keyframes pulse {
    0%, 100% { transform: scale(1); }
    50% { transform: scale(1.05); }
}

button {
    transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
    background-color: #1e3a8a;
    transform: translateY(-5px);
}

.dropdown-menu {
    transform: perspective(1000px) rotateX(0deg);
    transition: transform 0.3s ease;
}

.dropdown-menu.show {
    transform: perspective(1000px) rotateX(0deg);
}

.form-check-input {
    transition: transform 0.2s ease;
}

.form-check-input:checked {
    transform: scale(1.2);
}

input[type="text"], input[type="date"], input[type="email"], input[type="file"], select, textarea {
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

input[type="text"]:focus, input[type="date"]:focus, input[type="email"]:focus, input[type="file"]:focus, select:focus, textarea:focus {
    border-color: #6a11cb;
    box-shadow: 0 0 10px rgba(106, 17, 203, 0.5);
}

@keyframes float {
    0%, 100% { transform: translateY(0); }
    50% { transform: translateY(-10px); }
}

.floating {
    animation: float 3s ease-in-out infinite;
}
</style>
<script>
function toggleAnniversaryDate() {
    const yesRadio = document.querySelector('input[name="marital_status"][value="Yes"]');
    const anniversaryField = document.querySelector('input[name="anniversary_year"]');
    
    if (anniversaryField && yesRadio) {
        anniversaryField.style.display = yesRadio.checked ? 'inline' : 'none';
    }
}

function validateForm() {
    const tel = document.querySelector('input[name="whatsappno"]');
    const mobile = document.querySelector('input[name="phoneno"]');
    const email = document.querySelector('input[name="emailaddress"]');

    const telPattern = /^\d{10}$/;
    const mobilePattern = /^\d{10}$/;
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (!tel || !telPattern.test(tel.value)) {
        alert("WhatsApp Number must be exactly 10 digits.");
        return false;
    }

    if (!mobile || !mobilePattern.test(mobile.value)) {
        alert("Mobile Number must be exactly 10 digits.");
        return false;
    }

    if (!email || !emailPattern.test(email.value)) {
        alert("Please enter a valid Email ID.");
        return false;
    }

    return true;
}

function submitForm(event) {
    event.preventDefault();

    const form = document.getElementById('alumniForm');
    const formData = new FormData(form);

    fetch('<%= request.getContextPath() %>/register', {
        method: 'POST',
        body: formData,
    })
    .then(response => {
    	if (!response.ok) {
            return response.json().then(err => {
                throw new Error(err.error || 'Registration failed');
            });
        }
        alert('Thanks For The Details!');
        window.location.href = '<%= request.getContextPath() %>/membership';
    })
    .catch(error => {
        console.error('Error:', error);
        alert(`Registration failed: ${error.message}`);
    });
}



window.addEventListener('DOMContentLoaded', () => {
    const maritalStatusRadios = document.querySelectorAll('input[name="marital_status"]');
    maritalStatusRadios.forEach(radio => {
        radio.addEventListener('change', toggleAnniversaryDate);
    });

    toggleAnniversaryDate(); // Initialize the display state of the anniversary field

    const alumniForm = document.getElementById('alumniForm');
    if (alumniForm) {
        alumniForm.addEventListener('submit', submitForm);
    } else {
        console.error('Form with ID "alumniForm" not found.');
    }
});

const dropdownButton = document.getElementById('departmentDropdown');
const checkboxes = document.querySelectorAll('.form-check-input');

checkboxes.forEach((checkbox) => {
    checkbox.addEventListener('change', () => {
        const selectedDepartments = Array.from(checkboxes)
            .filter((cb) => cb.checked)
            .map((cb) => cb.nextElementSibling.textContent.trim());
        
        if (selectedDepartments.length > 0) {
            dropdownButton.textContent = selectedDepartments.join(', ');
        } else {
            dropdownButton.textContent = 'Select Departments';
        }
    });
});
</script>
</head>
<body>
    <div class="container">
        <div class="form-container floating">
            <h2 class="text-center">Alumni Registration Form</h2>
                   <form id="alumniForm" action="<%= request.getContextPath() %>/register" method="post" enctype="multipart/form-data">
    <div class="row">
    <div class="col-md-6">
        <div class="mb-3">
            <label class="form-label">Full Name:<span style="color: red;">*</span></label>
            <input type="text" name="fullname" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Father's Name: <span style="color: red;">*</span></label>
            <input type="text" name="fathersname" class="form-control" required>
        </div>
    </div>
    <div class="col-md-6">
        <div class="mb-3">
            <label class="form-label">Nationality:<span style="color: red;">*</span></label>
            <input type="text" name="nationality" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Date of Birth:<span style="color: red;">*</span></label>
            <input type="date" name="dob" class="form-control" required>
        </div>
    </div>
</div>

				 <div class="row">
    <div class="col-md-6">
				<div class="mb-3">
					<label class="form-label">Gender:<span style="color: red;">*</span></label> <select name="gender"
						class="form-select" required>
						<option value="Male">Male</option>
						<option value="Female">Female</option>
					</select>
				</div>
				<!-- Shift Selection -->
				<div class="mb-3">
					<label class="form-label">Shift:<span style="color: red;">*</span></label> <select id="shiftSelect"
						name="shift" class="form-select" required>
						<option value="">Select Shift</option>
						<option value="Shift 1">Shift 1</option>
						<option value="Shift 2">Shift 2</option>
						<option value="Not-Applicable">NA</option>
					</select>
				</div>
</div>
				<!-- Degree Information -->
				    <div class="col-md-6">
				
				<div class="mb-3">
					<label class="form-label">Degree Obtained:<span style="color: red;">*</span></label><br> <input
						type="checkbox" name="degree_obtained" value="UG"
						class="form-check-input degree-checkbox"> UG <input
						type="checkbox" name="degree_obtained" value="PG"
						class="form-check-input degree-checkbox"> PG <input
						type="checkbox" name="degree_obtained" value="M.Phil"
						class="form-check-input degree-checkbox"> M.Phil <input
						type="checkbox" name="degree_obtained" value="Ph.D"
						class="form-check-input degree-checkbox"> Ph.D
				</div>

				<!-- Department Information -->
<div class="mb-3">
    <label class="form-label">Department:<span style="color: red;">*</span></label>
    <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="departmentDropdown" data-bs-toggle="dropdown" aria-expanded="false">
            Select Departments
        </button>
        <div class="dropdown-menu p-3" style="min-width: 300px; max-height: 300px; overflow-y: auto;">
            <!-- Add your form-check elements here -->
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="deptBAEconomics" name="department" value="B.A. Economics">
                <label class="form-check-label" for="deptBAEconomics">B.A. Economics</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="deptMAEconomics" name="department" value="M.A. Economics">
                <label class="form-check-label" for="deptMAEconomics">M.A. Economics</label>
            </div>
           <div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptBAEnglish" name="department" value="B.A. English">
								<label class="form-check-label" for="deptBAEnglish">B.A.
									English</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptMAEnglish" name="department" value="M.A. English">
								<label class="form-check-label" for="deptMAEnglish">M.A.
									English</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptB.Com Commerce" name="department"
									value="B.Com Commerce"> <label class="form-check-label"
									for="deptB.Com Commerce">B.Com Commerce </label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox" id="deptBBA"
									name="department" value="BBA"> <label
									class="form-check-label" for="deptBBA">BBA</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox" id="deptMBA"
									name="department" value="MBA"> <label
									class="form-check-label" for="deptMBA">MBA</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptB.Sc Mathematics" name="department"
									value="B.Sc Mathematics"> <label
									class="form-check-label" for="deptB.Sc Mathematics">B.Sc
									Mathematics</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptM.Sc Mathematics" name="department"
									value="M.Sc Mathematics"> <label
									class="form-check-label" for="deptM.Sc Mathematics">M.Sc
									Mathematics</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptB.Sc Chemistry" name="department"
									value="B.Sc Chemistry"> <label class="form-check-label"
									for="deptB.Sc Chemistry">B.Sc Chemistry</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptM.Sc Chemistry" name="department"
									value="M.Sc Chemistry"> <label class="form-check-label"
									for="deptM.Sc Chemistry">M.Sc Chemistry</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptB.Sc BioChemistry" name="department"
									value="B.Sc BioChemistry"> <label
									class="form-check-label" for="deptB.Sc BioChemistry">B.Sc
									BioChemistry</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptM.Sc BioChemistry" name="department"
									value="M.Sc BioChemistry"> <label
									class="form-check-label" for="deptM.Sc BioChemistry">M.Sc
									BioChemistry</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptB.Sc Microbiology" name="department"
									value="B.Sc Microbiology"> <label
									class="form-check-label" for="deptB.Sc Microbiology">B.Sc
									Microbiology</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptM.Sc Microbiology" name="department"
									value="M.Sc Microbiology"> <label
									class="form-check-label" for="deptM.Sc Microbiology">M.Sc
									Microbiology</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptB.Sc Psychology" name="department"
									value="B.Sc Psychology"> <label
									class="form-check-label" for="deptB.Sc Psychology">B.Sc
									Psychology</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptM.Sc Counselling Psychology" name="department"
									value="M.Sc Counselling Psychology"> <label
									class="form-check-label" for="deptM.Sc Counselling Psychology">M.Sc
									counseling Psychology</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptB.Sc Data Science" name="department"
									value="B.Sc Data Science"> <label
									class="form-check-label" for="deptB.Sc Data Science">B.Sc
									Data Science</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptB.Com Banking and Finance" name="department"
									value="B.Com Banking and Finance"> <label
									class="form-check-label" for="deptB.Com Banking and Finance">B.Com
									Banking and Finance</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptM.S.W(Social Work)" name="department"
									value="M.S.W(Social Work)"> <label
									class="form-check-label" for="deptM.S.W(Social Work)">M.S.W(Social
									Work)</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="deptM.S.W (HRM)(Social Work)" name="department"
									value="M.S.W (HRM)(Social Work)"> <label
									class="form-check-label" for="deptM.S.W (HRM)(Social Work)">M.S.W
									(HRM)(Social Work)</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox" id="deptBCA"
									name="department" value="BCA"> <label
									class="form-check-label" for="deptBCA">BCA</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox" id="deptMCA"
									name="department" value="MCA"> <label
									class="form-check-label" for="deptMCA">MCA</label>
							</div>
					
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptB.A. Tamil" name="department" value="B.A. Tamil">
							<label class="form-check-label" for="deptB.A. Tamil">B.A.
								Tamil</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptM.A. Tamil" name="department" value="M.A. Tamil">
							<label class="form-check-label" for="deptM.A. Tamil">M.A.
								Tamil</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptB.A. History" name="department" value="B.A. History">
							<label class="form-check-label" for="deptB.A. History">B.A.
								History</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptB.Com (CA)" name="department" value="B.Com (CA)">
							<label class="form-check-label" for="deptB.Com (CA)">B.Com
								(CA)</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptB.Sc Zoology" name="department" value="B.Sc Zoology">
							<label class="form-check-label" for="deptB.Sc Zoology">B.Sc
								Zoology</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptB.Sc Artificial Intelligence and Machine Learning"
								name="department"
								value="B.Sc Artificial Intelligence and Machine Learning">
							<label class="form-check-label"
								for="deptB.Sc Artificial Intelligence and Machine Learning">B.Sc
								Artificial Intelligence and Machine Learning</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptDiploma in Computer Software Applications(PGDCSA)"
								name="department"
								value="Diploma in Computer Software Applications(PGDCSA)">
							<label class="form-check-label"
								for="deptDiploma in Computer Software Applications(PGDCSA)">Diploma
								in Computer Software Applications(PGDCSA)</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptDiploma in Medical Laboratory Technology(PGDMLT)"
								name="department"
								value="Diploma in Medical Laboratory Technology(PGDMLT)">
							<label class="form-check-label"
								for="deptDiploma in Medical Laboratory Technology(PGDMLT)">Diploma
								in Medical Laboratory Technology(PGDMLT)</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptDiploma in Human Resources Management(PGDHRM)"
								name="department"
								value="Diploma in Human Resources Management(PGDHRM)"> <label
								class="form-check-label"
								for="deptDiploma in Human Resources Management(PGDHRM)">Diploma
								in Human Resources Management(PGDHRM)</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptDiploma in Import and Export Management(PGDIEM)"
								name="department"
								value="Diploma in Import and Export Management(PGDIEM)">
							<label class="form-check-label"
								for="deptDiploma in Import and Export Management(PGDIEM)">Diploma
								in Import and Export Management(PGDIEM)</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptDiploma in Cyber Security(PGDCS)" name="department"
								value="Diploma in Cyber Security(PGDCS)"> <label
								class="form-check-label"
								for="deptDiploma in Cyber Security(PGDCS)">Diploma in
								Cyber Security(PGDCS)</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptDiploma in Data Science(PGDDS)" name="department"
								value="Diploma in Data Science(PGDDS)"> <label
								class="form-check-label"
								for="deptDiploma in Data Science(PGDDS)">Diploma in Data
								Science(PGDDS)</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox"
								id="deptDiploma in Aviation Management(PGDCAM)"
								name="department" value="Diploma in Aviation Management(PGDCAM)">
							<label class="form-check-label"
								for="deptDiploma in Aviation Management(PGDCAM)">Diploma
								in Aviation Management(PGDCAM)</label>
						</div>
						<div>
						<input type="text" name="department" class="form-control" placeholder="Enter Department">
					
						</div>
        </div>
    </div>
</div>
</div>
</div>
		
				 <div class="row">
    <div class="col-md-6">
<div class="mb-3">
    <label class="form-label">Stay At SHC:</label><br>
    
    <label>From:<span style="color: red;">*</span></label>
    <input type="number" name="shc_stay_from" 
           class="form-control d-inline w-auto" 
           placeholder="Enter Year (e.g., 2005)" 
           min="1900" max="2099" 
           required>
    
    <label>To:<span style="color: red;">*</span></label>
    <input type="number" name="shc_stay_to" 
           class="form-control d-inline w-auto" 
           placeholder="Enter Year (e.g., 2020)" 
           min="1900" max="2099" 
           required>
</div>

		<!-- Marital Status -->
		<div class="mb-3">
			<label class="form-label">Marital Status:<span style="color: red;">*</span></label><br> <input
				type="radio" name="marital_status" value="Yes"
				class="form-check-input"> Yes <input type="radio"
				name="marital_status" value="No" class="form-check-input">
			No
		</div></div>
		    <div class="col-md-6">
		
		<div class="mb-3">
			<label class="form-label">If Yes, Anniversary Date:</label> <input
				type="date" name="anniversary_year" class="form-control"
				style="display: none;">
		</div>
		<!-- Contact Information -->
		<div class="mb-3">
			<label class="form-label">WhatsApp No:<span style="color: red;">*</span></label> <input type="text"
				name="whatsappno" class="form-control" required>
		</div>
		</div>
		</div>
		 <div class="row">
    <div class="col-md-6">
        <div class="mb-3">
            <label class="form-label">Mobile No:<span style="color: red;">*</span></label>
            <input type="text" name="phoneno" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Email Address:<span style="color: red;">*</span></label>
            <input type="email" name="emailaddress" class="form-control" required>
        </div>
    </div>
    <div class="col-md-6">
        <div class="mb-3">
            <label class="form-label">Upload Photograph:<span style="color: red;">*</span></label>
            <input type="file" name="profileImage" class="form-control" accept="image/*" multiple>
        </div>
    </div>
</div>
<!-- Full Address -->
<div class="mb-3">
    <label class="form-label">Full Address:<span style="color: red;">*</span></label>
    <div class="row">
        <!-- Left Column -->
        <div class="col-md-6">
            <div class="mb-2">
                <input type="text" name="house_flat_number" placeholder="House/Flat Number" class="form-control" required>
            </div>
            <div class="mb-2">
                <input type="text" name="street_name" placeholder="Street Name" class="form-control" required>
            </div>
            <div class="mb-2">
                <input type="text" name="city" placeholder="City" class="form-control" required>
            </div>
            <div class="mb-2">
                <input type="text" name="state" placeholder="State" class="form-control" required>
            </div>
        </div>

        <!-- Right Column -->
        <div class="col-md-6">
            <div class="mb-2">
                <input type="text" name="postal_code" placeholder="Postal Code" class="form-control" required>
            </div>
            <div class="mb-2">
                <input type="text" name="landmark" placeholder="Landmark" class="form-control">
            </div>
            <div class="mb-2">
                <input type="text" name="area" placeholder="Area" class="form-control">
            </div>
            <div class="mb-2">
                <label class="form-label">Address Type:</label>
                    <input type="checkbox" name="address_type" value="Permanent" class="form-check-input"> Permanent
                    <input type="checkbox" name="address_type" value="Current" class="form-check-input"> Current
            </div>
        </div>
    </div>
</div>

		<!-- Work Information -->
		<h2 class="text-center">Work Information</h2>
				<div class="row">
    <!-- Left Column -->
    <div class="col-md-6">
        <div class="mb-3">
            <label class="form-label">Employer/Enterprise:<span style="color: red;">*</span></label><br> 
            <input type="radio" name="empstatus" value="Employer" class="form-check-input"> Employer 
            <input type="radio" name="empstatus" value="Enterprise" class="form-check-input"> Enterprise
        </div>
        <div class="mb-3">
            <label class="form-label">Job Designation:<span style="color: red;">*</span></label>
            <input type="text" name="jobdesig" class="form-control">
        </div>
        <div class="mb-3">
            <label class="form-label">Official Email:<span style="color: red;">*</span></label>
            <input type="email" name="officeemail" class="form-control">
        </div>
    </div>

    <!-- Right Column -->
    <div class="col-md-6">
        <div class="mb-3">
            <label class="form-label">Office Phone No:<span style="color: red;">*</span></label>
            <input type="text" name="officephoneno" class="form-control">
        </div>
        <div class="mb-3">
            <label class="form-label">Field of Expertise:<span style="color: red;">*</span></label>
            <input type="text" name="fieldofexpert" class="form-control">
        </div>
    </div>
</div>
                <div style="text-align: center;">
                    <button type="submit" class="btn btn-primary">Next</button>
                </div>
            </form>

        </div>
    </div>
</body>
</html>