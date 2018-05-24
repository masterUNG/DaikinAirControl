package masterung.androidthai.in.th.daikinaircontrol.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import masterung.androidthai.in.th.daikinaircontrol.MainActivity;
import masterung.androidthai.in.th.daikinaircontrol.R;
import masterung.androidthai.in.th.daikinaircontrol.utility.MyManage;
import masterung.androidthai.in.th.daikinaircontrol.utility.MyOpenHelper;

public class EditAndDeleteFragment extends Fragment {

    //    Explicit
    private String idString, nameString, ipAddressString, macAddressString;
    private EditText nameEditText, ipAddressEditText, macAddressEditText;


    public static EditAndDeleteFragment editAndDeleteInstance(String idString,
                                                              String nameString,
                                                              String ipAddressString,
                                                              String macAddressString) {

        EditAndDeleteFragment editAndDeleteFragment = new EditAndDeleteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", idString);
        bundle.putString("Name", nameString);
        bundle.putString("IpAddress", ipAddressString);
        bundle.putString("MacAddress", macAddressString);
        editAndDeleteFragment.setArguments(bundle);
        return editAndDeleteFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        GetValue Argument
        getValueArgument();

//        Show View
        showView();

//        Create Toolbar
        createToolbar();


    }   // Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemDelete) {
            deleteItem();
            return true;
        }

        if (item.getItemId() == R.id.itemEdit) {
            editItem();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void editItem() {

        nameString = nameEditText.getText().toString().trim();
        ipAddressString = ipAddressEditText.getText().toString().trim();
        macAddressString = macAddressEditText.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_name);
        builder.setTitle("Confirm Edit");
        builder.setMessage("Name = " + nameString + "\n" +
                "IP Address = " + ipAddressString + "\n" +
                "mac Address = " + macAddressString);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SQLiteDatabase sqLiteDatabase = getActivity()
                        .openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING,
                                Context.MODE_PRIVATE, null);

                ContentValues contentValues = new ContentValues();
                contentValues.put("Name", nameString);
                contentValues.put("IpAddress", ipAddressString);
                contentValues.put("MacAddress", macAddressString);

                sqLiteDatabase.update("airTABLE", contentValues,
                        "id" + "=" + idString, null);


                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new ListAirFragment())
                        .commit();


                dialog.dismiss();


            }   //onClick
        });
        builder.show();


    }

    private void deleteItem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_name);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Do You Want to Delete Item ?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                myDelete();

                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new ListAirFragment())
                        .commit();

                dialog.dismiss();

            }   // onClick
        });

        builder.show();


    }

    private void myDelete() {
        SQLiteDatabase sqLiteDatabase = getActivity()
                .openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING,
                        Context.MODE_PRIVATE, null);
        sqLiteDatabase.delete("airTABLE",
                "id" + "=" + idString, null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_edit_and_delete, menu);

    }

    private void createToolbar() {

        Toolbar toolbar = getView().findViewById(R.id.toolbarAddAir);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(nameString);
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Edit and Delete");

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        setHasOptionsMenu(true);


    }

    private void showView() {

        nameEditText = getView().findViewById(R.id.edtName);
        ipAddressEditText = getView().findViewById(R.id.edtIPAddress);
        macAddressEditText = getView().findViewById(R.id.edtMacAddress);

        nameEditText.setText(nameString);
        ipAddressEditText.setText(ipAddressString);
        macAddressEditText.setText(macAddressString);

    }

    private void getValueArgument() {
        idString = getArguments().getString("id");
        nameString = getArguments().getString("Name");
        ipAddressString = getArguments().getString("IpAddress");
        macAddressString = getArguments().getString("MacAddress");


        Log.d("24MayV1", "Mac on Edit ==> " + macAddressString);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_air, container, false);
        return view;
    }
}
