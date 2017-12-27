package bpdc.kunalmalhotra.workspace.cardashboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private List<Object> messageItems;


    public ChatAdapter(Context context, List<Object> messageItems) {
        this.context = context;
        this.messageItems = messageItems;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        //Setting message alignment
        if (messageItems.get(position) instanceof Input) {
            convertView = mInflater.inflate(R.layout.list_item_message_right,
                    null);
        } else if (messageItems.get(position) instanceof Output){
            convertView = mInflater.inflate(R.layout.list_item_message_left,
                    null);
        }

        TextView textMsg = (TextView) convertView.findViewById(R.id.textMessageView);
        textMsg.setText(messageItems.get(position).toString());

        return convertView;
    }
}
