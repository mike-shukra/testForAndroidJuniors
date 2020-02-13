package ru.yogago.testforandroidjuniors.ui.jokes;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


class BackgroundRestApiAction {

    private final String LOG_TAG = "myLog";
    private int jokesCount;
    private ArrayList<String> jokes = new ArrayList<>();

    BackgroundRestApiAction(int count) {
        this.jokesCount = count;
    }

    ArrayList<String> doAction(){
        Log.d(LOG_TAG, "BackgroundRestApiAction - doAction: " + this.hashCode());
        List<Value> values = null;
        try {
            values = getJsonReader(connect());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jokesCount; i++) {
            assert values != null;
            this.jokes.add(values.get(i).getJoke());
        }
        return jokes;
    }

    private List<Value> getJsonReader(HttpURLConnection myConnection) throws IOException {
        Log.d(LOG_TAG, "BackgroundRestApiAction - getJsonReader: " + this.hashCode());
        InputStream responseBody;
        InputStreamReader responseBodyReader;
        JsonReader jsonReader;
        List<Value> values;

        responseBody = myConnection.getInputStream();
        responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
        jsonReader = new JsonReader(responseBodyReader);

        values = readJokes(jsonReader).getValues();

        jsonReader.close();
        myConnection.disconnect();
        return values;
    }
    private HttpURLConnection connect() throws IOException {
        String url = "http://api.icndb.com/jokes/random/" + jokesCount;
        URL point;
        HttpURLConnection myConnection;

        point = new URL(url);

        myConnection = (HttpURLConnection) point.openConnection();
        myConnection.setRequestMethod("GET");
        myConnection.setRequestProperty("Value-Agent", "my-rest-app-v0.1");
        myConnection.setRequestProperty("Accept", "application/testforandroidjuniors");
        return myConnection;
    }

    private Jokes readJokes(JsonReader reader) throws IOException {
        String type = null;
        List<Value> values = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("type")) {
                type = reader.nextString();
            } else if (name.equals("value")) {
                values = readValues(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Jokes(type, values);
    }

    private List<Value> readValues(JsonReader reader) throws IOException {
        List<Value> values = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            values.add(readValue(reader));
        }
        reader.endArray();

        return values;
    }

    private Value readValue(JsonReader reader) throws IOException {
        long id = 0;
        String joke = null;
        List<String> categories = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "joke":
                    joke = reader.nextString();
                    break;
                case "categories":
                    categories = readStringArray(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Value(id, joke, categories);
    }

    private List<String> readStringArray(JsonReader reader) throws IOException {
        List<String> strings = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            strings.add(reader.nextString());
        }
        reader.endArray();
        return strings;
    }

}
