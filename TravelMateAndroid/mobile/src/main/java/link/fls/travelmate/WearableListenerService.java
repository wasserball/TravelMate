package link.fls.travelmate;

import com.google.android.gms.wearable.MessageEvent;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by frederik on 27.06.15.
 */
public class WearableListenerService extends com.google.android.gms.wearable.WearableListenerService {

    public final static String ACTION_BOOK_HOTEL = "BOOK_HOTEL";

    private final static String API_PATH = "http://192.168.241.250:8080/api";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        if(messageEvent.getPath().equals(ACTION_BOOK_HOTEL)) {
            String which = messageEvent.getData().toString();
            bookHotel("","","");
        }
    }

    private void bookHotel(String hotelID, String roomId, String guestName) {

        String json = "{\n" +
                "  \"room_id\": \"room2\",\n" +
                "  \"guest\": {\n" +
                "    \"name\": {\n" +
                "      \"nameFirst\": \"Maximilian\",\n" +
                "      \"nameLast\": \"Kleve\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(API_PATH + "/hotels/558ecb886a01239c46bfded5/book")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });


    }
}
