package com.example.bryan.tipeaze.Presets;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.bryan.tipeaze.R;

/**
 * TODO basically the problem is i need to always have some content displayed. So if the user never saved any presets
 * a default one is given. The question is how do i take that responsibility out of any class and have it be default functionality?
 * This class is responsible for simply one thing: To query the database and return data. It shouldn't care about how much
 * data was found or whatever, just query, give to adapter, and give to client.
 */

public class PresetQueryManager implements LoaderTemplate.LoaderResponseClient {

    private static final int LOADER_ID = 0b1100100;

    private LoaderTemplate presetLoader;

    private PresetAdapter adapter;

    private CursorLoader loader;

    private String[] columns =  { PresetContract.TABLE_PRESET_NAMES._ID, PresetContract.TABLE_PRESET_NAMES.COL_NAME };
    /**
     * LESSON:
     * Originally columns had only 1 value 'COL_NAME'. This caused the error "column _id does not exist"
     * in the CursorAdapter because
     * CursorAdapter requires _id to be present. So next time be sure to include _id where its needed like i did above
     *
     */



    public PresetQueryManager(@NonNull LoaderManager manager, Context context){
        presetLoader = new LoaderTemplate(manager, this, LOADER_ID);

        loader = new CursorLoader(context);
        loader.setUri(PresetContract.TABLE_PRESET_NAMES.presetUri);
        loader.setProjection(columns);

        adapter = new PresetAdapter(context, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id != LOADER_ID)
             return null;

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        swapCursorAdapter(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public PresetAdapter getAdapter(){
        return adapter;
    }

    public void swapCursorAdapter(Cursor cursor){
        adapter.swapCursor(cursor);
    }





}
