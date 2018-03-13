package au.com.nextdot.expandablelistview;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by sakib on 3/13/2018.
 */

class GroupInfo {

    private String name;
    private ArrayList<ChildInfo> list = new ArrayList<ChildInfo>();

    public String getName() {
        Log.d("getGroup",name);
        return name;
    }

    public void setName(String name) {
        Log.d("setGroup",name);
        this.name = name;
    }

    public ArrayList<ChildInfo> getProductList() {

        return list;
    }

    public void setProductList(ArrayList<ChildInfo> productList) {

        this.list = productList;
    }

}
