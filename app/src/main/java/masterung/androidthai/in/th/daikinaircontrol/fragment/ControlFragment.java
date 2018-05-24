package masterung.androidthai.in.th.daikinaircontrol.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import masterung.androidthai.in.th.daikinaircontrol.MainActivity;
import masterung.androidthai.in.th.daikinaircontrol.R;
import masterung.androidthai.in.th.daikinaircontrol.utility.MyConstant;
import masterung.androidthai.in.th.daikinaircontrol.utility.PostData;

public class ControlFragment extends Fragment {

    //    Explicit
    private int powerAnInt = 0;
    private String urlAir = "http://192.168.1.108/aircon/set_control_info?";
    private String prePower = "pow=";
    private String idString, nameString, ipAddressString, macAddressString;
    private String contentIOTString, powString, modeString, stempString, f_rateString, f_dirString;

    public static ControlFragment controlInstance(String idString,
                                                  String nameString,
                                                  String ipAddressString,
                                                  String macAddressString) {

        ControlFragment controlFragment = new ControlFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", idString);
        bundle.putString("Name", nameString);
        bundle.putString("IpAddress", ipAddressString);
        bundle.putString("MacAddress", macAddressString);
        controlFragment.setArguments(bundle);
        return controlFragment;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        GetValue Argument
        getValueArgument();

//        GetData IOT
        getDataIOT();

//        Create Toolbar
        createToolbar();


//        OnOff Controller
        onOffController();


    }   // Main Method

    private void getDataIOT() {

        try {

            PostData postData = new PostData(getActivity());
            postData.execute(contentIOTString);

            String jsonString = postData.get();
            Log.d("24MayV2", "json ==> " + jsonString);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemSetup) {
//            To Do
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMainFragment,
                            EditAndDeleteFragment.editAndDeleteInstance(idString, nameString, ipAddressString, macAddressString))
                    .addToBackStack(null)
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_control, menu);

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarControl);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(nameString);
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(ipAddressString);

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

    private void getValueArgument() {
        idString = getArguments().getString("id");
        nameString = getArguments().getString("Name");
        ipAddressString = getArguments().getString("IpAddress");
        macAddressString = getArguments().getString("MacAddress");

        MyConstant myConstant = new MyConstant();
        contentIOTString = "http://" + ipAddressString + myConstant.getUrlInfoString();
        Log.d("24MayV2", "contentIOT ==> " + contentIOTString);

        Log.d("23MayV1", "id ==> " + idString);
        Log.d("23MayV1", "Name ==> " + nameString);
        Log.d("23MayV1", "IpAddress ==> " + ipAddressString);
        Log.d("23MayV1", "MacAddress ==> " + macAddressString);
    }

    private void onOffController() {

        final SwitchCompat switchCompat = getView().findViewById(R.id.switchOnOff);
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchCompat.isChecked()) {
                    powerAnInt = 1;
                } else {
                    powerAnInt = 0;
                }

                Log.d("21MayV1", "powerAnInt ==> " + powerAnInt);

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        return view;
    }
}
