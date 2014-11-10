<?php

/*
 * Following code will update a product information
 * A product is identified by product id (tid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['tid']) && isset($_POST['course']) && ($_POST['count'])) {

    $tid = $_POST['tid'];
    $course = $_POST['course'];
    $count = $_POST['count'];

    // include db connect class
    require_once '/home/a8525473/public_html/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

	//hillman is the table name
    // mysql update row with matched tid
    $result = mysql_query("UPDATE hillman SET course = '$course', count = '$count' WHERE tid = $tid");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Table successfully updated.";

        // echoing JSON response
        echo json_encode($response);
    } else {

    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
