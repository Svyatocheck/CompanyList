package com.example.dbData.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbData.Controller.MyAdapter;
import com.example.dbData.Model.Note;
import com.example.dbData.Model.dbWorker;
import com.example.dbData.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyAdapter.OnNoteListener, MyAdapter.RemoveListener {

    Button addUser;
    private dbWorker dbHandler;

    private ArrayList<Note> notesList = new ArrayList<>();
    ArrayList<Note> helperList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUser = (Button) findViewById(R.id.addNote);
        addUser.setOnClickListener(this);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new dbWorker(MainActivity.this);


        helperList = dbHandler.onStartFilling();

        for (int i = 0; i < helperList.size(); i ++)
        {
            addUser(helperList.get(i).getCompanyName().toString(),
                    helperList.get(i).getFounder().toString(),
                    helperList.get(i).getProduct().toString(), -1);
        }

    }

    // Косяк с созданием новой записи при редактировании блять
    public int findUsingIterator(String name, String founder, String product,
                                     int position, ArrayList<Note> notesList) {
        for (Note note : notesList) {
            if (note.getCompanyName().equals(name))
            {
                return position;
            }
        }

        return -1;
    }

    public void addUser(String companyName, String founder, String product, int position)
    {
        RecyclerView recycler = findViewById(R.id.personsRecycle);
        MyAdapter listAdapter = new MyAdapter(notesList, this, this);
        recycler.setAdapter(listAdapter);

        try {
            //int finder = findUsingIterator(companyName, founder, product, position, notesList);

            if (position > -1)
            {
                notesList.get(position).setCompanyName(companyName);
                notesList.get(position).setFounder(founder);
                notesList.get(position).setProduct(product);

            } else {
                Note note = new Note(companyName, founder, product);
                notesList.add(0, note);

                //dbHandler.addNewNote(companyName, founder, product);
                dbHandler.compareProductCompany(companyName, product);
                dbHandler.compareCompanyFounder(companyName, founder);
            }

        } catch (Exception ignore)
        {}

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditPage.class);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1) {

            String companyName = data.getStringExtra("company");
            String founder = data.getStringExtra("founder");
            String product = data.getStringExtra("product");

            int position;

            try {
                position = Integer.parseInt(data.getStringExtra("position"));
                Toast.makeText(this, "Item updated!!", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                //Toast.makeText(this, "Позиции нема", Toast.LENGTH_SHORT).show();

                Toast.makeText(this, "A new item was created!", Toast.LENGTH_SHORT).show();
                position = -1;
            }

            addUser(companyName, founder, product, position);
        }

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, EditPage.class);
        intent.putExtra("note", notesList.get(position));
        intent.putExtra("position", String.valueOf(position));

        startActivityForResult(intent, 2);
    }


    public void hideKeyboard(View v)
    {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }


    @Override
    public void onRemoveClick(int position) {
       // Log.e("Hello world", String.valueOf(position));
        dbHandler.deleteCompareFounderNote(notesList.get(position).getFounder().toString());
        dbHandler.deleteCompareCompanyNote(notesList.get(position).getCompanyName().toString());
        dbHandler.deleteCompareProductNote(notesList.get(position).getProduct().toString());
    }
}