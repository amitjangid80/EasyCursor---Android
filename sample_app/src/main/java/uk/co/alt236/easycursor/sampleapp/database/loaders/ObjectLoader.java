package uk.co.alt236.easycursor.sampleapp.database.loaders;

import android.content.Context;
import android.database.Cursor;

import com.commonsware.cwac.loaderex.acl.AbstractCursorLoader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;
import uk.co.alt236.easycursor.sampleapp.container.JsonDataGsonModel;

public class ObjectLoader extends AbstractCursorLoader {
    private static final String UTF_8 = "UTF-8";
    private static final String DATA_SAMPLE_JSON_JSON = "data/sample_json.json";


    public ObjectLoader(Context context) {
        super(context);
    }

    @Override
    protected Cursor buildCursor() {
        EasyCursor cursor;
        try {

            final Gson gson = new Gson();
            final InputStream is = getContext().getAssets().open(DATA_SAMPLE_JSON_JSON);

            final List<JsonDataGsonModel> data = gson.fromJson(
                    new InputStreamReader(is),
                    new TypeToken<ArrayList<JsonDataGsonModel>>() {
                    }.getType());

            // the Json data we have do not have an "_id" field, so we will alias "_id" as "id"
            cursor = new EasyObjectCursor<JsonDataGsonModel>(
                    JsonDataGsonModel.class,
                    data,
                    "id");

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            cursor = null;
        }

        return cursor;
    }

    public String loadAssetsFileAsString(String path) {
        String json = null;
        try {
            final InputStream is = getContext().getAssets().open(path);
            final int size = is.available();
            final byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            json = new String(buffer, UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
