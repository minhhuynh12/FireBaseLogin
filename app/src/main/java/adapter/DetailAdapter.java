package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mypc.loginforfirebase.R;

import java.util.ArrayList;

import items.UserItems;

/**
 * Created by vitinhHienAnh on 08-10-17.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    ArrayList<UserItems> userItemsList;
    Context context;

    public DetailAdapter(ArrayList<UserItems> itemses){
        userItemsList = itemses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detail_user_items, parent , false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(userItemsList.get(position).getName());
        holder.tvEmail.setText(userItemsList.get(position).getEmail());
        holder.tvPhone.setText(userItemsList.get(position).getPhone_num());

    }

    @Override
    public int getItemCount() {
        return userItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPhone;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }
}
