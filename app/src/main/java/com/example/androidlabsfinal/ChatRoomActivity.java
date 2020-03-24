package com.example.androidlabsfinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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



    public static final String ACTIVITY_NAME = "CHAT_ACTIVITY";
    private ArrayList<Message> elements = new ArrayList<>();
    private MyListAdapter myAdapter;
    SQLiteDatabase db;




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

        //determine if you're on a phone or a tablet.
        boolean isPhone=false;
        if ((View)findViewById(R.id.rightChatFrame) == null) {
            isPhone=true;
        }

        //loads the elements arraylist from the database.
        loadDataFromDatabase();

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

                //Update the database with the data.
                ContentValues newRowValues = new ContentValues();

                //update the message:
                newRowValues.put(db_admin.COL_MESSAGE, messageText);

                //update the send type:
                newRowValues.put(db_admin.COL_SENDTYPE, "SEND");

                //Now insert in the database:
                long newId = db.insert(db_admin.TABLE_NAME, null, newRowValues);

                Message messageToCommit = new Message(messageText, "SEND", newId);


                //update the arraylist.
                elements.add(messageToCommit);
                myAdapter.notifyDataSetChanged();
                //clear the editText box.
                messageEditText.setText("");
            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the value of the edit text box.
                EditText messageEditText = (EditText)findViewById(R.id.chat_ET);
                String messageText = messageEditText.getText().toString();

                //Update the database with the data.
                ContentValues newRowValues = new ContentValues();

                //update the message:
                newRowValues.put(db_admin.COL_MESSAGE, messageText);

                //update the send type:
                newRowValues.put(db_admin.COL_SENDTYPE, "RECEIVE");

                //Now insert in the database:
                long newId = db.insert(db_admin.TABLE_NAME, null, newRowValues);

                Message messageToCommit = new Message(messageText, "RECEIVE", newId);

                elements.add(messageToCommit);
                //update the listview adapter
                myAdapter.notifyDataSetChanged();
                //clear the list view edit text
                messageEditText.setText("");
            }
        });

        //if tablet load the fragment on click listener.
        if(!isPhone) {
            myList.setOnItemClickListener((list, item, position, id) -> {
                //Add fragment loading here from slide 14.
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragFrameLayout, new DetailsFragment())
                        .commit();
            });
        } else //isPhone
            {
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
//                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }


        //longClick listener for the listview.
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView <?> parent, View view, int position, long idMain) {

                final int pos = position;
                final long idMainFinal = idMain;

                String builderMessage = "The selected row is: " + position + "The database id id: " + idMain;
                String builderTitle = "Do you want to delete this?";
                builder.setMessage(builderMessage).setTitle(builderTitle);
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //parent.remove(position);
                        //remove from database
                        db.delete(db_admin.TABLE_NAME, "_id=?",
                                new String[]{Long.toString(idMainFinal)});
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                myAdapter.notifyDataSetChanged();
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

        public long getItemId(int position) { return (long)elements.get(position).getId(); }

        public View getView(int position, View old, ViewGroup parent)
        {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //figure out if it is a send or receive type of message.
            Message currentMessage = (Message)getItem(position);

            //set the row layout based on the message type.
            if(currentMessage.getSendType().equals("SEND")){                    //send type case
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





    //database load to populate the elements arraylist.
    private void loadDataFromDatabase() {
        //get a database connection:
        db_admin dbOpener = new db_admin(this);
        db = dbOpener.getWritableDatabase();


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {db_admin.COL_ID, db_admin.COL_MESSAGE, db_admin.COL_SENDTYPE};
        //query all the results from the database:
        Cursor results = db.query(false, db_admin.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int messageColumnIndex = results.getColumnIndex(db_admin.COL_MESSAGE);
        int sendtypeColIndex = results.getColumnIndex(db_admin.COL_SENDTYPE);
        int idColIndex = results.getColumnIndex(db_admin.COL_ID);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String sendType = results.getString(sendtypeColIndex);
            String message = results.getString(messageColumnIndex);
            long id = results.getLong(idColIndex);

            //add to the array list:
            elements.add(new Message(message, sendType, id));


        }
        //print the results to the log after loading them.
        this.printCursor(results);
    }



    //print a generic cursor.

    public void printCursor( Cursor c ){
        //print the database/cursor information.
        Log.e(ACTIVITY_NAME, "............PRINTING CURSOR INFO..........");
        String[] colNames = c.getColumnNames(); //String[]
        int colCount = c.getColumnCount();

        Log.e(ACTIVITY_NAME, "\nDB VERSION: " + db.getVersion() );
        Log.e(ACTIVITY_NAME, "\nCOLUMNS IN COUNTER: " + colCount );
        Log.e(ACTIVITY_NAME, "COLUMN NAMES: ");
        for(int k =0 ; k < colNames.length; k++){
            Log.e(ACTIVITY_NAME, ">   " + colNames[k]);
        }

        Log.e(ACTIVITY_NAME, "\nNUMBER OF RESULTS(ROWS): " + c.getCount() + "\n");


        //print the contents of the cursor.
        c.moveToFirst();
        while( !c.isAfterLast() ){

            //c.getType()
            //print a row.
            Log.e(ACTIVITY_NAME, "************");
            for (int i = 0; i < colCount; i++) {
                Log.e(ACTIVITY_NAME, "|    " + c.getString(i));
            }
            c.moveToNext();
        }

    }

}