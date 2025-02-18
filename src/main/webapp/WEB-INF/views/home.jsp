<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>WeatherApp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background: linear-gradient(135deg, #6dd5fa, #2980b9);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .card {
            border-radius: 15px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card p-4">
                    <h1 class="text-center mb-3 text-primary">Weather App</h1>
                    <!-- Search Form -->
                    <form action="getWeather" method="post" class="d-flex">
                        <input type="text" name="city" id="city" placeholder="Enter city or location" class="form-control me-2" required>
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>

                    <!-- Weather Data Display -->
                    <div class="mt-4">
                        <%
                            String weatherData = (String) request.getAttribute("weatherData");
                            if (weatherData != null && !weatherData.isEmpty()) {
                        %>
                        <div class="alert alert-info text-center">
                            <h4>Weather Details</h4>
                            <p><%= weatherData %></p>
                        </div>
                        <%
                            }
                            String error = (String) request.getAttribute("error");
                            if (error != null) {
                        %>
                        <p class="text-danger text-center"><%= error %></p>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
