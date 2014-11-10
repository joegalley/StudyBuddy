<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();

// include db connect class
require_once '/home/a8525473/public_html/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all products from products table
$result = mysql_query("SELECT * FROM hillman") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["hillman"] = array();

    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $table = array();
        $table["tid"] = $row["tid"];
        $table["course"] = $row["course"];
        $table["count"] = $row["count"];

        // push single product into final response array
        array_push($response["hillman"], $table);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";

    // echo no users JSON
    echo json_encode($response);
}
?>
