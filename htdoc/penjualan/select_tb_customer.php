<?php 
	include "koneksi_db_penjualan.php";
	
	$query = mysql_query("SELECT * FROM tb_customer ORDER BY tgl_pembelian ASC");
	
	$json = array();
	
	while($row = mysql_fetch_assoc($query)){
		$json[] = $row;
	}
	
	echo json_encode($json);
	
	mysql_close($connect);
	
?>