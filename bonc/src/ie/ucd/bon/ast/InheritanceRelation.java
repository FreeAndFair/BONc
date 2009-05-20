
/**
  This class is generated automatically from normal_classes.tpl. 
  Do not edit.
 */
package ie.ucd.bon.ast;

import java.util.List;
import ie.ucd.bon.source.SourceLocation;
import ie.ucd.bon.ast.AstNode;

public class InheritanceRelation extends StaticRelation {


  private final Multiplicity multiplicity;

  private final BONType child;
  private final BONType parent;
  private final String semanticLabel;

  private final SourceLocation location;

  // === Constructors and Factories ===
  protected InheritanceRelation(BONType child, BONType parent, Multiplicity multiplicity, String semanticLabel) {
    this(child,parent,multiplicity,semanticLabel, null);    
  }

  protected InheritanceRelation(BONType child, BONType parent, Multiplicity multiplicity, String semanticLabel, SourceLocation location) {
    
    assert location != null;
    this.location = location;
    this.child = child; assert child != null;
    this.parent = parent; assert parent != null;
    this.multiplicity = multiplicity; 
    this.semanticLabel = semanticLabel; 
    
  }
  
  public static InheritanceRelation mk(BONType child, BONType parent, Multiplicity multiplicity, String semanticLabel) {
    return new InheritanceRelation(child, parent, multiplicity, semanticLabel);
  }

  public static InheritanceRelation mk(BONType child, BONType parent, Multiplicity multiplicity, String semanticLabel, SourceLocation location) {
    return new InheritanceRelation(child, parent, multiplicity, semanticLabel, location);
  }
  
  public SourceLocation getLocation() {
    return location;
  }

  // === Accessors ===

  public BONType getChild() { return child; }
  public BONType getParent() { return parent; }
  public Multiplicity getMultiplicity() { return multiplicity; }
  public String getSemanticLabel() { return semanticLabel; }

  // === Others ===
  @Override
  public InheritanceRelation clone() {
    BONType newChild = child;
    BONType newParent = parent;
    Multiplicity newMultiplicity = multiplicity == null ? null : multiplicity.clone();
    String newSemanticLabel = semanticLabel;
    
    return InheritanceRelation.mk(newChild, newParent, newMultiplicity, newSemanticLabel, location);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("InheritanceRelation ast node: ");
    
    sb.append("child = ");
    sb.append(child);
    sb.append(", ");
    
    sb.append("parent = ");
    sb.append(parent);
    sb.append(", ");
    
    sb.append("multiplicity = ");
    sb.append(multiplicity);
    sb.append(", ");
    
    sb.append("semanticLabel = ");
    sb.append(semanticLabel);
    sb.append(", ");
    
    if (sb.length() >= 2) {
      sb.delete(sb.length()-2,sb.length());
    }
    return sb.toString();
  }
}

