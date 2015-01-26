package fr.ecp.sio.superchat;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.ecp.sio.superchat.model.User;

/**
 * Created by Michaël on 05/12/2014.
 */
public class UsersAdapter extends BaseAdapter {

    private List<User> mUsers;


    public List<User> getUsers() {
        return mUsers;
    }

    private List<String>followingsHandles;


    public boolean isFollowed(User user){


        if (followingsHandles.contains(user.getHandle())){
            return  true;
        }else{
            return false;
        }

    }

    public void setUsers(List<User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    public List<String> getFollowingsHandles() {
        return followingsHandles;
    }



    public void setFollowingsHandles(List<String> followingsHandles) {
        this.followingsHandles = followingsHandles;
    }


    @Override
    public int getCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    @Override
    public User getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        }
        User user = getItem(position);
        TextView handleView = (TextView) convertView.findViewById(R.id.handle);
        handleView.setText(user.getHandle());
        TextView statusView = (TextView) convertView.findViewById(R.id.status);

        ImageView profilePictureView = (ImageView) convertView.findViewById(R.id.profile_picture);
        Picasso.with(convertView.getContext()).load(user.getProfilePicture()).into(profilePictureView);

        //Log.w("SELIM",   " =?= " + user.getFollowers());



        String connectedUserHandle=AccountManager.getUserHandle(parent.getContext());

            convertView.setBackgroundColor(Color.TRANSPARENT);
            statusView.setText("");

        if (connectedUserHandle!=null){
            if (user.getHandle().equals(connectedUserHandle)){
                convertView.setBackgroundColor(Color.CYAN);

                convertView.setSelected(true);

            }else if (isFollowed(user)){
                statusView.setText("Followed by "+connectedUserHandle);
            }
        }



        return convertView;
    }

}
