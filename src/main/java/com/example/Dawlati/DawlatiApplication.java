package com.example.Dawlati;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class DawlatiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DawlatiApplication.class, args);
	}

}


//cd C:\Program Files (x86)\Google\Chrome\Application
//chrome.exe --disable-web-security --disable-gpu --user-data-dir=~/chromeTemp
//dawlati_portal1