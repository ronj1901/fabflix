package sam.com.recyclerview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private EditText SearchText;
    private SharedPreferences sharedPreferences;
    private static final String MY_PREF = "myPref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchText  = (EditText) findViewById(R.id.movieInput);

        String query = getSharedPreferences(MY_PREF, MODE_PRIVATE).getString("query", "");
        SearchText.setText(query);



    }



    public void sendQuery(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText queryText  = (EditText) findViewById(R.id.movieInput);
        String query =  queryText.getText().toString();  // a query typed  as input
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREF, MODE_PRIVATE).edit();
        editor.putString("query", query);
        editor.apply();

        intent.putExtra("query",query);
        startActivity(intent);


    }
}
