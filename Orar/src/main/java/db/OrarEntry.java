package db;

import android.content.ContentValues;
import android.provider.BaseColumns;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Adi on 11/2/13.
 */
public class OrarEntry implements BaseColumns {
    public static final String TABLE_NAME = "files";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_URL = "url";

    public String name;
    public URL url;

    public OrarEntry(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    public OrarEntry(ContentValues contentValues) throws MalformedURLException {
        name = contentValues.getAsString(COLUMN_NAME);
        url = new URL(contentValues.getAsString(COLUMN_URL));
    }

    public ContentValues getContentValues()
    {
        ContentValues ret = new ContentValues();
        ret.put(COLUMN_NAME, name);
        ret.put(COLUMN_URL, url.toString());

        return ret;
    }
}
