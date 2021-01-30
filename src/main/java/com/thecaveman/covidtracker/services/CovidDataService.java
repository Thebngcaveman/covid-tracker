package com.thecaveman.covidtracker.services;

import com.thecaveman.covidtracker.models.LocationState;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidDataService {
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationState> allStates = new ArrayList<>();

    public List<LocationState> getAllStates() {
        return allStates;
    }

    @PostConstruct //when construct is done execute the rest
    @Scheduled(cron = "* * 1 * * *") //fetch it every single day if(* * * * * *) => fetch every sec
    public void fetchVirusData() throws IOException, InterruptedException { // throw exception incase cliend.send() fail
        List<LocationState> newStates = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(VIRUS_DATA_URL))
            .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            LocationState locationState = new LocationState();
            locationState.setState(record.get("Province/State"));
            locationState.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt((record.get(record.size() - 1))); //latest or today
            int prevCases = Integer.parseInt((record.get(record.size() - 2))); // yesterday
            locationState.setLatestCases(latestCases);
            locationState.setDiffFromLastDay(latestCases - prevCases);
            newStates.add(locationState);
        }
        this.allStates = newStates;
    }
}
