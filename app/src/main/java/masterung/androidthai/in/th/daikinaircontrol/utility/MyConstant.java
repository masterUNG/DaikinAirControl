package masterung.androidthai.in.th.daikinaircontrol.utility;

public class MyConstant {

//    private String urlInfoString = "http://192.168.1.108/aircon/get_control_info";

    private String urlInfoString = "/aircon/get_control_info";
    private String urlSetPowerString = "/aircon/set_control_info?pow=";
    private String urlSetModeString = "/aircon/set_control_info?mode=";

    private String[] fRateStrings = new String[]{"Auto", "Silent", "3", "4", "5", "6", "7"};
    private String[] fDirStrings = new String[]{"off", "Ver", "Horizon", "3D"};


    public String getUrlSetModeString() {
        return urlSetModeString;
    }

    public String getUrlSetPowerString() {
        return urlSetPowerString;
    }

    public String[] getfDirStrings() {
        return fDirStrings;
    }

    public String[] getfRateStrings() {
        return fRateStrings;
    }

    public String getUrlInfoString() {
        return urlInfoString;
    }
}   // Main Class
