Feature: Filter
    Scenario: Filter Items
        Given I have the following entries with the following name and descriptions:
            | Builder   | A pattern to incrementally build complex objects                                     |
            | Factory   | Constructs objects without specifying the exact type                                 |
            | Observer  | Allows external observers to be notified of changes internal to the observable class |
            | Singleton | There can only be one                                                                |
            | Composite | Each of these is either a base node or is composed of nodes                          |
        When I search "Builder"
        Then I should be given a list containing the Builder Object