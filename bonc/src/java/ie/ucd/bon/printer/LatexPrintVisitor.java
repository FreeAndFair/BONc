/**
 * Copyright (c) 2007-2009, Fintan Fairmichael, University College Dublin under the BSD licence.
 * See LICENCE.TXT for details.
 */
package ie.ucd.bon.printer;

import ie.ucd.bon.ast.AstNode;
import ie.ucd.bon.ast.BinaryExp;
import ie.ucd.bon.ast.BonSourceFile;
import ie.ucd.bon.ast.BooleanConstant;
import ie.ucd.bon.ast.CallExp;
import ie.ucd.bon.ast.CharacterConstant;
import ie.ucd.bon.ast.CharacterInterval;
import ie.ucd.bon.ast.ClassInterface;
import ie.ucd.bon.ast.ClassName;
import ie.ucd.bon.ast.Clazz;
import ie.ucd.bon.ast.ContractClause;
import ie.ucd.bon.ast.EnumerationElement;
import ie.ucd.bon.ast.Expression;
import ie.ucd.bon.ast.Feature;
import ie.ucd.bon.ast.FeatureArgument;
import ie.ucd.bon.ast.FeatureName;
import ie.ucd.bon.ast.FeatureSpecification;
import ie.ucd.bon.ast.FormalGeneric;
import ie.ucd.bon.ast.HasType;
import ie.ucd.bon.ast.IVisitorWithAdditions;
import ie.ucd.bon.ast.Indexing;
import ie.ucd.bon.ast.IntegerConstant;
import ie.ucd.bon.ast.IntegerInterval;
import ie.ucd.bon.ast.KeywordConstant;
import ie.ucd.bon.ast.Multiplicity;
import ie.ucd.bon.ast.RealConstant;
import ie.ucd.bon.ast.RenameClause;
import ie.ucd.bon.ast.SetConstant;
import ie.ucd.bon.ast.SpecificationElement;
import ie.ucd.bon.ast.StaticComponent;
import ie.ucd.bon.ast.StaticDiagram;
import ie.ucd.bon.ast.StringConstant;
import ie.ucd.bon.ast.Type;
import ie.ucd.bon.ast.TypeMark;
import ie.ucd.bon.ast.UnaryExp;
import ie.ucd.bon.ast.UnqualifiedCall;
import ie.ucd.bon.ast.BinaryExp.Op;
import ie.ucd.bon.ast.Clazz.Mod;
import ie.ucd.bon.ast.FeatureSpecification.Modifier;
import ie.ucd.bon.ast.KeywordConstant.Constant;
import ie.ucd.bon.ast.Quantification.Quantifier;
import ie.ucd.bon.ast.TypeMark.Mark;
import ie.ucd.bon.parser.tracker.ParsingTracker;
import ie.ucd.bon.source.SourceLocation;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LatexPrintVisitor extends AbstractPrintVisitor implements IVisitorWithAdditions, PrintAgent {

  public LatexPrintVisitor() {
  }

  public String getAllOutputAsString(ParsingTracker tracker, Map<String, Object> additionalData) throws IOException {
    //ByteArrayOutputStream start = new ByteArrayOutputStream();
    //FreeMarkerTemplate.writeTemplate(new PrintWriter(start), "latex-start.ftl", additionalData);
    //return start.toString().append(super.getAllOutputAsString(additionalData));
    return super.getAllOutputAsString(tracker, additionalData);
  }

  @Override
  public void visitStaticDiagram(StaticDiagram node, List<StaticComponent> components, String extendedId, String comment, SourceLocation loc) {
    visitAll(components);
  }

  @Override
  public void visitBonSourceFile(BonSourceFile node, List<SpecificationElement> bonSpecification, Indexing indexing, SourceLocation loc) {
    visitAll(bonSpecification);
  }

  @Override
  public void visitClassInterface(ClassInterface node, List<Feature> features, List<Type> parents, List<Expression> invariant, Indexing indexing, SourceLocation loc) {
    tp.printLine("\\nodepart{second}");
    tp.increaseIndentation();
    tp.printLine("\\begin{varwidth}[t!]{0.3\\textwidth}");
    visitAllPrintingSeparatorAndlines(parents, " \\\\", 1, false, true);
    tp.printLine("\\end{varwidth}");
    tp.printLine("\\hspace{2mm}");
    tp.printLine("\\begin{varwidth}[t!]{0.7\\textwidth}");
    visitAllPrintingSeparatorAndlines(parents, " \\\\", 1, false, true);
    tp.printLine("\\end{varwidth}");
    tp.decreaseIndentation();

    tp.printLine("\\nodepart{third}");
    tp.increaseIndentation();
    tp.printLine("\\begin{varwidth}[t!]{1.0\\textwidth}");
    tp.printLine("\\begin{flushleft}");

    visitAll(features);

    tp.printLine("\\end{flushleft}");
    tp.printLine("\\end{varwidth}");
    tp.decreaseIndentation();

    tp.printLine("\\nodepart{fourth}");
    tp.increaseIndentation();
    tp.printLine("\\begin{varwidth}[t!]{1.0\\textwidth}");
    tp.printLine("\\begin{flushleft}");
    visitAllPrintingSeparatorAndlines(invariant, "; \\\\", 1, false, true);
    tp.printLine("\\end{flushleft}");
    tp.printLine("\\end{varwidth}");
    tp.decreaseIndentation();
  }

  @Override
  public void visitClazz(Clazz node, ClassName name, List<FormalGeneric> generics, Mod mod, ClassInterface classInterface,
      Boolean reused, Boolean persistent, Boolean interfaced, String comment, SourceLocation loc) {
    tp.printLine("\\begin{tikzpicture}");
    tp.printLine("\\pgfsetcornersarced{\\pgfpoint{20mm}{20mm}}");
    tp.printLine("\\node [bonclass] [name=" + name.name + ", rectangle split, rectangle split, rectangle split parts=4] {%");
    tp.increaseIndentation();
    tp.print("\\textbf{");
    name.accept(this);
    tp.printLine("}");

    visitNodeIfNonNull(classInterface);

    tp.decreaseIndentation();
    tp.printLine("};");
    tp.printLine("\\node[rectangle, fill=white] at (" + name.name + ".third split) {\\textbf{Invariant}};");
    tp.printLine("\\end{tikzpicture}");
  }

  @Override
  public void visitClassName(ClassName node, String name, SourceLocation loc) {
    tp.print(sanitizeIdentifier(name));
  }

  @Override
  public void visitFeature(Feature node, List<FeatureSpecification> featureSpecifications,
      List<ClassName> selectiveExport, String comment, SourceLocation loc) {
    visitAll(featureSpecifications);
  }

  @Override
  public void visitFeatureSpecification(FeatureSpecification node, Modifier modifier, List<FeatureName> featureNames,
      List<FeatureArgument> arguments, ContractClause contracts, HasType hasType, RenameClause renaming, String comment, SourceLocation loc) {
    tp.printLine("\\vspace{2mm}");
    visitAllPrintingSeparator(featureNames, ", ", false);
    visitNodeIfNonNull(hasType);
    tp.printLine("\\\\");
    visitAll(arguments);
  }

  @Override
  public void visitFeatureName(FeatureName node, String name, SourceLocation loc) {
    tp.print("\\emph{" + sanitizeIdentifier(name) + "}");
  }

  @Override
  public void visitFeatureArgument(FeatureArgument node, String identifier, Type type, SourceLocation loc) {
    tp.printLine("\\hspace{5mm}");
    tp.print("$\\rightarrow$ ");
    if (identifier != null) {
      tp.print(sanitizeIdentifier(identifier));
      tp.print(": ");
    }
    type.accept(this);
    tp.printLine(" \\\\");
  }

  @Override
  public void visitHasType(HasType node, TypeMark mark, Type type, SourceLocation loc) {
    mark.accept(this);
    tp.print(' ');
    type.accept(this);
  }

  @Override
  public void visitType(Type node, String identifier, List<Type> actualGenerics, SourceLocation loc) {
    tp.print(sanitizeIdentifier(identifier));
    if (!actualGenerics.isEmpty()) {
      tp.print('[');
      visitAllPrintingSeparator(actualGenerics, ", ", false);
      tp.print(']');
    }
  }

  @Override
  public void visitTypeMark(TypeMark node, Mark mark, Integer multiplicity, SourceLocation loc) {
    switch(mark) {
    case AGGREGATE:
      tp.print(":{");
      break;
    case HASTYPE:
      tp.print(':');
      break;
    case NONE:
      break;
    case SHAREDMARK:
      tp.print(":(");
      tp.print(multiplicity);
      tp.print(')');
      break;
    }
  }

  @Override
  public void visitBinaryExp(BinaryExp node, Op op, Expression left,
      Expression right, SourceLocation loc) {
    left.accept(this);
    tp.printSpace();
    printBinaryExpOp(op);
    tp.printSpace();
    right.accept(this);
  }

  @Override
  public void visitUnaryExp(UnaryExp node, ie.ucd.bon.ast.UnaryExp.Op op,
      Expression expression, SourceLocation loc) {
    printUnaryExpOp(op);
    tp.print('(');
    tp.printSpace();
    expression.accept(this);
    tp.print(')');
  }

  @Override
  public void visitCallExp(CallExp node, Expression qualifier, List<UnqualifiedCall> callChain, SourceLocation loc) {
    if (qualifier != null) {
      qualifier.accept(this);
      tp.print('.');
    }
    visitAllPrintingSeparator(callChain, ".", false);
  }

  @Override
  public void visitUnqualifiedCall(UnqualifiedCall node, String id, List<Expression> args, SourceLocation loc) {
    tp.print(sanitizeIdentifier(id));
    if (!args.isEmpty()) {
      tp.print('(');
      visitAllPrintingSeparator(args, ", ", false);
      tp.print(')');
    }
  }

  @Override
  public void visitIntegerConstant(IntegerConstant node, Integer value, SourceLocation loc) {
    tp.print(value);
  }

  @Override
  public void visitIntegerInterval(IntegerInterval node, Integer start, Integer stop, SourceLocation loc) {
    tp.print(start);
    tp.print("..");
    tp.print(stop);
  }

  @Override
  public void visitKeywordConstant(KeywordConstant node, Constant constant, SourceLocation loc) {
    switch(constant) {
    case CURRENT:
      tp.print("Current");
      break;
    case RESULT:
      tp.print("Result");
      break;
    case VOID:
      tp.print("Void");
      break;
    }
  }

  @Override
  public void visitMultiplicity(Multiplicity node, Integer multiplicity, SourceLocation loc) {
    tp.print('{');
    tp.print(multiplicity);
    tp.print("} ");
  }

  @Override
  public void visitCharacterConstant(CharacterConstant node, Character value, SourceLocation loc) {
    tp.print('\'');
    tp.print(value);
    tp.print('\'');
  }

  
  
  @Override
  public void visitBooleanConstant(BooleanConstant node, Boolean value, SourceLocation loc) {
    tp.print(value.toString());
  }

  @Override
  public void visitRealConstant(RealConstant node, Double value, SourceLocation loc) {
    tp.print(value.toString());
  }

  @Override
  public void visitStringConstant(StringConstant node, String value, SourceLocation loc) {
    tp.print(value);
  }

  @Override
  public void visitSetConstant(SetConstant node, List<EnumerationElement> enumerations, SourceLocation loc) {
    tp.print("\\{");
    visitAllPrintingSeparator(enumerations, ", ", false);
    tp.print("\\}");
  }

  @Override
  public void visitCharacterInterval(CharacterInterval node, Character start,
      Character stop, SourceLocation loc) {
    tp.print('\'');
    tp.print(start);
    tp.print('\'');
    tp.print("..");
    tp.print('\'');
    tp.print(stop);
    tp.print('\'');
  }

  protected String toString(KeywordConstant.Constant constant) {
    switch (constant) {
    case CURRENT:
      return "Current";
    case VOID:
      return "Void";
    }
    return "";
  }

  protected String toString(Clazz.Mod modifier) {
    switch (modifier) {
    case DEFERRED:
      return "deferred ";
    case EFFECTIVE:
      return "effective ";
    case ROOT:
      return "root ";
    default:
      return "";
    }
  }

  protected void printUnaryExpOp(ie.ucd.bon.ast.UnaryExp.Op op) {
    switch (op) {
    case ADD:
      tp.print('+');
      break;
    case DELTA:
      tp.print("$\\Delta$");
      break;
    case NOT:
      tp.print("not");
      break;
    case OLD:
      tp.print("old");
      break;
    case SUB:
      tp.print('-');
      break;
    }
  }

  protected void printBinaryExpOp(Op op) {
    switch (op) {
    case ADD:
      tp.print("$+$");
      break;
    case AND:
      tp.print("\\textbf{and}");
      break;
    case DIV:
      tp.print("$/$");
      break;
    case EQ:
      tp.print("$=$");
      break;
    case EQUIV:
      tp.print("$\\leftrightarrow$");
      break;
    case GE:
      tp.print("$\\geq$");
      break;
    case GT:
      tp.print("$>$");
      break;
    case HASTYPE:
      tp.print(':');
      break;
    case IMPLIES:
      tp.print("$\\rightarrow$");
      break;
    case INTDIV:
      tp.print("//");
      break;
    case LE:
      tp.print("$\\leq$");
      break;
    case LT:
      tp.print("$<$");
      break;
    case MEMBEROF:
      tp.print("$\\in$");
      break;
    case MOD:
      tp.print("$\\%$");
      break;
    case MUL:
      tp.print("$*$");
      break;
    case NEQ:
      tp.print("$\\neq$");
      break;
    case NOTMEMBEROF:
      tp.print("$\\notin$");
      break;
    case OR:
      tp.print("\\textbf{or}");
      break;
    case POW:
      tp.print("\\^");
      break;
    case SUB:
      tp.print("$-$");
      break;
    case XOR:
      tp.print("\\textbf{xor}");
      break;
    }
  }

  protected void printQuantifier(Quantifier quantifier) {
    switch (quantifier) {
    case EXISTS:
      tp.print("\\exists");
      break;
    case FORALL:
      tp.print("\\forall");
      break;
    }
  }

  protected final void visitNodeIfNonNull(AstNode node) {
    if (node != null) {
      node.accept(this);
    }
  }

  public void visitAllPrintingSeparator(Collection<? extends AstNode> nodes, String separator, boolean separatorAtEnd) {
    for (Iterator<? extends AstNode> it = nodes.iterator(); it.hasNext();) {
      it.next().accept(this);
      if (it.hasNext() || separatorAtEnd) {
        tp.print(separator);
      }
    }
  }

  public void visitAllPrintingSeparatorAndlines(Collection<? extends AstNode> nodes, String separator, int numberOfLines, boolean separatorAtEnd, boolean linesAtEnd) {
    for (Iterator<? extends AstNode> it = nodes.iterator(); it.hasNext();) {
      it.next().accept(this);
      if (it.hasNext() || separatorAtEnd) {
        tp.print(separator);
      }
      if (it.hasNext() || linesAtEnd) {
        tp.printLines(numberOfLines);
      }
    }
  }

  private static String sanitizeIdentifier(String id) {
    return id.replace("_", "\\_");
  }

}
