package com.cabdespatch.driverapp.beta.activities;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
//import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SoundChooser extends AnyActivity
{
    public static String EXTRA_LAUNCH_REASON                 = "_LAUNCH_REASON";
    public static String LAUNCH_REASON_BROWSE_FOR_NEW_TRACK  = "_BROWSENEW";
    public static String LAUNCH_REASON_BROWSE_CURRENT_TRACKS = "_BROWSECURRENT";

    private String   oLaunchReason;
    private ListView oList;

    private class ShowCurrentAdapter extends BaseAdapter
    {
        private MediaPlayer  oMediaPlayer;
        private AudioEntry[] oItems;

        public ShowCurrentAdapter(AudioEntry[] _items)
        {
            this.oItems = _items;
            this.oMediaPlayer = new MediaPlayer();
        }

        @Override
        public int getCount()
        {
            return oItems.length;
        }

        @Override
        public AudioEntry getItem(int position)
        {
            return oItems[position];
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            LinearLayout l = (LinearLayout) SoundChooser.this.getLayoutInflater().inflate(R.layout.row_media_browser, null);
            TextView t = (TextView) l.findViewById(R.id.lblTrackName);
            String text = this.oItems[position].getTitle();
            if (text==null) { text=""; }
            if(text.equals(""))
            {
                File f = new File(this.oItems[position].getUri());
                text = f.getName();
            }
            t.setText(text);

            ImageButton btnDelete = (ImageButton) l.findViewById(R.id.btnTrackDelete);
            btnDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(ShowCurrentAdapter.this.oItems.length < 2)
                    {
                        new Dialog_MsgBox(SoundChooser.this, "You cannot delete the only remaining entry").show();
                    }
                    else
                    {
                        SoundChooser.this.onDeleteItemPressed(ShowCurrentAdapter.this.getItem(position).getUri());
                    }
                }
            });

            ImageButton btnPlay = (ImageButton) l.findViewById(R.id.btnTrackPreview);
            btnPlay.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        ShowCurrentAdapter.this.playSong(ShowCurrentAdapter.this.getItem(position).getUri());
                    }
                    catch (Exception ex)
                    {
                        cdToast.showLong(SoundChooser.this, "Could not play file");
                        ex.printStackTrace();
                    }

                }
            });

            ImageButton btnSelect = (ImageButton) l.findViewById(R.id.btnTrackSelect);
            btnSelect.setVisibility(View.GONE);

            return l;
        }

        private void playSong(String path) throws IllegalArgumentException,
                IllegalStateException, IOException
        {
            oMediaPlayer.reset();
            oMediaPlayer.setDataSource(path);
            oMediaPlayer.prepare();
            oMediaPlayer.start();
        }
    }
    private class PickNewAdapter extends BaseAdapter
    {
        private MediaPlayer  oMediaPlayer;
        private AudioEntry[] oItems;

        public PickNewAdapter(AudioEntry[] _items)
        {
            this.oItems = _items;
            this.oMediaPlayer = new MediaPlayer();
        }

        @Override
        public int getCount()
        {
            return oItems.length;
        }

        @Override
        public AudioEntry getItem(int position)
        {
            return oItems[position];
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            LinearLayout l = (LinearLayout) SoundChooser.this.getLayoutInflater().inflate(R.layout.row_media_browser, null);
            TextView t = (TextView) l.findViewById(R.id.lblTrackName);
            String text = this.oItems[position].getTitle();
            if (text==null) { text=""; }
            if(text.equals(""))
            {
                File f = new File(this.oItems[position].getUri());
                text = f.getName();
            }
            t.setText(text);

            ImageButton btnDelete = (ImageButton) l.findViewById(R.id.btnTrackDelete);
            btnDelete.setVisibility(View.GONE);

            ImageButton btnPlay = (ImageButton) l.findViewById(R.id.btnTrackPreview);
            btnPlay.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        PickNewAdapter.this.playSong(PickNewAdapter.this.getItem(position).getUri());
                    }
                    catch (Exception ex)
                    {
                        cdToast.showLong(SoundChooser.this, "Could not play file");
                        ex.printStackTrace();
                    }

                }
            });

            ImageButton btnSelect = (ImageButton) l.findViewById(R.id.btnTrackSelect);
            btnSelect.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    SoundChooser.this.onNewItemSelected(PickNewAdapter.this.getItem(position).getUri());
                }
            });

            return l;
        }

        private void playSong(String path) throws IllegalArgumentException,
                IllegalStateException, IOException
        {
            oMediaPlayer.reset();
            oMediaPlayer.setDataSource(path);
            oMediaPlayer.prepare();
            oMediaPlayer.start();
        }
    }


    private class AudioEntry
    {
        private String oUri;
        private String oDisplayName;
        private String oTitle;

        public AudioEntry(String _uri, String _displayName, String _title)
        {
            this.oUri = _uri;
            this.oDisplayName = _displayName;
            this.oTitle = _title;
        }

        public String getUri() { return this.oUri; }

        public String getDisplayName() { return this.oDisplayName; }
        public String getTitle() { return this.oTitle; }
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_chooser);
        this.setBackground();
        this.oLaunchReason = this.getIntent().getStringExtra(SoundChooser.EXTRA_LAUNCH_REASON);
        this.oList = (ListView) this.findViewById(R.id.list);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        TextView title = (TextView) this.findViewById(R.id.frmSoundChooser_title);

        ImageButton btnBack = (ImageButton) this.findViewById(R.id.frmSoundChooser_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SoundChooser.this.finish();
            }
        });

        ImageButton btnAddNew = (ImageButton) this.findViewById(R.id.frmSoundChooser_btnAddNew);

        if(this.oLaunchReason.equals(SoundChooser.LAUNCH_REASON_BROWSE_FOR_NEW_TRACK))
        {
            title.setText("Available Sounds");

            AudioEntry[] e = getMusicOnDevice();
            this.oList.setAdapter(new PickNewAdapter(e));

            btnAddNew.setVisibility(View.GONE);
        }
        else if(this.oLaunchReason.equals(SoundChooser.LAUNCH_REASON_BROWSE_CURRENT_TRACKS))
        {
            title.setText("Current Sounds");

            AudioEntry[] e = getCurrentMusic();
            this.oList.setAdapter(new ShowCurrentAdapter(e));

            btnAddNew.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent ij = new Intent(SoundChooser.this, SoundChooser.class);
                    ij.putExtra(SoundChooser.EXTRA_LAUNCH_REASON, SoundChooser.LAUNCH_REASON_BROWSE_FOR_NEW_TRACK);

                    Globals.StartActivity(SoundChooser.this, ij);
                }
            });
        }
        else
        {
            cdToast.showLong(this, "Invalid launch reason");
        }
    }



    private void onNewItemSelected(String _uri)
    {
        //cdToast.showLong(this, _uri);
        try
        {
            FileUtils.copyFileToDirectory(new File(_uri), new File(Globals.getJobOfferSoundsFileLocation(this)));
            this.finish();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            cdToast.showShort(this, "Could not copy file");
        }
    }

    private void onDeleteItemPressed(final String _uri)
    {
        Dialog_MsgBox d = new Dialog_MsgBox(getApplicationContext(), "Are you sure you wish to delete this file?", Dialog_MsgBox._SHOWBUTTONS.YESNO);
        d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
        {
            @Override
            public void ButtonPressed(Dialog_MsgBox._BUTTON _button)
            {
                if(_button.equals(Dialog_MsgBox._BUTTON.YES))
                {
                    if(FileUtils.deleteQuietly(new File(_uri)))
                    {
                        AudioEntry[] e = getCurrentMusic();
                        SoundChooser.this.oList.setAdapter(new ShowCurrentAdapter(e));
                    }
                    else
                    {
                        cdToast.showLong(SoundChooser.this, "Unable to delete file");
                    }
                }
            }
        });

        d.show();


    }

    private AudioEntry[] getMusicOnDevice() {
        final Cursor mCursor = managedQuery(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA}, null, null,
                "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

        int count = mCursor.getCount();

        AudioEntry[] songs = new AudioEntry[count];
        int i = 0;
        if (mCursor.moveToFirst()) {
            do {
                songs[i] = new AudioEntry(mCursor.getString(2), mCursor.getString(0), mCursor.getString(1));
                i++;
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return songs;
    }

    private AudioEntry[] getCurrentMusic()
    {
        List<AudioEntry> l = new LinkedList<AudioEntry>();
        File[] files = new File(Globals.getJobOfferSoundsFileLocation(this)).listFiles();

        for(File f:files)
        {
            if(!(f.isDirectory()))
            {
                if(Build.VERSION.SDK_INT >= 10)
                {
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(f.getAbsolutePath());
                    l.add(new AudioEntry(f.getAbsolutePath(), mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE), mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)));
                }
                else
                {
                    l.add(new AudioEntry(f.getAbsolutePath(), f.getName(), f.getName()));
                }

            }

        }

        return l.toArray(new AudioEntry[l.size()]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_sound_chooser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
