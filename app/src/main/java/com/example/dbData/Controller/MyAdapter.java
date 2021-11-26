package com.example.dbData.Controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbData.Model.Note;
import com.example.dbData.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PersonHolder> {

    // List to store all the contact details
    private final ArrayList<Note> noteItems;
    private final OnNoteListener mOnNoteListener;
    private final RemoveListener removeListener;

    public MyAdapter(ArrayList<Note> notesList, OnNoteListener onNoteListener,
                     RemoveListener removeListener) {
        this.noteItems = notesList;
        this.mOnNoteListener = onNoteListener;
        this.removeListener = removeListener;
    }

    @Override
    public int getItemCount() {
        return noteItems == null? 0: noteItems.size();
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new PersonHolder(view, mOnNoteListener, removeListener, noteItems);
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, final int position) {
        final Note note = noteItems.get(position);

        // Set the data to the views here
        holder.setCompanyName(note.getCompanyName());
        holder.setFounder(note.getFounder());
        holder.setProduct(note.getProduct());
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

    public interface RemoveListener {
        void onRemoveClick(int position);
    }
    // This is your ViewHolder class that helps to populate data to the view
    public class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView companyName;
        private final TextView founder;
        private final TextView product;

        private final ImageView companyIcon;

        OnNoteListener onNoteListener;
        RemoveListener removeListener;

        public PersonHolder(View itemView, OnNoteListener onNoteListener,
                            RemoveListener removeListener, ArrayList<Note> people) {
            super(itemView);

            companyName = itemView.findViewById(R.id.companyName);
            founder = itemView.findViewById(R.id.founderName);
            product = itemView.findViewById(R.id.productName);

            companyIcon = itemView.findViewById(R.id.avatarField);

            ImageView deleteItem = itemView.findViewById(R.id.removeItem);
            ImageView editItem = itemView.findViewById(R.id.editItem);

            this.onNoteListener = onNoteListener;
            this.removeListener = removeListener;

            itemView.setOnClickListener(this);

            deleteItem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Toast.makeText(itemView.getContext(), "Delete button pressed", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    removeListener.onRemoveClick(position);

                    people.remove(position);
                    notifyItemRemoved(position);
                }});


            editItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(itemView.getContext(), "Edit button pressed", Toast.LENGTH_SHORT).show();
                    onNoteListener.onNoteClick(getAdapterPosition());
                }});
        }

        public void setCompanyName(String name) {
            companyName.setText(name);
        }

        public void setFounder(String founder) {
            this.founder.setText(founder);

            try {
                companyIcon.setImageResource(R.drawable.company);

            } catch (Exception ignore){}
        }

        public void setProduct(String product) {
            this.product.setText(product);
        }

        @Override
        public void onClick(View view) {
            Log.e("Note", "Note clicked!");
        }

    }
}