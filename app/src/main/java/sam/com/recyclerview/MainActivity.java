package sam.com.recyclerview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView  = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems  = new ArrayList<>();
        Intent  intent = getIntent();
        String title = intent.getStringExtra("query");
        if(title.isEmpty()){
            final ProgressDialog progressDialog =  new ProgressDialog(this);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), " You did not specify movie name", Toast.LENGTH_LONG).show();
        }
        String URL_DATA  = "http://35.167.206.67:8080/FabFlix/AutocompleteServlet?search=" + title;

        loadRecyclerViewData(URL_DATA);



//        for(int i = 0; i < 10; i++){
//            ListItem listItem = new ListItem("heading" + i+1, "Lorem Ipsum dummy Text");
//            listItems.add(listItem);
//        }
//
//        adapter = new MyAdapter(listItems,this);
//
//        recyclerView.setAdapter(adapter);

    }

    private  void loadRecyclerViewData(final String url){
        final ProgressDialog progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Loading data .......");

        StringRequest stringRequest  = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                   // JSONObject jsonObject = new JSONObject(response);
                    JSONArray array =  new JSONArray(response);

                    for(int i =0 ; i < array.length(); i++){
                        JSONObject o  = array.getJSONObject(i);
                        String image = o.getString("banner_url");
                        if(image == null || image.isEmpty()){
                            image = "https://images-na.ssl-images-amazon.com/images/I/51Pd8JJrY2L._AC_UL320_SR216,320_.jpg";
                        }

                        ListItem item  = new ListItem(
                                o.getString("title"),
                                o.getString("director"),
                                image
                        );
                        listItems.add(item);

                    }

                    if(listItems.isEmpty()){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), " No movies found ", Toast.LENGTH_LONG).show();
                    }

                    adapter =  new MyAdapter(listItems,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

}
