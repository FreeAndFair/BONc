/**
 * Copyright (c) 2007-2009, Fintan Fairmichael, University College Dublin under the BSD licence.
 * See LICENCE.TXT for details.
 */
package ie.ucd.bon.typechecker;

import ie.ucd.bon.Main;
import ie.ucd.bon.ast.AbstractVisitorWithAdditions;
import ie.ucd.bon.ast.AstNode;
import ie.ucd.bon.ast.BonSourceFile;
import ie.ucd.bon.ast.ClassChart;
import ie.ucd.bon.ast.ClassEntry;
import ie.ucd.bon.ast.ClassInterface;
import ie.ucd.bon.ast.ClassName;
import ie.ucd.bon.ast.Clazz;
import ie.ucd.bon.ast.ClientEntityExpression;
import ie.ucd.bon.ast.ClientRelation;
import ie.ucd.bon.ast.Cluster;
import ie.ucd.bon.ast.ClusterChart;
import ie.ucd.bon.ast.ClusterEntry;
import ie.ucd.bon.ast.ContractClause;
import ie.ucd.bon.ast.Expression;
import ie.ucd.bon.ast.Feature;
import ie.ucd.bon.ast.FeatureArgument;
import ie.ucd.bon.ast.FeatureName;
import ie.ucd.bon.ast.FeatureSpecification;
import ie.ucd.bon.ast.FormalGeneric;
import ie.ucd.bon.ast.HasType;
import ie.ucd.bon.ast.IVisitorWithAdditions;
import ie.ucd.bon.ast.Indexing;
import ie.ucd.bon.ast.RenameClause;
import ie.ucd.bon.ast.SpecificationElement;
import ie.ucd.bon.ast.StaticComponent;
import ie.ucd.bon.ast.StaticDiagram;
import ie.ucd.bon.ast.StaticRef;
import ie.ucd.bon.ast.Type;
import ie.ucd.bon.ast.TypeMark;
import ie.ucd.bon.ast.Clazz.Mod;
import ie.ucd.bon.errorreporting.Problems;
import ie.ucd.bon.source.SourceLocation;
import ie.ucd.bon.typechecker.errors.ClassCannotHaveSelfAsParentError;
import ie.ucd.bon.typechecker.errors.DuplicateClassDefinitionError;
import ie.ucd.bon.typechecker.errors.DuplicateClusterDefinitionError;
import ie.ucd.bon.typechecker.errors.DuplicateFeatureDefinitionError;
import ie.ucd.bon.typechecker.errors.DuplicateFormalGenericNameError;
import ie.ucd.bon.typechecker.errors.DuplicateSystemDefinitionError;
import ie.ucd.bon.typechecker.errors.NameNotUniqueError;
import ie.ucd.bon.typechecker.informal.errors.DuplicateClassChartError;
import ie.ucd.bon.typechecker.informal.errors.DuplicateClusterChartError;
import ie.ucd.bon.util.AstUtil;
import ie.ucd.bon.util.STUtil;

import java.util.ArrayList;
import java.util.List;

public class STBuilderVisitor extends AbstractVisitorWithAdditions implements IVisitorWithAdditions {

  private final BONST st;
  private final Problems problems;
  private final VisitorContext context;

  public STBuilderVisitor(BONST st) {
    this.st = st;
    problems = new Problems("STBuilder");
    context = new VisitorContext();
  }

  public BONST getSt() {
    return st;
  }

  public Problems getProblems() {
    return problems;
  }

  @Override
  public void visitBonSourceFile(BonSourceFile node,
      List<SpecificationElement> bonSpecification, Indexing indexing,
      SourceLocation loc) {

    visitAll(bonSpecification);
    indexing(node, indexing);
  }

  @Override
  public void visitStaticDiagram(StaticDiagram node,
      List<StaticComponent> components, String extendedId, String comment,
      SourceLocation loc) {

    visitAll(components);
  }

