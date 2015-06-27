package link.fls.travelmate;

import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;

/**
 * Created by frederik on 27.06.15.
 */
public class WearableListenerService extends com.google.android.gms.wearable.WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        String action = messageEvent.getPath();
        if(action.equals("OPEN_DOOR")) {
            Intent i = new Intent();
            i.setAction(PostHotelNotificationReceiver.ACTION_SHOW_INROOM_NOTIFICATION);
            sendBroadcast(i);
        }

        if(action.equals("DISPLAY_HOTEL_INFO")) {
            Intent i = new Intent();
            i.setAction(PostHotelNotificationReceiver.ACTION_SHOW_HOTELINFO_NOTIFICATION);
            sendBroadcast(i);
        }

    }
}
