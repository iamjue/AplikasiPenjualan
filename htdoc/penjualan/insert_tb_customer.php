<?php
	include "koneksi_db_penjualan.php";
	
	$no_penjualan	= $_POST['no_penjualan'];
	$nama_barang 	= $_POST['nama_barang'];
	$nama_pembeli  = $_POST['nama_pembeli'];
	$no_hp			= $_POST['no_hp'];
	$harga_barang 	= $_POST['harga_barang'];
	$tgl_pembelian	= $_POST['tgl_pembelian'];
	$alamat			= $_POST['alamat'];
	
	class emp{}
	
	if (empty($no_penjualan) || empty($nama_barang) || empty($nama_pembeli) || empty($no_hp) || 
		empty($harga_barang) ||empty($tgl_pembelian)|| empty($alamat)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysql_query("INSERT INTO tb_customer (id,no_penjualan,nama_barang,nama_pembeli,no_hp,harga_barang,tgl_pembelian,alamat)
			VALUES(0,'".$no_penjualan."','".$nama_barang."','".$nama_pembeli."','".$no_hp."','".$harga_barang."','".$tgl_pembelian."','".$alamat."')");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data berhasil di simpan";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error simpan Data";
			die(json_encode($response)); 
		}	
	}
?>