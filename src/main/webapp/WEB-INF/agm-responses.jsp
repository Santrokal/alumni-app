<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shc.alumni.springboot.entity.FormField" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AGM Responses</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        .table-container {
            margin-top: 20px;
            overflow-x: auto;
        }
        .export-form {
            margin-bottom: 20px;
        }
        .btn-group {
            margin-bottom: 20px;
        }
        .alert {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2 class="form-title text-center mb-4">AGM Responses</h2>

        <!-- Display success or error messages -->
        <%
            String message = (String) request.getAttribute("message");
            String error = (String) request.getAttribute("error");
            if (message != null && !message.isEmpty()) {
        %>
            <div class="alert alert-success" role="alert">
                <%= message %>
            </div>
        <%
            }
            if (error != null && !error.isEmpty()) {
        %>
            <div class="alert alert-danger" role="alert">
                <%= error %>
            </div>
        <%
            }
        %>

        <!-- Activate/Deactivate AGM Form Button -->
        <div class="btn-group">
            <%
                Boolean isFormActive = (Boolean) request.getAttribute("isFormActive");
                if (isFormActive != null && isFormActive) {
            %>
                <form action="<%= request.getContextPath() %>/admin/deactivate-agm-form" method="POST" class="d-inline">
                    <button type="submit" class="btn btn-danger">Deactivate AGM Form</button>
                </form>
            <%
                } else {
            %>
                <form action="<%= request.getContextPath() %>/admin/activate-agm-form" method="POST" class="d-inline">
                    <button type="submit" class="btn btn-success">Activate AGM Form</button>
                </form>
            <%
                }
            %>
        </div>

        <!-- Export Table Form -->
        <%
            List<FormField> fields = (List<FormField>) request.getAttribute("fields");
            List<Object[]> responses = (List<Object[]>) request.getAttribute("responses");
            boolean isExportDisabled = (fields == null || fields.isEmpty() || responses == null);
        %>
        <form action="<%= request.getContextPath() %>/admin/agmexporttable" method="POST" class="export-form">
            <div class="input-group">
                <select name="format" class="form-select" required>
                    <option value="">Select Export Format</option>
                    <option value="xlsx">Excel (.xlsx)</option>
                    <option value="pdf">PDF (.pdf)</option>
                    <option value="docx">Word (.docx)</option>
                </select>
                <button type="submit" class="btn btn-primary" <%= isExportDisabled ? "disabled" : "" %>>
                    Export Table
                </button>
            </div>
            <% if (isExportDisabled) { %>
                <small class="text-muted mt-2 d-block">Export is disabled because no data is available.</small>
            <% } %>
        </form>

        <!-- Display Responses Table -->
        <%
            if (fields == null || fields.isEmpty()) {
        %>
            <p class="text-center mt-4">No form fields defined. Please create the AGM form.</p>
        <%
            } else {
        %>
            <div class="table-container">
                <table class="table table-bordered table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>Phone Number</th>
                            <%
                                for (FormField field : fields) {
                            %>
                                <th><%= field.getName() %></th>
                            <%
                                }
                            %>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (responses == null || responses.isEmpty()) {
                        %>
                            <tr>
                                <td colspan="<%= fields.size() + 1 %>" class="text-center">No responses found.</td>
                            </tr>
                        <%
                            } else {
                                for (Object[] resp : responses) {
                        %>
                            <tr>
                                <%
                                    for (Object column : resp) {
                                %>
                                    <td><%= column != null ? column.toString() : "" %></td>
                                <%
                                    }
                                %>
                            </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        <%
            }
        %>

        <a href="<%= request.getContextPath() %>/admin/adminhome" class="btn btn-primary mt-3">Back to Admin Home</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>