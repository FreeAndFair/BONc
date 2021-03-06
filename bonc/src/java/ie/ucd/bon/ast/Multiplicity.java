
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

public class Multiplicity extends ClientEntityExpression {



  public final Integer multiplicity;


  // === Constructors and Factories ===
  protected Multiplicity(Integer multiplicity, SourceLocation location) {
    super(location);
    this.multiplicity = multiplicity; assert multiplicity != null;
  }

  public static Multiplicity mk(Integer multiplicity, SourceLocation location) {
    return new Multiplicity(multiplicity, location);
  }

  // === Accessors ===

  public Integer getMultiplicity() { return multiplicity; }

  // === Visitor ===
  public void accept(IVisitorWithAdditions visitor) {
    visitor.visitMultiplicity(this, multiplicity, getLocation());
  }

  // === Others ===
  @Override
  public Multiplicity clone() {
    Integer newMultiplicity = multiplicity;
    return Multiplicity.mk(newMultiplicity, getLocation());
  }

  @Override
  public String toString() {
    return StringUtil.prettyPrint(this);
  }
}

