package com.vaibhav.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/com.vaibhav.features", // Placeholder
        glue = {"com.vaibhav.stepDefinition", "com.vaibhav.hooks"},
        tags = "not @wip",
        plugin = {"com.vaibhav.tools.StepLoggerPlugin"},// Placeholder
        monochrome = true
)
public class Runner {
}
