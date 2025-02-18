package com.service;

import com.dto.WeatherResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON parser

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        String url = UriComponentsBuilder.fromHttpUrl(weatherApiUrl)
                .queryParam("q", city)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        try {
            // Make API call
            String jsonResponse = restTemplate.getForObject(url, String.class);

            // Parse JSON response properly
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            WeatherResponse response = new WeatherResponse();
            response.setCityName(rootNode.path("name").asText());
            response.setDescription(rootNode.path("weather").get(0).path("description").asText());
            response.setTemperature(rootNode.path("main").path("temp").asDouble());
            response.setFeelsLike(rootNode.path("main").path("feels_like").asDouble());

            return response;

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null; // Handle invalid city name
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Return null if parsing fails
    }

    public String getFormattedWeather(String city) {
        WeatherResponse weatherResponse = getWeather(city);

        if (weatherResponse == null) {
            return "Invalid city name. Please enter a valid city.";
        }

        return String.format("Weather in %s:\n"
                + "Description: %s\n"
                + "Temperature: %.2f°C\n"
                + "Feels Like: %.2f°C",
                weatherResponse.getCityName(),
                weatherResponse.getDescription(),
                weatherResponse.getTemperature(),
                weatherResponse.getFeelsLike());
    }
}
