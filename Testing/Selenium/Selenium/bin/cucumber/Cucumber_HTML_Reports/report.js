$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/cucumber/sample.feature");
formatter.feature({
  "line": 1,
  "name": "This keyword defines the feature we are working on",
  "description": "",
  "id": "this-keyword-defines-the-feature-we-are-working-on",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 2,
  "name": "This keyword defines the feature we are working on",
  "description": "  \r\nBackground User has access to the system",
  "id": "this-keyword-defines-the-feature-we-are-working-on;this-keyword-defines-the-feature-we-are-working-on",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "comments": [
    {
      "line": 5,
      "value": "#keyword is used to define steps which are common to all the tests in the feature file."
    },
    {
      "line": 6,
      "value": "#    For example to purchase a product, you need to do following steps:"
    },
    {
      "line": 7,
      "value": "#    Navigate to Home Page"
    },
    {
      "line": 8,
      "value": "#    Click on the LogIn link"
    },
    {
      "line": 9,
      "value": "#    Enter UserName and Password"
    },
    {
      "line": 10,
      "value": "#    Click on Submit button"
    }
  ],
  "line": 11,
  "name": "User is on Home Page",
  "keyword": "Given "
});
formatter.step({
  "line": 12,
  "name": "User Navigate to LogIn Page",
  "keyword": "When "
});
formatter.step({
  "line": 13,
  "name": "User enters UserName and Password",
  "keyword": "And "
});
formatter.step({
  "line": 14,
  "name": "Message displayed LogIn Successfully",
  "keyword": "Then "
});
formatter.step({
  "line": 15,
  "name": "The user name displayed is wrong",
  "keyword": "But "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});