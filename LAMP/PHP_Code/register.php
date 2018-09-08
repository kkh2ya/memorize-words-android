<?php

$server_name = "localhost";
$mysql_user = "root";
$mysql_pass = "root";
$db_name = "memorize_english";

$con = mysqli_connect($server_name, $mysql_user, $mysql_pass, $db_name);

$words = $_POST["words"];
$answer = $_POST["answer"];
$sentence = $_POST["sentence"];
$sentence_answer = $_POST["sentence_answer"];

$statement = mysqli_prepare($con, "insert into userlist values(?,?,?,?)");
mysqli_stmt_bind_param($statement, "ssss", $words, $answer, $sentence, $sentence_answer);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = true;

echo json_encode($response);

?>
