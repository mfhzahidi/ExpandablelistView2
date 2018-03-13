package au.com.nextdot.expandablelistview;

import android.util.Log;

/**
 * Created by sakib on 3/13/2018.
 */

class ChildInfo {

    private String[] name;



    public String getName(int position) {
        Log.d("getChild",name[position]);
        return name[position];
    }

    public void setName(String[] name) {
        Log.d("setchild",name[0]);
        this.name = name;
    }
    public int getsize()
    {
        //Log.d("here", String.valueOf(name.length));
        return name.length;
    }
    public String[] getList()
    {
        return  name;
    }
}
