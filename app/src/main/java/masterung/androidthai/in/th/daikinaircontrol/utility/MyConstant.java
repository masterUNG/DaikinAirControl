package masterung.androidthai.in.th.daikinaircontrol.utility;

public class MyConstant {

//    private String urlInfoString = "http://192.168.1.108/aircon/get_control_info";

    private String urlInfoString = "/aircon/get_control_info";

    private String[] fRateStrings = new String[]{"Auto", "Silent", "3", "4", "5", "6", "7"};

//    3,4,5,6,7,A,B


    public String[] getfRateStrings() {
        return fRateStrings;
    }

    public String getUrlInfoString() {
        return urlInfoString;
    }
}   // Main Class
