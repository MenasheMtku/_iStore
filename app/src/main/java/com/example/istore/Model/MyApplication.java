package com.example.istore.Model;

import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyApplication extends Application {

    private static List<Vendor> vendorList = new ArrayList<Vendor>();
    private static int  nextId = 3;

    public MyApplication() {
        fillVendorsList();
    }

    private void fillVendorsList() {

        Vendor v1 = new Vendor(0,"Diplumat","https://שירות-לקוחות.org.il/wp-content/uploads/2020/01/diplomat-%D7%93%D7%99%D7%A4%D7%9C%D7%95%D7%9E%D7%98-%D7%9C%D7%95%D7%92%D7%95.jpg","0543698125");
        Vendor v2 = new Vendor(1,"Tnuva","https://petachtikva.co.il/wp-content/uploads/2019/07/%D7%97%D7%91%D7%A8%D7%AA-%D7%AA%D7%A0%D7%95%D7%91%D7%94-%D7%9C%D7%95%D7%92%D7%95.jpg","0543695252");
        Vendor v3 = new Vendor(2,"Coca-Cola","https://diversityisrael.org.il/wp-content/uploads/1630-864_logo_hevra_merkazit.png","0509198125");

        vendorList.addAll(Arrays.asList(v1,v2,v3));
    }

    public static List<Vendor> getVendorList() {
        return vendorList;
    }

    public static void setVendorList(List<Vendor> vendorList) {
        MyApplication.vendorList = vendorList;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        MyApplication.nextId = nextId;
    }
}
