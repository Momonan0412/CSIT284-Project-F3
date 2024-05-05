<?php
include 'connect.php';
// Initialize response array
$response = array();
if (!empty($_POST['username']) && !empty($_POST['password'])) {
    // Extract POST data
    $username = $_POST['username'];
    $password = $_POST['password'];
    try {
        // Database operation
        $query = "INSERT INTO `dbjavacrud`.`tbluseraccount` (username, password) VALUES (?, ?)";
        $stmt = $mysqliConnection->prepare($query);
        // Hash the password
        $hashedPassword = password_hash($password, PASSWORD_DEFAULT);
        $stmt->bind_param("ss", $username, $hashedPassword);
        if ($stmt && $stmt->execute()) {
            // User account created successfully
            $response['success'] = true;
            $response['message'] = "User account created successfully";
        } else {
            // Error occurred during user account creation
            $response['success'] = false;
            $response['message'] = "Error: Unable to create user account";
        }
        if ($stmt) {
            $stmt->close();
        } else {
            // Error preparing statement
            $response['success'] = false;
            $response['message'] = "Error: Unable to prepare statement";
        }
    } catch (Exception $e) {
        // Exception occurred, set error message in response
        $response['success'] = false;
        $response['message'] = "An error occurred: " . $e->getMessage();
    }
} else {
    // No data received, set error message in response
    $response['success'] = false;
    $response['message'] = "No data received";
}
// Encode response array to JSON and echo it
echo json_encode($response, JSON_PRETTY_PRINT);
?>
