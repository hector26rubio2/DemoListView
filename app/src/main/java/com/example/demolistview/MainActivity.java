package com.example.demolistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.demolistview.service.ItunesService;
import com.example.demolistview.service.model.Results;
import com.example.demolistview.service.model.Root;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageButton btnSearch;

    private TextInputLayout validationSong;
    private ListView lvItems = null;
    private List<Song> songs = null;
    private TextInputEditText txtSong;
    private ItunesService itunesService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itunesService = new ItunesService();
        songs = new ArrayList<>();
        initViews();
        initEvents();
    }

    private void updateList(Root root) {
        runOnUiThread(() -> {

            for (int i = 0; i < root.getResultCount(); i++) {
                Results r = root.getResults()[i];
                songs.add(new Song(r.getArtistName(),r.getTrackName(),r.getPreviewUrl(),r.getTrackId(),r.getArtworkUrl100()));
            }
            CustomListAdapter adapter = new CustomListAdapter(this, songs);
            lvItems.setAdapter(adapter);

        });

    }

    public void initViews() {
        lvItems = findViewById(R.id.lveItems);
        btnSearch = findViewById(R.id.btnSearch);
        txtSong = findViewById(R.id.txtSong);
        validationSong = findViewById(R.id.validationSong);




    }

    private void initEvents() {
        btnSearch.setOnClickListener(v -> {
            if (validation()) {
                itunesService.requestItunesData(txtSong.getText().toString(), (statusCode, root) -> {
                    if (statusCode == 200) {

                        updateList(root);


                    }
                });
            }
        });




    }



    private boolean validation() {
        if (txtSong.getText().toString().isEmpty()) {
            validationSong.setErrorEnabled(true);
            validationSong.setError(getString(R.string.error));

        } else {
            validationSong.setErrorEnabled(false);
            validationSong.setError(null);
            return true;
        }
        return false;
    }

}