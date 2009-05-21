
/**
  This class is generated automatically from normal_classes.tpl. 
  Do not edit.
 */
package ie.ucd.bon.ast;

import java.util.List;
import ie.ucd.bon.source.SourceLocation;

public class ContractClause extends AstNode {



  private final List<Expression> preconditions;
  private final List<Expression> postconditions;


  // === Constructors and Factories ===
  protected ContractClause(List<Expression> preconditions, List<Expression> postconditions, SourceLocation location) {
    super(location);
    this.preconditions = preconditions; 
    this.postconditions = postconditions; 
    
  }
  
  public static ContractClause mk(List<Expression> preconditions, List<Expression> postconditions, SourceLocation location) {
    return new ContractClause(preconditions, postconditions, location);
  }

  // === Accessors ===

  public List<Expression> getPreconditions() { return preconditions; }
  public List<Expression> getPostconditions() { return postconditions; }

  // === Others ===
  @Override
  public ContractClause clone() {
    List<Expression> newPreconditions = preconditions;
    List<Expression> newPostconditions = postconditions;
    
    return ContractClause.mk(newPreconditions, newPostconditions, getLocation());
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ContractClause ast node: ");
    
    sb.append("preconditions = ");
    sb.append(preconditions);
    sb.append(", ");
    
    sb.append("postconditions = ");
    sb.append(postconditions);
    sb.append(", ");
    
    if (sb.length() >= 2) {
      sb.delete(sb.length()-2,sb.length());
    }
    return sb.toString();
  }
}
