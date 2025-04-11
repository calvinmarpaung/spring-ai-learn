package com.calvin.demo.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

/*
  Weather API
  https://www.weatherapi.com/api-explorer.aspx
 */

public class WeatherService implements Function<WeatherService.Request, WeatherService.Response> {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherConfigProperties weatherProps;
    private final RestClient restClient;

    public WeatherService(WeatherConfigProperties weatherProps) {
        this.weatherProps = weatherProps;
        this.restClient = RestClient.create(weatherProps.apiUrl());
    }

    @Override
    public Response apply(Request request){
        Response response = restClient.get()
                .uri("/current.json?key={key}&q={q}",weatherProps.apiKey(),request.city())
                .retrieve()
                .body(Response.class);
        log.info("Weather API response: {}", response);
        return response;
    }

    public record Request(String city){}
    public record Location(String name, String region, String country, Long lat, Long lon) {}
    public record Current(String lastUpdated, String tempC, String isDay, Condition condition) {}
    public record Condition(String text) {}
    public record Response(Location location, Current current){}


}
