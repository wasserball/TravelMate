package link.fls.travelmate;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import link.fls.travelmate.async.SendWearMessageTask;

/*
 * This activity is only used for demo purposes - the magic happens in the services and background communication ;-)
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button demoNearHotel = (Button) findViewById(R.id.buttonSimulateNearHotel);
        final Button demoDoorOpen = (Button) findViewById(R.id.buttonSimulateDoor);

        demoNearHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simulateNearHotel();
                demoNearHotel.setEnabled(false);
            }
        });

        demoDoorOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simulateDoorOpening();
                demoDoorOpen.setEnabled(false);
            }
        });
    }

    private void simulateDoorOpening() {
        new SendWearMessageTask(this).execute("OPEN_DOOR", "");
    }

    private void simulateNearHotel() {
        new SendWearMessageTask(this).execute("DISPLAY_HOTEL_INFO", "");
    }
}
