package com.example.androidlabsfinal;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private Bundle dataFromActivity;
    private long id;
    private String msg;
    private boolean sendType;
    private boolean isPhone;
    private AppCompatActivity parentActivity;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //unload data from the bundle passed to this.
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ID_KEY);
        msg = dataFromActivity.getString(ChatRoomActivity.MESSAGE_KEY);
        sendType = dataFromActivity.getBoolean(ChatRoomActivity.IS_SEND_KEY);
        isPhone = dataFromActivity.getBoolean(ChatRoomActivity.IS_PHONE_KEY);


        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        //show the message
        TextView message = (TextView)result.findViewById(R.id.chatMsgFrag);
        message.setText(msg);

        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.chatIdFrag);
        idView.setText("ID=" + id);

        //set the checked box for the send type.
        CheckBox sendTypeChk = (CheckBox)result.findViewById(R.id.chatChkFrag);
        sendTypeChk.setChecked(sendType);

        // get the hide button, and add a click listener:
        Button hideButton = (Button)result.findViewById(R.id.chatHideBtn);

        hideButton.setOnClickListener( clk -> {
            //Tell the parent activity to remove the fragment.
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();

            //finish the activity if it's a phone, returning to the main chat activity.
            if(isPhone){getActivity().finish();}

        });

        return result;


        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }

}
