<?php

/*
 * Following code will get single product details
 * A product is identified by product id (tid)
 */

// array for JSON response
$response = array();

// include db connect class
require_once '/home/a8525473/public_html/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_GET["tid"])) {
    $tid = $_GET['tid'];

    // get a product from products table
    $result = mysql_query("SELECT *FROM products WHERE tid = $tid");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $product = array();
            $product["tid"] = $result["tid"];
            $product["course"] = $result["course"];
            $product["count"] = $result["count"];
            // success
            $response["success"] = 1;

            // user node
            $response["product"] = array();

            array_push($response["product"], $product);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";

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
