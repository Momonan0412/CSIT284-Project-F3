<?php
include 'connect.php';
// Initialize response array
$response = array();
if(!empty($_POST['username'])){
    $username = $_POST['username'];
    try{
        $connection = $mysqliConnection;

        // Prepare SQL statement
        $query = "SELECT * FROM `dbjavacrud`.`tbluseraccount` WHERE username = ?";
        $stmt = $connection->prepare($query);
        if (!$stmt) {
            throw new Exception("Error preparing SQL statement: " . $connection->error);
        }

        // Bind parameters and execute query
        $stmt->bind_param("s", $username);
        if (!$stmt->execute()) {
            throw new Exception("Error executing SQL statement: " . $stmt->error);
        }
        // Get result and check if user exists
        $result = $stmt->get_result();
        if ($result->num_rows == 1) {
            // Username found
            $response['success'] = true;
        } else {
            // Username not found
            $response['success'] = false;
        }
    } catch (Exception $e) {
        // Error occurred
        $response['success'] = false;
        $response['message'] = "An error occurred: " . $e->getMessage();
    }
} else {
    // No username provided
    $response['success'] = false;
    $response['message'] = "Please provide a username";
}
// Send response as JSON
echo json_encode($response, JSON_PRETTY_PRINT);
?>
