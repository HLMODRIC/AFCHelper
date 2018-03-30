package com.hl.afchelper.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hl.afchelper.R;
import com.hl.afchelper.entity.Data;
import com.hl.afchelper.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by YoKeyword on 16/6/5.
 */
public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.VH> {
    private List<Data> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    private OnItemClickListener mClickListener;


    public ListRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.content_recycler_list_item, parent, false);
        final VH holder = new VH(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Data data = mData.get(position);

        // 把每个图片视图设置不同的Transition名称, 防止在一个视图内有多个相同的名称, 在变换的时候造成混乱
        // Fragment支持多个View进行变换, 使用适配器时, 需要加以区分
        ViewCompat.setTransitionName(holder.img, String.valueOf(position) + "_image");
        ViewCompat.setTransitionName(holder.tvTitle, String.valueOf(position) + "_tv");

        RequestOptions options = new RequestOptions ()
                .apply(bitmapTransform(new RoundedCornersTransformation (30, 0, RoundedCornersTransformation.CornerType.ALL)))
                .error(R.drawable.load_error);

        Glide.with(mContext)
                .load(R.drawable.item_list_text)
                .apply (options)

                .into(holder.img);
        holder.tvTitle.setText(data.getNew_title ());
    }

    public void setDatas(ArrayList<Data> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Data getItem(int position) {
        return mData.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView img;

        public VH(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.txt_item_title);
            img = (ImageView) itemView.findViewById(R.id.iv_title);
        }
    }
}
