<?php
	include "koneksi_db_penjualan.php";
	
	$id 	= $_POST['id'];
	
	class emp{}
	
	if (empty($id)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Error Mengambil Data"; 
		die(json_encode($response));
	} else {
		$query 	= mysql_query("SELECT * FROM tb_customer WHERE id='".$id."'");
		$row 	= mysql_fetch_array($query);
		
		if (!empty($row)) {
			$response = new emp();
			$response->success = 1;
			$response->id = $row["id"];
			$response->no_penjualan = $row["no_penjualan"];
			$response->nama_barang = $row["nama_barang"];
			$response->nama_pembeli = $row["nama_pembeli"];
			$response->no_hp = $row["no_hp"];
			$response->harga_barang = $row["harga_barang"];
			$response->tgl_pembelian = $row["tgl_pembelian"];
			$response->alamat = $row["alamat"];
			die(json_encode($response));
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error Mengambil Data";
			die(json_encode($response)); 
		}	
	}
?>