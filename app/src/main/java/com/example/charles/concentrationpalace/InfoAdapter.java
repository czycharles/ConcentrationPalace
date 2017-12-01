package com.example.charles.concentrationpalace;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Info Layout Adapter
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder>{

    private List<String> mInfoList;
    private File file;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View InfoView;
        TextView InfoText;
        ViewHolder(View view){
            super (view);
            InfoView = view;
            InfoText = view.findViewById(R.id.info_list);
        }
    }
    InfoAdapter(List<String> InfoList){
        mInfoList = InfoList;
    }

//    private Resources getResources() {
//    // TODO Auto-generated method stub
//        Resources mResources;
//        mResources = getResources();
//        return mResources;
//    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_layout,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.InfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                final Context context = v.getContext();
                //Toast.makeText(context, "位置"+position, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 1:
                        AlertDialog.Builder ResetAlert = new AlertDialog.Builder(context);
                        ResetAlert.setTitle("删除记录？");
                        ResetAlert.setMessage("你目前的游戏进度会被清空");
                        ResetAlert.setCancelable(false);
                        ResetAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ResetAlert, int i) {
//                            data = getSharedPreferences("data", MODE_PRIVATE);
//                            data.edit().clear().apply();
                                file= new File(context.getFilesDir().getParentFile().getPath()+"/shared_prefs","data.xml");
                                if (file.exists()&&file.delete()){
                                    Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
                                }
                                else
                                    Toast.makeText(context, "删除文件出错，请关闭应用重试", Toast.LENGTH_LONG).show();
                            }
                        });
                        ResetAlert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ResetAlert, int i) {
                                ResetAlert.cancel();
                            }
                        });
                        ResetAlert.show();
                        break;
//                    case 2:
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse(parent.getContext().getResources().getString((R.string.info2_2))));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        parent.getContext().startActivity(intent);
//                        break;
                    case 3:
                        Intent intent1 = new Intent(Intent.ACTION_SENDTO);
                        intent1.setData(Uri.parse("mailto: "+ parent.getContext().getResources().getString((R.string.info3_2))));
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        parent.getContext().startActivity(intent1);
                        break;
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String item = mInfoList.get(position);
        holder.InfoText.setText(item);
    }

    @Override
    public int getItemCount(){
        return mInfoList.size();
    }
}
