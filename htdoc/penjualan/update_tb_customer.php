<?php
	include "koneksi_db_penjualan.php";

	$id 			= $_POST['id'];
	$no_penjualan	= $_POST['no_penjualan'];
	$nama_barang 	= $_POST['nama_barang'];
	$nama_pembeli   = $_POST['nama_pembeli'];
	$no_hp			= $_POST['no_hp'];
	$harga_barang 	= $_POST['harga_barang'];
	$tgl_pembelian	= $_POST['tgl_pembelian'];
	$alamat			= $_POST['alamat'];
	
	class emp{}
	
	if (empty($id) ||empty($no_penjualan) || empty($nama_barang) || empty($nama_pembeli) || empty($no_hp) || empty($harga_barang) ||empty($tgl_pembelian)|| empty($alamat)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysql_query("UPDATE tb_customer SET no_penjualan='".$no_penjualan."', nama_barang='".$nama_barang."', nama_pembeli='".$nama_pembeli."', no_hp='".$no_hp."', harga_barang='".$harga_barang."', tgl_pembelian='".$tgl_pembelian."', alamat='".$alamat."'
			WHERE id='".$id."'");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data berhasil di update";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error update Data";
			die(json_encode($response)); 
		}	
	}
?>