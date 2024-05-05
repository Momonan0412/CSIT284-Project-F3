<?php
include 'connect.php';
// Initialize response array
$response = array();
if (!empty($_POST['level']) && !empty($_POST['kanji']) && !empty($_POST['furigana']) && !empty($_POST['english'])) {
    $level = $_POST['level'];
    $kanji = $_POST['kanji'];
    $furigana = $_POST['furigana'];
    $english = $_POST['english'];
    try {
        $query = "INSERT INTO `dbjavacrud`.`tbljapanesevocabulary` (level, kanji, furigana, english) VALUES (?, ?, ?, ?)";
        $stmt = $mysqliConnection->prepare($query);
        $stmt->bind_param("ssss", $level, $kanji, $furigana, $english);
        $stmt->execute();
        $stmt->close();

        // Set success message in response
        $response['success'] = true;
        $response['message'] = "Data inserted successfully!";
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
