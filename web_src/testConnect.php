<?php
ini_set('display_errors',1);  error_reporting(E_ALL);

print("Starting connection...");

require_once '/home/a8525473/public_html/db_connect.php';

$db = new DB_CONNECT(); // creating class object(will open database connection)

?>
