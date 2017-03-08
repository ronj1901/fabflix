package sam.com.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }


    public void sendQuery(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText queryText  = (EditText) findViewById(R.id.movieInput);
        String query =  queryText.getText().toString();  // a query typed  as input
        intent.putExtra("query",query);
        startActivity(intent);


    }
}
