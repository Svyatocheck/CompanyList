package com.example.dbData.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dbData.Model.Note;
import com.example.dbData.Model.dbWorker;
import com.example.dbData.R;

import java.util.ArrayList;
import java.util.List;


public class EditPage extends AppCompatActivity implements View.OnClickListener {

    Spinner companyName;
    Spinner founder;
    Spinner product;

    ImageView addItemProduct;
    ImageView addItemCompany;
    ImageView addItemFounder;

    ImageView removeItemProduct;
    ImageView removeItemCompany;
    ImageView removeItemFounder;

    ImageView editItemProduct;
    ImageView editItemCompany;
    ImageView editItemFounder;

    Button btnOk;
    Button backBtn;
    ConstraintLayout constraint;

    String position = "";
    String m_Text = "";

    List<String> getCompany = new ArrayList<String>();
    List<String> getFounders = new ArrayList<String>();
    List<String> getProducts = new ArrayList<String>();

    private dbWorker dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
// ========================== Elelements on screen {
        constraint = (ConstraintLayout) findViewById(R.id.constraint);

        companyName = (Spinner) findViewById(R.id.compName);
        founder = (Spinner)findViewById(R.id.founder) ;
        product = (Spinner)findViewById(R.id.product) ;

        btnOk = (Button)findViewById(R.id.btnOk);
        backBtn =(Button)findViewById(R.id.backBtn);
        btnOk.setOnClickListener(this);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addItemFounder = (ImageView) findViewById(R.id.addItemFounder);
        addItemCompany = (ImageView) findViewById(R.id.addItemCompany);
        addItemProduct = (ImageView) findViewById(R.id.addItemProduct);

        removeItemFounder = (ImageView) findViewById(R.id.removeItemFounder);
        removeItemCompany = (ImageView) findViewById(R.id.removeItemCompany);
        removeItemProduct = (ImageView) findViewById(R.id.removeItemProduct);

        editItemFounder = (ImageView) findViewById(R.id.editItemFounder);
        editItemCompany = (ImageView) findViewById(R.id.editItemCompany);
        editItemProduct = (ImageView) findViewById(R.id.editItemProduct);

//================= Some magic with interface
        dbHandler = new dbWorker(EditPage.this);

