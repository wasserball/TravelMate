package link.fls.travelmate.cards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import link.fls.travelmate.PostHotelNotificationReceiver;
import link.fls.travelmate.R;
import link.fls.travelmate.async.SendWearMessageTask;

/**
 * Created by frederik on 27.06.15.
 */
public class BookingActivity extends Activity {

    ProgressBar mProgressBar;
    TextView mTextView;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBarBooking);
        mTextView = (TextView) findViewById(R.id.textViewBookingStatus);
        mImageView = (ImageView) findViewById(R.id.imageViewBookingStatus);

        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                Animation fadeOut2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        bookHotel();
                        mProgressBar.setVisibility(View.GONE);
                        mImageView.setVisibility(View.INVISIBLE);
                        mTextView.setVisibility(View.INVISIBLE);
                        showConfirmation(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                mProgressBar.startAnimation(fadeOut2);
                mImageView.startAnimation(fadeOut2);
                mTextView.startAnimation(fadeOut);
            }
        }, 4000);
    }

    private void showConfirmation(boolean success) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Buchung erfolgreich!");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Remove the notification from the stream
        Intent i = new Intent();
        i.setAction(PostHotelNotificationReceiver.ACTION_HIDE_HOTELINFO_NOTIFICATION);
        sendBroadcast(i);

        showLoginActivity();

        finish();
    }

    private void showLoginActivity() {
        Intent i = new Intent();
        i.setAction(PostHotelNotificationReceiver.ACTION_SHOW_LOGIN_NOTIFICATION);
        sendBroadcast(i);
    }

    private void bookHotel() {
        new SendWearMessageTask(this).execute("BOOK_HOTEL", "");
    }
}
