/**
 * Copyright (c) 2007-2009, Fintan Fairmichael, University College Dublin under the BSD licence.
 * See LICENCE.TXT for details.
 */
package ie.ucd.bon.printer;

import ie.ucd.bon.parser.tracker.ParsingTracker;

public class ClassDictionaryGenerator {

  public static String generateDictionary(ParsingTracker tracker) throws UnableToGenerateClassDictionaryException {
    String newLine = System.getProperty("line.separator");
    
    StringBuilder sb = new StringBuilder();
    //TODO fix!
////    InformalTypingInformation iti = tracker.getInformalTypingInformation();
//    ClusterChartDefinition sysDef = iti.getSystem();
//    Map<String,ClassChartDefinition> classes = iti.getClasses(); 
//    Set<String> classNames = new TreeSet<String>(classes.keySet());
//    Graph<String,ClusterChartDefinition> classClusterGraph = iti.getClassClusterGraph();
//    
//    
//    
//    if (sysDef == null) {
//      throw new UnableToGenerateClassDictionaryException("No system defined!");
//    }
//    if (classNames.size() == 0) {
//      throw new UnableToGenerateClassDictionaryException("No classes in system!");
//    }
//
//    sb.append("dictionary ");
//    sb.append(sysDef.getName());
//    sb.append(newLine);
//    
//    sb.append("  explanation");
//    sb.append(newLine);
//    sb.append("    \"BONc automatically generated class dictionary.\"");
//    sb.append(newLine);
//
//    for (String className : classNames) {
//      ClassChartDefinition classDef = classes.get(className);
//      
//      Collection<ClusterChartDefinition> inClusters = classClusterGraph.get(className);
//      if (inClusters == null || inClusters.size() == 0) {
//        //TODO or just ignore?
//        throw new UnableToGenerateClassDictionaryException("Class " + className + " is not in any cluster!");
//      } else {
//        sb.append("  class " + className + " cluster ");
//        for (ClusterChartDefinition clusterDef : inClusters) {
//          sb.append(clusterDef.getName());
//          sb.append(", ");
//        }
//        sb.deleteCharAt(sb.length()-1);
//        sb.deleteCharAt(sb.length()-1);
//        
//        sb.append(newLine);
//        sb.append("  description");
//        sb.append(newLine);
//        sb.append("    ");
//        if (classDef.getExplanation() == null || "".equals(classDef.getExplanation())) {
//          //sb.append("\"");
//          sb.append(iti.getAlternativeClassDescription(className));
//          //sb.append("\"");
//        } else {
//          sb.append(classDef.getExplanation());
//        }
//        sb.append(newLine);
//      }
//    }
//
//    sb.append("end");
//    sb.append(newLine);
//    sb.append(newLine);
    
    return sb.toString();
  }
  
}