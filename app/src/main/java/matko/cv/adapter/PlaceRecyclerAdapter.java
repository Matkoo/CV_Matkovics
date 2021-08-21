package matko.cv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    private int firstItem;

    private int lastItem;

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

        firstItem = placeList.get(0).getId();
        lastItem  = placeList.get(placeList.size()-1).getId();

        return (PlaceVH) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceVH holder, int position) {


        holder.txPlaceName.setText(placeList.get(position).getPlaceName());
        holder.txTimeSpent.setText(placeList.get(position).getTimeSpent());

        //when english the language change the description and etc to english
        if ( Locale.getDefault().getLanguage().contains("en")){
            holder.txType.setText(placeList.get(position).getTypeEN());
            holder.txdescHU.setText(placeList.get(position).getDescEN());
        }else{
            holder.txType.setText(placeList.get(position).gettypeHU());
            holder.txdescHU.setText(placeList.get(position).getdescHU());
        }

        boolean isExpanded = placeList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // set the visibility for the arrow on the menu
        if ( placeList.get(position).getId() == firstItem ) {
            holder.backwardArrow.setVisibility(View.INVISIBLE);

        }else {
            holder.backwardArrow.setVisibility(View.VISIBLE);
        }
        if (placeList.get(position).getId() == lastItem){
            holder.forwardArrow.setVisibility(View.INVISIBLE);
        }else {
            holder.forwardArrow.setVisibility(View.VISIBLE);
        }



    }


    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class PlaceVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txPlaceName, txType, txTimeSpent, txdescHU;
        ImageView forwardArrow,backwardArrow;
        OnPlaceListener onPlaceListener;

        ConstraintLayout expandableLayout;

        public PlaceVH(@NonNull View itemView, OnPlaceListener onPlaceListener) {
            super(itemView);

            this.onPlaceListener = onPlaceListener;

            txPlaceName = itemView.findViewById(R.id.txPlaceName);
            txType = itemView.findViewById(R.id.txType);
            txTimeSpent = itemView.findViewById(R.id.txTimeSpent);
            txdescHU = itemView.findViewById(R.id.txdescHU);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            backwardArrow = itemView.findViewById(R.id.backArrow);
            forwardArrow = itemView.findViewById(R.id.forwardArrow);


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
