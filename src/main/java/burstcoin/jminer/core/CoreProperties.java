package burstcoin.jminer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class CoreProperties
{
  private static final Logger LOG = LoggerFactory.getLogger(CoreProperties.class);
  private static final String STRING_LIST_PROPERTY_DELIMITER = ",";
  private static final Properties PROPS = new Properties();

  // default values
  private static final int DEFAULT_CHUNK_PART_NONCES = 320000;
  private static final int DEFAULT_RESTART_INTERVAL = 240;
  private static final int DEFAULT_PLATFORM_ID = 0;
  private static final int DEFAULT_DEVICE_ID = 0;
  private static final boolean DEFAULT_POOL_MINING = true;
  private static final boolean DEFAULT_DEV_POOL = false;
  private static final long DEFAULT_TARGET_DEADLINE = Long.MAX_VALUE;
  private static final String DEFAULT_SOLO_SERVER = "http://localhost:8125";
  private static final int DEFAULT_READ_PROGRESS_PER_ROUND = 9;
  private static final int DEFAULT_REFRESH_INTERVAL = 2000;
  private static final int DEFAULT_CONNECTION_TIMEOUT = 6000;
  private static final int DEFAULT_WINNER_RETRIES_ON_ASYNC = 4;
  private static final int DEFAULT_WINNER_RETRY_INTERVAL_IN_MS = 500;
  private static final boolean DEFAULT_SCAN_PATHS_EVERY_ROUND = false;
  private static final int DEFAULT_DEV_POOL_COMMITS_PER_ROUND = 3;
  // there seams to be a issue on checker
  private static final boolean DEFAULT_OPT_DEV_POOL = false;


  static
  {
    try
    {
      PROPS.load(new FileInputStream(System.getProperty("user.dir") + "/jminer.properties"));
    }
    catch(IOException e)
    {
      LOG.error(e.getLocalizedMessage());
    }
  }

  private static Integer readProgressPerRound;
  private static Long refreshInterval;
  private static Long connectionTimeout;
  private static Integer winnerRetriesOnAsync;
  private static Long winnerRetryIntervalInMs;
  private static Boolean scanPathsEveryRound;
  private static Integer devPoolCommitsPerRound;
  private static Boolean optDevPool;
  private static Boolean devPool;
  private static Boolean poolMining;
  private static Long targetDeadline;
  private static List<String> plotPaths;
  private static Long chunkPartNonces;
  private static Integer restartInterval;
  private static Integer deviceId;
  private static Integer platformId;
  private static String walletServer;
  private static String numericAccountId;
  private static String soloServer;
  private static String passPhrase;
  private static String poolServer;

  private CoreProperties()
  {
    // no instances
  }

  public static int getReadProgressPerRound()
  {
    if(readProgressPerRound == null)
    {
      readProgressPerRound = asInteger("readProgressPerRound", DEFAULT_READ_PROGRESS_PER_ROUND);
    }
    return readProgressPerRound;
  }

  public static long getRefreshInterval()
  {
    if(refreshInterval == null)
    {
      refreshInterval = asLong("refreshInterval", DEFAULT_REFRESH_INTERVAL);
    }
    return refreshInterval;
  }

  public static long getConnectionTimeout()
  {
    if(connectionTimeout == null)
    {
      connectionTimeout = asLong("connectionTimeout", DEFAULT_CONNECTION_TIMEOUT);
    }
    return connectionTimeout;
  }

  public static int getWinnerRetriesOnAsync()
  {
    if(winnerRetriesOnAsync == null)
    {

      winnerRetriesOnAsync = asInteger("winnerRetriesOnAsync", DEFAULT_WINNER_RETRIES_ON_ASYNC);
    }
    return winnerRetriesOnAsync;
  }

  public static long getWinnerRetryIntervalInMs()
  {
    if(winnerRetryIntervalInMs == null)
    {
      winnerRetryIntervalInMs = asLong("winnerRetryIntervalInMs", DEFAULT_WINNER_RETRY_INTERVAL_IN_MS);
    }
    return winnerRetryIntervalInMs;
  }

  public static boolean isScanPathsEveryRound()
  {
    if(scanPathsEveryRound == null)
    {
      scanPathsEveryRound = asBoolean("scanPathsEveryRound", DEFAULT_SCAN_PATHS_EVERY_ROUND);
    }
    return scanPathsEveryRound;
  }

  public static int getDevPoolCommitsPerRound()
  {
    if(devPoolCommitsPerRound == null)
    {
      devPoolCommitsPerRound = asInteger("devPoolCommitsPerRound", DEFAULT_DEV_POOL_COMMITS_PER_ROUND);
    }
    return devPoolCommitsPerRound;
  }

  public static boolean isOptDevPool()
  {
    if(optDevPool == null)
    {
      optDevPool = asBoolean("optDevPool", DEFAULT_OPT_DEV_POOL);
    }
    return optDevPool;
  }

  public static boolean isDevPool()
  {
    if(devPool == null)
    {
      devPool = asBoolean("devPool", DEFAULT_DEV_POOL);
    }
    return devPool;
  }

  public static boolean isPoolMining()
  {
    if(poolMining == null)
    {
      poolMining = asBoolean("poolMining", DEFAULT_POOL_MINING);
    }
    return poolMining;
  }

  public static long getTargetDeadline()
  {
    if(targetDeadline == null)
    {
      targetDeadline = asLong("targetDeadline", DEFAULT_TARGET_DEADLINE);
    }
    return targetDeadline;
  }

  public static String getPoolServer()
  {
    if(poolServer == null)
    {
      poolServer = asString("poolServer", null);
      if(poolServer == null)
      {
        LOG.error("Error: property 'poolServer' is required for pool-mining!");
      }
    }
    return poolServer;
  }

  public static String getWalletServer()
  {
    if(walletServer == null)
    {
      walletServer = asString("walletServer", "disabled");
      if(isPoolMining())
      {
        LOG.info("Winner feature disabled, property 'walletServer' undefined!");

      }
    }
    return walletServer.equals("disabled") ? null : walletServer; //no default, to turn winner on and off.
  }

  public static String getNumericAccountId()
  {
    if(numericAccountId == null)
    {
      numericAccountId = asString("numericAccountId", null);
      boolean poolMining = isPoolMining();
      if(poolMining && numericAccountId == null)
      {
        LOG.error("Error: property 'numericAccountId' is required for pool-mining!");
      }
    }
    return numericAccountId;
  }

  public static String getSoloServer()
  {
    if(soloServer == null)
    {
      soloServer = asString("soloServer", DEFAULT_SOLO_SERVER);
      boolean poolMining = isPoolMining();
      if(!poolMining && soloServer.equals(DEFAULT_SOLO_SERVER))
      {
        LOG.info("Default '" + DEFAULT_SOLO_SERVER + "' used for 'soloServer' property!");
      }
    }
    return !StringUtils.isEmpty(soloServer) ? soloServer : DEFAULT_SOLO_SERVER;
  }

  public static String getPassPhrase()
  {
    if(passPhrase == null)
    {
      passPhrase = asString("passPhrase", "noPassPhrase");
      boolean poolMining = isPoolMining();
      if(!poolMining && passPhrase.equals("noPassPhrase"))
      {
        LOG.error("Error: property 'passPhrase' is required for solo-mining!");
      }
    }
    return passPhrase; // we deliver "noPassPhrase", should find no plots!
  }

  public static int getPlatformId()
  {
    if(platformId == null)
    {
      platformId = asInteger("platformId", DEFAULT_PLATFORM_ID);
    }
    return platformId;
  }

  public static int getDeviceId()
  {
    if(deviceId == null)
    {
      deviceId = asInteger("deviceId", DEFAULT_DEVICE_ID);
    }
    return deviceId;
  }

  public static int getRestartInterval()
  {
    if(restartInterval == null)
    {
      restartInterval = asInteger("restartInterval", DEFAULT_RESTART_INTERVAL);
    }
    return restartInterval;
  }

  public static List<String> getPlotPaths()
  {
    if(plotPaths == null)
    {
      plotPaths = asStringList("plotPaths", new ArrayList<>());
      if(plotPaths.isEmpty())
      {
        LOG.error("Error: property 'plotPaths' required!  "); // as long as we have no scan feature
      }
    }

    return plotPaths;
  }

  public static long getChunkPartNonces()
  {
    if(chunkPartNonces == null)
    {
      chunkPartNonces = asLong("chunkPartNonces", DEFAULT_CHUNK_PART_NONCES);
    }
    return chunkPartNonces;

  }

  private static Boolean asBoolean(String key, boolean defaultValue)
  {
    String booleanProperty = PROPS.containsKey(key) ? String.valueOf(PROPS.getProperty(key)) : null;
    Boolean value = null;
    if(!StringUtils.isEmpty(booleanProperty))
    {
      try
      {
        value = Boolean.valueOf(booleanProperty);
      }
      catch(Exception e)
      {
        LOG.error("property: '" + key + "' value should be of type 'boolean'.");
      }
    }
    return value != null ? value : defaultValue;
  }

  private static int asInteger(String key, int defaultValue)
  {
    String integerProperty = PROPS.containsKey(key) ? String.valueOf(PROPS.getProperty(key)) : null;
    Integer value = null;
    if(!StringUtils.isEmpty(integerProperty))
    {
      try
      {
        value = Integer.valueOf(integerProperty);
      }
      catch(NumberFormatException e)
      {
        LOG.error("value of property: '" + key + "' should be a numeric (int) value.");
      }
    }
    return value != null ? value : defaultValue;
  }

  private static long asLong(String key, long defaultValue)
  {
    String integerProperty = PROPS.containsKey(key) ? String.valueOf(PROPS.getProperty(key)) : null;
    Long value = null;
    if(!StringUtils.isEmpty(integerProperty))
    {
      try
      {
        value = Long.valueOf(integerProperty);
      }
      catch(NumberFormatException e)
      {
        LOG.error("value of property: '" + key + "' should be a numeric (long) value.");
      }
    }
    return value != null ? value : defaultValue;
  }

  private static List<String> asStringList(String key, List<String> defaultValue)
  {
    String stringListProperty = PROPS.containsKey(key) ? String.valueOf(PROPS.getProperty(key)) : null;
    List<String> value = null;
    if(!StringUtils.isEmpty(stringListProperty))
    {
      try
      {
        value = Arrays.asList(stringListProperty.trim().split(STRING_LIST_PROPERTY_DELIMITER));
      }
      catch(NullPointerException | NumberFormatException e)
      {
        LOG.error("property: '" + key + "' value should be 'string(s)' separated by '" + STRING_LIST_PROPERTY_DELIMITER + "' (comma).");
      }
    }

    return value != null ? value : defaultValue;
  }

  private static String asString(String key, String defaultValue)
  {
    String value = PROPS.containsKey(key) ? String.valueOf(PROPS.getProperty(key)) : defaultValue;
    return StringUtils.isEmpty(value) ? defaultValue : value;
  }
}