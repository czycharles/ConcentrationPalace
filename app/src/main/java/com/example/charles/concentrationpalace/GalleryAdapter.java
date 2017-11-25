package com.example.charles.concentrationpalace;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Gallery Adapter.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{

    private List<GalleryItem> mGalleryList;
    private GridLayoutManager glm;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View GalleryView;
        ImageView GalleryImage;

        ViewHolder(View view){
            super (view);
            GalleryView = view;
            GalleryImage = view.findViewById(R.id.gallery_list);
        }
    }
    GalleryAdapter(List<GalleryItem> GalleryList, GridLayoutManager gridLayoutManager){
        mGalleryList = GalleryList;
        glm = gridLayoutManager;
    }

    private OnItemClickListener mOnItemClickListener = null;
    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_layout,parent,false);
        final GalleryAdapter.ViewHolder holder = new GalleryAdapter.ViewHolder(view);

        holder.GalleryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v,position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position){
        GalleryItem item = mGalleryList.get(position);
        holder.GalleryImage.setBackgroundResource(item.getImageId());
        ViewGroup.LayoutParams param = holder.GalleryImage.getLayoutParams(); //获取button背景的LayoutParams实例
        param.height = glm.getWidth()/glm.getSpanCount()
                - holder.GalleryImage.getPaddingLeft();
    }

    @Override
    public int getItemCount(){
        return mGalleryList.size();
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
