
/**
  This class is generated automatically from normal_classes.tpl. 
  Do not edit.
 */
package ie.ucd.bon.ast;

import java.util.List;
import ie.ucd.bon.source.SourceLocation;
import ie.ucd.bon.ast.AstNode;

public class FeatureArgument extends AstNode {



  private final String identifier;
  private final BONType type;

  private final SourceLocation location;

  // === Constructors and Factories ===
  protected FeatureArgument(String identifier, BONType type) {
    this(identifier,type, null);    
  }

  protected FeatureArgument(String identifier, BONType type, SourceLocation location) {
    
    assert location != null;
    this.location = location;
    this.identifier = identifier; 
    this.type = type; assert type != null;
    
  }
  
  public static FeatureArgument mk(String identifier, BONType type) {
    return new FeatureArgument(identifier, type);
  }

  public static FeatureArgument mk(String identifier, BONType type, SourceLocation location) {
    return new FeatureArgument(identifier, type, location);
  }
  
  public SourceLocation getLocation() {
    return location;
  }

  // === Accessors ===

  public String getIdentifier() { return identifier; }
  public BONType getType() { return type; }

  // === Others ===
  @Override
  public FeatureArgument clone() {
    String newIdentifier = identifier;
    BONType newType = type;
    
    return FeatureArgument.mk(newIdentifier, newType, location);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("FeatureArgument ast node: ");
    
    sb.append("identifier = ");
    sb.append(identifier);
    sb.append(", ");
    
    sb.append("type = ");
    sb.append(type);
    sb.append(", ");
    
    if (sb.length() >= 2) {
      sb.delete(sb.length()-2,sb.length());
    }
    return sb.toString();
  }
}

