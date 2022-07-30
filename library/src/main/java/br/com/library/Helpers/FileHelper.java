package br.com.library.Helpers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public interface FileHelper {
  static String getExtension(String fileName) {
    String reversed = StringHelper
      .reverse(fileName);

    return StringHelper
      .reverse(reversed.substring(0, reversed.indexOf(".")));
  }

  static String getEncoding(String fileName) throws Exception {
    try {
      FileReader fileReader = new FileReader(fileName);
      String encoding = fileReader.getEncoding();
      fileReader.close();

      return encoding;
    } catch(Exception e) {
      throw e;
    }
  }

  static String readAsText(String fileName) throws Exception {
    try {
      FileInputStream file = new FileInputStream(fileName);
      InputStreamReader reader = new InputStreamReader(file);
      BufferedReader buffer = new BufferedReader(reader);

      String content = "";
      String line = buffer.readLine();

      while(line != null) {
        content += line.concat("\n");
        line = buffer.readLine();
      }
      buffer.close();
      
      return content;
    } catch(Exception e) {
      throw e;
    }
  }

  static String readFile(String fileName) throws Exception {
    try {
      switch(getExtension(fileName)) {
        case "txt":
          return readAsText(fileName);
        default:
          return "File extension not supported.";
      }
    } catch(Exception e) {
      throw e;
    }
  }
}