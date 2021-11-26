package com.example.dbData.Model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class dbWorker extends SQLiteOpenHelper {

    // existing tables
    public static final String TABLE_COMPANY = "Company";
    public static final String TABLE_PRODUCT = "Product";
    public static final String TABLE_FOUNDER = "Founder";
    public static final String COMPANY_FOUNDER = "Company_Founder";
    public static final String PRODUCT_COMPANY = "Product_Company";

    // columns
    public static final String id = "id";
    public static final String Name = "Name";
    public static final String CName = "CName";
    public static final String FName = "FName";
    public static final String PName = "PName";
    public static final String founder_id = "id_founder";
    public static final String company_id = "id_company";
    public static final String product_id = "id_product";

    //DataBase Information
    static final String DB_NAME="maker2.db";

    // database version
    static final int DB_VERSION = 1;

    // creating a constructor for our database handler.
    public dbWorker(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // ArrayList<Note>

    // PIA vpn
    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String query = "CREATE TABLE " + TABLE_COMPANY + " " +
                "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CName + " TEXT UNIQUE)";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_FOUNDER + " " +
                "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FName + " TEXT UNIQUE)";
        db.execSQL(query);

        query =  "CREATE TABLE " + TABLE_PRODUCT + " " +
                "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PName + " TEXT UNIQUE)";
        db.execSQL(query);

        query =  "CREATE TABLE " + COMPANY_FOUNDER + " " +
                "(" + founder_id + " INTEGER, "
                + company_id + " INTEGER, "
                + "FOREIGN KEY (" + founder_id + ") REFERENCES " + TABLE_FOUNDER + " (" + id + "), "
                + "FOREIGN KEY (" + company_id + ") REFERENCES " + TABLE_COMPANY + "(" + id + "))";
        db.execSQL(query);

        query =  "CREATE TABLE " + PRODUCT_COMPANY + " " +
                "(" + product_id + " INTEGER, "
                + company_id + " INTEGER, "
                + "FOREIGN KEY (" + product_id + ") REFERENCES " + TABLE_PRODUCT + " (" + id + "), "
                + "FOREIGN KEY (" + company_id + ") REFERENCES " + TABLE_COMPANY + "(" + id + "))";
        db.execSQL(query);


    }

    public ArrayList<Note> onStartFilling()
    {
        ArrayList<Note> notesList = new ArrayList<>();

        int cValue;
        int fValue;
        int pValue;
        int counter = 0;

        try {

            String selectQuery = "SELECT " + CName + "," + PName + "," + FName + " FROM " + TABLE_COMPANY
                    + " JOIN " + COMPANY_FOUNDER + " ON " + TABLE_COMPANY + ".id = " + COMPANY_FOUNDER + "." + company_id
                    + " JOIN " + TABLE_FOUNDER + " ON " + COMPANY_FOUNDER + "." + founder_id + " = " + TABLE_FOUNDER + ".id"
                    + " JOIN " + PRODUCT_COMPANY + " ON " + TABLE_COMPANY + ".id" + " = " + PRODUCT_COMPANY + "." + company_id
                    + " JOIN " + TABLE_PRODUCT + " ON " + PRODUCT_COMPANY + "." + product_id + " = " + TABLE_PRODUCT + ".id";


            SQLiteDatabase db = this.getReadableDatabase();
            @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {
                    cValue = c.getColumnIndex(CName);
                    fValue = c.getColumnIndex(FName);
                    pValue = c.getColumnIndex(PName);

                    Note note = new Note(c.getString(cValue).toString(), c.getString(fValue).toString(), c.getString(pValue).toString());
                    notesList.add(counter, note);

                    counter += 1;

                } while (c.moveToNext());
            }

            return notesList;
        }

        catch (Exception ex)
        {
            Log.e("Insert to db:", "Error while load data!");
            return null;
        }

    }

    public void updateCompany(String companyName, String newCompanyName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CName", newCompanyName); //These Fields should be your String values of actual column names

        db.update(TABLE_COMPANY, cv, "CName = ?", new String[]{companyName});

        db.close();
    }

    public void updateFounder(String founderName, String renameFounder)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("FName", renameFounder); //These Fields should be your String values of actual column names

        db.update(TABLE_FOUNDER, cv, "FName = ?", new String[]{founderName});

        db.close();
    }

    public void updateProduct(String productName, String renameProduct)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("PName", renameProduct); //These Fields should be your String values of actual column names

        db.update(TABLE_PRODUCT, cv, "PName = ?", new String[]{productName});

        db.close();
    }

    public void addNewFounder(String data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FName, data);
        long res = db.insert(TABLE_FOUNDER, null, values);

        db.close();

        if (res == -1)
            Log.e("Insert to db:", "Error while input Founder!");
        else
            Log.d("Insert to db:", "Founder input completed!");
    }

    public void addNewCompany(String data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(CName, data);
            long res = db.insert(TABLE_COMPANY, null, values);

            Log.d("Insert to db:", "Company input completed!");

            db.close();
        }catch (Exception ex)
        {
            Log.e("Insert to db:", "Error while input Company!");
        }

    }

    public void addNewProduct(String data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PName, data);
        long res = db.insert(TABLE_PRODUCT, null, values);

        db.close();


        if (res == -1)
            Log.e("Insert to db:", "Error while input Product!");
        else
            Log.d("Insert to db:", "Product input completed!");
    }

    public void deleteCompareCompanyNote(String companyName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String idCompany = "0";
        Cursor cursorCompany = db.query(TABLE_COMPANY, null, "cname=?",
                new String[]{companyName}, null, null, null);

        if (cursorCompany.moveToFirst()) {
            do {
                idCompany = cursorCompany.getString((cursorCompany.getColumnIndex(id)));
            } while (cursorCompany.moveToNext());
        }
        cursorCompany.close();

        try {
            db.delete(COMPANY_FOUNDER, company_id + "=?", new String[]{idCompany});
            db.delete(PRODUCT_COMPANY, company_id + "=?", new String[]{idCompany});
        } catch (Exception ex)
        {
            Log.e("Removing items from compare:", "DB not contains this values or we have errors!");
        }

        db.close();
    }

    public void deleteCompareFounderNote(String founderName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String idFounder = "0";
        Cursor cursorFounder = db.query(TABLE_FOUNDER, null, "fname=?",
                new String[]{founderName}, null, null, null);


        if (cursorFounder.moveToFirst()) {
            do {
                idFounder = cursorFounder.getString((cursorFounder.getColumnIndex(id)));
            } while (cursorFounder.moveToNext());
        }
        cursorFounder.close();


        try {
            db.delete(COMPANY_FOUNDER, founder_id + "=?", new String[]{idFounder});
        } catch (Exception ex)
        {
            Log.e("Removing items from compare:", "DB not contains this values or we have errors!");
        }
        db.close();
    }

    public void deleteCompareProductNote(String productName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String idProduct = "0";
        Cursor cursorProduct = db.query(TABLE_PRODUCT, null, "pname=?",
                new String[]{productName}, null, null, null);

        if (cursorProduct.moveToFirst()) {
            do {
                idProduct = cursorProduct.getString((cursorProduct.getColumnIndex(id)));
            } while (cursorProduct.moveToNext());
        }
        cursorProduct.close();

        try {
            db.delete(PRODUCT_COMPANY, product_id + "=?", new String[]{idProduct});
        } catch (Exception ex)
        {
            Log.e("Removing items from compare:", "DB not contains this values or we have errors!");

        }
        db.close();
    }

    public void deleteCompany(String companyName) {

        deleteCompareCompanyNote(companyName);
        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_COMPANY, "cname=?", new String[]{companyName});
        db.close();
    }

    public void deleteProduct(String productName) {

        deleteCompareProductNote(productName);
        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_PRODUCT, "pname=?", new String[]{productName});
        db.close();
    }

    public void deleteFounder(String founder) {

        deleteCompareFounderNote(founder);
        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_FOUNDER, "fname=?", new String[]{founder});
        db.close();
    }

    public void addNewNote(String companyName, String founder, String product)
    {
        addNewFounder(founder);
        addNewCompany(companyName);
        addNewProduct(product);
    }

    @SuppressLint("Range")
    public List<String> getCompanyNames()
    {
        List<String> getCompany = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_COMPANY;

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to tags list
                getCompany.add(c.getString(c.getColumnIndex(CName)));
            } while (c.moveToNext());
        }

        db.close();
        return getCompany;
    }

    @SuppressLint("Range")
    public List<String> getFounderNames()
    {
        List<String> getFounder = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_FOUNDER;

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to tags list
                getFounder.add(c.getString(c.getColumnIndex(FName)));
            } while (c.moveToNext());
        }

        db.close();
        return getFounder;
    }

    @SuppressLint("Range")
    public List<String> getProductNames()
    {
        List<String> getProducts = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to tags list
                getProducts.add(c.getString(c.getColumnIndex(PName)));
            } while (c.moveToNext());
        }

        db.close();
        return getProducts;
    }

    public void compareCompanyFounder(String companyName, String Founder)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String idCompany = "";
        Cursor cursorCompany = db.query(TABLE_COMPANY, null, "cname=?",
                new String[]{companyName}, null, null, null);

        if (cursorCompany.moveToFirst()) {
            do {
                idCompany = cursorCompany.getString((cursorCompany.getColumnIndex(id)));
            } while (cursorCompany.moveToNext());
        }
        cursorCompany.close();


        String idFounder = "";
        Cursor cursorFounder = db.query(TABLE_FOUNDER, null, "fname=?",
                new String[]{Founder}, null, null, null);

        if (cursorFounder.moveToFirst()) {
            do {
                idFounder = cursorFounder.getString((cursorFounder.getColumnIndex(id)));
            } while (cursorFounder.moveToNext());
        }
        cursorFounder.close();

        String select = "SELECT * FROM " + COMPANY_FOUNDER + " WHERE " + company_id + " = ? AND " + founder_id + " = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select, new String[]{idCompany, idFounder});

        if (cursor.getCount() == 0) {

            ContentValues values = new ContentValues();
            values.put(founder_id, idFounder);
            values.put(company_id, idCompany);
            long res = db.insert(COMPANY_FOUNDER, null, values);


            if (res == -1)
                Log.e("Insert to db:", "Error while input Company_Founder!");
            else
                Log.d("Insert to db:", "Company_Founder input completed!");

        } else if (cursor.getCount() > 0){
            Log.d("Insert to db:", "This note already exist: Company_Founder");
        }

        db.close();


    }

    public void compareProductCompany(String companyName, String Product)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String idCompany = "";
        Cursor cursorCompany = db.query(TABLE_COMPANY, null, "cname=?",
                new String[]{companyName}, null, null, null);
        if (cursorCompany.moveToFirst()) {
            do {
                 idCompany = cursorCompany.getString((cursorCompany.getColumnIndex(id)));
            } while (cursorCompany.moveToNext());
        }
        cursorCompany.close();


        String idProduct = "";
        Cursor cursorProduct = db.query(TABLE_PRODUCT, null, "pname=?",
                new String[]{Product}, null, null, null);
        if (cursorProduct.moveToFirst()) {
            do {
                idProduct = cursorProduct.getString((cursorProduct.getColumnIndex(id)));
            } while (cursorProduct.moveToNext());
        }
        cursorProduct.close();


        String select = "SELECT * FROM " + PRODUCT_COMPANY + " WHERE " + company_id + " = ? AND " + product_id + " = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select, new String[]{idCompany, idProduct});

        if (cursor.getCount() == 0) {

            ContentValues values = new ContentValues();
            values.put(product_id, idProduct);
            values.put(company_id, idCompany);
            long res = db.insert(PRODUCT_COMPANY, null, values);

            if (res == -1)
                Log.e("Insert to db:", "Error while input Product_Company!");
            else
                Log.d("Insert to db:", "Product_Company input completed!");

        } else if (cursor.getCount() > 0){
            Log.d("Insert to db:", "This note already exist: Product_Company");
        }

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOUNDER);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + COMPANY_FOUNDER);

        onCreate(db);
    }


}
