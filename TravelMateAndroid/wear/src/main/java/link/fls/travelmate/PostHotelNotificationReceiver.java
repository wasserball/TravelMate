package link.fls.travelmate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import link.fls.travelmate.cards.BookingActivity;
import link.fls.travelmate.cards.CheckInActivity;
import link.fls.travelmate.cards.DisplayHotelActivity;
import link.fls.travelmate.cards.InRoomActivity;
import link.fls.travelmate.cards.QrCodeActivity;


public class PostHotelNotificationReceiver extends BroadcastReceiver {
    public static final String CONTENT_KEY = "contentText";

    public static final String ACTION_SHOW_HOTELINFO_NOTIFICATION = "link.fls.travelmate.SHOW_BOOKING";
    public static final String ACTION_HIDE_HOTELINFO_NOTIFICATION = "link.fls.travelmate.HIDE_BOOKING";

    public static final String ACTION_SHOW_LOGIN_NOTIFICATION = "link.fls.travelmate.SHOW_LOGIN";

    public static final String ACTION_SHOW_INROOM_NOTIFICATION = "link.fls.travelmate.SHOW_INROOM";


    public PostHotelNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //String text = intent.getStringExtra(CONTENT_KEY);
        String action = intent.getAction();

        if(action.equals(ACTION_SHOW_HOTELINFO_NOTIFICATION)) {
            showNotification(context);
        }

        if(action.equals(ACTION_HIDE_HOTELINFO_NOTIFICATION)) {
            hideNotification(context);
        }

        if(action.equals(ACTION_SHOW_LOGIN_NOTIFICATION)) {
            showLoginActivity(context);
        }

        if(action.equals(ACTION_SHOW_INROOM_NOTIFICATION)) {
            showInRoomActivity(context);
        }
    }

    private void showNotification(Context context) {
        Intent displayIntent = new Intent(context, DisplayHotelActivity.class);
        Intent bookingIntent = new Intent(context, BookingActivity.class);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Meliá Düsseldorf ist verfügbar!")
                .extend(new Notification.WearableExtender()
                        .setCustomSizePreset(Notification.WearableExtender.SIZE_LARGE)
                        .setBackground(BitmapFactory.decodeResource(context.getResources(), R.drawable.dummy_hotelimage))
                        .setDisplayIntent(PendingIntent.getActivity(context, 0, displayIntent, PendingIntent.FLAG_UPDATE_CURRENT)))
                .addAction(new Notification.Action(R.drawable.ic_action_book, "Buchen", PendingIntent.getActivity(context, 0, bookingIntent, PendingIntent.FLAG_CANCEL_CURRENT)))
                .build();

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);
    }

    private void hideNotification(Context context) {
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
    }

    private void showLoginActivity(Context context) {
        Intent displayIntent = new Intent(context, CheckInActivity.class);
        Intent qrIntent = new Intent(context, QrCodeActivity.class);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("TravelMate für Meliá Düsseldorf")
                .extend(new Notification.WearableExtender()
                        .setHintHideIcon(true)
                        .setCustomSizePreset(Notification.WearableExtender.SIZE_LARGE)
                        .setBackground(BitmapFactory.decodeResource(context.getResources(), R.drawable.dummy_reception))
                        .setDisplayIntent(PendingIntent.getActivity(context, 10, displayIntent, PendingIntent.FLAG_UPDATE_CURRENT)))
                .addAction(new Notification.Action(R.drawable.ic_action_qrcode, "QR-Code Check-In", PendingIntent.getActivity(context, 0, qrIntent, PendingIntent.FLAG_CANCEL_CURRENT)))
                .build();

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(10, notification);
    }

    private void showInRoomActivity(Context context) {
        Intent helpdeskIntent = new Intent(context, QrCodeActivity.class);

        Intent displayIntent = new Intent(context, InRoomActivity.class);
        displayIntent.putExtra(InRoomActivity.EXTRA_TITLE, "Guten Abend!");
        displayIntent.putExtra(InRoomActivity.EXTRA_DESCRIPTION, "Hier können Sie mit einem Tipp Ihren Roomservice buchen. Probieren Sie es aus!");
        Notification notification = new Notification.Builder(context)
                .setGroup("roomService")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Guten Abend!")
                .extend(new Notification.WearableExtender()
                        .setCustomSizePreset(Notification.WearableExtender.SIZE_LARGE)
                        .setBackground(BitmapFactory.decodeResource(context.getResources(), R.drawable.dummy_inroom))
                        .setDisplayIntent(PendingIntent.getActivity(context, 10, displayIntent, PendingIntent.FLAG_UPDATE_CURRENT)))
                .addAction(new Notification.Action(R.drawable.ic_action_helpdesk, "Helpdesk anrufen", PendingIntent.getActivity(context, 0, helpdeskIntent, PendingIntent.FLAG_CANCEL_CURRENT)))
                .build();

        Intent displayIntentBettlaken = new Intent(context, InRoomActivity.class);
        displayIntentBettlaken.putExtra(InRoomActivity.EXTRA_TITLE, "Neue Bettlaken");
        displayIntentBettlaken.putExtra(InRoomActivity.EXTRA_DESCRIPTION, "Tippen, um neue Bettlaken zu erhalten.");
        Notification notification2 = new Notification.Builder(context)
                .setGroup("roomService")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Neue Bettlaken")
                .extend(new Notification.WearableExtender()
                        .setHintHideIcon(true)
                        .setCustomSizePreset(Notification.WearableExtender.SIZE_SMALL)
                        .setBackground(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_bedsheet))
                        .setDisplayIntent(PendingIntent.getActivity(context, 20, displayIntentBettlaken, PendingIntent.FLAG_UPDATE_CURRENT)))
                .addAction(new Notification.Action(R.drawable.ic_action_helpdesk, "Helpdesk anrufen", PendingIntent.getActivity(context, 0, helpdeskIntent, PendingIntent.FLAG_CANCEL_CURRENT)))
                .build();

        Intent displayIntentDuschgel = new Intent(context, InRoomActivity.class);
        displayIntentDuschgel.putExtra(InRoomActivity.EXTRA_TITLE, "Neues Duschgel");
        displayIntentDuschgel.putExtra(InRoomActivity.EXTRA_DESCRIPTION, "Tippen, um neues Duschgel zu erhalten.");
        Notification notification3 = new Notification.Builder(context)
                .setGroup("roomService")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Neues Duschgel")
                .extend(new Notification.WearableExtender()
                        .setHintHideIcon(true)
                        .setCustomSizePreset(Notification.WearableExtender.SIZE_SMALL)
                        .setBackground(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_showergel))
                        .setDisplayIntent(PendingIntent.getActivity(context, 30, displayIntentDuschgel, PendingIntent.FLAG_UPDATE_CURRENT)))
                .addAction(new Notification.Action(R.drawable.ic_action_helpdesk, "Helpdesk anrufen", PendingIntent.getActivity(context, 0, helpdeskIntent, PendingIntent.FLAG_CANCEL_CURRENT)))
                .build();

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(10, notification3);
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(20, notification2);
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(30, notification);
    }
}