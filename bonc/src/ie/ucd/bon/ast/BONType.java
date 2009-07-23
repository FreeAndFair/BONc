package ie.ucd.bon.ast;

import ie.ucd.bon.source.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BONType extends Type {

  private static Map<String,BONType> typeMap = new HashMap<String,BONType>();

  public BONType(String identifier, List<Type> actualGenerics,
      String fullString, SourceLocation location) {
    super(identifier, actualGenerics, fullString, location);
    typeMap.put(fullString, this);
  }

  public static BONType mk(String identifier, List<Type> actualGenerics, String fullString, SourceLocation location) {
    BONType type = typeMap.get(fullString);
    if (type == null) {
      return new BONType(identifier, actualGenerics, fullString, location);
    } else {
      return type;
    }
  }
  
  public static BONType mk(String fullString) {
    return typeMap.get(fullString);
  }
  
  public boolean hasGenerics() {
    return getActualGenerics() != null;
  }
  
  public String getNonGenericType() {
    return getIdentifier();
  }
}