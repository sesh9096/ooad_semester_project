Feature: Custom Filter
    Scenario: Creating a Custom Filter
        Given I create a filter with the Following settings:
            | Exact Match |
        And I have the following entries with the following name and descriptions:
            | Builder            | A pattern to incrementally build complex objects                                     |
            | Factory            | Constructs objects without specifying the exact type                                 |
            | Abstract Factory   | Constructs objects without specifying the exact type                                 |
            | Observer           | Allows external observers to be notified of changes internal to the observable class |
            | Singleton          | There can only be one                                                                |
            | Composite          | Each of these is either a base node or is composed of nodes                          |
        When I search "Factory"
        Then I should be given a list containing the Factory Object