package matko.cv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import matko.cv.R;
import matko.cv.model.Place;

/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:gergelymatkovics82@gmail.com">gergelymatkovics82@gmail.com</a>
 */

public class PlaceRecyclerAdapter extends RecyclerView.Adapter<PlaceRecyclerAdapter.PlaceVH> {

    private List<Place> placeList = new ArrayList<>();

    private OnPlaceListener mOnPlaceListener;

    private Context mContext;

    private int lastPlace;

    public PlaceRecyclerAdapter(List<Place> placeList, Context mContext, OnPlaceListener mOnPlaceListener) {
        this.placeList = placeList;
        this.mContext = mContext;
        this.mOnPlaceListener = mOnPlaceListener;
    }

    @NonNull
    @Override
    public PlaceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        RecyclerView.ViewHolder holder = new PlaceVH(view, mOnPlaceListener);


        lastPlace = -1;

        return (PlaceVH) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceVH holder, int position) {

        holder.txPlaceName.setText(placeList.get(position).getPlaceName());
        holder.txType.setText(placeList.get(position).getType());
        holder.txTimeSpent.setText(placeList.get(position).getTimeSpent());
        holder.txDesc.setText(placeList.get(position).getDesc());

        boolean isExpanded = placeList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);


    }


    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class PlaceVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txPlaceName, txType, txTimeSpent, txDesc;
        OnPlaceListener onPlaceListener;

        ConstraintLayout expandableLayout;

        public PlaceVH(@NonNull View itemView, OnPlaceListener onPlaceListener) {
            super(itemView);

            this.onPlaceListener = onPlaceListener;

            txPlaceName = itemView.findViewById(R.id.txPlaceName);
            txType = itemView.findViewById(R.id.txType);
            txTimeSpent = itemView.findViewById(R.id.txTimeSpent);
            txDesc = itemView.findViewById(R.id.txDesc);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);



            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Place place = placeList.get(getAdapterPosition());
            place.setExpanded(!place.isExpanded());
            notifyItemChanged(getAdapterPosition());
            onPlaceListener.onPlaceClick(getAdapterPosition());
        }
    }

    public interface OnPlaceListener {
        void onPlaceClick(int position);
    }
}
