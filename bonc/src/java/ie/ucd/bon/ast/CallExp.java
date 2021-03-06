
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

public class CallExp extends Expression {


  public final Expression qualifier;

  public final List<UnqualifiedCall> callChain;


  // === Constructors and Factories ===
  protected CallExp(Expression qualifier, List<UnqualifiedCall> callChain, SourceLocation location) {
    super(location);
    this.qualifier = qualifier; 
    this.callChain = callChain; assert callChain != null;
  }

  public static CallExp mk(Expression qualifier, List<UnqualifiedCall> callChain, SourceLocation location) {
    return new CallExp(qualifier, callChain, location);
  }

  // === Accessors ===

  public Expression getQualifier() { return qualifier; }
  public List<UnqualifiedCall> getCallChain() { return callChain; }

  // === Visitor ===
  public void accept(IVisitorWithAdditions visitor) {
    visitor.visitCallExp(this, qualifier, callChain, getLocation());
  }

  // === Others ===
  @Override
  public CallExp clone() {
    Expression newQualifier = qualifier == null ? null : qualifier.clone();
    List<UnqualifiedCall> newCallChain = callChain;
    return CallExp.mk(newQualifier, newCallChain, getLocation());
  }

  @Override
  public String toString() {
    return StringUtil.prettyPrint(this);
  }
}

