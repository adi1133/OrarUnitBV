package ro.epb.orar;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;

import db.OrarEntry;

/**
 * Created by Adi on 11/2/13.
 */
public class OrarListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter mSimpleCursorAdapter;
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
            Contacts._ID,
            Contacts.DISPLAY_NAME,
            Contacts.CONTACT_STATUS,
            Contacts.CONTACT_PRESENCE,
            Contacts.PHOTO_ID,
            Contacts.LOOKUP_KEY,
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("You have no pages being monitored");
        setHasOptionsMenu(false);
        String[] data = {"asd", "dada"};
        data = new String[0];
        setListAdapter(new ArrayAdapter<String>(getActivity(),R.layout.orar_cell, R.id.left, data ));
        mSimpleCursorAdapter = new SimpleCursorAdapter(getActivity(),R.layout.orar_cell,null,new String[]{Contacts.DISPLAY_NAME},new int[]{ R.id.left }, 0);
        setListAdapter(mSimpleCursorAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(
//                getActivity(),
//                OrarEntry.TABLE_NAME, //table name
//                new String[]{OrarEntry.COLUMN_NAME, OrarEntry.COLUMN_URL}, //selected rows
//                null, //where clause
//                null, //arguments for where clause
//                null, //do not group results
//                null, //do not filter the groups
//                null  //do not order the results
//        );
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

        String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + Contacts.DISPLAY_NAME + " != '' ))";
        return new CursorLoader(getActivity(), Contacts.CONTENT_URI,
                CONTACTS_SUMMARY_PROJECTION, select, null,
                Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mSimpleCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mSimpleCursorAdapter.swapCursor(null);
    }
}
