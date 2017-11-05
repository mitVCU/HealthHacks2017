package com.mittens.healthhacks;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private final List<String> rooms;

    public RoomAdapter(List<String> rooms) {
        this.rooms = rooms;
    }

    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewTag = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.room_row_layout, parent, false);
        return new ViewHolder((viewTag));
    }

    @Override
    public void onBindViewHolder(RoomAdapter.ViewHolder holder, final int position) {
        holder.itemName.setText(rooms.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PatientStatusActivity.class);
                intent.putExtra("patient", rooms.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.room_name);
        }
    }
}
