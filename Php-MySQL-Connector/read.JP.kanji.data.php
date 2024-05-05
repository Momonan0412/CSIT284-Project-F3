<?php
include 'connect.php';
// Initialize response array
$response = array();
if(!empty($_POST['level'])){
    $level = $_POST['level'];
    // $level = "Level 1";
    $connection = $mysqliConnection;
    $connection->set_charset("utf8");
    $query = "SELECT * FROM `dbjavacrud`.`tbljapanesevocabulary` WHERE level = ?";
    $stmt = $connection->prepare($query);
    if (!$stmt) {
        throw new Exception("Error preparing SQL statement: " . $connection->error);
    }
        
    // Bind parameters and execute query
    $stmt->bind_param("s", $level);
    if (!$stmt->execute()) {
        throw new Exception("Error executing SQL statement: " . $stmt->error);
    }
    // Get result and loop through rows
    $result = $stmt->get_result();
    while ($row = $result->fetch_assoc()) {
        // Do something with each row, for example, add it to the response array
        $response['kanjis'][] = $row; // corrected this line
    }
    // Output the response array (or handle it in any other way)
    // header('Content-Type: application/json; charset=utf-8');
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
}
?>