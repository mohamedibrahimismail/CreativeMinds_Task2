package com.example.creativeminds_second_task.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeminds_second_task.R;
import com.example.creativeminds_second_task.data.remote.models.RepoModel;


import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "RecyclerViewAdapter";

    Resources resource;
    Context context;
    List<RepoModel> list;
    private List<RepoModel> ListFiltered;;
    Communicator communicator;



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
//                String charString = constraint.toString();
//                if (charString.isEmpty()) {
//                    ListFiltered = list;
//                } else {
//                    List<RepoModel> filteredList = new ArrayList<>();
//                    for (RepoModel row : list) {
//                        if (row.getName().toLowerCase().startsWith(charString)) {
//                               filteredList.add(row);
//                            }
//                    }
//                    ListFiltered = filteredList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = ListFiltered;
//
//                return filterResults;

                Log.e(TAG, "performFiltering: "+constraint );

                List<RepoModel> filteredList = new ArrayList<>();

                    if (constraint == null || constraint.length() == 0) {
                        filteredList = new ArrayList<>(ListFiltered);
                        Log.e(TAG, "empty: ");
                    } else {
                        String filterPattern = constraint.toString().toLowerCase().trim();
                        for (RepoModel item : ListFiltered) {
                            if (item.getName().toLowerCase().startsWith(filterPattern)) {
                                filteredList.add(item);
                            }
                        }
                    }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list.clear();
                list.addAll((List) results.values);
            //     ListFiltered = (ArrayList<RepoModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface Communicator{
        public void handleLongClicked(String repo_url,String owner_url);
    }


    public RecyclerViewAdapter(Context context, List<RepoModel> list) {
        this.list = list;
        this.ListFiltered  = new ArrayList<>(list);
        this.resource = context.getResources();
        this.context = context;
        this.communicator = (Communicator)context;

    }

    public void addDataToList(List<RepoModel> list) {
        if(this.list!=null) {
            this.list.addAll(list);
        }else {
            this.list = list;
        }
        this.ListFiltered  = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.repo_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {


        TextView reponame;
        TextView description;
        TextView usernameofrepoowner;
        View itemView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnLongClickListener(this);
            reponame = (TextView) itemView.findViewById(R.id.reponame);
            description = (TextView) itemView.findViewById(R.id.description);
            usernameofrepoowner = (TextView) itemView.findViewById(R.id.usernameofrepoowner);

        }


        public void setData(RepoModel data) {
            reponame.setText(data.getName());
            description.setText(data.getDescription());
            if(data.getOwner()!=null) {
                usernameofrepoowner.setText(data.getOwner().getLogin());
            }
            if(!data.getFork()) {
                itemView.setBackgroundColor(resource.getColor(R.color.lightgreen));
            }else {
                itemView.setBackgroundColor(resource.getColor(R.color.white));
            }
        }


        @Override
        public boolean onLongClick(View v) {
            communicator.handleLongClicked(list.get(getPosition()).getHtmlUrl(),list.get(getPosition()).getOwner().getHtmlUrl());
            return true;
        }
    }
}
