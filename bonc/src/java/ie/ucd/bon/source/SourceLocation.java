/**
 * Copyright (c) 2007-2009, Fintan Fairmichael, University College Dublin under the BSD licence.
 * See LICENCE.TXT for details.
 */
package ie.ucd.bon.source;

import ie.ucd.bon.Main;

import java.io.File;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;

public class SourceLocation implements Comparable<SourceLocation> {

  public static final int UNKNOWN = -1;

  public static final SourceLocation NO_LOCATION = new SourceLocation(null, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN);
  public static SourceLocation noLocationInFile(File file) {
    return new SourceLocation(file, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN);
  }
  public static final String STDIN_TEXT = "<stdin>";

  protected final File sourceFile;
  protected int lineNumber;
  protected int charPositionInLine;

  protected int absoluteCharPositionStart;
  protected int absoluteCharPositionEnd;
  protected int endLineNumber;

  public SourceLocation(File sourceFile, int lineNumber,
      int charPositionInLine, int absoluteCharPositionStart,
      int absoluteCharPositionEnd, int endLineNumber
  ) {
    this.sourceFile = sourceFile;
    this.lineNumber = lineNumber;
    this.charPositionInLine = charPositionInLine;
    this.absoluteCharPositionEnd = absoluteCharPositionEnd;
    this.absoluteCharPositionStart = absoluteCharPositionStart;
    this.endLineNumber = endLineNumber;
  }
  
  public SourceLocation(SourceLocation loc) {
    this(loc.sourceFile, loc.lineNumber, loc.charPositionInLine, loc.absoluteCharPositionStart, loc.absoluteCharPositionEnd, loc.endLineNumber);
  }

  public SourceLocation(Token t, File sourceFile) {
    this(t, t, sourceFile);
  }

  public SourceLocation(Token start, Token end, File sourceFile) {
    this.sourceFile = sourceFile;
    //System.out.println("start token: " + start);
    this.lineNumber = start.getLine();
    this.endLineNumber = end.getLine();
    this.charPositionInLine = start.getCharPositionInLine();

    //System.out.println("SourceLoc from token: " + start.getText());

    if (start instanceof CommonToken) {
      CommonToken cToken = (CommonToken)start;
      this.absoluteCharPositionStart = cToken.getStartIndex();
    } else {
      Main.logDebug(("Not CommonToken. " + start.getClass()));
      if (Main.isDebug()) {
        Thread.dumpStack();
      }
      this.absoluteCharPositionStart = UNKNOWN;
    }

    
    if (end instanceof CommonToken) {
      CommonToken cToken = (CommonToken)end;
      this.absoluteCharPositionEnd = cToken.getStopIndex();
    } else {
      Main.logDebug("Not CommonToken. " + (end == null ? null : end.getClass()));
      if (Main.isDebug()) {
        Thread.dumpStack();
      }
      this.absoluteCharPositionEnd = UNKNOWN;
    }
  }

  public SourceLocation(SourceLocation start, SourceLocation end) {
    this(start.sourceFile, start.lineNumber, start.charPositionInLine, start.absoluteCharPositionStart, end.absoluteCharPositionEnd, end.endLineNumber);
  }

  public void setStartToken(Token start) {
    this.lineNumber = start.getLine();
    this.charPositionInLine = start.getCharPositionInLine();
    if (start instanceof CommonToken) {
      this.absoluteCharPositionStart = ((CommonToken)start).getStartIndex();
    } else {
      this.absoluteCharPositionStart = -1;
    }
  }

  public void setEndToken(Token end) {
    if (end instanceof CommonToken) {
      this.absoluteCharPositionEnd = ((CommonToken)end).getStopIndex();
    } else {
      this.absoluteCharPositionEnd = -1;
    }
  }

  public final File getSourceFile() {
    return sourceFile;
  }

  public final String getSourceFilePath() {
    return sourceFile != null ? sourceFile.getPath() : "stdin";
  }

