package com.example.loginsystemlab5partb.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsystemlab5partb.DB.UserDB;
import com.example.loginsystemlab5partb.Model.StatusModel;
import com.example.loginsystemlab5partb.R;
import com.example.loginsystemlab5partb.UpdateStatus;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<StatusModel> statusModelList;

    public PostAdapter(List<StatusModel> statusModelList) {
        this.statusModelList = statusModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.statuslist,null,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, final int position) {
        h.tvUserName.setText(statusModelList.get(position).getUserName());

        h.tvStatus.setText(statusModelList.get(position).getStatus());
        h.imgPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup=new PopupMenu(view.getContext(),view);
                popup.inflate(R.menu.popupmenu);
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.btnEdit:
                                Intent i=new Intent(view.getContext(), UpdateStatus.class);
                                Bundle b=new Bundle();
                                b.putParcelable("OldValue",statusModelList.get(position));
                                i.putExtras(b);
                                view.getContext().startActivity(i);
                                return true;


                            case R.id.btnDelete:
                                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                                builder.setMessage("Are You Sure You Want To Delete This Status????????");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        UserDB udb=new UserDB(view.getContext());
                                        udb.RemoveStatus(statusModelList.get(position).getStatusID());
                                        statusModelList.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(view.getContext(), "Remove Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.create();
                                builder.show();
                                return true;


                            default:
                                return false;
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvUserName,tvStatus;
        ImageView imgPopup;


        public ViewHolder(@NonNull View v) {
            super(v);
            tvUserName=v.findViewById(R.id.tvUserName);
            tvStatus=v.findViewById(R.id.tvStatus);
            imgPopup=v.findViewById(R.id.imgPopup);
        }
    }
}