  @Override
  public void visitClazz(Clazz node, ClassName name, List<FormalGeneric> generics,
      Mod mod, ClassInterface classInterface, Boolean reused,
      Boolean persistent, Boolean interfaced, String comment, SourceLocation loc) {

    Clazz clazz = st.classes.get(name.getName());
    Cluster cluster = st.clusters.get(name.getName());

    if (cluster != null) {
      problems.addProblem(new NameNotUniqueError(node.getReportingLocation(), "Class", name.getName(), "cluster", cluster.getLocation()));
    } else if (clazz != null) {
      //TODO differentiate between expanded and flat class forms
      problems.addProblem(new DuplicateClassDefinitionError(node.getReportingLocation(), clazz));
    } else {
      st.classes.put(name.getName(), node);

      if (!context.clusterStack.empty()) {
        st.classClusterMap.put(name.getName(), context.clusterStack.peek());
        //TODO check if already in a cluster?
      }

      //Deal with the formal generics
      st.genericsMap.put(node, generics);
      List<Type> filledInGenerics = new ArrayList<Type>(generics.size());
      for (FormalGeneric fGeneric : generics) {
        if (!st.genericNamesMap.containsKey(node, fGeneric.identifier)) {
          st.genericNamesMap.put(node, fGeneric.identifier, fGeneric);
          Type filledInType = fGeneric.type == null ? STUtil.anyType(fGeneric.location)
              : STUtil.fillInPlaceHolders(fGeneric.type, st.filledInGenericNamesMap.getSecondDimension(node), true);
          st.filledInGenericNamesMap.put(node, fGeneric.identifier, filledInType);
          filledInGenerics.add(filledInType);
        } else {
          problems.addProblem(new DuplicateFormalGenericNameError(fGeneric.location, fGeneric.identifier));
        }
      }      
      st.filledInGenericsMap.put(node, filledInGenerics);
      Main.logDebug("Original generics for " + name + ": " + generics);
      Main.logDebug("Filled in generics for " + name + ": " + filledInGenerics);
      
      Type cType = Type.mk(name.name, AstUtil.formalGenericTypes(generics), loc);
      st.classNameToTypeMap.put(name.name, cType);
      
      context.clazz = node;
      visitAll(generics);
      visitNode(classInterface);
      context.clazz = null;
    }
  }

  @Override
  public void visitCluster(Cluster node, String name,
      List<StaticComponent> components, Boolean reused, String comment, SourceLocation loc) {

    Clazz clazz = st.classes.get(name);
    Cluster cluster = st.clusters.get(name);

    if (clazz != null) {
      problems.addProblem(new NameNotUniqueError(node.getReportingLocation(), "Cluster", name, "class", clazz.getLocation()));
    } else if (cluster != null) {
      problems.addProblem(new DuplicateClusterDefinitionError(node.getReportingLocation(), cluster));
    } else {
      st.clusters.put(name, node);
    }

    if (!context.clusterStack.empty()) {
      st.clusterClusterGraph.put(name, context.clusterStack.peek());
    }

    context.clusterStack.push(node);
    visitAll(components);
    context.clusterStack.pop();
  }

  @Override
  public void visitClassInterface(ClassInterface node, List<Feature> features,
      List<Type> parents, List<Expression> invariant, Indexing indexing,
      SourceLocation loc) {

    for (Type parent : parents) {
      if (parent.getIdentifier().equals(context.clazz.getName().getName())) {
        problems.addProblem(new ClassCannotHaveSelfAsParentError(parent.getReportingLocation(), context.clazz.getName().getName()));
      } else {
        st.classInheritanceGraph.put(context.clazz.getName().getName(), parent);
        st.simpleClassInheritanceGraph.put(context.clazz.getName().getName(), parent.getIdentifier());
      }
    }

    //TODO proper ST for feature
    visitAll(features);
    visitAll(invariant);

    indexing(context.clazz, indexing);
  }



  @Override
  public void visitFeature(Feature node,
      List<FeatureSpecification> featureSpecifications,
      List<ClassName> selectiveExport, String comment, SourceLocation loc) {

    context.selectiveExport = selectiveExport;
    context.selectiveExportStrings = new ArrayList<String>(selectiveExport.size());
    context.selectiveExportPrivate = false;
    for (ClassName name : selectiveExport) {
      if (name.name.equals("NONE")) {
        if (selectiveExport.size() != 1) {
          //TODO error!
        }
        context.selectiveExportPrivate = true;
      }
      context.selectiveExportStrings.add(name.name);
    }

    visitAll(featureSpecifications);
  }

