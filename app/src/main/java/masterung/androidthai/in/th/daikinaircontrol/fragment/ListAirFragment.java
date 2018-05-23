package masterung.androidthai.in.th.daikinaircontrol.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import masterung.androidthai.in.th.daikinaircontrol.MainActivity;
import masterung.androidthai.in.th.daikinaircontrol.R;
import masterung.androidthai.in.th.daikinaircontrol.utility.MyManage;
import masterung.androidthai.in.th.daikinaircontrol.utility.MyOpenHelper;

public class ListAirFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();

//        Create ListView
        createListView();


    }   // Main Class

    private void createListView() {
        MyManage myManage = new MyManage(getActivity());

        try {

            SQLiteDatabase sqLiteDatabase = getActivity()
                    .openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING,
                            Context.MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM airTABLE", null);
            cursor.moveToFirst();

            String[] idStrings = new String[cursor.getCount()];
            String[] nameStrings = new String[cursor.getCount()];
            String[] ipAddressStrings = new String[cursor.getCount()];
            String[] macAddressStrings = new String[cursor.getCount()];

            for (int i = 0; i < cursor.getCount(); i += 1) {

                idStrings[i] = cursor.getString(0);
                nameStrings[i] = cursor.getString(1);
                ipAddressStrings[i] = cursor.getString(2);
                macAddressStrings[i] = cursor.getString(3);

                Log.d("23MayV1", "Name[" + i + "] ==> " + nameStrings[i]);
                cursor.moveToNext();

            }

            ListView listView = getView().findViewById(R.id.listViewAir);
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, nameStrings);
            listView.setAdapter(stringArrayAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itmeAddAir) {

//            To Do

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMainFragment, new AddAirFragment())
                    .addToBackStack(null)
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_list_air, menu);

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarListAir);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Air Conditioning");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Dailin Air Condition");

        setHasOptionsMenu(true);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_air, container, false);
        return view;
    }
}




