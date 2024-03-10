package com.example.tweetReader.services;


import com.example.tweetReader.entities.Tweet;
import com.example.tweetReader.Parser.TweetReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

@RestController
public class TwitterService {
    private Tweet[] allTweets = null;

    // Constructor to initialize and load tweets from JSON file
    public TwitterService(){
        TweetReader reader = new TweetReader();
        allTweets = reader.readerJsonFile();
    }

    /*
        Retrieve all tweets with create time, id, and tweet text.
        Returns a list of maps containing tweet information.
     */
    @GetMapping(value = "/tweets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getAllTweets() {
        // Initialize a list to hold maps containing tweet information
        List<Map<String, Object>> tweetsList = new ArrayList<>();

        // Iterate through all tweets to construct a list of maps, populating the map
        for (Tweet t : allTweets) {
            Map<String, Object> tweetMap = new HashMap<>();
            tweetMap.put("created_at", t.getCreated_at());
            tweetMap.put("id", t.getId());
            tweetMap.put("text", t.getText());
            tweetsList.add(tweetMap);
        }

        // return the list of maps containing the desired tweet information
        return tweetsList;
    } // end of getAllTweets

    /*
        Retrieve all external links in tweets text field.
        Use regular expression to extract links. Group links based by tweet id.
     */
    @GetMapping(value = "/externalLinks", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, List<String>> getExternalLinks(){
        // Initialize a map to hold the external links found in the tweets
        Map<Long, List<String>> externalLinksByTweetId = new HashMap<>();

        try {
            // Defining a regular expression to match URLs
            Pattern pattern = Pattern.compile("(?<=^|\\s)(https?://[^\\s]+)(?=$|\\s)"); // Regex to match URLs

            // Iterate through all tweets to extract external links and add them to the list
            for (Tweet t : allTweets) {
                List<String> links = new ArrayList<>();
                Matcher matcher = pattern.matcher(t.getText());
                while (matcher.find()) {
                    links.add(matcher.group());
                }
                if (!links.isEmpty()) {
                    externalLinksByTweetId.put(t.getId(), links);
                }
            }
        } catch (PatternSyntaxException e) {
            System.err.println("Invalid regular expression: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error occurred while processing tweets: " + e.getMessage());
        }

        // return the map holding external links
        return externalLinksByTweetId;
    } // end of getExternalLinks

    /*
        Retrieve details about a given tweet based on tweet id.
        Tweet details need to be created_at, text, and user screen_name.
        @Param id is the id of the tweet to be retrieved.
        In Postman, put id in the Key field, and tweet id number in the Value field.
     */
    @GetMapping(value = "/tweets/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTweetDetails(@RequestParam long id){
        // Initialize a map to hold tweet details
        Map<String, Object> tweetDetails = new HashMap<>();
        try {
            // Flag to track if the tweet has been found
            boolean tweetFound = false;
            // Iterate through all the tweets and populate the map if the id is found
            for (Tweet t : allTweets) {
                if (t.getId() == id) {
                    tweetFound = true;
                    tweetDetails.put("created_at", t.getCreated_at());
                    tweetDetails.put("text", t.getText());
                    tweetDetails.put("user", t.getUser().getScreen_name());
                    break;
                }
            }
            if (!tweetFound) {
                tweetDetails.put("Error", "Tweet with ID " + id + " not found.");
            }
        } catch (Exception e) {
            tweetDetails.put("Error", "An error occurred while processing the request.");
            e.printStackTrace();
        }

        // return the map holding tweet details
        return tweetDetails;
    } // end of getTweetDetails

    /*
        Retrieve detailed profile information about a Twitter user based on their screen name.
        profile information to be retrieved: location, description, followers_count, and friends_count.
        @Param screen_name is the screen name of the desired Twitter user to be retrieved.
        In Postman Key is screen_name and value is the desired screen_name.
     */
    @GetMapping(value = "/profile/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getDetailedProfile(@RequestParam String screen_name){
        // Initializing a map to store profile details
        Map<String, Object> profileDetails = new HashMap<>();

        try {
            // Flag to track if the profile is found, if not let user know.
            boolean profileFound = false;
            // Iterate through all tweets to find the correct user and populate the map
            for (Tweet t : allTweets) {
                if (t.getUser().getScreen_name().equals(screen_name)) {
                    profileFound = true;
                    profileDetails.put("location", t.getUser().getLocation());
                    profileDetails.put("description", t.getUser().getDescription());
                    profileDetails.put("followers_count", t.getUser().getFollowers_count());
                    profileDetails.put("friends_count", t.getUser().getFriends_count());
                    break;
                }
            }
            if (!profileFound) {
                profileDetails.put("Error", "User with screen name " + screen_name + " not found.");
            }
        } catch (Exception e) {
            profileDetails.put("Error", "An error occurred while processing the request.");
            e.printStackTrace();
        }

        // returning the map containing profile details
        return profileDetails;
    } // end of getDetailedProfile
} // end of TwitterService Class
