# Twitter Reader

This program provides a set of RESTful APIs to interact with Twitter data stored in a JSON file. It allows users to retrieve tweets, extract external links from tweets, and obtain detailed information about tweets and Twitter users.

## Getting Started

To use this program, follow these steps:
1. Import the project into your desired IDE that supports Spring Boot.
2. Make sure Java and Spring Boot is installed.
3. Run the TwitterReaderApplication via Spring Boot.
4. To test APIs it is suggested to use Postman.

## Endpoints

### 1. /tweets
- **Method:** GET
- **Description:** Retrieves all tweets with creation time, ID, and tweet text.
- **Parameters:** None
- **Example:** ```http://localhost:8080/tweets```

### 2. /externalLinks
- **Method:** GET
- **Description:** Retrieves all external links in tweets' text fields.
- **Parameters:** None
- **Example:** ```http://localhost:8080/externalLinks```

### 3. /tweets/details
- **Method:** GET
- **Description:** Retrieves details about a given tweet based on tweet ID.
- **Parameters:** id (tweet ID)
- **Example:** ```http://localhost:8080/tweets/details?id=311964132205268992```

### 4. /profile/details
- **Method:** GET
- **Description:** Retrieves detailed profile information about a Twitter user based on their screen name.
- **Parameters:** screen_name (Twitter user's screen name)
- **Example:** ```http://localhost:8080/profile/details?screen_name=zephoria```

## Files
- `TwitterService.java`: Contains RESTful API endpoints for interacting with Twitter data.
- `TweetReader.java`: Parses tweets from a JSON file using Gson library.
- `Tweet.java`: Defines the structure of a tweet.
- `User.java`: Defines the structure of a Twitter user.
- `twitterInfo.json`: Contains tweets in JSON format.

## Dependencies
- Spring Boot: For building and using RESTful APIs
- Gson: For parsing JSON data, Gson is made by Google.

## Screenshots
- ### /tweets 
   ![](https://github.com/Untraditional/Tweet-Reader/blob/main/Screenshots/tweets%20api.png)

- ### /externalLinks
   ![](https://github.com/Untraditional/Tweet-Reader/blob/main/Screenshots/external%20links%20api.png)
  
- ### /tweets/details
  ![](https://github.com/Untraditional/Tweet-Reader/blob/main/Screenshots/tweet%20details%20api.png)

- ### /profile/details
   ![](https://github.com/Untraditional/Tweet-Reader/blob/main/Screenshots/profile%20details%20api.png)
