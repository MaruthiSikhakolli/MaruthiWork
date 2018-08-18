Feature: This keyword defines the feature we are working on
Scenario: This keyword defines the feature we are working on
    Given User is on Home Page
    When User Navigate to LogIn Page
    And User enters UserName and Password
    Then Message displayed LogIn Successfully
    But The user name displayed is wrong
    
# * Keyword
#This (*) keyword is very special. This keyword defies the whole purpose of having Given, When, Then and all the other keywords. 
#Basically Cucumber doesn’t care about what Keyword you use to define test steps, all it cares about what code it needs to execute for each step.

########### Data Driven Scenarios ###############
#1. Parameterizing by giving the details directly in the step
@SmokeTest @RegressionTest 
#These are known as cucumber tags. We define these tag values in the runner file. Then only scenarios with these tags will be executed.
#We can define multiple tags for single scenario
Scenario: Successful Login with Valid Credentials
	Given User is on Home Page
	When User Navigate to LogIn Page
	And User enters "testuser_1" and "Test@123"
	Then Message displayed LogIn Successfully
	
#2. Parameterizing using Examples keyword
Scenario Outline: Successful Login with Valid Credentials
	Given User is on Home Page
	When User Navigate to LogIn Page
	And User enters "<username>" and "<password>"
	Then Message displayed LogIn Successfully
	
Examples:
    | username   | password |
    | testuser_1 | Test@153 |
    | testuser_2 | Test@153 |
    
#3. Parameterizing using DataTable
Scenario: Successful Login with Valid Credentials
	Given User is on Home Page
	When User Navigate to LogIn Page
	And User enters Credentials to LogIn
    | testuser_1 | Test@153 |
	Then Message displayed LogIn Successfully
	
#Difference between Scenario Outline & Data Table
#Scenario Outline:
#
#    This uses Example keyword to define the test data for the Scenario
#    This works for the whole test
#    Cucumber automatically run the complete test the number of times equal to the number of data in the Test Set
#
#Test Data:
#
#    No keyword is used to define the test data
#    This works only for the single step, below which it is defined
#    A separate code is need to understand the test data and then it can be run single or multiple times but again just for the single step, 
#	 not for the complete test

#Maps in Data Tables with Header
Scenario: Successful Login with Valid Credentials
	Given User is on Home Page
	When User Navigate to LogIn Page
	And User enters Credentials to LogIn using Maps
	| Username   | Password |
    | testuser_1 | Test@153 |
	Then Message displayed LogIn Successfully
	
#Maps in Data Tables with Multiple Test Data
Scenario: Successful Login with Valid Credentials
	Given User is on Home Page
	When User Navigate to LogIn Page
	And User enters Credentials to LogIn using Maps with multiple data
	| Username   | Password |
    | testuser_1 | Test@153 |
    | testuser_2 | Test@154 |
	Then Message displayed LogIn Successfully


#Map Data Tables to Class Objects //There will not be any difference for this one and previous once here. You can see the difference in the step def file
Scenario: Successful Login with Valid Credentials
	Given User is on Home Page
	When User Navigate to LogIn Page
	And User enters Credentials to LogIn using class objects
	| Username   | Password |
    | testuser_1 | Test@153 |
    | testuser_2 | Test@154 |
	Then Message displayed LogIn Successfully

#Understanding the functionality of Background keyword
Scenario: This keyword defines the feature we are working on
Background User has access to the system
	#Step-1 
	Given User is on Home Page
	#Step-2
    When User Navigate to LogIn Page
    #Step-3
    And User enters UserName and Password
    #Step-4
    Then Message displayed LogIn Successfully
#keyword is used to define steps which are common to all the tests in the feature file. 
#    For example to purchase a product, you need to do following steps:
#    Navigate to Home Page
#    Click on the LogIn link
#    Enter UserName and Password
#    Click on Submit button and should land on home page
#When we execute Background scenario, all the steps (Steps 1 to 4) defined under n will be executed.

#It is really necessary to understand the right usage of Background. As hooks as well gives similar kind of functionality and more over 
#almost all the task can be done by hooks as well. This is why it is critical to use the background at the right place in the test. 