        getCompany = dbHandler.getCompanyNames();
        ArrayAdapter<String> companyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getCompany);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companyName.setAdapter(companyAdapter);

        getFounders = dbHandler.getFounderNames();
        ArrayAdapter<String> founderAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getFounders);
        founderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        founder.setAdapter(founderAdapter);

        getProducts = dbHandler.getProductNames();
        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getProducts);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product.setAdapter(productAdapter);



        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        addItemCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add button pressed!", Toast.LENGTH_SHORT).show();
                builder.setTitle("Add new Company");

                // Set up the input
                EditText input = new EditText(EditPage.this);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        dbHandler.addNewCompany(m_Text);
                        m_Text = "";

                        try {
                            getCompany = dbHandler.getCompanyNames();
                            companyAdapter.clear();
                            companyAdapter.addAll(getCompany);
                            companyAdapter.notifyDataSetChanged();
                        } catch (Exception ignore){}
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }});

        addItemFounder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add button pressed!", Toast.LENGTH_SHORT).show();
                builder.setTitle("Add new Founder");

                // Set up the input
                EditText input = new EditText(EditPage.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType. TYPE_TEXT_VARIATION_PERSON_NAME);

                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        dbHandler.addNewFounder(m_Text);
                        m_Text = "";

                        try {
                            getFounders = dbHandler.getFounderNames();
                            founderAdapter.clear();
                            founderAdapter.addAll(getFounders);
                            founderAdapter.notifyDataSetChanged();
                        } catch (Exception ignore){}
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }});

        addItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add button pressed!", Toast.LENGTH_SHORT).show();
                builder.setTitle("Add new Product");

                // Set up the input
                EditText input = new EditText(EditPage.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT |  InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        dbHandler.addNewProduct(m_Text);
                        m_Text = "";

                        try {
                            getProducts = dbHandler.getProductNames();
                            productAdapter.clear();
                            productAdapter.addAll(getProducts);
                            productAdapter.notifyDataSetChanged();
                        } catch (Exception ignore){}
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }});


        AlertDialog.Builder removeBuilder = new AlertDialog.Builder(this);

        removeItemCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Remove button pressed!", Toast.LENGTH_SHORT).show();
                removeBuilder.setTitle("Remove Company");

                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

                // Set up the input
                Spinner input = new Spinner(EditPage.this);

                ArrayAdapter<String> inputAdapter = new ArrayAdapter<String>(EditPage.this,
                        android.R.layout.simple_spinner_item, getCompany);
                inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                input.setAdapter(inputAdapter);

                removeBuilder.setView(input);

                // Set up the buttons
                removeBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getSelectedItem().toString();
                        dbHandler.deleteCompany(m_Text);
                        m_Text = "";

                        try {
                            getCompany = dbHandler.getCompanyNames();
                            companyAdapter.clear();
                            companyAdapter.addAll(getCompany);
                            companyAdapter.notifyDataSetChanged();
                        } catch (Exception ignore){}
                    }
                });

                removeBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                removeBuilder.show();
            }
        });

        removeItemFounder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Remove button pressed!", Toast.LENGTH_SHORT).show();
                removeBuilder.setTitle("Remove Founder");

                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

                // Set up the input
                Spinner input = new Spinner(EditPage.this);

                ArrayAdapter<String> inputAdapter = new ArrayAdapter<String>(EditPage.this,
                        android.R.layout.simple_spinner_item, getFounders);
                inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                input.setAdapter(inputAdapter);

                removeBuilder.setView(input);

                // Set up the buttons
                removeBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getSelectedItem().toString();
                        dbHandler.deleteFounder(m_Text);
                        m_Text = "";

                        try {
                            getFounders = dbHandler.getFounderNames();
                            founderAdapter.clear();
                            founderAdapter.addAll(getFounders);
                            founderAdapter.notifyDataSetChanged();
                        } catch (Exception ignore){}
                    }
                });

                removeBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                removeBuilder.show();
            }
        });

        removeItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Remove button pressed!", Toast.LENGTH_SHORT).show();
                removeBuilder.setTitle("Remove Product");

                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

                // Set up the input
                Spinner input = new Spinner(EditPage.this);

                ArrayAdapter<String> inputAdapter = new ArrayAdapter<String>(EditPage.this,
                        android.R.layout.simple_spinner_item, getProducts);
                inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                input.setAdapter(inputAdapter);

                removeBuilder.setView(input);

                // Set up the buttons
                removeBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getSelectedItem().toString();
                        dbHandler.deleteProduct(m_Text);
                        m_Text = "";

                        try {
                            getProducts = dbHandler.getProductNames();
                            productAdapter.clear();
                            productAdapter.addAll(getProducts);
                            productAdapter.notifyDataSetChanged();
                        } catch (Exception ignore){}
                    }
                });

                removeBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                removeBuilder.show();
            }
        });

        AlertDialog.Builder updateBuilder = new AlertDialog.Builder(this);

        editItemCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add button pressed!", Toast.LENGTH_SHORT).show();
                updateBuilder.setTitle("Edit company");

                // Set up the input
                EditText input = new EditText(EditPage.this);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                updateBuilder.setView(input);

                // Set up the buttons
                updateBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        dbHandler.updateCompany(companyName.getSelectedItem().toString(), m_Text);
                        m_Text = "";

                        try {
                            getCompany = dbHandler.getCompanyNames();
                            companyAdapter.clear();
                            companyAdapter.addAll(getCompany);
                            companyAdapter.notifyDataSetChanged();
                        } catch (Exception ignore){}
                    }
                });

                updateBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                updateBuilder.show();
            }
        });

        editItemFounder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add button pressed!", Toast.LENGTH_SHORT).show();
                updateBuilder.setTitle("Edit founder");

                // Set up the input
                EditText input = new EditText(EditPage.this);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                updateBuilder.setView(input);

                // Set up the buttons
                updateBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        dbHandler.updateFounder(founder.getSelectedItem().toString(), m_Text);
                        m_Text = "";

                        try {
                            getFounders = dbHandler.getFounderNames();
                            founderAdapter.clear();
                            founderAdapter.addAll(getFounders);
                            founderAdapter.notifyDataSetChanged();
                        } catch (Exception ignore){}
                    }
                });

                updateBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                updateBuilder.show();
            }
        });

        editItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add button pressed!", Toast.LENGTH_SHORT).show();
                updateBuilder.setTitle("Edit product");

                // Set up the input
                EditText input = new EditText(EditPage.this);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                updateBuilder.setView(input);

                // Set up the buttons
                updateBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        dbHandler.updateProduct(product.getSelectedItem().toString(), m_Text);
                        m_Text = "";

                        try {
                            getProducts = dbHandler.getProductNames();
                            productAdapter.clear();
                            productAdapter.addAll(getProducts);
                            productAdapter.notifyDataSetChanged();
                        } catch (Exception ignore){}
                    }
                });

                updateBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                updateBuilder.show();
            }
        });

        try {
            getInfo();
//            companyName.setEnabled(false);
//
//            editItemCompany.setEnabled(false);
//            addItemCompany.setEnabled(false);
//            removeItemCompany.setEnabled(false);
        } catch (Exception ignore)
        {}

    }

    public void hideKeyboard(View v)
    {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }
    
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i = 0;i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    protected void getInfo()
    {
        Intent i = getIntent();
        Note note = (Note) i.getSerializableExtra("note");

        //Log.d("myTag", note.getName());
        companyName.setSelection(getIndex(companyName, note.getCompanyName()));
        founder.setSelection(getIndex(founder, note.getFounder()));
        product.setSelection(getIndex(product, note.getProduct()));
        
        position = i.getStringExtra("position");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        String name = "";
        String person = "";
        String production = "";

        try {
            name = companyName.getSelectedItem().toString();
            person = founder.getSelectedItem().toString() ;
            production = product.getSelectedItem().toString();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "The fields were not filled in...", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, null);
            finish();
        }

        if (!name.equals("") && !person.equals("") && !production.equals("")) {

            if (!position.isEmpty()) {
                // Значит элемент в режиме редактирования
                intent.putExtra("position", position);
                position = "";
            }

            intent.putExtra("company", name);
            intent.putExtra("founder", person);
            intent.putExtra("product", production);

            setResult(RESULT_OK, intent);

        } else {
            Toast.makeText(this, "The fields were not filled in...", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, null);
        }

        finish();
    }

}