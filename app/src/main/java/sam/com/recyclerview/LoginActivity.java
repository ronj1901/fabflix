package sam.com.recyclerview;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }



    public void connectToTomcat(View view){

        //

        final Map<String, String> params = new HashMap<String, String>();


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        EditText emaileditText = (EditText) findViewById(R.id.emailText);
        EditText passwordeditText = (EditText) findViewById(R.id.passwordTextBox);
        final String email = emaileditText.getText().toString();
        final String pw = passwordeditText.getText().toString();
        final ProgressDialog progressDialog =  new ProgressDialog(this);
        if(email.isEmpty() || pw.isEmpty()){
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), " Please fill up !", Toast.LENGTH_LONG).show();
        }

        params.put("email", email);
        params.put("password", pw);

        String url = "http://52.25.32.130:8080//TomcatForm/servlet/TomcatForm";



        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);

                        if(response.contains("You have entered an invalid email or password")){
                            Intent resultPage = new Intent(LoginActivity.this, LoginActivity.class);
                            resultPage.putExtra("result", response);
                            startActivity(resultPage);
                        }
                        else {
                            Intent resultPage = new Intent(LoginActivity.this, SearchActivity.class);
                            startActivity(resultPage);
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {

                params.put("email", email);
                params.put("password", pw);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(postRequest);


        return ;
    }


}