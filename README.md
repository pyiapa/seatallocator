# seatallocator #
- - - -

## Author ##

Name:  Paris Yiapanis
Email: pyiapa@gmail.com


## Project Description ##


This project determines the best sitting arrangements for a flight. 
It takes as an input a file of passengers and plane dimensions (as a command line argument) 
and prints the results to standard output.
The output is the best passenger sitting configuration as well as overall satisfaction based on passenger preferences.

## Assumptions ##


* Group travelers with NO window seat preference get full satisfaction (i.e. score of 1) if they seat with their group. 

* Group travelers with window seat preference get full satisfaction only if they seat with their group and their window 
preference is satisfied. 

* Group travelers with window seat preference and separated will only get 0.5 if they manage to seat in a window, otherwise
no satisfaction (i.e. score of 0). Similarly they get only 0.5 if they are seated in a window but separated from their group.

* Group travelers with NO window seat preference and separated from their group will get 0 satisfaction

* Group travelers with more than 1 window preferences are considered fully satisfied as long as they seat in the same row 
as their group and their window preference is satisfied (even if someone else is seated in between). For example, assume there
is a group of 2 travelers and both want window seat. If the plane has rows size 3 then they must be seated with a gap in the
middle or another passenger in between. Nevertheless, they still count as satisfied as they are seated in the same row.

* If a group of travelers is to be split, then all travelers from that group will be dissatisfied. From that point onwards
 they can only get 0.5 satisfaction if they have a window preference and they manage to get one.
 
* If in a group of travelers there are more than 2 window seat preferences, we satisfy two and for the rest prioritize
their group preference over their window preference. Otherwise, if we split them to seat in a window in another row,  
then the rest of their group will also be dissatisfied.
 
* Single travelers with NO window seat preference always get full satisfaction so long as there is a seat available.
* Single travelers with window preference, get 1 if their preference is satisfied, otherwise 0.

* When the plane is over-subscribed (i.e. more passengers than sitting capacity) then, all passengers in the file are 
considered for the optimization. At the end we maintain only the passengers that fit in the available space and yield
higher total satisfaction.

* When plane is over subscribed, passengers that didn't manage to seat will count negatively towards the overall satisfaction.

* Any form of invalid input will terminate the application.


## General Approach ##


This is an NP-complete problem and I have approached it using a greedy approximation algorithm. I see this as a variation
of the bin packing problem which tries to fit objects of different volumes in a finite number of bins. My approach is a 
variation of the First-fit Decreasing algorithm for the bin packing problem.

Larger groups of travelers (but that are able to fit in a row) are considered first as they are more constraint and curry many
satisfaction points. If two groups have the same size, then the group with more window preferences is considered first.
Thus, we operate by first sorting groups of travelers to be seated in decreasing order by their sizes, and then inserting each 
group into the first row in the plane with sufficient remaining space.
My approach operates with virtual plane rows, allowing the case of over-subscripted plane to be considered without any
modification to the algorithm. After configuration, the virtual rows are cut-off to the size of the plane rows. The top
n rows (n being number of plane rows) of the virtual rows yield approximately the maximum possible satisfaction. Please,
read the next section for more details.


## Detailed Approach ##


1. Read and parse input file. The passengers are represented as a 2D list. Each element in the 1D is a list of
 group travelers. All passengers are read and considered at this point regardless of the actual size of the plane.
 
2. Sort each passenger group in descending order of window preference. This will make it easier later when trying to
 seat passengers from a given group. Within a group, we'll first sit passengers with window preference.
 
3. Sort groups in descending order of size. That is, larger groups of travelers come first in the list. If two groups 
have the same size, the group with higher number of window preferences will come first.

4. Open/Create a new virtual row in the plane

5. Grab a passenger group from the top of the list

6. Search for a suitable row to fit the group in question. A suitable row ideally should be able to fit the entire
group and also satisfy any window preferences

7. If a suitable row is found then we sit the group there. 

8. If a suitable row is not found then open a new row and try again. 

9. If group didn't fit because it's larger than the row size then we split the group (which means that everyone in 
the group is dissatisfied) and put it in another list to deal with them later, essentially placing them at the bottom 
of our priority as they can yield very little satisfaction at this point.
 
10. Repeat steps 5-9 until all passengers that their group didn't split (and originally single passengers) are seated.

11. Sort the list of passengers that they were separated from the group by window preference in descending order.
This aims to get at least a bit of satisfaction from the separated passengers with window preference.

13. We perform steps 6-8 until all passengers are seated. 

14. Note that at this point we may have a sitting configuration larger the the available rows in 
the plane (if the flight was over-subscribed).

15. Cut-off the excess rows based on the actual number of plane rows and return it as the final seating configuration. If
actual rows is n then we get only the first n virtual rows.

16. Calculate the total satisfaction.


## Classes ##


* Main - the driver of the application
* Passenger - holds information about a traveler
* RowDescriptor - provides information about a given row in the plane
* SittingArrangement- holds the result (the final sitting arrangement and overall satisfaction)
* PassengerParser - parses the input
* SeatAllocator - the heart of the application, computes sitting arrangement and satisfaction


## Building and running ##


* The project was built using maven 3.3.9 and Java 1.8.0_91

* To build unzip the folder seatallocator and build with maven through command line or import to eclipse
as a maven project.

* There are several sample inputs that can be found under src/test/resources/input

* Below I also provide a link to the final jar file including sample inputs. To run the application,
cd inside the directory of the provided jar file and type the following command in the 
terminal: 

`code()`

java -jar seatallocator-0.0.1-SNAPSHOT.jar Input.Normal

Replace the input file with one of the other files in the directory if needed

* link to application: https://www.dropbox.com/s/d3c73c07wckor29/seatallocator_jar_and_inputs.zip?dl=0


## Tests ##


* There are two classes that implement a total of 16 test cases. 7 cases of various valid inputs and 9 cases
that test the application will not crash and behave as appropriate on invalid input. 
