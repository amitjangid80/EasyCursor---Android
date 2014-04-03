package uk.co.alt236.easycursor.sampleapp.database.loaders;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;

import uk.co.alt236.easycursor.EasyCursor;
import uk.co.alt236.easycursor.jsoncursor.EasyJsonCursor;
import android.content.Context;
import android.database.Cursor;

import com.commonsware.cwac.loaderex.acl.AbstractCursorLoader;

public class JsonLoader extends AbstractCursorLoader{
	private static final String UTF_8 = "UTF-8";
	private static final String DATA_SAMPLE_JSON_JSON = "data/sample_json.json";


	public JsonLoader(Context context) {
		super(context);
	}

	@Override
	protected Cursor buildCursor() {
		EasyCursor cursor;
		try {

			// the Json data we have do not have an "_id" field, so we will alias "_id" as "id"

			cursor = new EasyJsonCursor(
					new JSONArray(loadAssetsFileAsString(DATA_SAMPLE_JSON_JSON)),
					"id");
		} catch (JSONException e) {
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
