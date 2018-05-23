package masterung.androidthai.in.th.daikinaircontrol.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyManage {

    private Context context;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MyManage(Context context) {
        this.context = context;

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }

    public long addValue(String nameString,
                         String ipAddressString,
                         String macAddressString) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", nameString);
        contentValues.put("IpAddress", ipAddressString);
        contentValues.put("MacAddress", macAddressString);
        return sqLiteDatabase.insert("airTABLE", null, contentValues);

    }

}
