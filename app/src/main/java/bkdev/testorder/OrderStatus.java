package bkdev.testorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import bkdev.testorder.Common.Common;
import bkdev.testorder.Model.Request;
import bkdev.testorder.Model.User;
import bkdev.testorder.ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder>adapter;

    FirebaseDatabase database;
    DatabaseReference requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(!getIntent().hasExtra("userPhone")){
            Log.i("phone number",Common.currentUser.getPhone());
            loadOrders(Common.currentUser.getPhone());
        }
        else{
            Log.i("phone number",getIntent().getStringExtra("userPhone"));
            loadOrders(getIntent().getStringExtra("userPhone"));
        }
    }



    private void loadOrders(String phone) {

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone")
                        .equalTo(phone)
        ) {
            @Override

            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, final int position) {
                
                    viewHolder.txtOrderId.setText("รหัสรายการสั่งซื้อ : " + adapter.getRef(position).getKey());
                    viewHolder.txtOrderStatus.setText("สถานะการสั่งซื้อ : " + Common.convertCodeToStatus(model.getStatus()));
                    viewHolder.txtOrderAddress.setText("ที่อยู่ : " + model.getAddress());
                    viewHolder.txtOrderPhone.setText("เบอร์โทรศัพท์ : " + model.getPhone());


                viewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent orderDetail = new Intent(OrderStatus.this,OrderDetail.class);
                        Common.currentRequest = model;
                        orderDetail.putExtra("OrderId",adapter.getRef(position).getKey());
                        startActivity(orderDetail);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }
}

