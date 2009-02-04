package ie.ucd.bon.typechecker.errors;

import ie.ucd.bon.source.SourceLocation;
import ie.ucd.bon.typechecker.TypeCheckingError;

public class StaticTypeCannotHaveGenericsHere extends TypeCheckingError {

  private static final String message = "Static component %s cannot have generics %s";
  
  private final String staticComponentName;
  private final String locationDescription;
  
  public StaticTypeCannotHaveGenericsHere(SourceLocation loc, String staticComponentName, String locationDescription) {
    super(loc);
    this.staticComponentName = staticComponentName;
    this.locationDescription = locationDescription;
  }

  @Override
  public String getMessage() {
    return String.format(message, staticComponentName, locationDescription);
  }

}