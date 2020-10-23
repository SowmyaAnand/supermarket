package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder>
{

    ArrayList<String> personNames = new ArrayList<String>();
    Context context;
    Integer orderid_adapter;
    ArrayList<String> lts=new ArrayList<String>();
   ArrayList<Integer> payment_type= new ArrayList<>();
    ArrayList personNames_offers = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    int quantity=1;
    ArrayList<String> items_name_price_myorders = new ArrayList<>();
    ArrayList<String> items_image_myorders = new ArrayList<>();
    ArrayList<Integer> return_request_orders_list_array_orderid = new ArrayList<>();
    ArrayList<Integer> order_id_list = new ArrayList<>();
    ArrayList<String> items_name_refund_myordersadapter = new ArrayList<>();
    ArrayList<String> items_name_myorders; ArrayList<String>items_name_quantity_myorders;ArrayList<Integer> items_name_status_myorders;ArrayList<Integer> items_count_myorders;
    public MyOrdersAdapter(Context context,ArrayList<String> items_name_myorders, ArrayList<String>items_name_quantity_myorders,ArrayList<Integer> items_name_status_myorders,ArrayList<Integer> items_count_myorders, ArrayList<String> items_name_price_myorders,ArrayList<String> items_image_myorders,ArrayList<Integer> return_request_orders_list_array_orderid,ArrayList<Integer> order_id_list,ArrayList<Integer> payment_type, ArrayList<String> items_name_refund_myordersadapter) {
        this.context = context;
        this.personNames = personNames;
        this.lts.addAll(personNames);
        this.items_name_myorders=items_name_myorders;
        this.items_count_myorders=items_count_myorders;
        this.items_name_quantity_myorders=items_name_quantity_myorders;
        this.items_name_status_myorders=items_name_status_myorders;
        this.items_name_price_myorders=items_name_price_myorders;
        this.items_image_myorders=items_image_myorders;
        this.return_request_orders_list_array_orderid=return_request_orders_list_array_orderid;
        this.order_id_list=order_id_list;
        this.items_name_refund_myordersadapter=items_name_refund_myordersadapter;
        this.payment_type=payment_type;

    }
    @Override
    public MyOrdersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderslist, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyOrdersAdapter.MyViewHolder vh = new MyOrdersAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyOrdersAdapter.MyViewHolder holder, final int position) {
String pay_txt;
        // set the data in items
        String name = (String) items_name_myorders.get(position);
        String quan = (String) items_name_quantity_myorders.get(position);
        String pr =items_name_price_myorders.get(position);
        String img =items_image_myorders.get(position);
        Integer count = items_count_myorders.get(position);
        Integer st = items_name_status_myorders.get(position);
        Integer p_type =payment_type.get(position);
        holder.name.setText(name);
        holder.quantityy.setText(quan);


        Log.e("myorders","adapter orders are ="+items_name_refund_myordersadapter);
        if(p_type==1)
        {
           pay_txt="Gpay";
        }
        else
        {
            pay_txt="Cash On Delivery";
        }
        holder.cod_available.setText(pay_txt);


        String cntt = "COUNT :"+count;
        holder.count.setText(cntt);
        if(st==0)
        {

        holder.status.setText("ORDER PENDING FOR APPROVAL");
        }
        else if(st==1)
        {
            holder.status.setText("ORDER APPROVED AND READY TO SHIP");
        }
        else if(st==2)
        {
            holder.status.setText("REQUEST FOR RETURN");
        }

        else if(st==3)
        {
            holder.status.setText("ORDER RETURN ACCEPTED");
        }

        if((items_name_refund_myordersadapter.get(position).equals("1"))&&((st==1)))
        {
            holder.return_btn.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.return_btn.setVisibility(View.GONE);
        }

holder.return_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Myorders.call_push_replacement();
    }
});
Log.e("myordersadapter","pr="+pr);
        Integer int_pr_initial=0;
        if(pr.length()>0)
        {
            String[] separated = pr.split(" ");
            Log.e("cart","the value is "+separated[1] );
            String val = separated[1];
            Log.e("cart","the value is "+val );
             int_pr_initial = Integer.valueOf(val);
        }




        Integer tot_count_price = count*int_pr_initial;
        String string_tot_count_price= "Rs: "+String.valueOf(tot_count_price);
        holder.price.setText(string_tot_count_price);






        String Sb_img =items_image_myorders.get(position);
        Log.e("myorderadapter","order_adapter="+order_id_list+position);

        Glide.with(context).load(Sb_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_my);

        holder.return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderid_adapter = order_id_list.get(position);
                String url = "http://dailyestoreapp.com/dailyestore/api/";

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                ResponseInterface mainInterface = retrofit.create(ResponseInterface.class);
                Call<CustomerAppResponseLogin> call = mainInterface.changeOrderStatus(orderid_adapter,2);
                call.enqueue(new Callback<CustomerAppResponseLogin>() {
                    @Override
                    public void onResponse(Call<CustomerAppResponseLogin> call, retrofit2.Response<CustomerAppResponseLogin> response) {
                        CustomerAppResponseLogin listCategoryResponseobject = response.body();
                        int success = Integer.parseInt(response.body().getResponsedata().getSuccess());
                        try {


                            if(success==1)
                            {
                                holder.return_btn.setText("RETURN  REQUESTED");
//                                holder.pd_pending.setTextColor(ContextCompat.getColor(context, white));
//                                holder.pd_pending.setBackgroundColor(ContextCompat.getColor(context, green));
                                Toast.makeText(context,"Received You Request.Our Representative Will Contact You Shortly",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(context,"NO ORDERS",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT).show();
                        }





                    }

                    @Override
                    public void onFailure(Call<CustomerAppResponseLogin> call, Throwable t) {
                        Log.e("error",t.getMessage());
                       // dialog.dismiss();
                    }
                });




            }
        });
        // implement setOnClickListener event on item view.
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // display a toast with person name on item click
//                Intent next= new Intent(context,description.class);
//                context.startActivity(next);
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return items_name_myorders.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,quantityy,price,status,count,cod_available;// init the item view's
        Button return_btn;
        ImageView img_my;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Title_myorder);
            quantityy = (TextView) itemView.findViewById(R.id.qty_myorder);
           price=(TextView) itemView.findViewById(R.id.prce_myorder);
           status=(TextView) itemView.findViewById(R.id.status_myorder);
           count=(TextView) itemView.findViewById(R.id.count_myorder);
           img_my=itemView.findViewById(R.id.img_myorder);
           return_btn=itemView.findViewById(R.id.return_button);
           cod_available=itemView.findViewById(R.id.pay_type_text);
            // get the reference of item view's

        }
    }
    public void filter(String charText) {
        Log.e("texting if","persons="+charText);
        charText = charText.toLowerCase(Locale.getDefault());
        Log.e("texting if2","persons="+charText);
        personNames.clear();
        Iterator itr=personNames.iterator();
        if (charText.length() == 0) {
            Log.e("texting if3","persons="+charText);
            personNames.addAll(lts);
        } else {
            for(int i =0;i<lts.size();i++) {
                Log.e("texting else","persons="+lts.get(i));
                String s = (String) lts.get(i);
                if (s.toLowerCase(Locale.getDefault()).contains(charText)) {
                    personNames.add(s);
                }
            }
        }
        Log.e("text","persons="+personNames);
        notifyDataSetChanged();
    }
    private void push_notif()
    {

        String url_push = "http://dailyestoreapp.com/dailyestore/";
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_push)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        ResponseInterface mainInterface = retrofit.create(ResponseInterface.class);
        Call<PushNotificationadaptertrial> call = mainInterface.pushnotificationtrial();
        call.enqueue(new Callback<PushNotificationadaptertrial>() {
            @Override
            public void onResponse(Call<PushNotificationadaptertrial> call, retrofit2.Response<PushNotificationadaptertrial> response) {




            }

            @Override
            public void onFailure(Call<PushNotificationadaptertrial> call, Throwable t) {

            }
        });


    }

}

