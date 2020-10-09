# Simple Recommender
This is a project developed as part of a course by Duke University: "Build a recommendation system" offered through Coursera. The materials used for it are licensed under the Creative Commons Attribution 4.0 International License (CC BY 4.0).

A simple movie recommender (think Netflix suggestions but much much simpler and smaller) is implemented here, in particular the collaborative filtering part of it. Collaborative filtering basically means filtering or suggesting things to a user, based on other users that are similar to them in the sense of what they like. Additionally, it is possible to filter the results further by some other criteria, e.g. the year the movie was released, its director, genre etc. 

As part of the course, several CSV files were provided as real world data, based on which a "database" was created. 

# The Most Essential Classes
## MovieDatabase
This class acts as a database storing the movie information loaded from the CSV files. It provides core database functionality like simple lookups of some properties. The backbone of this class is a HashMap to store the information, which provides fast reading operations on it.

## RaterDatabase
This class, on the other hand, acts a database for raters (= users) storing their ratings for different movies. This is also implemented with the use of a HashMap to store all of the users' information. 

## MovieRunnerWithSimilarRatings
This class serves/d as a class to test the implementation of the main functionalities. It contains multiple methods that tests the suggestions for movies and different filters. 

## FourthRatings
This class provides the main functionality behind the recommendation system. It contains methods to calculate average ratings for movies, even using certain filters, and most notably the method to get "suggested" ratings for movies based on the weighted averages of similar users' ratings. These suggestions can also be filtered by some criteria.

## Filter Interface
An interface which promises a method which returns whether a record satisfies the filter condition. The individual filters for genre, year, directors etc. implement this interface.
