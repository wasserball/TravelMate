package link.fls.travelmate.async;

import android.content.Context;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by frederik on 27.06.15.
 */
public class SendWearMessageTask extends ApiClientAsyncTask<String, String, Integer> {

    public SendWearMessageTask(Context context) {
        super(context);
    }

    @Override
    protected Integer doInBackgroundConnected(String[] params) {
        String path = params[0];
        String message = params[1];

        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(getGoogleApiClient()).await();
        for (Node node : nodes.getNodes()) {
            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                    getGoogleApiClient(), node.getId(), path, message.getBytes()).await();
        }

        return null;
    }

}