  public final String getFileName() {
    return sourceFile == null ? null : sourceFile.getName();
  }

  public final int getLineNumber() {
    return lineNumber;
  }

  public final int getEndLineNumber() {
    return endLineNumber;
  }

  public final int getCharPositionInLine() {
    return charPositionInLine;
  }

  public final int getAbsoluteCharPositionStart() {
    return absoluteCharPositionStart;
  }

  public final int getAbsoluteCharPositionEnd() {
    return absoluteCharPositionEnd;
  }

  @Override
  public String toString() {
    return "File: " + (sourceFile != null ? sourceFile.getPath() : "stdin") + ", line: " + lineNumber + ", char: " + charPositionInLine;
  }

  public String getFilePath() {
    return getFilePath(sourceFile);
  }

  public static String getFilePath(File file) {
    if (file == null) {
      return STDIN_TEXT;
    } else {
      return file.getPath();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof SourceLocation)) {
      return false;
    }
    SourceLocation other = (SourceLocation)obj;
    if (this.sourceFile != null && !this.sourceFile.equals(other.sourceFile)) {
      return false;
    }
    return this.lineNumber == other.lineNumber && this.charPositionInLine == other.charPositionInLine
    && this.absoluteCharPositionStart == other.absoluteCharPositionStart
    && this.absoluteCharPositionEnd == other.absoluteCharPositionEnd;
  }

  public int compareTo(SourceLocation o) {
    if (o == null) {
      return 1;
    }
    int fileCompare = this.compareFile(o);
    if (fileCompare != 0) {
      return fileCompare;
    }

    int lineNumberCompare = this.compareLineNumber(o);
    if (lineNumberCompare != 0) {
      return lineNumberCompare;
    }

    int charPositionCompare = this.compareCharacterPosition(o);
    if (charPositionCompare != 0) {
      return charPositionCompare;
    }

    return 0;
  }


  private int compareFile(final SourceLocation o) {
    //General errors not involving a specific file...
    if (sourceFile == null) {
      return o.sourceFile == null ? 0 : -1;
    } else if (o.sourceFile == null) {
      return 1;
    }

    //Compare file name
    return this.getFileName().compareTo(o.getFileName());
  }

  private int compareLineNumber(final SourceLocation o) {
    //Compare line number
    if (this.getLineNumber() == UNKNOWN) {
      return o.getLineNumber() == UNKNOWN ? 0 : -1;
    } else if (o.getLineNumber() == UNKNOWN) {
      return 1;
    } else {
      return this.getLineNumber() - o.getLineNumber();
    }
  }

  private int compareCharacterPosition(final SourceLocation o) {
    //Compare character position
    if (this.getCharPositionInLine() == UNKNOWN) {
      return o.getCharPositionInLine() == UNKNOWN ? 0 : -1;
    } else if (o.getCharPositionInLine() == UNKNOWN) {
      return 1;
    } else {
      return this.getCharPositionInLine() - o.getCharPositionInLine();
    }
  }
  
  public boolean isRealLocation() {
    return absoluteCharPositionStart != UNKNOWN 
        && absoluteCharPositionEnd != UNKNOWN
        && charPositionInLine != UNKNOWN
        && lineNumber != UNKNOWN;
  }

  @Override
  public SourceLocation clone() {
    return new SourceLocation(this);
  }
  
  public MutableSourceLocation mutableClone() {
    return new MutableSourceLocation(this);
  }
  
  public SourceLocation shortenToLength(int newLength) {
    return mutableClone().setAbsoluteCharPositionEnd(absoluteCharPositionStart + newLength);
  }

  @Override
  public int hashCode() {
    return (this.sourceFile == null ? 0 : this.sourceFile.hashCode()) + (this.lineNumber << 16) + (this.absoluteCharPositionStart << 8) + this.charPositionInLine;
  }
}
