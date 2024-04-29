Feature: Insertion
    Scenario: Inserting a new entry into the db
        When I make a new Entry
        And I provide the name "Factory"
        And I provide the description "A pattern for creating objects"
        And I provide the part "n."
        And I save the entry
        Then I should be on the search page
        And I should have an Entry called "Factory"
        And it should have the description "A pattern for creating objects"
        And it should have the part "n."
