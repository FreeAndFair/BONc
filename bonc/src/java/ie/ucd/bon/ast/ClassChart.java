
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

public class ClassChart extends InformalChart {


  public final ClassName name;
  public final Indexing indexing;

  public final List<ClassName> inherits;
  public final List<String> queries;
  public final List<String> commands;
  public final List<String> constraints;
  public final String explanation;
  public final String part;


  // === Constructors and Factories ===
  protected ClassChart(ClassName name, List<ClassName> inherits, List<String> queries, List<String> commands, List<String> constraints, Indexing indexing, String explanation, String part, SourceLocation location) {
    super(location);
    this.name = name; assert name != null;
    this.inherits = inherits; assert inherits != null;
    this.queries = queries; assert queries != null;
    this.commands = commands; assert commands != null;
    this.constraints = constraints; assert constraints != null;
    this.indexing = indexing; 
    this.explanation = explanation; 
    this.part = part; 
  }

  public static ClassChart mk(ClassName name, List<ClassName> inherits, List<String> queries, List<String> commands, List<String> constraints, Indexing indexing, String explanation, String part, SourceLocation location) {
    return new ClassChart(name, inherits, queries, commands, constraints, indexing, explanation, part, location);
  }

  // === Accessors ===

  public ClassName getName() { return name; }
  public List<ClassName> getInherits() { return inherits; }
  public List<String> getQueries() { return queries; }
  public List<String> getCommands() { return commands; }
  public List<String> getConstraints() { return constraints; }
  public Indexing getIndexing() { return indexing; }
  public String getExplanation() { return explanation; }
  public String getPart() { return part; }

  // === Visitor ===
  public void accept(IVisitorWithAdditions visitor) {
    visitor.visitClassChart(this, name, inherits, queries, commands, constraints, indexing, explanation, part, getLocation());
  }

  // === Others ===
  @Override
  public ClassChart clone() {
    ClassName newName = name == null ? null : name.clone();
    List<ClassName> newInherits = inherits;
    List<String> newQueries = queries;
    List<String> newCommands = commands;
    List<String> newConstraints = constraints;
    Indexing newIndexing = indexing == null ? null : indexing.clone();
    String newExplanation = explanation;
    String newPart = part;
    return ClassChart.mk(newName, newInherits, newQueries, newCommands, newConstraints, newIndexing, newExplanation, newPart, getLocation());
  }

  @Override
  public String toString() {
    return StringUtil.prettyPrint(this);
  }
}

