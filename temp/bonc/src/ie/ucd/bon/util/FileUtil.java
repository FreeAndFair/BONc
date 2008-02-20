/**
 * Copyright (c) 2007, Fintan Fairmichael, University College Dublin under the BSD licence.
 * See LICENCE.TXT for details.
 */
package ie.ucd.bon.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileUtil {

  public static boolean checkDirExists(File dir) {
    if (dir.isDirectory()) {
      return true;
    } else {
      return dir.mkdirs();
    }
  }

  /**
   * Checks if the File represented by the given String actually exists,
   * and creates any necessary subdirectories
   * @param dirPath The path of the file to check
   * @return A boolean indicating if the file exists.
   */
  public static boolean checkDirExists(String dirPath) {
    return checkDirExists(new File(dirPath));
  }

  public static Reader getResourceReader(String filePath) {
    InputStream istream = new FileUtil().getClass().getClassLoader().getResourceAsStream(filePath);
    if (istream != null) {
      BufferedReader br = new BufferedReader(new InputStreamReader(istream));
      return br;    
    } else {
      return null;
    }
  }

}
