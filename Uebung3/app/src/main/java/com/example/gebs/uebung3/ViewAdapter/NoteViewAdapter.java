package com.example.gebs.uebung3.ViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gebs.uebung3.R;
import com.example.gebs.uebung3.db.Note;

import java.util.ArrayList;

/**
 * Created by gebs on 3/16/17.
 */

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.ViewHolder> {

    public ArrayList<Note> notes;
public NoteAdapterClickListener listener;

    public interface NoteAdapterClickListener {
        void recyclerViewClick(long noteID);
    }

    public NoteViewAdapter(ArrayList<Note> _notes,NoteAdapterClickListener _listener) {
        this.notes = _notes;
        this.listener = _listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card_view, parent, false);
        ViewHolder vh = new ViewHolder(v, new ViewHolder.NoteClickListener() {
            @Override
            public void noteOnClick(long noteID) {
                listener.recyclerViewClick(noteID);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.Text.setText(note.getText());
        holder.Title.setText(note.getTitle());
        holder.ID = note.get_id();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title;
        TextView Text;
        long ID;
        public NoteClickListener listener;

        //listener passed to viewHolder
        public interface NoteClickListener {
            void noteOnClick(long noteID);
        }

        ViewHolder(View itemView,NoteClickListener listener) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.cvlblTitle);
            Text = (TextView) itemView.findViewById(R.id.cvlblText);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            listener.noteOnClick(ID);
        }


    }
}
