package com.prosolvr.QED;

import com.prosolvr.QED.utils.CsvXLSXConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages = {"com.prosolvr.QED"})
public class OpenApiQedEndpointsApplication {

	public static ApplicationContext appContext;


	public static void main(String[] args) throws Exception{
		ApplicationContext ctx = SpringApplication.run(OpenApiQedEndpointsApplication.class, args);
		appContext = ctx;

//		FileInputStream fis = new FileInputStream("/Users/ulhaschamkeri/downloads/causes_main.csv");
//		String s = new String(fis.readAllBytes());
//		fis.close();
//		CsvXLSXConverter converter = new CsvXLSXConverter(s);
//		System.out.println(converter.generateFile());
	}
}
