package com.moveo.epicure.cucumber.step;

import com.moveo.epicure.EpicureApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Suite
@IncludeEngines("cucumber")
@SpringBootTest(classes = {EpicureApplication.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@SelectClasspathResource("feature")
@CucumberContextConfiguration
public class EpicureTestRunner {

}
