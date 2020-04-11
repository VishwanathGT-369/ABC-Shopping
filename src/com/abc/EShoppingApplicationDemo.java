package com.abc;

import com.abc.application.EShoppingApplication;

import java.io.IOException;

public class EShoppingApplicationDemo {

	public static void main(String[] args) throws IOException {

		EShoppingApplication eShoppingApplication = new EShoppingApplication();
		
		eShoppingApplication.onStart();
		
		eShoppingApplication.launchApplication();
		
		eShoppingApplication.beforeDestroy();

	}

}
