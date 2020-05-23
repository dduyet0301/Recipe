package com.bbbbnnnn.readdatabase.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.bbbbnnnn.readdatabase.MainActivity;
import com.bbbbnnnn.readdatabase.R;
import com.bbbbnnnn.readdatabase.getdatabase.MyDatabase;

public class RecipeDetail extends AppCompatActivity {

    WebView web;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        web = findViewById(R.id.webView);
        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.EXTRA_URL);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_share_more, menu);
        Intent intent = getIntent();
        String love = intent.getStringExtra(MainActivity.EXTRA_LOVE);
        if (love.equals("0")){
            menu.getItem(0).setChecked(false);
            menu.getItem(0).setIcon(R.drawable.heart_borderline);
        }else {
            menu.getItem(0).setChecked(true);
            menu.getItem(0).setIcon(R.drawable.heart_black);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_share:
                Intent intentInvite = new Intent(Intent.ACTION_SEND);
                intentInvite.setType("text/plain");
                String body = "Link to your app";
                String subject = String.valueOf(R.string.app_name);
                intentInvite.putExtra(Intent.EXTRA_SUBJECT, subject);
                intentInvite.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intentInvite, "Share"));
                break;
            case R.id.menu_love:
                Intent intent = getIntent();
                int id = intent.getIntExtra(MainActivity.EXTRA_ID, 0);
                String love = intent.getStringExtra(MainActivity.EXTRA_LOVE);
                if (!item.isChecked()) {
                    item.setChecked(true);
                    menu.getItem(0).setIcon(R.drawable.heart_black);
                    isLove("1", id);
                    Toast.makeText(this, "Love", Toast.LENGTH_SHORT).show();
                } else {
                    item.setChecked(false);
                    menu.getItem(0).setIcon(R.drawable.heart_borderline);
                    isLove("0", id);
                    Toast.makeText(this, "unLove", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void isLove(String love, int id) {
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.open();
        SQLiteDatabase database = myDatabase.getMyDatabase();
        database.execSQL("UPDATE DongVat SET love = '" + love + "' WHERE Id ='" + id + "'");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }
}
