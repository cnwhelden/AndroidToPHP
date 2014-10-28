<?php 

	$json = file_get_contents('php://input');
	$data = json_decode($json);

	$username = $data->username;
	$password = $data->password;

	// TODO: Query database and change if-else block
	$db_username = Eric;
	$db_password = pw1234;

	if ($username != $db_username) {
		$returnVal = array(returncode => 1, username => $username);
	} 
	else if ($password != $db_password) {
		$returnVal = array(returncode => 2, password => $password);
	} 
	else {
		$returnVal = array(returncode => 0);
	}

	echo json_encode($returnVal);

?>
