
# SMSMaps

# What it does
SMS Maps returns directions between two points given by the user, all via SMS.

# How it was built
We Used Twilio to create a workflow between a user’s phone (SMS), a program that pings Google Maps API for directions, and a system to send the directions back via SMS. Also, we used Google Maps API to implement the detailed instructions for each route. This tool allows for a change in a variety of parameters such as: mode of transportation, optimization based on the traveling salesman problem, origin/destination, and avoidances (tolls, creeks, etc.).

