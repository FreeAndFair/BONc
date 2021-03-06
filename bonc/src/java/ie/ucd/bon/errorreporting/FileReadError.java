/**
 * Copyright (c) 2007-2009, Fintan Fairmichael, University College Dublin under the BSD licence.
 * See LICENCE.TXT for details.
 */
package ie.ucd.bon.errorreporting;

import ie.ucd.bon.parser.errors.ParsingError;
import ie.ucd.bon.source.SourceLocation;

import java.io.File;

public class FileReadError extends ParsingError {

  private static final String MESSAGE = "An I/O error occurred whilst reading %s: %s";

  //private final File sourceFile;
  private final String exceptionMessage;

  public FileReadError(File sourceFile, String exceptionMessage) {
    super(SourceLocation.noLocationInFile(sourceFile), true);
    //this.sourceFile = sourceFile;
    this.exceptionMessage = exceptionMessage;
  }

  @Override
  public String getMessage() {
    return String.format(MESSAGE, getLocation().getFilePath(), exceptionMessage);
  }
}
