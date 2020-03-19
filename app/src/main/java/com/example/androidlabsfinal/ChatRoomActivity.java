package com.example.androidlabsfinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {


    private ArrayList<Message> elements = new ArrayList<>();
    //    private ArrayList<String> elements = new ArrayList<>( Arrays.asList( "one", "Two", "Three3", "four", "5"/*Empty*/ ) );
    private MyListAdapter myAdapter;


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //initialize the listview and assign list view adapter
        ListView myList = findViewById(R.id.chatListView);
        myList.setAdapter( myAdapter = new MyListAdapter());

        //build handlers for the button presses. These will update the messages arraylist.
        Button sendButton = (Button)findViewById(R.id.sendButton);
        Button receiveButton = (Button)findViewById(R.id.receiveButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the value of the edit text box.
                EditText messageEditText = (EditText)findViewById(R.id.chat_ET);
                String messageText = messageEditText.getText().toString();


                Message messageToCommit = new Message(messageText, true);
                elements.add(messageToCommit);
                myAdapter.notifyDataSetChanged();
            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the value of the edit text box.
                EditText messageEditText = (EditText)findViewById(R.id.chat_ET);
                String messageText = messageEditText.getText().toString();


                Message messageToCommit = new Message(messageText, false);
                elements.add(messageToCommit);
                //update the listview adapter
                myAdapter.notifyDataSetChanged();
                //clear the list view
                messageEditText.setText("");
            }
        });

        //longClick listener for the listview.

        //Set an onItemLongClick() listener to the listView. Whenever you click on the list at a certain row, show an AlertDialog with the title: “Do you want to delete this?”, the message should display the following information:
        // a. “The selected row is: “ followed by the index that was clicked.
        // b. “The database id id:” followed by the database id that is given.

//        ListView lv = (ListView)findViewById(R.id.chatListView);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //changed from lv.setOn...
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView <?> parent, View view, int position, long id) {

                //work around for scoping purposes with anonymous function calls.
                final int pos = position;
                String builderMessage = "The selected row is: " + position + "The database id id: " + id;
                String builderTitle = "Do you want to delete this?";
                builder.setMessage(builderMessage).setTitle(builderTitle);
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //parent.remove(position);
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                        //   finish();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //   finish();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.show();
                return true;

            }
        });




    }

    //list adapter class.
    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return elements.size(); }

        public Object getItem(int position) {
            //return a message object
            return elements.get(position);
        }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();


            //figure out if it is a send or receive type of message.
            Message currentMessage = (Message)getItem(position);

            //set the row layout based on the message type.
            if(currentMessage.isSendType()){                    //send type case

                if(newView == null) {
                    newView = inflater.inflate(R.layout.send_row_layout, parent, false);
                }
            } else{  //receive type case.
                if(newView == null) {
                    newView = inflater.inflate(R.layout.receive_row_layout, parent, false);
                }
            }

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.chatListViewTextView);
            tView.setText(currentMessage.getMessage());

            //return it to be put in the table
            return newView;
        }
    }
}
