<?php

/*
 * Following code will delete a product from table
 * A product is identified by product id (tid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['tid'])) {
    $tid = $_POST['tid'];

    // include db connect class
    require_once '/home/a8525473/public_html/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched tid
    $result = mysql_query("DELETE FROM products WHERE tid = $tid");

    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Table successfully deleted";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No table found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
