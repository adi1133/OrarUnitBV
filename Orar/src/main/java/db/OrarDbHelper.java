package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Adi on 11/2/13.
 */
public class OrarDbHelper extends SQLiteOpenHelper {
    public static final int SCHEMA_VERSION = 1;
    public static final String DATABASE_NAME = "OrarDb";
    public static final String SQL_CREATE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",OrarEntry.TABLE_NAME, OrarEntry._ID, OrarEntry.COLUMN_NAME, OrarEntry.COLUMN_URL);
    public static final String SQL_DELETE = String.format("DROP TABLE IF EXISTS %s",OrarEntry.TABLE_NAME);
    public OrarDbHelper(Context context) {
        super(context, DATABASE_NAME , null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(SQL_DELETE);
        onCreate(sqLiteDatabase);
    }

    public static long addOrarEntry(SQLiteDatabase db, OrarEntry entry)
    {
        return db.replace(OrarEntry.TABLE_NAME,null,entry.getContentValues());
    }

    public static Cursor getAllOrarEntries(SQLiteDatabase db)
    {
        return db.query(OrarEntry.TABLE_NAME, //table name
                new String[]{OrarEntry.COLUMN_NAME, OrarEntry.COLUMN_URL}, //selected rows
                null, //where clause
                null, //arguments for where clause
                null, //do not group results
                null, //do not filter the groups
                null  //do not order the results
        );

    }
}
