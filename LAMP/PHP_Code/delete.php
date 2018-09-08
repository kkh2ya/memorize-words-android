<?php

$server_name = "localhost";
$mysql_user = "root";
$mysql_pass = "root";
$db_name = "memorize_english";

$con = mysqli_connect($server_name, $mysql_user, $mysql_pass, $db_name);

$words = $_POST["words"];

$statement = mysqli_prepare($con, "delete from userlist where words = ?");

mysqli_stmt_bind_param($statement, "s", $words);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = true;

echo json_encode($response);

?>
