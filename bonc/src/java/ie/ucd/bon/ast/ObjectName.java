
/**
 * Copyright (c) 2007-2010, Fintan Fairmichael, University College Dublin under the BSD licence.
 * See LICENCE.TXT for details.
 *
 * This class is generated automatically from normal_classes.tpl.
 * Do not edit.
 */
package ie.ucd.bon.ast;

import java.util.List;
import ie.ucd.bon.source.SourceLocation;
import ie.ucd.bon.util.StringUtil;

public class ObjectName extends AstNode {


  public final ClassName className;

  public final String extendedId;


  // === Constructors and Factories ===
  protected ObjectName(ClassName className, String extendedId, SourceLocation location) {
    super(location);
    this.className = className; assert className != null;
    this.extendedId = extendedId; 
  }

  public static ObjectName mk(ClassName className, String extendedId, SourceLocation location) {
    return new ObjectName(className, extendedId, location);
  }

  // === Accessors ===

  public ClassName getClassName() { return className; }
  public String getExtendedId() { return extendedId; }

  // === Visitor ===
  public void accept(IVisitorWithAdditions visitor) {
    visitor.visitObjectName(this, className, extendedId, getLocation());
  }

  // === Others ===
  @Override
  public ObjectName clone() {
    ClassName newClassName = className == null ? null : className.clone();
    String newExtendedId = extendedId;
    return ObjectName.mk(newClassName, newExtendedId, getLocation());
  }

  @Override
  public String toString() {
    return StringUtil.prettyPrint(this);
  }
}

