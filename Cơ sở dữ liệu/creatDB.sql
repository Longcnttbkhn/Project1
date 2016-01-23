SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `timphongtro` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `timphongtro` ;

-- -----------------------------------------------------
-- Table `timphongtro`.`taikhoan`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `timphongtro`.`taikhoan` (
  `IDTaiKhoan` INT NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(45) NOT NULL ,
  `TenHienThi` VARCHAR(45) NOT NULL ,
  `MatKhau` VARCHAR(45) NOT NULL ,
  `TinhTrangHienTai` INT NOT NULL ,
  `SoLanTaoPhong` INT NOT NULL ,
  `SoLanDeXuatPhong` INT NOT NULL ,
  PRIMARY KEY (`IDTaiKhoan`) )
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `timphongtro`.`dangkytaikhoan`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `timphongtro`.`dangkytaikhoan` (
  `MaXacNhan` INT NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `TenHienThi` VARCHAR(45) NOT NULL ,
  `MatKhau` VARCHAR(45) NOT NULL )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `timphongtro`.`phongtro`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `timphongtro`.`phongtro` (
  `IDPhongTro` INT NOT NULL AUTO_INCREMENT ,
  `DiaChi` VARCHAR(150) NOT NULL ,
  `KhuVuc` INT NOT NULL ,
  `DienTich` FLOAT NOT NULL ,
  `GiaPhong` INT NOT NULL ,
  `TinhTrang` INT NOT NULL ,
  `ChiSoTinhTrang` INT NOT NULL ,
  `SoNguoiTrongPhong` INT NOT NULL ,
  `CauHoiBaoMat` INT NOT NULL ,
  `TraLoiCauHoiBaoMat` VARCHAR(45) NOT NULL ,
  `ThongTinBoSung` VARCHAR(150) NOT NULL ,
  `IDTaiKhoanTao` INT NOT NULL ,
  `DiemDanhGiaTB` FLOAT NOT NULL ,
  `TGCoNguoiO` INT NOT NULL ,
  PRIMARY KEY (`IDPhongTro`) ,
  INDEX `fk_phongtro_taikhoan_idx` (`IDTaiKhoanTao` ASC) ,
  CONSTRAINT `fk_phongtro_taikhoan`
    FOREIGN KEY (`IDTaiKhoanTao` )
    REFERENCES `timphongtro`.`taikhoan` (`IDTaiKhoan` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `timphongtro`.`phongdexuat`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `timphongtro`.`phongdexuat` (
  `IDPhongDeXuat` INT NOT NULL AUTO_INCREMENT ,
  `IDTaiKhoanDeXuat` INT NOT NULL ,
  `DiaChi` VARCHAR(150) NOT NULL ,
  `KhuVuc` INT NOT NULL ,
  `DienTich` FLOAT NOT NULL ,
  `GiaPhong` INT NOT NULL ,
  `NgayDeXuat` DATE NULL ,
  `ThongTinBoSung` VARCHAR(150) NULL ,
  PRIMARY KEY (`IDPhongDeXuat`) ,
  INDEX `fk_phongdexuat_taikhoan1_idx` (`IDTaiKhoanDeXuat` ASC) ,
  CONSTRAINT `fk_phongdexuat_taikhoan1`
    FOREIGN KEY (`IDTaiKhoanDeXuat` )
    REFERENCES `timphongtro`.`taikhoan` (`IDTaiKhoan` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `timphongtro`.`taikhoan_o_phongtro`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `timphongtro`.`taikhoan_o_phongtro` (
  `IDPhongTro` INT NOT NULL ,
  `NgayChuyenDen` DATE NOT NULL ,
  `NgayChuyenDi` DATE NULL ,
  `DiemDanhGia` FLOAT NOT NULL ,
  `IDTaiKhoan` INT NOT NULL ,
  PRIMARY KEY (`IDPhongTro`, `IDTaiKhoan`) ,
  INDEX `fk_taikhoan_has_phongtro_phongtro1_idx` (`IDPhongTro` ASC) ,
  INDEX `fk_taikhoan_has_phongtro_taikhoan1_idx` (`IDTaiKhoan` ASC) ,
  CONSTRAINT `fk_taikhoan_has_phongtro_taikhoan1`
    FOREIGN KEY (`IDTaiKhoan` )
    REFERENCES `timphongtro`.`taikhoan` (`IDTaiKhoan` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_taikhoan_has_phongtro_phongtro1`
    FOREIGN KEY (`IDPhongTro` )
    REFERENCES `timphongtro`.`phongtro` (`IDPhongTro` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

USE `timphongtro` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
