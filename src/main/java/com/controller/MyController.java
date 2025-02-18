package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.WeatherResponse;
import com.service.WeatherService;

@Controller
public class MyController {

	@Autowired
	WeatherService weatherService;
	
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}

	@PostMapping("/getWeather")
	public String getweather(@RequestParam("city") String city , Model mod) {
		WeatherResponse weatherData = weatherService.getWeather(city);
		if(weatherData == null) {
			mod.addAttribute("error","City not found. Please check the name and try again.");
		}
		else {
			mod.addAttribute("weatherData",weatherService.getFormattedWeather(city));
		}
		return "home";
	}
}
