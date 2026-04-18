package com.vaibhav.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ConfigReader {
    private static final Logger LOGGER = LogManager.getLogger(ConfigReader.class);
    public static String product;
    public static String platform;
    public static String environment;
    public static String rootDirPath;
    private static JsonNode config;

    public static void loadConfig() {
        product = System.getProperty("product");
        environment = System.getProperty("environment");
        platform = System.getProperty("platform");
        rootDirPath = System.getProperty("rootDir");

        LOGGER.info("Product: {}", product);
        LOGGER.info("Environment: {}", environment);
        LOGGER.info("Platform: {}", platform);
        LOGGER.info("rootDirPath: {}", rootDirPath);

        if (platform == null) {
            LOGGER.error("Platform is not set.");
            throw new IllegalArgumentException("Platform must be set.");
        }

        if ("web".equalsIgnoreCase(platform)) {
            if (product == null || environment == null) {
                LOGGER.error("Product and Environment must be set for web platform.");
                throw new IllegalArgumentException("Product and Environment must be set for web platform.");
            }
        } else if ("android".equalsIgnoreCase(platform)) {
            if (product == null) {
                LOGGER.error("Product, appPackage and appActivity must be set for Android platform.");
                throw new IllegalArgumentException("Product, appPackage and appActivity must be set for Android platform.");
            }
        } else if ("mweb".equalsIgnoreCase(platform)) {
            if (product == null || environment == null) {
                LOGGER.error("Product and Environment must be set for mweb platform.");
                throw new IllegalArgumentException("Product and Environment must be set for mweb platform.");
            }
        } else if ("ios".equalsIgnoreCase(platform)) {
            if (product == null || environment == null) {
                LOGGER.error("Product and Environment must be set for ios platform.");
                throw new IllegalArgumentException("Product and Environment must be set for ios platform.");
            }
        } else {
            LOGGER.error("Unsupported platform: {}", platform);
            throw new IllegalArgumentException("Unsupported platform: " + platform);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            File configFile = new File("src/test/java/com/vaibhav/utils/environments.json");

            if (!configFile.exists()) {
                LOGGER.error("Configuration file not found: {}", configFile.getAbsolutePath());
                throw new IOException("Configuration file not found.");
            }

            JsonNode root = mapper.readTree(configFile);
            config = root.path(product).path(environment);

            if (config.isMissingNode()) {
                LOGGER.error("Configuration not found for product '{}' and environment URL '{}'.", product, environment);
                throw new IllegalArgumentException("Configuration not found for the specified product and environment URL.");
            }

            if ("web".equalsIgnoreCase(platform)) {
                LOGGER.info("Config for product '{}' and environment URL '{}': {}", product, environment, config.path("baseUrl").asText());
            }

        } catch (IOException e) {
            LOGGER.error("Error reading configuration file", e);
            throw new RuntimeException("Error reading configuration file", e);
        }
    }

    public static String getConfigValue(String key) {
        if (config == null) {
            throw new IllegalStateException("Configuration has not been loaded.");
        }
        return config.path(key).asText();
    }

    public static void resetConfig() {
        config = null;
    }

    public static String getPlatform() {
        if (platform == null) {
            throw new IllegalStateException("Platform has not been loaded.");
        }
        return platform;
    }

    static void updateAppPaths(JsonNode jsonObject, String projectRoot) {
        if (jsonObject.isObject()) {
            jsonObject.fieldNames().forEachRemaining(fieldName -> {
                JsonNode childNode = jsonObject.get(fieldName);

                if (fieldName.equalsIgnoreCase("app") && childNode.isTextual()) {
                    String relativePath = childNode.asText();

                    // Print the relative path before modification
                    LOGGER.info("Relative Path: {}", relativePath);

                    String absolutePath = Paths.get(projectRoot, relativePath).toString();

                    // Print the absolute path
                    LOGGER.info("Absolute Path: {}", absolutePath);

                    // Update the JSON object with the absolute path
                    ((ObjectNode) jsonObject).put(fieldName, absolutePath);
                } else {
                    updateAppPaths(childNode, projectRoot);  // Recursively process nested objects
                }
            });
        }
    }
}