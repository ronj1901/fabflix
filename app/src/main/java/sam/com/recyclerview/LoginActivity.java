package sam.com.recyclerview;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
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


    private SharedPreferences sharedPreferences;
    private static final String MY_PREF = "myPref";
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText) findViewById(R.id.emailText);
        passwordEditText = (EditText) findViewById(R.id.passwordTextBox);
        String email = getSharedPreferences(MY_PREF, MODE_PRIVATE).getString("email", "");

        emailEditText.setText(email);




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
        //
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREF, MODE_PRIVATE).edit();
        editor.putString("email", email);
        editor.apply();

        final ProgressDialog progressDialog =  new ProgressDialog(this);
        if(email.isEmpty() || pw.isEmpty()){
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), " Please fill up !", Toast.LENGTH_LONG).show();
        }

        params.put("email", email);
        params.put("password", pw);

        // changed the api
        String url = "http://ec2-54-202-112-215.us-west-2.compute.amazonaws.com:8080/WebProject/AndroidLogIn/";




        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);

                        if(response.contains("1")){
                            Intent resultPage = new Intent(LoginActivity.this, LoginActivity.class);
                            resultPage.putExtra("result", response);

                            startActivity(resultPage);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), " usenname or password invalid !", Toast.LENGTH_LONG).show();
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