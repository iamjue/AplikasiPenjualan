-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 20 Jan 2019 pada 13.45
-- Versi server: 10.1.31-MariaDB
-- Versi PHP: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_penjualan`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_customer`
--

CREATE TABLE `tb_customer` (
  `id` int(11) NOT NULL,
  `no_penjualan` int(10) DEFAULT NULL,
  `nama_barang` varchar(50) DEFAULT NULL,
  `nama_pembeli` varchar(50) DEFAULT NULL,
  `no_hp` varchar(13) DEFAULT NULL,
  `harga_barang` int(11) DEFAULT NULL,
  `tgl_pembelian` date DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_stok`
--

CREATE TABLE `tb_stok` (
  `idBarang` int(10) NOT NULL,
  `namaBarang` varchar(50) NOT NULL,
  `stokBarang` int(4) NOT NULL,
  `hargaBarang` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_stok`
--

INSERT INTO `tb_stok` (`idBarang`, `namaBarang`, `stokBarang`, `hargaBarang`) VALUES
(2018111, 'Laptop Lenovo Thingpad', 6, 8000000),
(2018122, 'Laptop Hp Pavilon', 1, 6500000),
(2018123, 'Laptop Lenovo Ideapad', 5, 7000000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_users`
--

CREATE TABLE `tb_users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_users`
--

INSERT INTO `tb_users` (`id`, `username`, `password`) VALUES
(1, 'admin@gmail.com', 'admin123'),
(2, 'jue', '12345678');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_customer`
--
ALTER TABLE `tb_customer`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tb_stok`
--
ALTER TABLE `tb_stok`
  ADD PRIMARY KEY (`idBarang`);

--
-- Indeks untuk tabel `tb_users`
--
ALTER TABLE `tb_users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_customer`
--
ALTER TABLE `tb_customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `tb_users`
--
ALTER TABLE `tb_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
