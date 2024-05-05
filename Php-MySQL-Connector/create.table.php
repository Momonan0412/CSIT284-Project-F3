<?php
include 'connect.php';
// Initialize response array
$response = array();
$resultOne  = false; 
$resultTwo = false; 
$resultThree = false; 
$connection = $mysqliConnection;
$queryCheckTableExistsOne = "SELECT 1 FROM `dbjavacrud`.`tbluseraccount` LIMIT 1";
$tableExistsOne = mysqli_query($connection, $queryCheckTableExistsOne);

$queryCheckTableExistsTwo = "SELECT 1 FROM `dbjavacrud`.`tbluserprofile` LIMIT 1";
$tableExistsTwo = mysqli_query($connection, $queryCheckTableExistsTwo);

$queryCheckTableExistsThree = "SELECT 1 FROM `dbjavacrud`.`tbljapanesevocabulary` LIMIT 1";
$tableExistsThree = mysqli_query($connection, $queryCheckTableExistsThree);

try {
    $queryOne = "CREATE TABLE IF NOT EXISTS `dbjavacrud`.`tbluseraccount` (" .
                "`account_id` INT NOT NULL AUTO_INCREMENT," .
                "`username` VARCHAR(255) NOT NULL," .
                "`password` VARCHAR(255) NOT NULL," .
                "PRIMARY KEY (`account_id`)) ENGINE = InnoDB;";
    if($tableExistsOne === false){
        $resultOne = mysqli_query($connection, $queryOne);
    }            
    $queryTwo = "CREATE TABLE IF NOT EXISTS `dbjavacrud`.`tbluserprofile` (" .
                "`profile_id` INT NOT NULL AUTO_INCREMENT," .
                "`account_id` INT NOT NULL," .
                "`firstname` VARCHAR(255) NOT NULL," .
                "`lastname` VARCHAR(255) NOT NULL," .
                "`gender` VARCHAR(10) NOT NULL," .
                "`email` VARCHAR(255) NOT NULL," .
                "`student_id` VARCHAR(255) NOT NULL," .
                "`prog_languages` VARCHAR(255) NOT NULL," .
                "PRIMARY KEY (`profile_id`)," .
                "FOREIGN KEY (`account_id`) REFERENCES `tbluseraccount`(`account_id`) " .
                "ON DELETE CASCADE" .
                ") ENGINE = InnoDB;";
    if($tableExistsTwo === false){
        $resultTwo = mysqli_query($connection, $queryTwo);
    }
    $queryThree = "CREATE TABLE IF NOT EXISTS `dbjavacrud`.`tbljapanesevocabulary` (" .
                "`kanji_id` INT NOT NULL AUTO_INCREMENT," .
                "`level` VARCHAR(255) NOT NULL," .
                "`kanji` VARCHAR(255) NOT NULL," .
                "`furigana` VARCHAR(255) NOT NULL," .
                "`english` VARCHAR(255) NOT NULL," .
                "PRIMARY KEY (`kanji_id`)," .
                "UNIQUE (`kanji`)" .  // Add UNIQUE constraint to kanji column
                ") ENGINE = InnoDB;";
    
    if($tableExistsThree === false){
        $resultThree = mysqli_query($connection, $queryThree);
    }
    // Check the result of each query and add status messages to the response array
    $response['success'] = true;
    $response['message'] = "Tables creation status";
    $response['tables'] = array(
        "tbluseraccount" => ($resultOne) ? "Table user account created successfully" : "Error creating table user account: " . mysqli_error($connection),
        "tbluserprofile" => ($resultTwo) ? "Table user profile created successfully" : "Error creating table user profile: " . mysqli_error($connection),
        "tbljapanesevocabulary" => ($resultThree) ? "Table japanese vocabulary created successfully" : "Error creating table japanese vocabulary: " . mysqli_error($connection)
    );
} catch (Exception $e) {
    // Exception occurred, set error message in response
    $response['success'] = false;
    $response['errorMessage'] = "An error occurred: " . $e->getMessage();
}
// Encode response array to JSON and echo it
echo json_encode($response, JSON_PRETTY_PRINT);
?>