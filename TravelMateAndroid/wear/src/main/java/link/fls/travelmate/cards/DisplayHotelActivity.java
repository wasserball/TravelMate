package link.fls.travelmate.cards;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import link.fls.travelmate.R;


public class DisplayHotelActivity extends Activity implements View.OnClickListener{

    private LinearLayout satisfaction, features;
    private boolean isFeaturesVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_hotel);

        satisfaction = (LinearLayout) findViewById(R.id.linearLayoutSatisfaction);
        features = (LinearLayout) findViewById(R.id.linearLayoutFeatures);

        satisfaction.setOnClickListener(this);
        features.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(isFeaturesVisible) {
            animateFade(satisfaction, features);
            isFeaturesVisible = false;
        } else {
            animateFade(features, satisfaction);
            isFeaturesVisible = true;
        }
    }

    private void animateFade(final View in, final View out){
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                out.setVisibility(View.INVISIBLE);
                in.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        in.startAnimation(fadeIn);
        out.startAnimation(fadeOut);
    }
}