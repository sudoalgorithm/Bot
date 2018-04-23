package bpdc.kunalmalhotra.workspace.cardashboard;

import android.os.AsyncTask;
import android.util.Log;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConversationConnector {
    private final String USERNAME = "f34e11fe-128b-4e99-9a1f-91d44baa9f91";
    private final String PASSWORD = "4emu6OvBtLbj";
    private final String WORKSPACEID = "ea66ff15-7bf6-46ac-8614-f1ac3ae49266";
    private ConversationService service;
    private Session activeSession;
    Map<String, Object> context = null;
    private Double strongestToneScore = 0.0;
    private String strongestToneScoreName = "";

    public ConversationConnector(Session activeSession) {
        this.activeSession = activeSession;
        initialzeDialog();
    }

    public void initialzeDialog() {
        new InitializeDialog().execute((Void[]) null);
    }

    public void sendInput(String text) {
        new SendInput().execute(text);
    }

    private class InitializeDialog extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            service = new ConversationService("2016-09-20");
            service.setUsernameAndPassword(USERNAME, PASSWORD);
            Log.d("ConversationConnector", "Conversation created.");
            return null;
        }
    }

    private class SendInput extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... params) {
            MessageRequest input = new MessageRequest.Builder().inputText(params[0]).build();


            MessageRequest newMessage = new MessageRequest.Builder()
                    .inputText(params[0])
                    .context(context)
                    .build();

            MessageResponse response = service.message(WORKSPACEID, newMessage).execute();
            context = response.getContext();
            System.out.println("---- context json ---- " + context.toString());


            ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
            service.setUsernameAndPassword("aff6e203-0b37-4f5b-a8e5-fc62223b58d5", "gK4joReK68Hv");
            String text = params[0];
            ToneAnalysis tone = service.getTone(text, null).execute();
//            System.out.println("--------- Tone analyzer ------"+tone);
            //getting only 1st element in tone list - emotion tone
            System.out.println("--------- Document Tone ------" + tone.getDocumentTone().getTones().get(0));
            System.out.println("---------------------- Document Tones ------" + tone.getDocumentTone().getTones().get(0).getTones());

            List<ToneScore> documentEmotionToneList = tone.getDocumentTone().getTones().get(0).getTones();
            for (int i = 0; i < documentEmotionToneList.size(); ++i) {
                System.out.println("--------------------------------- Document Tone Score of " + i + ": " + documentEmotionToneList.get(i).getScore());
                if (strongestToneScore < documentEmotionToneList.get(i).getScore()) {
                    strongestToneScore = documentEmotionToneList.get(i).getScore();
                    strongestToneScoreName = documentEmotionToneList.get(i).getName();
                }
            }
            if (strongestToneScore > 0.7)
                System.out.println("---------------------- Output to user/backend?-------- I detected " + strongestToneScoreName + "(" + strongestToneScore + ")");

            else
                System.out.println("---------------------- Output to user/backend?-------- I detected Neutral");


            if (response.getText().isEmpty()) {
                ArrayList<String> none = new ArrayList<String>();
                none.add("No Response");
                activeSession.addOutput(none);
            }
            activeSession.addOutput(response.getText());
            Log.d("ConversationConnector", "Output added");
            return null;
        }
    }

}
