package com.example.twitterreader.Parser;

import java.io.*;
import com.example.twitterreader.entities.Tweet;
import com.google.gson.*;
/*
    Created to read tweets from twitterInfo.json using Gson library from Google.
    Create an array of tweets parsed from the JSON file.
 */
public class TweetReader {
    public Tweet[] readerJsonFile(){
        GsonBuilder build = new GsonBuilder();
        build.setPrettyPrinting(); // Changing the JSON output to be the pretty printing format
        Gson formatter = build.create();

        // Initialize file and reader
        File file = new File("src/main/resources/twitterInfo.json");
        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); // Throw an exception if file not found
        }

        JsonArray tweets = (JsonArray) JsonParser.parseReader(reader); // parse JSON file into JsonArray
        String json = tweets.toString(); // Convert JsonArray to a string
        return formatter.fromJson(json, Tweet[].class); // Deserialize JSON string to an array of Tweet objects using Gson
    }
}
