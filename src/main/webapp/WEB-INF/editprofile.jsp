<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Alumni Profile</title>
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #6a11cb, #2575fc);
            color: white;
            font-family: 'Arial', sans-serif;
        }
        .container {
            margin-top: 50px;
            background: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            padding: 20px;
            color: #000;
        }
    </style>
</head>
<body>
<form action="<%= request.getContextPath() %>/updateprofile" method="POST" enctype="multipart/form-data">
    <div class="container">
        <h2 class="text-center">Edit Alumni Profile</h2>	

        <!-- Personal Information Section -->
        <h4>Personal Information</h4>
        <table class="table">
 <tr>
            <th>Profile Image</th>
            <td>
                <input type="file" name="profileImage" class="form-control">
            </td>
        </tr>
            <tr>
                <th>Full Name</th>
                <td><input type="text" name="fullname" value="${alumni.fullname}" class="form-control" required></td>
            </tr>
            <tr>
                <th>Father's Name</th>
                <td><input type="text" name="fathersname" value="${alumni.fathersname}" class="form-control" required></td>
            </tr>
            <tr>
                <th>Nationality</th>
                <td><input type="text" name="nationality" value="${alumni.nationality}" class="form-control"></td>
            </tr>
            <tr>
                <th>Date of Birth</th>
                <td><input type="date" name="dob" value="${alumni.dob}" class="form-control"></td>
            </tr>
            <tr>
                <th>Gender</th>
                <td>
                    <select name="gender" class="form-control">
                        <option value="Male" ${alumni.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${alumni.gender == 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${alumni.gender == 'Other' ? 'selected' : ''}>Other</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>Shift</th>
                <td><input type="text" name="shift" value="${alumni.shift}" class="form-control"></td>
            </tr>
            <tr>
    <th>Degree Obtained</th>
    <td>
        <input type="checkbox" name="degree_obtained" value="UG" class="form-check-input degree-checkbox"> UG
        <input type="checkbox" name="degree_obtained" value="PG" class="form-check-input degree-checkbox"> PG
        <input type="checkbox" name="degree_obtained" value="M.Phil" class="form-check-input degree-checkbox"> M.Phil
        <input type="checkbox" name="degree_obtained" value="Ph.D" class="form-check-input degree-checkbox"> Ph.D
    </td>
</tr>

<!-- Hidden input field to store database value -->
<input type="hidden" id="degree_obtained_value" value="${alumni.degree_obtained}">
<script>
    document.addEventListener("DOMContentLoaded", function() {
        let storedDegrees = document.getElementById("degree_obtained_value").value; // Get value from hidden input
        let checkboxes = document.querySelectorAll(".degree-checkbox");

        if (storedDegrees) {
            let degreeArray = storedDegrees.split(","); // Assuming degrees are stored as "UG,PG,M.Phil"
            
            checkboxes.forEach(function(checkbox) {
                if (degreeArray.includes(checkbox.value)) {
                    checkbox.checked = true;
                }
            });
        }
    });
</script>

            <tr>
                <th>Department</th>
                <td><input type="text" name="department" value="${alumni.department}" class="form-control"></td>
            </tr>
            <tr>	
                <th>SHC Stay From</th>
                <td><input type="number" name="shc_stay_from" value="${alumni.shcStayFrom}" class="form-control"></td>
            </tr>
            <tr>
                <th>SHC Stay To</th>
                <td><input type="number" name="shc_stay_to" value="${alumni.shcStayTo}" class="form-control"></td>
            </tr>
            <tr>
                <th>Marital Status</th>
                <td><input type="text" name="marital_status" value="${alumni.marital_status}" class="form-control"></td>
            </tr>
            <tr>
                <th>Anniversary Year</th>
                <td><input type="date" name="anniversary_year" value="${alumni.anniversary_year}" class="form-control"></td>
            </tr>	
            <tr>
                <th>Whatsapp Number</th>
                <td><input type="text" name="whatsappno" value="${alumni.whatsappno}" class="form-control"></td>
            </tr>
            <tr>
                <th>Phone Number</th>
                <td><input type="text" name="phoneno" value="${alumni.phoneno}" class="form-control" required></td>
            </tr>
            <tr>
                <th>Email Address</th>
                <td><input type="email" name="emailaddress" value="${alumni.emailaddress}" class="form-control" readonly></td>
            </tr>
            <tr>
                <th>House Flat Number</th>
                <td><input type="text" name="house_flat_number" value="${alumni.house_flat_number}" class="form-control"></td>
            </tr>
            <tr>
                <th>Street Name</th>
                <td><input type="text" name="street_name" value="${alumni.street_name}" class="form-control"></td>
            </tr>
            <tr>
                <th>City Name</th>
                <td><input type="text" name="city" value="${alumni.city}" class="form-control"></td>
            </tr>
            <tr>
                <th>State Name</th>
                <td><input type="text" name="state" value="${alumni.state}" class="form-control"></td>
            </tr>
            <tr>
                <th>Postal Code</th>
                <td><input type="text" name="postal_code" value="${alumni.postal_code}" class="form-control"></td>
            </tr>
            <tr>
                <th>Landmark</th>
                <td><input type="text" name="landmark" value="${alumni.landmark}" class="form-control"></td>
            </tr>
            <tr>
                <th>Area Name</th>
                <td><input type="text" name="area" value="${alumni.area}" class="form-control"></td>
            </tr>
            <tr>
                <th>Address Type</th>
                <td><input type="text" name="address_type" value="${alumni.address_type}" class="form-control"></td>
            </tr>
        </table>

        <!-- Work Information Section -->
        <h4>Work Information</h4>
        <table class="table">
            <tr>
                <th>Employment Status</th>
                <td><input type="text" name="empstatus" value="${alumni.empstatus}" class="form-control"></td>
            </tr>
            <tr>
                <th>Job Designation</th>
                <td><input type="text" name="jobdesig" value="${alumni.jobdesig}" class="form-control"></td>
            </tr>
            <tr>
                <th>Office Phone Number</th>
                <td><input type="text" name="officephoneno" value="${alumni.officephoneno}" class="form-control"></td>
            </tr>
            <tr>
                <th>Office Email</th>
                <td><input type="email" name="officeemail" value="${alumni.officeemail}" class="form-control"></td>
            </tr>
            <tr>
                <th>Field of Expertise</th>
                <td><input type="text" name="fieldofexpert" value="${alumni.fieldofexpert}" class="form-control"></td>
            </tr>
        </table>

        <!-- Submit Button -->
        <div style="text-align: center;">
            <button type="submit" class="btn btn-success">Update Profile</button>
        </div>
    </div>
</form>

</body>
</html>
