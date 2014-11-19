package eu.tamere.jeudidulibre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = getParentActivityIntent();
                navigateUpTo(parentIntent);
                return true;
            case R.id.action_share:
                this.share();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, event.title);
        intent.putExtra(Intent.EXTRA_TEXT, event.description);

        startActivity(Intent.createChooser(intent, "Partager"));
    }
}