  @Override
  public void visitFeatureSpecification(FeatureSpecification node,
      FeatureSpecification.Modifier modifier, List<FeatureName> featureNames,
      List<FeatureArgument> arguments, ContractClause contracts,
      HasType hasType, RenameClause renaming, String comment, SourceLocation loc) {

    for (FeatureName name : featureNames) {
      FeatureSpecification other = st.featuresMap.get(context.clazz, name.getName());
      if (other == null) {
        st.featuresMap.put(context.clazz, name.getName(), node);
      } else {
        problems.addProblem(new DuplicateFeatureDefinitionError(node.getReportingLocation(), context.clazz.getName().getName(), name.getName(), other));
      }
    }

    st.featureDeclaringClassMap.put(node, context.clazz);	
    st.selectiveExportMap.put(node, context.selectiveExport);
    st.selectiveExportStringsMap.put(node, context.selectiveExportStrings);
    st.selectiveExportPrivateMap.put(node, context.selectiveExportPrivate);

    visitNode(contracts);
  }

  @Override
  public void visitClassChart(ClassChart node, ClassName name,
      List<ClassName> inherits, List<String> queries, List<String> commands,
      List<String> constraints, Indexing indexing, String explanation,
      String part, SourceLocation loc) {

    //Check if name unique
    ClusterChart otherCluster = st.informal.clusters.get(name.getName());
    if (otherCluster != null) {
      problems.addProblem(new NameNotUniqueError(node.getReportingLocation(), "Class chart", name.getName(), "cluster chart", otherCluster.getLocation()));
    } else {
      ClassChart clazz = st.informal.classes.get(name.getName());
      if (clazz != null) {
        problems.addProblem(new DuplicateClassChartError(node.getReportingLocation(), clazz));
      } else {
        st.informal.classes.put(name.getName(), node);
      }
    }

    for (ClassName parent : inherits) {
      if (parent.equals(name)) {
        problems.addProblem(new ClassCannotHaveSelfAsParentError(node.getReportingLocation(), name.getName()));
      } else {
        st.informal.classInheritanceGraph.put(node, parent.name);
        st.informal.simpleClassInheritanceGraph.put(name.name, parent.name);
      }
    }

    indexing(node, indexing);
  }

  @Override
  public void visitClusterChart(ClusterChart node, String name, Boolean isSystem,
      List<ClassEntry> classes, List<ClusterEntry> clusters, Indexing indexing,
      String explanation, String part, SourceLocation loc) {

    //If system, set system
    if (isSystem) {
      if (st.informal.systemChart != null) {
        //Duplicate system, add error.
        problems.addProblem(new DuplicateSystemDefinitionError(node.getReportingLocation(), st.informal.systemChart));
        return;
      } else {
        st.informal.systemChart = node;
      }
    }

    //Check if name unique
    ClassChart otherClass = st.informal.classes.get(name);
    if (otherClass != null) {
      problems.addProblem(new NameNotUniqueError(node.getReportingLocation(), "Cluster chart", name, "class chart", otherClass.getLocation()));
    } else {
      ClusterChart otherCluster = st.informal.clusters.get(name);
      if (otherCluster != null) {
        problems.addProblem(new DuplicateClusterChartError(node.getReportingLocation(), otherCluster));
        return;
      } else {
        st.informal.clusters.put(name, node);
      }
    }

    context.clusterChart = node;
    visitAll(classes);   //TODO check for duplicate class entries
    visitAll(clusters);  //TODO check for duplicate cluster entries
    context.clusterChart = null;

    indexing(node, indexing);
  }
  
  @Override
  public void visitClassEntry(ClassEntry node, String name, String description, SourceLocation loc) {
    st.informal.classClusterGraph.put(name, context.clusterChart);
    if (!"".equals(description.trim())) {
      st.informal.descriptionGraph.put(name, description);
    }
  }
  

  @Override
  public void visitClusterEntry(ClusterEntry node, String name, String description, SourceLocation loc) {
    st.informal.clusterClusterGraph.put(name, context.clusterChart);
    if (!"".equals(description.trim())) {
      st.informal.descriptionGraph.put(name, description);
    }
  }

  @Override
  public void visitClientRelation(ClientRelation node, StaticRef client,
      StaticRef supplier, ClientEntityExpression clientEntities,
      TypeMark typeMark, String semanticLabel, SourceLocation loc) {
    st.clientRelations.add(node);
  }

  private void indexing(AstNode node, Indexing indexing) {
    if (indexing != null) {
      st.indexing.put(node, indexing);
    }
  }
  
}

