package ecod.dubai.workspace.bankingbot;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ecod.dubai.workspace.R;

public class BankingBotActivity extends Activity implements View.OnClickListener{
    private static BankingBotActivity BBInstance = null;
    private ChatAdapter chatAdapter;
    private List<Object> messages;
    private Button sendButton;
    private EditText inputEditText;
    private ListView messagesListView;
    private Session activeSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_pharmacist);

        BBInstance = this;

        messages = new ArrayList<>();
        sendButton = (Button) findViewById(R.id.sendButton);
        inputEditText = (EditText) findViewById(R.id.inputEditText);
        messagesListView = (ListView) findViewById(R.id.messagesListView);
        chatAdapter = new ChatAdapter(this, messages);
        messagesListView.setAdapter(chatAdapter);
        sendButton.setOnClickListener(this);
        activeSession = new Session();
        inputEditText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            onClick(sendButton);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    public static synchronized BankingBotActivity getBBInstance(){
        if (Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        if (BBInstance == null){
            BBInstance = new BankingBotActivity();
        }
        return BBInstance;
    }

    @Override
    public void onClick(View v) {
        if (!inputEditText.getText().toString().isEmpty()){
            activeSession.addInput(inputEditText.getText().toString());
            inputEditText.setText("");
        }

    }

    public void addMessage(final Object message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messages.add(message);
                chatAdapter.notifyDataSetChanged();
            }
        });

    }
}
