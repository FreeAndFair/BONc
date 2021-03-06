/**
 * Copyright (c) 2007-2009, Fintan Fairmichael, University College Dublin under the BSD licence.
 * See LICENCE.TXT for details.
 */
package ie.ucd.bon;

import static ie.ucd.clops.util.OptionUtil.SortingOption.ALPHABETICAL_BY_FIRST_ALIAS;
import ie.ucd.bon.clinterface.BONcOptionStore;
import ie.ucd.bon.clinterface.BONcOptionsInterface;
import ie.ucd.bon.clinterface.BONcParseResult;
import ie.ucd.bon.clinterface.BONcParser;
import ie.ucd.bon.clinterface.InvalidArgumentsError;
import ie.ucd.bon.errorreporting.ExceptionalError;
import ie.ucd.bon.errorreporting.Problems;
import ie.ucd.bon.parser.tracker.ParsingTracker;
import ie.ucd.clops.util.OptionUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Fintan
 *
 */
public final class Main {

  private static boolean debug = false;

  /** Prevent instantiation of Main. */
  private Main() { }

  public static boolean isDebug() {
    return debug;
  }

  public static void logDebug(final String debugMessage) {
    if (debug) {
      System.out.println("Debug: " + debugMessage);
    }
  }

  public static void main(final String[] args) {
    try {
      main2(args, true);
    } catch (Exception e) {
      System.out.println("Uncaught exception: " + e);
      e.printStackTrace(System.out);
    }
  }

  public static Problems main2(final String[] args, final boolean exitOnFailure) {
    Problems overallProblems = new Problems("Overall problems");
    try {
      //CLOLogger.setLogLevel(Level.FINE);
      BONcParseResult clParseResult = BONcParser.parse(args, "bonc");
      if (clParseResult.successfulParse()) {
        BONcOptionStore options = clParseResult.getOptionStore();

        debug = options.isDebugSet() && options.getDebug();

        if (options.getPrintMan()) {
          OptionUtil.printOptionsInManFormat(System.out, OptionUtil.sortOptions(options, ALPHABETICAL_BY_FIRST_ALIAS));
          return overallProblems;
        } else if (options.getPrintReadme()) {
          OptionUtil.printOptions(System.out, OptionUtil.sortOptions(options, ALPHABETICAL_BY_FIRST_ALIAS), 80, 2);
          return overallProblems;
        } else if (options.getPrintBashCompletion()) {
          OptionUtil.printBashCompletionOptionsScript(System.out, options.getOptionsWithoutErrorOption(), "bonc");
          return overallProblems;
        } else if (options.getHelp()) {
          System.out.println(API.getVersion());
          System.out.println("Options:");
          OptionUtil.printOptions(System.out, OptionUtil.sortOptions(options, ALPHABETICAL_BY_FIRST_ALIAS), 80, 2);
          return overallProblems;
        } else if (options.getVersion()) {
          System.out.println(API.getVersion());
          return overallProblems;
        } else {
          List<File> files = options.getRawSourceFiles();
          Main.logDebug(files.size() + " files:");
          for (File file : files) {
            Main.logDebug("\t" + file.getPath());
          }
          Problems problems = run(files, options);
          if (problems.getNumberOfErrors() > 0 && exitOnFailure) {
            System.exit(1);
          } else {
            return problems;
          }
        }
      }

      //Invalid command line parse
      overallProblems.addProblem(new InvalidArgumentsError("Invalid arguments."));
      overallProblems.printProblems(System.out);
      clParseResult.printErrorsAndWarnings(System.out);
      //TODO print usage
      if (exitOnFailure) {
        System.exit(1);
      }
      return overallProblems;

    } catch (Exception e) {
      overallProblems.addProblem(new ExceptionalError(e));
      System.out.println("Something unexpected went wrong.");
      if (isDebug()) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
      if (exitOnFailure) {
        System.exit(1);
      }
      return overallProblems;
    }
  }

  public static Problems run(final List<File> files, final BONcOptionsInterface so) {
    Problems totalProblems = new Problems("Total problems");

    List<File> filesDuplicatesRemoved = new ArrayList<File>(new HashSet<File>(files));
    
    //Timing info?
    boolean printTiming = so.getTime();
    boolean readFromStdIn = so.getReadFromStdin();

    ParsingTracker tracker = API.parse(filesDuplicatesRemoved, readFromStdIn, printTiming);
    Problems parseProblems = tracker.getErrorsAndWarnings();
    totalProblems.addProblems(parseProblems);
    if (readFromStdIn) {
      filesDuplicatesRemoved.add(null);
    }

    if (tracker.continueFromParse()) {
      Problems typeCheckProblems = API.typeCheck(tracker, so, printTiming);
      totalProblems.addProblems(typeCheckProblems);
    } else {
      tracker.setFinalMessage("Not typechecking due to parse errors.");
    }

    API.printResults(totalProblems, tracker, System.out);

    if (so.isPrintSet()) {
      File outputFile = so.isPrintOutputSet() ? so.getPrintOutput() : null;
      API.print(so.getPrint(), so.getGenClassDic(), outputFile, tracker, so.getPrintExtraWork(), printTiming);
    }

    if (so.isGraphSet()) {
      API.displayGraphs(tracker, so);
    }

    return totalProblems;
  }

}
