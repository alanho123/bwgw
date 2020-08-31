package com.example.bwgw;

import com.example.bwgw.util.QRCodeUtil;
import com.google.zxing.WriterException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class BwgwApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BwgwApplication.class, args);
	}

	@Bean
	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}

	@Override
	public void run(String... args) throws Exception {
		String filePath = "/Users/jason/breeze/git/images/breeze-rewards.png";
		int size = 250;
		String fileType = "png";
		File qrFile = new File(filePath);
		try {
//			QRCodeUtil.createQRImage(qrFile, "twitter://user?id=12345", size, fileType);
			QRCodeUtil.createQRImage(qrFile, "breeze_rewards://user?id=12345", size, fileType);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
