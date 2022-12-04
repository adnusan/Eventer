package com.example.eventer

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.MessageAdapter
import com.example.eventer.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageActivity : AppCompatActivity() {

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var chatList: DatabaseReference

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private var messageList = ArrayList<Message>()

    //unique id for each chat room between two users
    var receiverRoom: String? = null
    var senderRoom: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        //initialize firebase
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        //receive data from intent
        val name = intent.getStringExtra("name")
        val receiverId = intent.getStringExtra("profileId")
        val senderId = FirebaseAuth.getInstance().currentUser?.uid

        //creating unique id for room between two users since user id are unique
        senderRoom = receiverId + senderId
        receiverRoom = senderId + receiverId

        supportActionBar?.title = name //displays username on toolbar


        //initializing
        messageRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.chat_messageBox)
        sendButton = findViewById(R.id.chat_sendButton)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        //logics for sending message
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter




        //logics for adding messages to recycler view
        //get data from firebase and show data in recycler view
        databaseReference.child("chats").child(senderRoom!!).child("messages")
                .addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //looping through all the messages inside the room
                        messageList.clear() //clear message list

                        for (data in snapshot.children){
                            val message = data.getValue(Message::class.java) //getting message object from firebase
                            messageList.add(message!!) //adding message object to array list
                        }
                        messageAdapter.notifyDataSetChanged() //notifying adapter that data has changed
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@MessageActivity, error.message, Toast.LENGTH_SHORT).show()
                    }

        })

        //when send button is clicked call sendMessage function
        sendButton.setOnClickListener() {
            val message = messageBox.text.toString()

            //create message object using message model
            //constructor(message: String?, senderId: String?, receiverId: String?)
            val messageObject = Message(message, senderId, receiverId)

            //adding message to firebase if message is not empty
            if (message.isNotEmpty()) {
                sendMessage(messageObject,name!!) //calling sendMessage function

            databaseReference.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageObject).addOnSuccessListener { //on success
                        databaseReference.child("chats").child(receiverRoom!!).child("messages").push()
                            .setValue(messageObject)
                    }
            messageBox.setText("") //clearing message box after sending message



            }
        }

    }


    private fun sendMessage(messageObject: Message, name: String) {
        //logics to handle send button click
        databaseReference.child("chats").child(senderRoom!!).child("messages").push()
            .setValue(messageObject).addOnSuccessListener { //on success
                databaseReference.child("chats").child(receiverRoom!!).child("messages")
                    .push()
                    .setValue(messageObject)
            }
        messageBox.setText("")


        //adding user to chat fragment: Latest chats will be displayed
        chatList = FirebaseDatabase.getInstance().getReference("chatList")
            .child(messageObject.senderId!!).child(messageObject.receiverId!!)

        chatList.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()){
                    chatList.child("senderId").setValue(messageObject.receiverId)
                    //add senderId user name to chatList
                    chatList.child("name").setValue(name)
                }
                }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MessageActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}
