package com.bbbbnnnn.readdatabase.nav;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bbbbnnnn.readdatabase.R;

public class RatingApp extends AppCompatActivity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    private RatingBar ratingBar;
    private TextView tvStarNumber;
    private Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_app);
        ratingBar = findViewById(R.id.ratingBar);
        tvStarNumber = findViewById(R.id.starNumber);
        btnSubmit = findViewById(R.id.buttonSubmit);
        btnSubmit.setOnClickListener(this);
        ratingBar.setOnRatingBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        float star = ratingBar.getRating();
        if (star > 0.0f) {
            Toast.makeText(this, String.valueOf(star), Toast.LENGTH_SHORT).show();
            finish();
        } else
            Toast.makeText(this, R.string.toast_rate, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        String star = String.valueOf(ratingBar.getRating());
        tvStarNumber.setText(star);
    }
}
