package eu.tamere.jeudidulibre;


import java.util.List;

import retrofit.http.GET;

public interface JDLService {
    @GET("/events.json")
    List<Event> listEvents();
}