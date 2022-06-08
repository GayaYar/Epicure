package com.moveo.epicure.cucumber;

import com.moveo.epicure.EpicureApplication;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Suite
@IncludeEngines("cucumber")
@SpringBootTest(classes = {EpicureApplication.class, EpicureTestRunner.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@SelectClasspathResource("feature")
public class EpicureTestRunner {

}
