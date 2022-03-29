package com.minakov.yandexraspintegration.it.feign;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.minakov.yandexraspintegration.feign.YandexRaspClient;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.util.TestUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@SpringBootIT
class YandexRaspClientTest extends AbstractIT {
    @Value("classpath:data/feign/yandexrasp/stationlist/response.json")
    private Resource stationList;
    @Value("classpath:data/feign/yandexrasp/schedulestation/response.json")
    private Resource scheduleStation;

    @Autowired
    private YandexRaspClient client;

    @Test
    void getStationList() {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/stations_list")).withHeader("Content-Type", equalTo("application/json"))
                        .withQueryParam("lang", equalTo("ru_RU"))
                        .withQueryParam("format", equalTo("json"))
                        .willReturn(aResponse().withHeader("Content-Type", "application/json")
                                .withBody(TestUtil.readResource(stationList))
                                .withStatus(200)));

        {
            final var response = client.getStationList("api_key", "ru_RU", "json");

            assertEquals(1, response.getCountries().size());

            final var country = response.getCountries().get(0);

            assertEquals("esr_code_l225", country.getCodes().getEsrCode());
            assertEquals("l225", country.getCodes().getYandexCode());
            assertEquals("Russia", country.getTitle());
            assertEquals(1, country.getRegions().size());

            final var region = country.getRegions().get(0);

            assertEquals("esr_code_r10174", region.getCodes().getEsrCode());
            assertEquals("r10174", region.getCodes().getYandexCode());
            assertEquals("St. Petersburg and Leningrad Region", region.getTitle());
            assertEquals(1, region.getSettlements().size());

            final var settlement = region.getSettlements().get(0);

            assertEquals("esr_code_c2", settlement.getCodes().getEsrCode());
            assertEquals("c2", settlement.getCodes().getYandexCode());
            assertEquals("St. Petersburg", settlement.getTitle());
            assertEquals(1, settlement.getStations().size());

            final var station = settlement.getStations().get(0);

            assertEquals("esr_code_s9600366", station.getCodes().getEsrCode());
            assertEquals("s9600366", station.getCodes().getYandexCode());
            assertEquals("LED", station.getTitle());
            assertEquals("direction_s9600366", station.getDirection());
            assertEquals("airport", station.getStationType());
            assertEquals("plane", station.getTransportType());
            assertEquals(59.799489, station.getLatitude());
            assertEquals(30.271525, station.getLongitude());
        }
    }

    @Test
    void getScheduleByStation() {
        wireMockServer.stubFor(get(urlPathEqualTo("/schedule")).withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("station", equalTo("s9600213"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.readResource(scheduleStation))
                        .withStatus(200)));

        final var response =
                client.getScheduleByStation("api_key", "s9600213", null, null, null, null, null, null, null, null,
                        null);

        assertNotNull(response);
        assertNotNull(response.getScheduleDirection());
        assertNotNull(response.getDirections());
        assertEquals(LocalDate.of(2017, 10, 28), response.getDate());
        assertEquals("to Moscow", response.getScheduleDirection().getCode());
        assertEquals("To Moscow", response.getScheduleDirection().getTitle());

        assertEquals(3, response.getDirections().size());
        assertEquals("arrival", response.getDirections().get(0).getCode());
        assertEquals("Arrival", response.getDirections().get(0).getTitle());
        assertEquals("to Moscow", response.getDirections().get(1).getCode());
        assertEquals("To Moscow", response.getDirections().get(1).getTitle());
        assertEquals("all", response.getDirections().get(2).getCode());
        assertEquals("All directions", response.getDirections().get(2).getTitle());

        final var pagination = response.getPagination();

        assertEquals(210, pagination.getTotal());
        assertEquals(100, pagination.getLimit());
        assertEquals(0, pagination.getOffset());

        final var station = response.getStation();

        assertEquals("s9600213", station.getCode());
        assertEquals("Sheremetievo", station.getTitle());
        assertEquals("airport", station.getStationType());
        assertEquals("popular_title", station.getPopularTitle());
        assertEquals("short_title", station.getShortTitle());
        assertEquals("train", station.getTransportType());
        assertEquals("station", station.getType());

        final var schedules = response.getSchedule();

        assertNotNull(schedules);
        assertEquals(1, schedules.size());

        final var schedule = schedules.get(0);

        assertEquals("6, 7, 8, 9, 13, 14 february", schedule.getExceptDays());
        assertEquals(OffsetDateTime.of(LocalDateTime.of(2017, 2, 27, 0, 4, 0), ZoneOffset.ofHours(3)),
                schedule.getArrival());
        assertEquals(OffsetDateTime.of(LocalDateTime.of(2017, 2, 27, 0, 5, 0), ZoneOffset.ofHours(3)),
                schedule.getDeparture());
        assertEquals(Boolean.FALSE, schedule.getIsFuzzy());
        assertEquals("everyday", schedule.getDays());
        assertEquals("without stops", schedule.getStops());
        assertEquals("A2", schedule.getTerminal());
        assertEquals("P1", schedule.getPlatform());

        final var thread1 = schedule.getThread();

        assertNotNull(thread1);
        assertNotNull(thread1.getTransportSubtype());
        assertEquals("7303A_9600213_g13_af", thread1.getUid());
        assertEquals("airport Sheremetievo - Moscow", thread1.getTitle());
        assertEquals("a/p Sheremetievo - Moscow", thread1.getShortTitle());
        assertEquals("7303", thread1.getNumber());
        assertEquals("suburban", thread1.getTransportType());
        assertEquals("vehicle", thread1.getVehicle());
        assertEquals("aeroexpress", thread1.getExpressType());
        assertEquals("#FF7F44", thread1.getTransportSubtype().getColor());
        assertEquals("suburban", thread1.getTransportSubtype().getCode());
        assertEquals("Suburban train", thread1.getTransportSubtype().getTitle());

        final var carrier1 = thread1.getCarrier();

        assertNotNull(carrier1);
        assertNotNull(carrier1.getCodes());
        assertEquals(153, carrier1.getCode());
        assertEquals("Central Suburban Passenger Company", carrier1.getTitle());
        assertEquals("icao", carrier1.getCodes().getIcao());
        assertEquals("iata", carrier1.getCodes().getIata());
        assertEquals("sirena", carrier1.getCodes().getSirena());

        final var intervalSchedules = response.getIntervalSchedule();

        assertNotNull(intervalSchedules);
        assertEquals(1, intervalSchedules.size());

        final var intervalSchedule = response.getIntervalSchedule().get(0);

        assertNull(intervalSchedule.getExceptDays());
        assertNull(intervalSchedule.getTerminal());
        assertEquals(Boolean.FALSE, intervalSchedule.getIsFuzzy());
        assertEquals("everyday", intervalSchedule.getDays());
        assertEquals("", intervalSchedule.getStops());
        assertEquals("", intervalSchedule.getPlatform());

        final var thread2 = intervalSchedule.getThread();

        assertNotNull(thread2);
        assertNotNull(thread2.getTransportSubtype());
        assertNull(thread2.getCarrier());
        assertNull(thread2.getVehicle());
        assertNull(thread2.getExpressType());
        assertEquals("502-*28mxt*29_0_f9744758t9744460_r2531_1", thread2.getUid());
        assertEquals("Moscow (м. Medvedkovo) — s.Pirigivskiy", thread2.getTitle());
        assertEquals("Moscow (м. Medvedkovo) — s.Pirigivskiy1", thread2.getShortTitle());
        assertEquals("502", thread2.getNumber());
        assertEquals("bus", thread2.getTransportType());
        assertEquals("#ff0000", thread2.getTransportSubtype().getColor());
        assertEquals("bus", thread2.getTransportSubtype().getCode());
        assertEquals("Bus", thread2.getTransportSubtype().getTitle());
    }
}
