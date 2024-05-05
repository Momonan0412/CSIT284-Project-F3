<?php
include 'connect.php';
// Initialize response array
$response = array();
if(!empty($_POST['username']) && !empty($_POST['password'])){
    $username = $_POST['username'];
    $password = $_POST['password'];
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
            $row = $result->fetch_assoc();
            $hashedPasswordFromDB = $row['password'];
            if (password_verify($password, $hashedPasswordFromDB)) {
                // Login successful
                $response['success'] = true;
                $response['message'] = "Login successful";
                $response['account_id'] = $row['account_id'];
                $response['username'] = $row['username'];
            } else {
                // Incorrect password
                $response['success'] = false;
                $response['message'] = "Login failed. Incorrect password.";
            }
        } else {
            // User not found
            $response['success'] = false;
            $response['message'] = "Login failed. Incorrect username";
        }
    } catch (Exception $e) {
        // Error occurred
        $response['success'] = false;
        $response['message'] = "An error occurred: " . $e->getMessage();
    }
} else {
    // No username or password provided
    $response['success'] = false;
    $response['message'] = "Please provide both username and password";
}
// Send response as JSON
echo json_encode($response, JSON_PRETTY_PRINT);
?>