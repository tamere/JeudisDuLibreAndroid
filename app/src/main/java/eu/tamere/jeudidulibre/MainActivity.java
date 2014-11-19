package eu.tamere.jeudidulibre;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private ListView eventsList;
    private EventsAdapter eventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup List

        eventsAdapter = new EventsAdapter(this);
        eventsList = (ListView) findViewById(R.id.events_list);
        eventsList.setAdapter(eventsAdapter);

        eventsList.setOnItemClickListener(this);

        // Setup REST calls

        // https://developer.android.com/tools/devices/emulator.html#networkaddresses
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://10.0.2.2:1701")
                .build();

        JDLService service = restAdapter.create(JDLService.class);

        // Initialize list

        UpdateEventsTasks task = new UpdateEventsTasks(service);
        task.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(this, eventsAdapter.getItem(position).description, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra(EventActivity.EVENT, eventsAdapter.getItem(position));
        startActivity(intent);
    }

    public class UpdateEventsTasks extends AsyncTask<Void, Void, List<Event>> {
        private JDLService service;

        public UpdateEventsTasks(JDLService service) {
            super();
            this.service = service;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            try {
                return service.listEvents();
            } catch(RetrofitError e) {
                Log.e("JDL", e.toString());
                return new ArrayList<Event>();
            }
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            eventsAdapter.clear();
            eventsAdapter.addAll(events);
            eventsAdapter.notifyDataSetChanged();
        }
    }
}
