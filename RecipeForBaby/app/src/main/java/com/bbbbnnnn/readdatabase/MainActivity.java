package com.bbbbnnnn.readdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.bbbbnnnn.readdatabase.adapter.MyDatabaseAdapter;
import com.bbbbnnnn.readdatabase.getdatabase.MyDatabase;
import com.bbbbnnnn.readdatabase.model.Recipe;
import com.bbbbnnnn.readdatabase.nav.Favorite;
import com.bbbbnnnn.readdatabase.nav.RatingApp;
import com.bbbbnnnn.readdatabase.webview.RecipeDetail;
import com.google.android.material.navigation.NavigationView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements MyDatabaseAdapter.RecipeItemListener, NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_URL = "extra.url";
    public static final String EXTRA_ID = "extra.id";
    public static final String EXTRA_LOVE = "extra.love";
    private RecyclerView lvRecipe;
    private ArrayList<Recipe> data;
    private MyDatabaseAdapter adapter;
    private Recipe recipe;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    public String boDau(String str) {
        String tmp = Normalizer.normalize(str, Normalizer.Form.NFD).toLowerCase().replaceAll("[ /_.,:-]", "");
        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher(tmp).replaceAll("");
    }

    private void initData() {
        nav = findViewById(R.id.navigation_view);
        nav.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        data = new ArrayList<>();
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.open();
        SQLiteDatabase database = myDatabase.getMyDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM DongVat", null);

        if (cursor != null && cursor.getCount() > 0 && cursor.isBeforeFirst()) {
            while (cursor.moveToNext()) {
                recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                data.add(recipe);
                adapter.setData(data);
            }
        }

    }

    private void initView() {
        lvRecipe = findViewById(R.id.listViewRecipe);
        adapter = new MyDatabaseAdapter(getLayoutInflater());
        lvRecipe.setAdapter(adapter);
        adapter.setListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        lvRecipe.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //enable navigation open click
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = boDau(newText);
                ArrayList<Recipe> newList = new ArrayList<>();
                for (Recipe item : data) {
                    String name = boDau(item.getTenHinh());
                    if (name.contains(newText)) {
                        newList.add(item);
                    }
                }
                adapter.setFilter(newList);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRecipeItemClick(int position, Recipe item) {
        this.recreate();
        Intent intent = new Intent(this, RecipeDetail.class);
        intent.putExtra(EXTRA_URL, item.getYoutube());
        intent.putExtra(EXTRA_ID, item.getId());
        intent.putExtra(EXTRA_LOVE, item.getLove());
        startActivity(intent);
//        Toast.makeText(this, item.getTenHinh(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_menu_love:
                Intent intentFavorite = new Intent(this, Favorite.class);
                startActivity(intentFavorite);
                break;
            case R.id.nav_menu_rate_app:
                Intent intent = new Intent(this, RatingApp.class);
                startActivity(intent);
                break;
            case R.id.nav_menu_share:
                Intent intentInvite = new Intent(Intent.ACTION_SEND);
                intentInvite.setType("text/plain");
                String body = "Link to your app";
                String subject = "Món Ăn Cho Bé Yêu";
                intentInvite.putExtra(Intent.EXTRA_SUBJECT, subject);
                intentInvite.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intentInvite, "Share"));
                break;
            case R.id.nav_menu_google_play:
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
            case R.id.nav_menu_exit:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
