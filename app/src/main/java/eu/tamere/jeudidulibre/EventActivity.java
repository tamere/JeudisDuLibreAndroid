package eu.tamere.jeudidulibre;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class EventActivity extends Activity {
    public final static String EVENT = "serialized-event";

    private ImageView eventPicture;
    private TextView eventTitle;
    private TextView eventSpeaker;
    private TextView eventDescription;

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle extras = getIntent().getExtras();
        event = (Event)extras.getSerializable(EVENT);

        eventTitle = (TextView) findViewById(R.id.event_title);
        eventDescription = (TextView) findViewById(R.id.event_description);
        eventSpeaker = (TextView) findViewById(R.id.event_speaker);
        eventPicture = (ImageView) findViewById(R.id.event_image);

        eventTitle.setText(event.title);
        eventDescription.setText(event.description);
        eventSpeaker.setText(event.speaker);
    }
}
