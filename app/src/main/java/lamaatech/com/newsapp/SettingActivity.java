package lamaatech.com.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.topic_editText)
    public EditText topicEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


    }


    @OnClick(R.id.searchButton)
    protected void onSearchButtonClick() {

        String topic = topicEditText.getText().toString();

        if (topic != null) {

            Intent output = new Intent();
            output.putExtra("topic", topic);

            setResult(Activity.RESULT_OK, output);
            finish();
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
