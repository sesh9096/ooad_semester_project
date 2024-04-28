Feature: Exact Filter Search
    Scenario: Searching using the Exact Filter
        Given I use the Exact Match Strategy
#        And I have the following entries:
#            | Builder            | N.| A pattern to incrementally build complex objects                                     |
#            | Factory            | N.| Constructs objects without specifying the exact type                                 |
#            | Abstract Factory   | N.| Constructs objects without specifying the exact type                                 |
#            | Observer           | N.| Allows external observers to be notified of changes internal to the observable class |
#            | Singleton          | N.| There can only be one                                                                |
#            | Composite          | N.| Each of these is either a base node or is composed of nodes                          |
        When I search "Factory"
        Then I should see the search Text "Factory"
        And I should see a list containing an Entry with the name "Factory"
