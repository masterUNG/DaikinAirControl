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
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import masterung.androidthai.in.th.daikinaircontrol.MainActivity;
import masterung.androidthai.in.th.daikinaircontrol.R;
import masterung.androidthai.in.th.daikinaircontrol.utility.MyConstant;
import masterung.androidthai.in.th.daikinaircontrol.utility.PostData;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class ControlFragment extends Fragment {

    //    Explicit
    private int powerAnInt = 0;
    private String idString, nameString, ipAddressString, macAddressString;
    private String contentIOTString, powString, modeString, stempString, f_rateString, f_dirString;

    private SwitchCompat aSwitch;


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

    private void fanRateController() {

        int indexInt = 6;

        if (f_rateString.equals("A")) {
            indexInt = 0;
        } else if (f_rateString.equals("B")) {
            indexInt = 1;
        } else if (f_rateString.equals("3")) {
            indexInt = 2;
        } else if (f_rateString.equals("4")) {
            indexInt = 3;
        } else if (f_rateString.equals("5")) {
            indexInt = 4;
        } else if (f_rateString.equals("6")) {
            indexInt = 5;
        } else {
            indexInt = 6;
        }

        Spinner spinner = getView().findViewById(R.id.spinnerRate);
        MyConstant myConstant = new MyConstant();
        String[] strings = myConstant.getfRateStrings();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setSelection(indexInt);


    }

    private void getDataIOT() {

        try {

            PostData postData = new PostData(getActivity());
            postData.execute(contentIOTString);

            String jsonString = postData.get();

            jsonString = "[" + jsonString + "]";
            Log.d("24MayV2", "json ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String paramString = jsonObject.getString("param");
            paramString = "[" + paramString + "]";
            Log.d("24MayV2", "paramString ==> " + paramString);

            JSONArray jsonArray1 = new JSONArray(paramString);
            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

            powString = jsonObject1.getString("pow");
            f_rateString = jsonObject1.getString("f_rate");
            f_dirString = jsonObject1.getString("f_dir");
            stempString = jsonObject1.getString("stemp");
            modeString = jsonObject1.getString("mode");

            Log.d("24MayV2", "pow ==> " + powString);
            Log.d("24MayV2", "f_rate ==> " + f_rateString);
            Log.d("24MayV2", "f_dir ==> " + f_dirString);
            Log.d("24MayV2", "stemp ==> " + stempString);
            Log.d("24MayV2", "mode ==> " + modeString);

            //Show Power
            aSwitch = getView().findViewById(R.id.switchOnOff);
            if (Integer.parseInt(powString.trim()) == 1) {
                Log.d("24MayV3", "ON status");
                aSwitch.setChecked(true);
            } else {
                Log.d("24MayV3", "OFF status");
                aSwitch.setChecked(false);
            }

//            Show Mode
            String[] modeStrings = new String[]{"FAN", "Cool", "DRY"};
            TextView textView = getView().findViewById(R.id.txtMode);
            textView.setText(modeStrings[Integer.parseInt(modeString.trim())]);

//            FanRate Controller
            fanRateController();

//            FanDir Cintroller
            fanDirCintroller();

//            Show Temp
            showTemp();

        } catch (Exception e) {

            Log.d("24MayV3", "e ==> " + e.toString());
        }

    }

    private void showTemp() {
        TextView textView1 = getView().findViewById(R.id.txtTemp);
        textView1.setText(stempString+ " " + getString(R.string.unit_temp));

        CircularSeekBar circularSeekBar = getView().findViewById(R.id.circularSeekbar);



        float currentFloat = (float) ((Float.parseFloat(stempString.trim()) - 18) * 6.666667);

        circularSeekBar.setProgress(currentFloat);

        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                Log.d("24MayV4", "Porgress ==> " + progress);
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });

    }

    private void fanDirCintroller() {

        Spinner spinner = getView().findViewById(R.id.spinnerDir);
        MyConstant myConstant = new MyConstant();
        String[] strings = myConstant.getfDirStrings();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setSelection(Integer.parseInt(f_dirString.trim()));


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



        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyConstant myConstant = new MyConstant();

                if (aSwitch.isChecked()) {
                    powerAnInt = 1;
                } else {
                    powerAnInt = 0;
                }

                Log.d("21MayV1", "powerAnInt ==> " + powerAnInt);

                String urlOnOffString = "http://" + ipAddressString +
                        myConstant.getUrlSetPowerString() + Integer.toString(powerAnInt);

                Log.d("24MayV5", "urlPower ==> " + urlOnOffString);

//                MyThread IOT
                myThreadIOT(urlOnOffString);


            }   // onClick
        });






    }   // onOff

    private void myThreadIOT(String urlString) {

        try {

            PostData postData = new PostData(getActivity());
            postData.execute(urlString);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        return view;
    }
}
