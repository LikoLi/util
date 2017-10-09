package org.liko.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileSplit {
	private static long EACH_FILE_LENGTH = 400000;
	
	private static final String[] AVAILABLE_SUFFIXS = {".txt", ".csv"};

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("#############################################");
		System.out.println("             TXT OR CSV SPLITER             ");
		System.out.println("                           by Liko          ");
		System.out.println("#############################################");
		File file;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Input file path:");
			String path = scanner.nextLine();
			File tempFile = new File(path);
			if (tempFile.isDirectory() || tempFile.isFile()) {
				file = tempFile;
				break;
			} else {
				System.out.println("Error Path!!!");
			}
		}
		while (true) {
			try {
				System.out.println("Input the file number of rows: ");
				String nextLine = scanner.nextLine();
				EACH_FILE_LENGTH = Long.parseLong(nextLine);
				break;
			} catch (Exception e) {
				System.out.println("Input Error...");
			} 
		}
		File[] files = file.listFiles();
		if(files == null) {
			files  = new File[] {file};
		}
		System.out.println("Please wait...");
		int j = 0;
		for (File fe : files) {
			String suffix = getSuffix(fe.getName());
			if(!isSuffixAvailable(suffix)) continue;
			System.out.println("Process " + fe.getName() + ".");
			File newFile = new File(fe.getPath() + ".new" + j + suffix);
			FileOutputStream fos = new FileOutputStream(newFile, true);
			PrintWriter pw = new PrintWriter(fos);
			scanner = new Scanner(new FileInputStream(fe));
			int i = 0;
			while (scanner.hasNextLine()) {
				if (i >= EACH_FILE_LENGTH) {
					i = 0;
					j++;
					pw.flush();
					newFile = new File(fe.getPath() + ".new" + j + suffix);
					fos = new FileOutputStream(newFile, true);
					pw = new PrintWriter(fos);
				}
				String line = scanner.nextLine();
				pw.append(line + System.getProperty("line.separator"));
				i++;
			}
			pw.flush();
			pw.close();
		}
		System.out.println("Done.");
	}
	
	public static String getSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.'));
	}
	
	private static boolean isSuffixAvailable(String suffix) {
		for(String availableSuffix : AVAILABLE_SUFFIXS) {
			if(availableSuffix.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}
}
