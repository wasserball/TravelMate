package link.fls.travelmate.cards;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import link.fls.travelmate.R;
import link.fls.travelmate.async.SendWearMessageTask;

/**
 * Created by frederik on 27.06.15.
 */
public class InRoomActivity extends Activity {

    public static final String EXTRA_TITLE = "link.fls.travelmate.INROOM_TITLE";
    public static final String EXTRA_DESCRIPTION = "link.fls.travelmate.INROOM_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inroom);

        final String title = getIntent().getExtras().getString(EXTRA_TITLE);
        String description = getIntent().getExtras().getString(EXTRA_DESCRIPTION);

        TextView tvTitle = (TextView) findViewById(R.id.textViewInRoomTitle);
        TextView tvDescription = (TextView) findViewById(R.id.textViewInRoomDescription);

        tvTitle.setText(title);
        tvDescription.setText(description);

        LinearLayout inRoom = (LinearLayout) findViewById(R.id.linearLayoutInroom);
        inRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(500);
                Toast.makeText(getApplicationContext(), title + " bestellt!", Toast.LENGTH_SHORT).show();
                new SendWearMessageTask(getApplicationContext()).execute("CREATE_TASK", title);
            }
        });
    }
}
