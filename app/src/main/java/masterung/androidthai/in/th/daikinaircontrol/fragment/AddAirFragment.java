package masterung.androidthai.in.th.daikinaircontrol.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import masterung.androidthai.in.th.daikinaircontrol.MainActivity;
import masterung.androidthai.in.th.daikinaircontrol.R;
import masterung.androidthai.in.th.daikinaircontrol.utility.MyAlert;
import masterung.androidthai.in.th.daikinaircontrol.utility.MyManage;

public class AddAirFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();


    }   // Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itmeSave) {
//            To Do
            checkAndSave();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkAndSave() {

        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText ipAddressEditText = getView().findViewById(R.id.edtIPAddress);
        EditText macAddressEditText = getView().findViewById(R.id.edtMacAddress);

        String nameString = nameEditText.getText().toString().trim();
        String ipAddressString = ipAddressEditText.getText().toString().trim();
        String macAddressString = macAddressEditText.getText().toString().trim();

        if (nameString.isEmpty() || ipAddressString.isEmpty()) {

//            Have Space
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.normalDialog("Have Space", "Please Fill All Blank");

        } else {

//            No Space
            MyManage myManage = new MyManage(getActivity());
            myManage.addValue(nameString, ipAddressString, macAddressString);
            getActivity().getSupportFragmentManager().popBackStack();

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_air, menu);
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarAddAir);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Add Aircondition");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please Fill All Blank");

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_air, container, false);
        return view;
    }
}
