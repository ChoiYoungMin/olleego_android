package com.edn.olleego.adapter.mission.missionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.missionlist.detail.MissionListDetailAdapter;
import com.edn.olleego.model.usermission.Exgroup2;
import com.edn.olleego.model.usermission.Mission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2016-08-09.
 */
public class MissionListAdapter extends BaseAdapter {
    Mission mission;
    MissionListViewHolder holder = null;

    boolean type;

    LayoutInflater inflater;
    Context context;
    ArrayList<MissionListData> missionListDatas = new ArrayList<MissionListData>();

    MissionListDetailAdapter missionListDetailAdapter_food;
    MissionListDetailAdapter missionListDetailAdapter_life;
    MissionListDetailAdapter missionListDetailAdapter_expo;
    public MissionListAdapter(LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return missionListDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return missionListDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        missionListDetailAdapter_food = new MissionListDetailAdapter(inflater, context);
        missionListDetailAdapter_life = new MissionListDetailAdapter(inflater, context);
        missionListDetailAdapter_expo = new MissionListDetailAdapter(inflater, context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mission_list, null);

            holder = new MissionListViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (MissionListViewHolder) convertView.getTag();
        }

        MissionListData missionListData = missionListDatas.get(position);

        holder.mission_list_day.setText(String.valueOf(position + 1) + " 일차");
        holder.mission_list_title_1.setText(missionListData.mission_list_title_1);
        holder.mission_list_listview_1.setAdapter(missionListDetailAdapter_life);

        try {
            for (int i = 0; i < mission.getMi_days().get(position).getLife().getLf_list().size(); i++) {
                type = false;

                    if(mission.getMi_days().get(position).getLife().getLf_list().get(i).get_id() == missionListData.life_list) {
                        type = true;


                }
                missionListDetailAdapter_life.add(mission.getMi_days().get(position).getLife().getLf_list().get(i).getTitle(),type);
            }
        }
        catch (NullPointerException e ) {

        } catch (IndexOutOfBoundsException e){

        }

        holder.mission_list_title_2.setText(missionListData.mission_list_title_2);
        holder.mission_list_listview_2.setAdapter(missionListDetailAdapter_expo);
        //missionListDetailAdapter_life.add();
        try {
        for(int i=0; i<mission.getMi_days().get(position).getExgroup().get(0).getEx_list().size(); i++){
            type = false;
            for(int j=0; j<missionListData.ex_list.getEx_list().size(); j++) {
                if(mission.getMi_days().get(position).getExgroup().get(0).getEx_list().get(i).getEx().get_id() == missionListData.ex_list.getEx_list().get(j)) {
                    type = true;
                }

            }


                missionListDetailAdapter_expo.add(mission.getMi_days().get(position).getExgroup().get(0).getEx_list().get(i).getEx().getTitle(),type);
            }
        }catch (NullPointerException e ) {

        } catch (IndexOutOfBoundsException e){

        }
        holder.mission_list_title_3.setText(missionListData.mission_list_title_3);
        holder.mission_list_listview_3.setAdapter(missionListDetailAdapter_food);
        //missionListDetailAdapter_food.add();
        try {
        for(int i=0; i<mission.getMi_days().get(position).getFood().getFd_list().size(); i++){
            type = false;
                if(mission.getMi_days().get(position).getFood().getFd_list().get(i).get_id() == missionListData.food_list) {
                    type = true;
            }


                missionListDetailAdapter_food.add(mission.getMi_days().get(position).getFood().getFd_list().get(i).getTitle(), type);
            }
        }catch (NullPointerException e ) {

        } catch (IndexOutOfBoundsException e){

        }


        listViewHeightSet(missionListDetailAdapter_life,holder.mission_list_listview_1);
        listViewHeightSet(missionListDetailAdapter_expo,holder.mission_list_listview_2);
        listViewHeightSet(missionListDetailAdapter_food,holder.mission_list_listview_3);


        return convertView;
    }

    public void add(String mission_list_day, String mission_list_title_1, String mission_list_title_2, String mission_list_title_3, Mission mission, int life_list, int food_list, Exgroup2 ex_list) {
        MissionListData missionListData = new MissionListData( mission_list_day,  mission_list_title_1,  mission_list_title_2,  mission_list_title_3, life_list,food_list,ex_list);
        this.mission = mission;

        missionListDatas.add(missionListData);
        notifyDataSetChanged();
    }

    private void listViewHeightSet(Adapter listAdapter, ListView listView)
    {
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
