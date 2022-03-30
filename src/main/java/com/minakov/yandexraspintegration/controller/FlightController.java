package com.minakov.yandexraspintegration.controller;

import com.minakov.yandexraspintegration.controller.graphql.type.schedule.flight.Flight;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FlightController {

    @QueryMapping
    public List<Flight> flights() {
        throw new UnsupportedOperationException();
    }
}
