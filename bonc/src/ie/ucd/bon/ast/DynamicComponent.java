
/**
  This class is generated automatically from abstract_classes.tpl. 
  Do not edit.
 */
package ie.ucd.bon.ast;

import ie.ucd.bon.source.SourceLocation;

public abstract class DynamicComponent extends AstNode {

  public DynamicComponent(SourceLocation location) {
    super(location);
  }

  // a more specific return type
  @Override
  public abstract DynamicComponent clone();
}
