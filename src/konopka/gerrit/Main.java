package konopka.gerrit;

import konopka.util.ArgsHelper;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Martin on 29.6.2015.
 */
public class Main {

  //  private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String ARG_LOG4J = "-l";
    private static final String ARG_PROPS = "-p";

    private static boolean initLogging(List<String> arguments) {
        try {
            String log4j_file = ArgsHelper.getArgument(arguments, ARG_LOG4J);
            System.out.println("Loading properties from file: " + log4j_file + "...");
            PropertyConfigurator.configure(log4j_file);
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Properties file expected but path not specified.");
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument.");
        }
        return false;
    }

    private static Configuration initConfiguration(List<String> arguments) {
        try {
            String props_file = ArgsHelper.getArgument(arguments, ARG_PROPS);
            System.out.println("Loading configuration from file: " + props_file + "...");
            return new Configuration(props_file);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Configuration file expected but path not specified.");
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument.");
        }

        return null;
    }


    public static void main(String[] args) throws InterruptedException {

//        String log4j_file = "log4j.properties";
//        String props_file = "symbols.xml";

        boolean init_log4j = false;
        Configuration config = null;

        List<String> arguments = Collections.emptyList();
        if (args != null && args.length > 0) {
            arguments = Arrays.asList(args);
        }

        if (arguments.size() > 0) {
            init_log4j = initLogging(arguments);
        }
        if (init_log4j == false) {
            System.out.println("Loading default properties...");
            PropertyConfigurator.configure("log4j.properties");
        }



        if (arguments.size() > 0) {
            config = initConfiguration(arguments);
        }

        if (config == null) {
            System.out.println("Loading default configuration...");
            config = new Configuration();
        }

        try {
            GerritMiner miner = new GerritMiner(config);
            miner.init();
            miner.mine();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }


    }

}
