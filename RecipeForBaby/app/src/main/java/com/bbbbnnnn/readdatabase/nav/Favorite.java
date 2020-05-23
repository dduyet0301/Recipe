package com.bbbbnnnn.readdatabase.nav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.bbbbnnnn.readdatabase.MainActivity;
import com.bbbbnnnn.readdatabase.R;
import com.bbbbnnnn.readdatabase.adapter.MyDatabaseAdapter;
import com.bbbbnnnn.readdatabase.getdatabase.MyDatabase;
import com.bbbbnnnn.readdatabase.model.Recipe;
import com.bbbbnnnn.readdatabase.webview.RecipeDetail;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity implements MyDatabaseAdapter.RecipeItemListener {

    private RecyclerView lvFavorite;
    private ArrayList<Recipe> data;
    private MyDatabaseAdapter adapter;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        initViews();
        initData();
    }

    private void initData() {
        data = new ArrayList<>();
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.open();
        SQLiteDatabase database = myDatabase.getMyDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM DongVat", null);

        if (cursor != null && cursor.getCount() > 0 && cursor.isBeforeFirst()) {
            while (cursor.moveToNext()) {
                if (cursor.getString(4).equals("1")){
                recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
                data.add(recipe);
                adapter.setData(data);}
            }
        }

    }

    private void initViews() {
        lvFavorite = findViewById(R.id.listViewFavorite);
        adapter = new MyDatabaseAdapter(getLayoutInflater());
        lvFavorite.setAdapter(adapter);
        adapter.setListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        lvFavorite.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onRecipeItemClick(int position, Recipe item) {
//        this.recreate();
        Intent intent = new Intent(this, RecipeDetail.class);
        intent.putExtra(MainActivity.EXTRA_URL, item.getYoutube());
        intent.putExtra(MainActivity.EXTRA_ID,item.getId());
        intent.putExtra(MainActivity.EXTRA_LOVE,item.getLove());
        startActivity(intent);
    }
}
