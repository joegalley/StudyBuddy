<?php
$mysql_host = "mysql1.000webhost.com";
$mysql_database = "a8525473_rfid";
$mysql_user = "a8525473_admin";
$mysql_password = "x@12345";

$con=mysqli_connect($mysql_host,$mysql_user,$mysql_password,$mysql_database);
$sql="CREATE TABLE table1(Username CHAR(30),Password CHAR(30),Role CHAR(30))";
if (mysqli_query($con,$sql))
{
   echo "Table have been created successfully";
}
?>
