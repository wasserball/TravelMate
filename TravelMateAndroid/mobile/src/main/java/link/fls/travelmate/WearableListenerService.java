package link.fls.travelmate;

import com.google.android.gms.wearable.MessageEvent;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by frederik on 27.06.15.
 */
public class WearableListenerService extends com.google.android.gms.wearable.WearableListenerService {

    public final static String ACTION_BOOK_HOTEL = "BOOK_HOTEL";
    public final static String ACTION_CREATE_TASK = "CREATE_TASK";

    public final static String TEST_HOTEL_ID = "558ee16be2037ab74c1aa724";

    private final static String API_PATH = "http://192.168.241.250:8080/api";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        if(messageEvent.getPath().equals(ACTION_BOOK_HOTEL)) {
            String which = messageEvent.getData().toString();
            bookHotel("","","");
        }

        if(messageEvent.getPath().equals(ACTION_CREATE_TASK)) {
            String which = null;
            try {
                which = new String(messageEvent.getData(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            createTask(which);
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
                .url(API_PATH + "/hotels/" + TEST_HOTEL_ID + "/book")
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

    private void createTask(String what) {
        String json = "{\n" +
                "  \"room_id\": \"room2\",\n" +
                "  \"sendDate\": \"2015-06-28T08:00:00.511Z\",\n" +
                "  \"name\": \"" + what + "\"\n" +
                "}";

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(API_PATH + "/hotels/" + TEST_HOTEL_ID + "/service")
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
