//////////////////// Studybud ////////////////////

Project creators:
Aly - ui gal
Ben - arduino hardware guy
Joe - andriod guy - project co-creator
Saul - backend guy - project leader
William - nfc guy

This is the server code that connects to the MySQL database to retain the information about the available tables for students to study at.

The term "hillman" is used because it is a library at the University of Pittsburgh and is the primary location for the study sessions to occur.

Each table id is unique.

--- Bugs ---
There is an error currently that when the program tries to decrement the student count at a table from 1 to 0 the value remains 1.

Last edited: November 9, 2014 at 8:40:53 PM EST
