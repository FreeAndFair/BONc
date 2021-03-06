
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

public class NamedIndirection extends IndirectionElement {


  public final ClassName className;

  public final List<IndirectionElement> indirectionElements;


  // === Constructors and Factories ===
  protected NamedIndirection(ClassName className, List<IndirectionElement> indirectionElements, SourceLocation location) {
    super(location);
    this.className = className; assert className != null;
    this.indirectionElements = indirectionElements; assert indirectionElements != null;
  }

  public static NamedIndirection mk(ClassName className, List<IndirectionElement> indirectionElements, SourceLocation location) {
    return new NamedIndirection(className, indirectionElements, location);
  }

  // === Accessors ===

  public ClassName getClassName() { return className; }
  public List<IndirectionElement> getIndirectionElements() { return indirectionElements; }

  // === Visitor ===
  public void accept(IVisitorWithAdditions visitor) {
    visitor.visitNamedIndirection(this, className, indirectionElements, getLocation());
  }

  // === Others ===
  @Override
  public NamedIndirection clone() {
    ClassName newClassName = className == null ? null : className.clone();
    List<IndirectionElement> newIndirectionElements = indirectionElements;
    return NamedIndirection.mk(newClassName, newIndirectionElements, getLocation());
  }

  @Override
  public String toString() {
    return StringUtil.prettyPrint(this);
  }
}

