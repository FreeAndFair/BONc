
/**
  This class is generated automatically from normal_classes.tpl. 
  Do not edit.
 */
package ie.ucd.bon.ast;

import java.util.List;
import ie.ucd.bon.source.SourceLocation;

public class Clazz extends StaticComponent {
  public static enum ModA {
    NONE, 
    DEFERRED, 
    ROOT, 
    EFFECTIVE
  }  public static enum ModB {
    INTERFACED, 
    REUSED, 
    PERSISTENT, 
    NONE
  }

  private final ClassInterface classInterface;

  private final ModA modA;
  private final ModB modB;
  private final List<FormalGeneric> generics;
  private final String comment;


  // === Constructors and Factories ===
  protected Clazz(ModA modA, ModB modB, ClassInterface classInterface, List<FormalGeneric> generics, String comment, SourceLocation location) {
    super(location);
    this.modA = modA; 
    this.modB = modB; 
    this.classInterface = classInterface; 
    this.generics = generics; 
    this.comment = comment; 
    
  }
  
  public static Clazz mk(ModA modA, ModB modB, ClassInterface classInterface, List<FormalGeneric> generics, String comment, SourceLocation location) {
    return new Clazz(modA, modB, classInterface, generics, comment, location);
  }

  // === Accessors ===

  public ModA getModA() { return modA; }
  public ModB getModB() { return modB; }
  public ClassInterface getClassInterface() { return classInterface; }
  public List<FormalGeneric> getGenerics() { return generics; }
  public String getComment() { return comment; }

  // === Visitor ===
  public void accept(IVisitor visitor) {
    visitor.visitClazz(this);
  }

  // === Others ===
  @Override
  public Clazz clone() {
    ModA newModA = modA;
    ModB newModB = modB;
    ClassInterface newClassInterface = classInterface == null ? null : classInterface.clone();
    List<FormalGeneric> newGenerics = generics;
    String newComment = comment;
    
    return Clazz.mk(newModA, newModB, newClassInterface, newGenerics, newComment, getLocation());
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Clazz ast node: ");
    
    sb.append("modA = ");
    sb.append(modA);
    sb.append(", ");
    
    sb.append("modB = ");
    sb.append(modB);
    sb.append(", ");
    
    sb.append("classInterface = ");
    sb.append(classInterface);
    sb.append(", ");
    
    sb.append("generics = ");
    sb.append(generics);
    sb.append(", ");
    
    sb.append("comment = ");
    sb.append(comment);
    sb.append(", ");
    
    if (sb.length() >= 2) {
      sb.delete(sb.length()-2,sb.length());
    }
    return sb.toString();
  }
}

