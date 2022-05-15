package com.example.demolistview;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demolistview.service.ItunesService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private List<Song> songs;
    private int estado;
    private MediaPlayer mediaPlayer;

    public CustomListAdapter(Context newContext, List<Song> newList) {
        this.context = newContext;
        this.songs = newList;
        estado = 0;
        mediaPlayer = null;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;


        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_view_row_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Song currentSong = songs.get(position);

        viewHolder.txtArtistName.setText(currentSong.getArtistName());
        viewHolder.txtTrackName.setText(currentSong.getTrackName());
        String urlImg = currentSong.getArtworkUrl100();

        if (!urlImg.equals("")) {
            Picasso.get()
                    .load(currentSong.getArtworkUrl100())
                    .into(viewHolder.imgArArtist);
        }
        String destFilename = context.getCacheDir() + "/" + currentSong.getTrackId() + ".m4a";
        validation(destFilename, viewHolder, currentSong.getPreviewUrl());

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnSong.setOnClickListener(v -> {
            if (new File(destFilename).exists()) {
                try {
                    if (mediaPlayer != null)
                        mediaPlayer.release();

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(destFilename);
                    mediaPlayer.prepare();
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                ItunesService.downloadFile(destFilename, currentSong.getPreviewUrl());

                finalViewHolder.btnSong.setImageResource(android.R.drawable.ic_media_play);
            }


        });
        return convertView;
    }


    private class ViewHolder {
        ImageButton btnSong;
        TextView txtArtistName;
        TextView txtTrackName;
        ImageView imgArArtist;


        public ViewHolder(View view) {
            imgArArtist = view.findViewById(R.id.imgArtist);
            txtArtistName = view.findViewById(R.id.txtName);
            txtTrackName = view.findViewById(R.id.txtDescription);
            btnSong = view.findViewById(R.id.btnSong);
        }
    }

    private void validation(String destFilename, ViewHolder viewHolder, String previewUrl) {


        if (new File(destFilename).exists()) {
            viewHolder.btnSong.setImageResource(android.R.drawable.ic_media_play);

        } else {
            viewHolder.btnSong.setImageResource(android.R.drawable.stat_sys_download);


        }

    }
}

